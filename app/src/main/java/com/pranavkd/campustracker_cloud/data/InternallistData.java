package com.pranavkd.campustracker_cloud.data;

public class InternallistData {
    private int student_id;
    private String student_name;
    private int marks_obtained;
    private int max_marks;
    private int internal_id;
    InternallistData(int student_id,String student_name,int marks_obtained,int max_marks,int internal_id)
    {
        this.student_id = student_id;
        this.student_name = student_name;
        this.marks_obtained = marks_obtained;
        this.max_marks = max_marks;
        this.internal_id = internal_id;
    }
    public int getStudent_id()
    {
        return student_id;
    }
    public String getStudent_name()
    {
        return student_name;
    }
    public int getMarks_obtained()
    {
        return marks_obtained;
    }
    public int getMax_marks()
    {
        return max_marks;
    }
    public int getInternal_id()
    {
        return internal_id;
    }
}
