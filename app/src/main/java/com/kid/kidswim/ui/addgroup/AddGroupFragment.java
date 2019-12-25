package com.kid.kidswim.ui.addgroup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.kid.kidswim.LoginActivity;
import com.kid.kidswim.MainActivity;
import com.kid.kidswim.R;
import com.kid.kidswim.command.SysCoachListInfo;
import com.kid.kidswim.command.SysDictInfo;
import com.kid.kidswim.command.UserInfo;
import com.kid.kidswim.enums.KidswimAttEnum;
import com.kid.kidswim.events.AddGroupAddressEvent;
import com.kid.kidswim.events.AddGroupEvent;
import com.kid.kidswim.ui.gallery.GalleryViewModel;
import com.kid.kidswim.utlis.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class AddGroupFragment extends Fragment {

    private AddGroupViewModel addGroupViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        addGroupViewModel = ViewModelProviders.of(this).get(AddGroupViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_group, container, false);
        String str = (String) getArguments().get("theKey");


        //时间选择器
//        TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                coath_choice_valueView.setText(sdf.format(date));
//            }
//        }).build();
//        pvTime.setDate(Calendar.getInstance());
//        pvTime.show();
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        final Button btn = getActivity().findViewById(R.id.add_group_submin_btn);

        //回调创建分组接口
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView coachId_View = getActivity().findViewById(R.id.coath_choice_value_att);
                final TextView address_View = getActivity().findViewById(R.id.address_choice_value_att);
                final TextView beginDateStr_View = getActivity().findViewById(R.id.begin_date_choice_value_att);
                final TextView endDateStr_View = getActivity().findViewById(R.id.end_date_choice_value_att);
                final TextView learnBeginTimeStr_View = getActivity().findViewById(R.id.learn_begin_choice_value_att);
                final TextView courselevel_View = getActivity().findViewById(R.id.course_level_choice_value_att);

                String coachId = coachId_View.getText().toString().trim();
                String address = address_View.getText().toString().trim();
                String beginDateStr = beginDateStr_View.getText().toString().trim();
                String endDateStr = endDateStr_View.getText().toString().trim();
                String learnBeginTimeStr = learnBeginTimeStr_View.getText().toString().trim();
                String courselevel = courselevel_View.getText().toString().trim();

                if(coachId == null || coachId.equals("")) {

                }
                if(address == null || address.equals("")) {

                }
                if(beginDateStr == null || beginDateStr.equals("")) {

                }
                if(endDateStr == null || endDateStr.equals("")) {

                }
                if(learnBeginTimeStr == null || learnBeginTimeStr.equals("")) {

                }
                if(courselevel == null || courselevel.equals("")) {

                }

                //此处调用接口
            }
        });

        //教练栏点击事件
        final LinearLayout layout1 = getActivity().findViewById(R.id.add_group_linearLayout1);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path="http://120.79.137.103:10080/kidswim/att/hall/findAllCoach";
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
                        SysCoachListInfo sysCoachListInfo = jsonUtil.json2Object(objStr, SysCoachListInfo.class);
                        EventBus.getDefault().post(new AddGroupEvent(sysCoachListInfo));
                    }
                });
            }
        });

        //地址栏点击事件
        final LinearLayout layout2 = getActivity().findViewById(R.id.add_group_linearLayout2);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取网络上的servlet路径
                String path="http://120.79.137.103:10080/kidswim/att/base/findDictListByType";
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("type", KidswimAttEnum.kidswimFlag.課程地址.getName())
                        .build();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(path)
                        .post(body)
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
                        SysDictInfo sysDictInfo = jsonUtil.json2Object(objStr, SysDictInfo.class);
                        EventBus.getDefault().post(new AddGroupAddressEvent(sysDictInfo));
                    }
                });
            }
        });
    }

    /**
     * onEvent事件，设置教练选择器
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddGroupEvent event) {
        SysCoachListInfo s = event.getSysCoachListInfo();
        final TextView coath_choice_valueView = getView().findViewById(R.id.coath_choice_value);
        final List<SysCoachListInfo.SysBaseCoachs> coachs = s.getSysBaseCoachslist();
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String coachId= coachs.get(options1).getId();
                String tx = coachs.get(options1).getNameCn() ;
                coath_choice_valueView.setText(tx);
                final TextView coachId_View = getActivity().findViewById(R.id.coath_choice_value_att);
                coachId_View.setText(coachId);
            }
        }).build();
        List<String> nameList = new ArrayList<String>();
        for (SysCoachListInfo.SysBaseCoachs coach: coachs) {
            nameList.add(coach.getNameCn());
        }
        pvOptions.setPicker(nameList);
        pvOptions.show();
    }

}
