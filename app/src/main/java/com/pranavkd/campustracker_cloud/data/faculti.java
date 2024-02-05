package com.pranavkd.campustracker_cloud.data;

public class faculti {
    private String facultie_name;
    private int facultie_id;
    private String facultie_password;

    public faculti(String facultie_name, int facultie_id, String facultie_password) {
        this.facultie_name = facultie_name;
        this.facultie_id = facultie_id;
        this.facultie_password = facultie_password;
    }

    public String getName() {
        return facultie_name;
    }
    public int getFaculti_id() {
        return facultie_id;
    }
    public String getPassowd() {
        return facultie_password;
    }
}
