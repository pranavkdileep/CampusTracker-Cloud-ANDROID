package com.pranavkd.campustracker_cloud;
import okhttp3.MultipartBody;
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
import com.pranavkd.campustracker_cloud.apicaller.GetinternalsApi;
import com.pranavkd.campustracker_cloud.apicaller.getPerfomance;
import com.pranavkd.campustracker_cloud.data.BulkAssignment;
import com.pranavkd.campustracker_cloud.data.BulkInternals;
import com.pranavkd.campustracker_cloud.data.InternallistData;
import com.pranavkd.campustracker_cloud.data.Subject;
import com.pranavkd.campustracker_cloud.data.BulkAttendance;
import com.pranavkd.campustracker_cloud.interfaces.ApiHelperLoaded;
import com.pranavkd.campustracker_cloud.interfaces.Loging;
import com.pranavkd.campustracker_cloud.interfaces.OnApiLoaded;

import java.io.File;
import java.io.IOException;
import java.util.List;

interface Api {
    @GET("getallsubjects")
    Call<List<Subject>> getAllSubjects();
}

public class Apihelper {
    final String url;
    final String key;
    GetinternalsApi getinternalsApi;
    OnApiLoaded onApiLoadedListener;
    List<Subject> subjects;
    List<InternallistData> internallistDataList;
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
    public Apihelper()
    {
        url = null;
        key = null;
    }



