package com.pranavkd.campustracker_cloud;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;
import com.pranavkd.campustracker_cloud.apicaller.getPerfomance;
import com.pranavkd.campustracker_cloud.data.PerfomanceStudents;
import com.pranavkd.campustracker_cloud.data.Subject;
import com.pranavkd.campustracker_cloud.data.BulkAttendance;
import com.pranavkd.campustracker_cloud.data.studentAttendance;

import java.util.List;

interface Api {
    @GET("getallsubjects")
    Call<List<Subject>> getAllSubjects();
}

public class Apihelper {
    final String url;
    final String key;
    List<Subject> subjects;
    MainActivity main;
    private String responseData;
    Response response;

    public Apihelper(Context context, MainActivity main) {
        constantsetup constantsetu = new constantsetup(context);
        url = constantsetu.getURL();
        key = constantsetu.getKey();
        this.main = main;
    }
    public Apihelper(Context context) {
        constantsetup constantsetu = new constantsetup(context);
        url = constantsetu.getURL();
        key = constantsetu.getKey();
    }



    public void addSubject(String subjectNameText, Context context) {
        // Define the Thread 't' with a Runnable that performs the network operation
        Thread t = new Thread(() -> {
            okhttp3.Response response = null;
            String responseData = null;

            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("subject_name", subjectNameText);
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(url + "addsubject")
                        .post(body)
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer " + key)
                        .addHeader("Content-Type", "application/json")
                        .build();

                response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    responseData = response.body().string();
                    loadSubjects(context);
                } else {
                }
                Toast.makeText(context, "responseData", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


    }
    public void addBulkAttantance(int subject_id, String date, List<BulkAttendance> bulkAttendance) {

        Thread t = new Thread(() -> {
            okhttp3.Response response = null;
            String responseData = null;

            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("subject_id", subject_id);
                jsonObject.put("date", date);
                Gson gson = new Gson();
                String bulkAttendanceJson = gson.toJson(bulkAttendance);
                JSONArray bulkAttendanceJsonArray = new JSONArray(bulkAttendanceJson);
                jsonObject.put("bulk_attendance", bulkAttendanceJsonArray);
                Log.e("json", jsonObject.toString());
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(url + "addbulkattendance")
                        .post(body)
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer " + key)
                        .addHeader("Content-Type", "application/json")
                        .build();

                response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    responseData = response.body().string();
                } else {
                    Log.d("response", response.toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public void loadSubjects(Context context) {
        Api api = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Api.class);

        api.getAllSubjects().enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                subjects = response.body();
                try {
                    if(main!=null) {
                        main.updateSubjects(subjects);
                    }
                } catch (Exception e) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {
                // Handle failure
            }
        });


    }

    public void deleteSubject(int subjectId, Context context) {
        Toast.makeText(context, "delete" + subjectId, Toast.LENGTH_SHORT).show();
        Thread t = new Thread(() -> {
            okhttp3.Response response = null;
            String responseData = null;

            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("subject_id", subjectId);
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(url + "removesubject")
                        .post(body)
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer " + key)
                        .addHeader("Content-Type", "application/json")
                        .build();

                response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    responseData = response.body().string();
                    loadSubjects(context);
                } else {
                }
            } catch (Exception e) {
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public void add_student(String student_name, int subject_id, Context context) {
        // Define the Thread 't' with a Runnable that performs the network operation
        Thread t = new Thread(() -> {
            okhttp3.Response response = null;
            String responseData = null;

            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("student_name", student_name);
                jsonObject.put("subject_id", subject_id);
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(url + "addstudent")
                        .post(body)
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer " + key)
                        .addHeader("Content-Type", "application/json")
                        .build();

                response = client.newCall(request).execute();
                getPerfomance getPerfomance = new getPerfomance(context);
                getPerfomance.getData(subject_id, context);

                if (response.isSuccessful()) {
                    responseData = response.body().string();
                    loadSubjects(context);
                } else {
                }
            } catch (Exception e) {
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }



    }
    public void updateAttendance(int attendanceId,boolean is_present)
    {
        Thread t = new Thread(() -> {
            okhttp3.Response response = null;
            String responseData = null;

            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("attendance_id", attendanceId);
                jsonObject.put("present", is_present);
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(url + "updateattendance")
                        .post(body)
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer " + key)
                        .addHeader("Content-Type", "application/json")
                        .build();

                response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    responseData = response.body().string();
                } else {
                }
            } catch (Exception e) {
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void delete_student(int i, Context context,int subjectId) {
        Thread t = new Thread(() -> {
            okhttp3.Response response = null;
            String responseData = null;

            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("student_id", i);
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(url + "removestudent")
                        .post(body)
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer " + key)
                        .addHeader("Content-Type", "application/json")
                        .build();

                response = client.newCall(request).execute();


                if (response.isSuccessful()) {
                    responseData = response.body().string();
                    getPerfomance getPerfomance = new getPerfomance(context);
                    getPerfomance.getData(subjectId, context);
                } else {
                }
                Toast.makeText(context, "responseData", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    List<studentAttendance> studentAttendanceList;


}
