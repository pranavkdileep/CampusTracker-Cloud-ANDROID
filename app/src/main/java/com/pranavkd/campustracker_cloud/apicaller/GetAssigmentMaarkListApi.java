package com.pranavkd.campustracker_cloud.apicaller;

import android.content.Context;
import android.util.Log;

import com.pranavkd.campustracker_cloud.constantsetup;
import com.pranavkd.campustracker_cloud.data.AssignmentsDateList;
import com.pranavkd.campustracker_cloud.interfaces.AssignmentData;
import com.pranavkd.campustracker_cloud.interfaces.Getassignments;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetAssigmentMaarkListApi {
    public int subjectId;
    public int assignmentNo;
    public String url;
    public List<AssignmentsDateList> assignmentsDateList;

    public GetAssigmentMaarkListApi(int subjectId,int assignmentNo, Context context) {
        this.subjectId = subjectId;
        this.assignmentNo = assignmentNo;
        constantsetup constantsetu = new constantsetup(context);
        this.url=constantsetu.getURL();
    }
    public void getAssignmentMarkList(AssignmentData assignmentData) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Getassignments assignmentDataInstance = retrofit.create(Getassignments.class);
        retrofit2.Call<List<AssignmentsDateList>> call = assignmentDataInstance.getassignment(subjectId,assignmentNo);
        call.enqueue(new retrofit2.Callback<List<AssignmentsDateList>>() {
            @Override
            public void onResponse(retrofit2.Call<List<AssignmentsDateList>> call, retrofit2.Response<List<AssignmentsDateList>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }
                assignmentsDateList = response.body();
                assignmentData.onAssignmentDataLoaded(assignmentsDateList);
            }
            @Override
            public void onFailure(retrofit2.Call<List<AssignmentsDateList>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }
}
