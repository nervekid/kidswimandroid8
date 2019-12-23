package com.kid.kidswim.ui.addgroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddGroupViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public AddGroupViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("新建分组");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
