package com.kid.kidswim.ui.addgroupdetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddGroupDetailsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddGroupDetailsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("学员加入分组");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
