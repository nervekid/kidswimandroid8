package com.kid.kidswim.ui.insertgroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InsertGroupViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public InsertGroupViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("加入分组");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
