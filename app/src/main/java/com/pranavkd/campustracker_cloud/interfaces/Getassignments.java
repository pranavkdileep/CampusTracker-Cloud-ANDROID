package com.pranavkd.campustracker_cloud.interfaces;

import com.pranavkd.campustracker_cloud.data.AssignmentsDateList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Getassignments {
    @GET("getassignment/{subject_id}/{assignment_no}")
    Call<List<AssignmentsDateList>> getassignment(@Path("subject_id") int subject_id, @Path("assignment_no") int assignment_no);
}
