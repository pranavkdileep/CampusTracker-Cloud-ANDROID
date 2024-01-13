package com.pranavkd.campustracker_cloud.data;

public class PerfomanceStudents {
    private int student_id;
    private String student_name;
    private int total_lectures;
    private int lectures_present;
    private float attendance_percentage;
    private float average_internal_marks;

    public PerfomanceStudents(int student_id,String student_name,int total_lectures,int lectures_present,float attendance_percentage,float average_internal_marks)
    {
        this.student_id=student_id;
        this.student_name=student_name;
        this.total_lectures=total_lectures;
        this.lectures_present=lectures_present;
        this.attendance_percentage=attendance_percentage;
        this.average_internal_marks=average_internal_marks;
    }
    public int getStudentId() {
        return student_id;
    }
    public String getStudentName() {
        return student_name;
    }
    public int getTotalLectures() {
        return total_lectures;
    }
    public int getLecturesPresent() {
        return lectures_present;
    }
    public float getAttendancePercentage() {
        return attendance_percentage;
    }
    public float getAverageInternalMarks() {
        return average_internal_marks;
    }

}
