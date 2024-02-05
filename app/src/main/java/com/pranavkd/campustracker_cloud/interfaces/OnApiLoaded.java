package com.pranavkd.campustracker_cloud.interfaces;

import com.pranavkd.campustracker_cloud.data.InternallistData;
import com.pranavkd.campustracker_cloud.data.faculti;

import java.util.List;

public interface OnApiLoaded {
    void onApiLoaded(List<InternallistData> internallistData);
    void onFacultiLoaded(List<faculti> faculti);
}
