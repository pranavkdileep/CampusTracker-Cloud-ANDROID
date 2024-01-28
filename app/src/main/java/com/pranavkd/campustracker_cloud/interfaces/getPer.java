package com.pranavkd.campustracker_cloud.interfaces;

import com.pranavkd.campustracker_cloud.data.PerfomanceStudents;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface getPer {
    @GET("getperformance/{subject_id}")
    Call<List<PerfomanceStudents>> getPerfomance(@Path("subject_id") int subject_id);
    @GET("getperformance_dumy/{subject_id}")
    Call<List<PerfomanceStudents>> getPerfomancedumy(@Path("subject_id") int subject_id);
}
