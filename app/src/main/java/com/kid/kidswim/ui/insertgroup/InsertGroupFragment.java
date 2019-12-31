package com.kid.kidswim.ui.insertgroup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.kid.kidswim.R;
import com.kid.kidswim.ui.addgroup.AddGroupViewModel;

import org.greenrobot.eventbus.EventBus;

public class InsertGroupFragment extends Fragment {

    private InsertGroupViewModel insertGroupViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        insertGroupViewModel = ViewModelProviders.of(this).get(InsertGroupViewModel.class);
        View root = inflater.inflate(R.layout.fragment_insert_group, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //在此尝试获取SharedPreferences对象信息
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginUserToken", Context.MODE_PRIVATE);
        final String userId = sharedPreferences.getString("loginId", "");

        final String addressStr = (String) getArguments().get("addressStr");
        final String groupBeginDateStr = (String) getArguments().get("groupBeginDateStr");
        final String learnBeginTimeStr = (String) getArguments().get("learnBeginTimeStr");
    }
}
