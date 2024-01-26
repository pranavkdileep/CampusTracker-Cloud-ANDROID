package com.pranavkd.campustracker_cloud.data;

public class AssignmentsDateList {
    private int student_id;
    private String student_name;
    private int marks_obtained;
    private int max_marks;
    private int assignment_id;
    AssignmentsDateList(int student_id,String student_name,int marks_obtained,int max_marks,int assignment_id)
    {
        this.student_id = student_id;
        this.student_name = student_name;
        this.marks_obtained = marks_obtained;
        this.max_marks = max_marks;
        this.assignment_id = assignment_id;
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
    public int getAssignment_id()
    {
        return assignment_id;
    }
}
