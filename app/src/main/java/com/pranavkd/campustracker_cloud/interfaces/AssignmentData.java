package com.pranavkd.campustracker_cloud.interfaces;

import com.pranavkd.campustracker_cloud.data.AssignmentsDateList;

import java.util.List;

import retrofit2.Call;

public interface AssignmentData {
    void onAssignmentDataLoaded(List<AssignmentsDateList> assignmentsDateList);

}
