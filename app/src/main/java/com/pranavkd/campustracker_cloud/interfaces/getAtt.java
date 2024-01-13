package com.pranavkd.campustracker_cloud.interfaces;

import com.pranavkd.campustracker_cloud.data.PerfomanceStudents;
import com.pranavkd.campustracker_cloud.data.studentAttendance;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface getAtt {
    @GET("getattendance/{student_id}")
    Call<List<studentAttendance>> getAttendance(@Path("student_id") int student_id);
}
