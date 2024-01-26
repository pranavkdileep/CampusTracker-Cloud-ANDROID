package com.pranavkd.campustracker_cloud.interfaces;

import com.pranavkd.campustracker_cloud.data.InternallistData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetInternals {
    @GET("getinternal/{subject_id}/{internal_no}")
    Call<List<InternallistData>> getInternals(@Path("subject_id") int subject_id, @Path("internal_no") int internal_no);
}
