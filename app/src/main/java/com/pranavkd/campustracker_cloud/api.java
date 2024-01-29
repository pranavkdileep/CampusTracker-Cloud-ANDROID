package com.pranavkd.campustracker_cloud;

import com.pranavkd.campustracker_cloud.data.Subject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface api {
    @GET("getallsubjects/{faculty_id}")
    Call<List<Subject>> getAllSubjects(@Header("Authorization") String authHeader, @Path("faculty_id") int faculty_id);
}