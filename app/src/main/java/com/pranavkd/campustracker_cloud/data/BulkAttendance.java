package com.pranavkd.campustracker_cloud.data;

public class BulkAttendance {
    private final int student_id;
    private Boolean present;
    public BulkAttendance(int student_id, Boolean present)
    {
        this.student_id=student_id;
        this.present=present;
    }
    public int getStudentId() {
        return student_id;
    }
    public Boolean getPresent() {
        return present;
    }

    public void setPresent(boolean b) {
        this.present = b;
    }
}
