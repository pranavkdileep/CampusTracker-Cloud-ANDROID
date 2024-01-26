package com.pranavkd.campustracker_cloud.data;

public class BulkInternals {
    private int student_id;
    private int marks_obtained;
    public BulkInternals(int student_id, int marks_obtained)
    {
        this.student_id=student_id;
        this.marks_obtained=marks_obtained;
    }
    public int getStudentId() {
        return student_id;
    }
    public int getMarksObtained() {
        return marks_obtained;
    }
    public void setStudentId(int student_id) {
        this.student_id = student_id;
    }
    public void setMarksObtained(int marks_obtained) {
        this.marks_obtained = marks_obtained;
    }
}
