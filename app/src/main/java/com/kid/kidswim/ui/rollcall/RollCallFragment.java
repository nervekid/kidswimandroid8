package com.kid.kidswim.ui.rollcall;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.alibaba.fastjson.JSONObject;
import com.kid.kidswim.R;
import com.kid.kidswim.command.IdAndName;
import com.kid.kidswim.command.SysDictInfo;
import com.kid.kidswim.command.SysSaleUserInfo;
import com.kid.kidswim.enums.KidswimAttEnum;
import com.kid.kidswim.events.InsertGroupLevelEvent;
import com.kid.kidswim.events.InsertSaleUserDetailsShowEvent;
import com.kid.kidswim.events.JumpToGalleryFragmentEvent;
import com.kid.kidswim.result.AddGroupResult;
import com.kid.kidswim.ui.insertgroup.InsertGroupViewModel;
import com.kid.kidswim.utlis.JsonUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class RollCallFragment extends Fragment {

    private RollCallViewModel rollCallViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        rollCallViewModel = ViewModelProviders.of(this).get(RollCallViewModel.class);
        View root = inflater.inflate(R.layout.fragment_insert_group, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
