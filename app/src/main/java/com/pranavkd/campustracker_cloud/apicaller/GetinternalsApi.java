package com.pranavkd.campustracker_cloud.apicaller;

import android.content.Context;

import com.pranavkd.campustracker_cloud.InternalFragment;
import com.pranavkd.campustracker_cloud.constantsetup;
import com.pranavkd.campustracker_cloud.data.InternallistData;
import com.pranavkd.campustracker_cloud.interfaces.GetInternals;
import com.pranavkd.campustracker_cloud.interfaces.OnApiLoaded;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetinternalsApi {
    public int subjectId;
    public int internalNo;
    public Context context;
    public InternalFragment internalFragment;
    public List<InternallistData> internallistDataList;
    public String url;
    OnApiLoaded onApiLoadedListener;
    public GetinternalsApi(int subjectId,int internalNo, Context context, InternalFragment internalFragment) {
        constantsetup constantsetu = new constantsetup(context);
        this.url=constantsetu.getURL();
        this.subjectId = subjectId;
        this.context = context;
        this.internalFragment = internalFragment;
        this.internalNo = internalNo;
    }
    public GetinternalsApi(int subjectId,int internalNo,OnApiLoaded onApiLoadedListener) {
        constantsetup constantsetu = new constantsetup(context);
        this.url=constantsetu.getURL();
        this.subjectId = subjectId;
        this.internalNo = internalNo;
        this.onApiLoadedListener = onApiLoadedListener;
    }

    public List<InternallistData> getInternals(OnApiLoaded onApiLoadedListener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetInternals getInternalsInstance = retrofit.create(GetInternals.class);
        Call<List<InternallistData>> call = getInternalsInstance.getInternals(subjectId,internalNo);
        call.enqueue(new Callback<List<InternallistData>>() {
            @Override
            public void onResponse(Call<List<InternallistData>> call, Response<List<InternallistData>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }
                internallistDataList = response.body();
                onApiLoadedListener.onApiLoaded(internallistDataList);
            }

            @Override
            public void onFailure(Call<List<InternallistData>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        return internallistDataList;
    }
    public List<InternallistData> getInternals() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetInternals getInternalsInstance = retrofit.create(GetInternals.class);
        Call<List<InternallistData>> call = getInternalsInstance.getInternals(subjectId,internalNo);
        call.enqueue(new Callback<List<InternallistData>>() {
            @Override
            public void onResponse(Call<List<InternallistData>> call, Response<List<InternallistData>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }
                internallistDataList = response.body();
                onApiLoadedListener.onApiLoaded(internallistDataList);
            }

            @Override
            public void onFailure(Call<List<InternallistData>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        return internallistDataList;
    }


}
