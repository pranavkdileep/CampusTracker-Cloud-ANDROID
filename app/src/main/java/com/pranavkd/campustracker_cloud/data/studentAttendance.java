package com.pranavkd.campustracker_cloud.data;

public class studentAttendance {
    private int studentId;
    private String studentName;
    private String date;
    private int AttandanceId;
    private Boolean present;

    public studentAttendance(int studentId, String studentName, String date, int AttandanceId, Boolean present) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.date = date;
        this.AttandanceId = AttandanceId;
        this.present = present;
    }

    public int getStudentId() {
        return studentId;
    }
    public String getStudentName() {
        return studentName;
    }
    public String getDate() {
        return date;
    }
    public int getAttandanceId() {
        return AttandanceId;
    }
    public Boolean getPresent() {
        return present;
    }
}
