package com.pranavkd.campustracker_cloud.data;

public class BulkAssignment {
    private int student_id;
    private int marks_obtained;
    public BulkAssignment(int student_id, int marks_obtained) {
        this.student_id = student_id;
        this.marks_obtained = marks_obtained;
    }
    public int getStudent_id() {
        return student_id;
    }
    public int getMark_obtained() {
        return marks_obtained;
    }
    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
    public void setMark_obtained(int mark_obtained) {
        this.marks_obtained = mark_obtained;
    }
}
