package com.kid.kidswim.ui.gallery;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.kid.kidswim.R;
import com.kid.kidswim.command.GroupInfo;
import com.kid.kidswim.command.SysCoachListInfo;
import com.kid.kidswim.events.AddGroupEvent;
import com.kid.kidswim.events.GroupShowEvent;
import com.kid.kidswim.utlis.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        String path="http://120.79.137.103:10080/kidswim/att/group/findGroupByAll";
        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(path)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Message message = Message.obtain();
                message.what = 1;
                message.obj = response.body().string();

                String objStr =  message.obj.toString();
                JsonUtil jsonUtil = new JsonUtil();
                GroupInfo groupInfo = jsonUtil.json2Object(objStr, GroupInfo.class);
                EventBus.getDefault().post(new GroupShowEvent(groupInfo));
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * onEvent事件，设置教练选择器
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GroupShowEvent event) {
        GroupInfo groupInfo = event.getGroupInfo();
        List<GroupInfo.GroupInfos> infos = groupInfo.getGroupList();
        ConstraintLayout conLayout = getActivity().findViewById(R.id.gallery_con_layout);
        LinearLayout layout = getActivity().findViewById(R.id.gallery_ver_layout);
//        for (int i = 0; i < infos.size(); i++) {
//            Button btn = new Button(getActivity());
//            btn.setBackgroundColor(Color.parseColor("#009688"));
//            btn.setTextColor(Color.parseColor("#FFFFFF"));
//            btn.setText("分组编号" + infos.get(i).getCode());
//            btn.setTop(i * 50);
//            layout.addView(btn);
//        }
        Button btn1 = new Button(getActivity());
        btn1.setBackgroundColor(Color.parseColor("#009688"));
        btn1.setTextColor(Color.parseColor("#FFFFFF"));
        btn1.setText("分组编号SDFSDFSDFSDF");
       // btn1.setTop(i * 50);
        layout.addView(btn1);

        Button btn2 = new Button(getActivity());
        btn2.setBackgroundColor(Color.parseColor("#009688"));
        btn2.setTextColor(Color.parseColor("#FFFFFF"));
        btn2.setText("分组编号222222222222222");
        //btn2.setTop(i * 50);
        layout.addView(btn2);
    }
}