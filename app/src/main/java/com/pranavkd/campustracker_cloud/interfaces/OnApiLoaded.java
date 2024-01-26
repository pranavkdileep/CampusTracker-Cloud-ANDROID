package com.pranavkd.campustracker_cloud.interfaces;

import com.pranavkd.campustracker_cloud.data.InternallistData;

import java.util.List;

public interface OnApiLoaded {
    void onApiLoaded(List<InternallistData> internallistData);
}
