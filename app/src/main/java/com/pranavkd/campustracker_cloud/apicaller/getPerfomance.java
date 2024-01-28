package com.pranavkd.campustracker_cloud.apicaller;

import android.content.Context;
import android.widget.Toast;

import com.pranavkd.campustracker_cloud.MarkBulkAttendanve;
import com.pranavkd.campustracker_cloud.StudentFragment;
import com.pranavkd.campustracker_cloud.constantsetup;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.pranavkd.campustracker_cloud.interfaces.OnStudentsLoadedListener;
import com.pranavkd.campustracker_cloud.interfaces.getPer;
import com.pranavkd.campustracker_cloud.data.PerfomanceStudents;

import java.util.List;

public class getPerfomance {
    private static  String url;
    private StudentFragment studentFragment;
    MarkBulkAttendanve markBulkAttendanve;
    public getPerfomance(Context context, StudentFragment studentFragment)
    {
        constantsetup constantsetu = new constantsetup(context);
        this.url=constantsetu.getURL();

        this.studentFragment = studentFragment;
    }
    public getPerfomance(Context context)
    {
        constantsetup constantsetu = new constantsetup(context);
        this.url=constantsetu.getURL();
    }
    public getPerfomance(Context context, MarkBulkAttendanve markBulkAttendanve)
    {
        constantsetup constantsetu = new constantsetup(context);
        this.url=constantsetu.getURL();

        this.markBulkAttendanve = markBulkAttendanve;
    }
    public void getData(int subject_id, Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        getPer getPerInstance = retrofit.create(getPer.class);

        Call<List<PerfomanceStudents>> call = getPerInstance.getPerfomance(subject_id);

        call.enqueue(new Callback<List<PerfomanceStudents>>() {
            @Override
            public void onResponse(Call<List<PerfomanceStudents>> call, Response<List<PerfomanceStudents>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }

                List<PerfomanceStudents> perfomanceStudents = response.body();

                if (studentFragment != null) {
                    studentFragment.updateAdapter(perfomanceStudents);
                }
            }

            @Override
            public void onFailure(Call<List<PerfomanceStudents>> call, Throwable t) {
                t.printStackTrace();
            }
        });
}
    public void getStudentList(int subject_id, OnStudentsLoadedListener listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getPer getPerInstance = retrofit.create(getPer.class);

        Call<List<PerfomanceStudents>> call = getPerInstance.getPerfomancedumy(subject_id);

        call.enqueue(new Callback<List<PerfomanceStudents>>() {
            @Override
            public void onResponse(Call<List<PerfomanceStudents>> call, Response<List<PerfomanceStudents>> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                List<PerfomanceStudents> perfomanceStudents = response.body();
                if(markBulkAttendanve!=null) {
                    markBulkAttendanve.updateList(perfomanceStudents);
                }
                if (listener != null) {
                    listener.onStudentsLoaded(perfomanceStudents);
                }
            }

            @Override
            public void onFailure(Call<List<PerfomanceStudents>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}