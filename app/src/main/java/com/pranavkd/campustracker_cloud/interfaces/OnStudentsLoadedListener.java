package com.pranavkd.campustracker_cloud.interfaces;

import com.pranavkd.campustracker_cloud.data.PerfomanceStudents;

import java.util.List;

public interface OnStudentsLoadedListener {
    void onStudentsLoaded(List<PerfomanceStudents> students);
}