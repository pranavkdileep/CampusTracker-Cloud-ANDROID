package com.pranavkd.campustracker_cloud.data;

public class Subject {
    private int subject_id;
    private String subject_name;
    public Subject(int subject_id,String subject_name)
    {
        this.subject_id=subject_id;
        this.subject_name=subject_name;
    }
    public int getSubjectId() {
        return subject_id;
    }

    public String getSubjectName() {
        return subject_name;
    }
}
