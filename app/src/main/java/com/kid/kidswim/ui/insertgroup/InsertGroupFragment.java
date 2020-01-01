package com.kid.kidswim.ui.insertgroup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.kid.kidswim.R;
import com.kid.kidswim.command.SysDictInfo;
import com.kid.kidswim.enums.KidswimAttEnum;
import com.kid.kidswim.events.AddGroupLevelEvent;
import com.kid.kidswim.events.InsertGroupLevelEvent;
import com.kid.kidswim.ui.addgroup.AddGroupViewModel;
import com.kid.kidswim.utlis.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class InsertGroupFragment extends Fragment {

    private InsertGroupViewModel insertGroupViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
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

        final String codeValue = (String) getArguments().get("code");
        final String coachNameValue = (String) getArguments().get("coachName");
        final String courseAddressValue = (String) getArguments().get("courseAddress");
        final String courseAddressNameValue = (String) getArguments().get("courseAddressName");
        final String groupBeginTimeStrValue = (String) getArguments().get("groupBeginTimeStr");
        final String groupLearnBeginTimeValue = (String) getArguments().get("groupLearnBeginTime");
        final String groupLearnBeginTimeStrValue = (String) getArguments().get("groupLearnBeginTimeStr");


        //级别栏点击事件
        final LinearLayout insert_group_linearLayout6_view = getActivity().findViewById(R.id.insert_group_linearLayout6);
        insert_group_linearLayout6_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取网络上的servlet路径
                String path="http://120.79.137.103:10080/kidswim/att/base/findDictListByType";
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("type", KidswimAttEnum.kidswimFlag.課程對應級別.getName())
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
                        EventBus.getDefault().post(new InsertGroupLevelEvent(sysDictInfo));
                    }
                });
            }
        });

        final TextView levaelValue_View = getActivity().findViewById(R.id.insert_group_level_choice_value_att);
        levaelValue_View.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //此处不需要调用
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //此处不需要调用
            }

            @Override
            public void afterTextChanged(Editable s) {
                //此处调用
            }
        });
    }

    /**
     * onEvent事件，设置级别选择器
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(InsertGroupLevelEvent event) {
        SysDictInfo s = event.getSysDictInfo();
        final TextView insert_group_level_choice_valueView = getView().findViewById(R.id.insert_group_level_choice_value);
        final List<SysDictInfo.dictList> dicts = s.getDictLists();
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String value = dicts.get(options1).getValue();
                String tx = dicts.get(options1).getLabel() ;
                insert_group_level_choice_valueView.setText(tx);
                final TextView levaelValue_View = getActivity().findViewById(R.id.insert_group_level_choice_value_att);
                levaelValue_View.setText(value);
            }
        }).build();
        List<String> nameList = new ArrayList<String>();
        for (SysDictInfo.dictList dict: dicts) {
            nameList.add(dict.getLabel());
        }
        pvOptions.setPicker(nameList);
        pvOptions.show();
    }
}
