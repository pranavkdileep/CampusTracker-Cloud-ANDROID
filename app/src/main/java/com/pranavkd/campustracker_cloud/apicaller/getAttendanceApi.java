package com.pranavkd.campustracker_cloud.apicaller;

import android.content.Context;
import android.widget.Toast;

import com.pranavkd.campustracker_cloud.AttendanceFragment;
import com.pranavkd.campustracker_cloud.constantsetup;
import com.pranavkd.campustracker_cloud.data.studentAttendance;
import com.pranavkd.campustracker_cloud.interfaces.getAtt;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class getAttendanceApi {
    private static  String url;
    private int studentId;
    private AttendanceFragment attendanceFragment;
    public getAttendanceApi(int id,Context context,AttendanceFragment attendanceFragment)
    {
        constantsetup constantsetu = new constantsetup(context);
        this.url=constantsetu.getURL();
        this.studentId = id;
        this.attendanceFragment = attendanceFragment;
    }

    List<studentAttendance> studentAttendanceList;


    public List<studentAttendance> getAttendance(int id, Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getAtt getAttInstance = retrofit.create(getAtt.class);
        Call<List<studentAttendance>> call = getAttInstance.getAttendance(id);
        call.enqueue(new Callback<List<studentAttendance>>() {
            @Override
            public void onResponse(Call<List<studentAttendance>> call, Response<List<studentAttendance>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }
                studentAttendanceList = response.body();
                attendanceFragment.updateList(studentAttendanceList);
            }

            @Override
            public void onFailure(Call<List<studentAttendance>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        return studentAttendanceList;
    }
}
