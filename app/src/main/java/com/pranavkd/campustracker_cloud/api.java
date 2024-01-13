package com.pranavkd.campustracker_cloud;

import com.pranavkd.campustracker_cloud.data.Subject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface api {
    @GET("getallsubjects")
    Call<List<Subject>> getAllSubjects();
}