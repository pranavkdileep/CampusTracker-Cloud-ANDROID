package com.pranavkd.campustracker_cloud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SubjectActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    ImageView downloadButton;
    public int subjectId;
    public Bundle args = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        subjectId = getIntent().getIntExtra("subjectId", 0);
        String subjectName = getIntent().getStringExtra("subjectName");
        setTitle(subjectName);
        TextView textView = findViewById(R.id.titleTextView);
        textView.setText(subjectId + " " + subjectName);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        StudentFragment studentFragment = new StudentFragment(subjectId);
        args = new Bundle();
        args.putInt("subjectId", subjectId);
        downloadButton = findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubjectActivity.this, Download.class);
                intent.putExtra("subjectId", subjectId);
                startActivity(intent);
            }
        });
        studentFragment.setArguments(args);



        setupViewPager(viewPager);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Student");
                    break;
                case 1:
                    tab.setText("Attendance");
                    break;
                case 2:
                    tab.setText("Internal");
                    break;
                case 3:
                    tab.setText("Assignment");
                    break;

            }
        }).attach();
    }
    private void setupViewPager(ViewPager2 viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
    }

    public void loadper() {

    }

    private class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(AppCompatActivity activity) {
            super(activity);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new StudentFragment(subjectId);
                case 1:
                    AttendanceFragment attendanceFragment = new AttendanceFragment();
                    attendanceFragment.setArguments(args);
                    return attendanceFragment;
                case 2:
                    InternalFragment internalFragment = new InternalFragment();
                    internalFragment.setArguments(args);
                    return internalFragment;
                case 3:
                    AssignmentFragment assignmentFragment = new AssignmentFragment();
                    assignmentFragment.setArguments(args);
                    return assignmentFragment;
                default:
                    return new Fragment(); // This case should never happen
            }
        }

        @Override
        public int getItemCount() {
            return 4; // Since we have 5 tabs
        }
    }
}