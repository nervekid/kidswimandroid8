package com.kid.kidswim.ui.rollcall;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RollCallViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public RollCallViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("课程点名");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
