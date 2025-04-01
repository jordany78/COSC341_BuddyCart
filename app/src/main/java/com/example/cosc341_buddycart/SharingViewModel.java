package com.example.cosc341_buddycart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.HashMap;
import java.util.Map;

public class SharingViewModel extends ViewModel {

    private final Map<String, MutableLiveData<Object>> dataMap = new HashMap<>();

    // Data setter
    // <T> = generic type
    public <T> void setData(String key, T data) {

        if (!dataMap.containsKey(key)) { // If var. doesn't exist, create

            dataMap.put(key, new MutableLiveData<>());
        }

        dataMap.get(key).setValue(data);
    }

    // Data getter
    public <T> LiveData<T> getData(String key) {

        if (!dataMap.containsKey(key)) { // If var. doesn't exist, create (but no value assigned)

            dataMap.put(key, new MutableLiveData<>());

        }

        return (LiveData<T>) dataMap.get(key);
    }
}

