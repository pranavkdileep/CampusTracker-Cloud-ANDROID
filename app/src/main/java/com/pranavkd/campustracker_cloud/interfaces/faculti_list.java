package com.pranavkd.campustracker_cloud.interfaces;

import com.pranavkd.campustracker_cloud.data.PerfomanceStudents;
import com.pranavkd.campustracker_cloud.data.faculti;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface faculti_list {


        @GET("getallfaculties")
        Call<List<faculti>> getallfaculties(@Header("Authorization") String authHeader);

}