    public void addSubject(String subjectNameText,int faculty_id, Context context,ApiHelperLoaded apiHelperLoaded) {
        // Define the Thread 't' with a Runnable that performs the network operation
        Thread t = new Thread(() -> {
            okhttp3.Response response = null;
            String responseData = null;

            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("subject_name", subjectNameText);
                jsonObject.put("facultie_id", faculty_id);
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
                    apiHelperLoaded.onApiHelperLoaded();
                    responseData = response.body().string();
                    Log.e("response", responseData);
                } else {
                    Log.e("response", response.toString());
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


    public void loadSubjects(Context context,int faculty_id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api apiinterface = retrofit.create(api.class);
        constantsetup db = new constantsetup(context);
        String authHeader = "Bearer "+db.getKey();
        Call<List<Subject>> call = apiinterface.getAllSubjects(authHeader,faculty_id);
        call.enqueue(new Callback<List<Subject>>() {
            @Override
            public void onResponse(Call<List<Subject>> call, Response<List<Subject>> response1) {
                if (!response1.isSuccessful()) {
                    System.out.println("Code: " + response1.code());
                    return;
                }
                subjects = response1.body();
                main.updateSubjects(subjects);
            }

            @Override
            public void onFailure(Call<List<Subject>> call, Throwable t) {
                t.printStackTrace();
            }
        });


    }

    public void deleteSubject(int subjectId, Context context,int faculty_id) {
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
                    loadSubjects(context, faculty_id);
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
    public void add_student(String student_name, int subject_id, Context context,ApiHelperLoaded apiHelperLoaded) {
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
                    apiHelperLoaded.onApiHelperLoaded();
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

    public void updateInternal(int attId, int newinternalmark, int subject_id, int internalno, OnApiLoaded onApiLoadedListener) {
        Thread t = new Thread(() -> {
            okhttp3.Response response = null;
            String responseData = null;

            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("internal_id", attId);
                jsonObject.put("marks_obtained", newinternalmark);
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(url + "updateinternal")
                        .post(body)
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer " + key)
                        .addHeader("Content-Type", "application/json")
                        .build();

                response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    responseData = response.body().string();
                    getinternalsApi = new GetinternalsApi(subject_id, internalno, new OnApiLoaded() {
                        @Override
                        public void onApiLoaded(List<InternallistData> internallistData) {
                            internallistDataList = internallistData;
                            onApiLoadedListener.onApiLoaded(internallistDataList);
                        }
                    });
                    internallistDataList = getinternalsApi.getInternals();
                } else {
                    // Handle unsuccessful response
                }
            } catch (Exception e) {
                // Handle exception
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
    public void addBulkInternals(int subject_id, int internalNo, int max_mark, List<BulkInternals> bulkInternals, ApiHelperLoaded apiHelperLoaded) {
        Thread t = new Thread(()->{
        try {
            okhttp3.Response response = null;
            String responseData = null;
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("subject_id", subject_id);
            jsonObject.put("internal_number", internalNo);
            jsonObject.put("max_marks", max_mark);
            Gson gson = new Gson();
            String bulkInternalsJson = gson.toJson(bulkInternals);
            JSONArray bulkInternalsJsonArray = new JSONArray(bulkInternalsJson);
            jsonObject.put("bulk_internal", bulkInternalsJsonArray);
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Log.e("json", jsonObject.toString());
            Request request = new Request.Builder()
                    .url(url + "addbulkinternal")
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", "Bearer " + key)
                    .addHeader("Content-Type", "application/json")
                    .build();

            response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                responseData = response.body().string();
                apiHelperLoaded.onApiHelperLoaded();
            } else {
                Log.e("response", response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        });
        t.start();
    }

    public void updateAssignment(int attId, int i, ApiHelperLoaded assignmentUpdated) {
        Thread t = new Thread(() -> {
            okhttp3.Response response = null;
            String responseData = null;

            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("assignment_id", attId);
                jsonObject.put("marks_obtained", i);
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(url + "updateassignment")
                        .post(body)
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer " + key)
                        .addHeader("Content-Type", "application/json")
                        .build();

                response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    responseData = response.body().string();
                    assignmentUpdated.onApiHelperLoaded();
                } else {
                    // Handle unsuccessful response
                }
            } catch (Exception e) {
                // Handle exception
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        });
        t.start();
    }

    public void addBulkAssignment(int subjectidIntInt, int assignemtIntInt, int maxMarkIntInt, List<BulkAssignment> bulkAssignmentList, ApiHelperLoaded bulkAssignmentMarksAdded) {
        Thread t = new Thread(() -> {
            okhttp3.Response response = null;
            String responseData = null;

            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("subject_id", subjectidIntInt);
                jsonObject.put("assignment_number", assignemtIntInt);
                jsonObject.put("max_marks", maxMarkIntInt);
                Gson gson = new Gson();
                String bulkAssignmentJson = gson.toJson(bulkAssignmentList);
                JSONArray bulkAssignmentJsonArray = new JSONArray(bulkAssignmentJson);
                jsonObject.put("bulk_assignment", bulkAssignmentJsonArray);
                Log.e("json", jsonObject.toString());
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(url + "addbulkassignment")
                        .post(body)
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer " + key)
                        .addHeader("Content-Type", "application/json")
                        .build();

                response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    responseData = response.body().string();
                    bulkAssignmentMarksAdded.onApiHelperLoaded();
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
    }


    public void uploadStudentsListXls(int subjectId, File file, ApiHelperLoaded uploaded) {
        Thread t = new Thread(() -> {
            okhttp3.Response response = null;
            String responseData = null;
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))
                        .build();
                Log.e("json", requestBody.toString());
                Request request = new Request.Builder()
                        .url(url + "uploadstudentslist"+"/"+subjectId)
                        .post(requestBody)
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer " + key)
                        .addHeader("Content-Type", "multipart/form-data")
                        .build();

                response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    responseData = response.body().string();
                    uploaded.onApiHelperLoaded();
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
    }

    public void login(String url, String key, int faculity, String password, Loging loging) {
        Thread t = new Thread(() -> {
            okhttp3.Response response = null;
            String responseData = null;
            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("facultie_id", faculity);
                jsonObject.put("facultie_password", password);
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(url + "login")
                        .post(body)
                        .addHeader("accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .build();
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    responseData = response.body().string();
                    JSONObject jsonObject1 = new JSONObject(responseData);
                    if(jsonObject1.getString("login").equals("true"))
                    {
                        loging.login(true);
                    }
                    else
                    {
                        loging.login(false);
                    }
                } else {
                    Log.e("response", response.body().toString());
                    loging.login(false);
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
    }
    public void adminauth(String url, String key, int faculity, String password, Loging loging) {
        Thread t = new Thread(() -> {
            okhttp3.Response response = null;
            String responseData = null;
            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("admin_password", password);
                RequestBody body = RequestBody.create(JSON, jsonObject.toString());
                Request request = new Request.Builder()
                        .url(url + "adminlogin")
                        .post(body)
                        .addHeader("accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .build();
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    responseData = response.body().string();
                    JSONObject jsonObject1 = new JSONObject(responseData);
                    if(jsonObject1.getString("admin").equals("true"))
                    {
                        loging.login(true);
                    }
                    else
                    {
                        loging.login(false);
                    }
                } else {
                    Log.e("response", response.body().toString());
                    loging.login(false);
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
    }
}





