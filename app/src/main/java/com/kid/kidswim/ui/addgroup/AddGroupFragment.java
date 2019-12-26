package com.kid.kidswim.ui.addgroup;

import android.app.FragmentManager;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import com.kid.kidswim.events.AddGroupLevelEvent;
import com.kid.kidswim.events.JumpToGalleryFragmentEvent;
import com.kid.kidswim.result.AddGroupResult;
import com.kid.kidswim.ui.gallery.GalleryFragment;
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
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class AddGroupFragment extends Fragment {

    private AddGroupViewModel addGroupViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        addGroupViewModel = ViewModelProviders.of(this).get(AddGroupViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_group, container, false);
        String str = (String) getArguments().get("theKey");
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
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginUserToken", Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("loginId", "");

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
                    Toast.makeText(getActivity(), "请选择教练！", Toast.LENGTH_SHORT).show();
                }
                else if(address == null || address.equals("")) {
                    Toast.makeText(getActivity(), "请选择泳池！", Toast.LENGTH_SHORT).show();
                }
                else if(beginDateStr == null || beginDateStr.equals("")) {
                    Toast.makeText(getActivity(), "请选择分组开始时间！", Toast.LENGTH_SHORT).show();
                }
                else  if(endDateStr == null || endDateStr.equals("")) {
                    Toast.makeText(getActivity(), "请选择分组结束时间！", Toast.LENGTH_SHORT).show();
                }
                else if(learnBeginTimeStr == null || learnBeginTimeStr.equals("")) {
                    Toast.makeText(getActivity(), "请选择上课开始时间！", Toast.LENGTH_SHORT).show();
                }
                else if(courselevel == null || courselevel.equals("")) {
                    Toast.makeText(getActivity(), "请选择级别！", Toast.LENGTH_SHORT).show();
                }
                else {
                    //获取网络上的servlet路径
                    String path="http://120.79.137.103:10080/kidswim/att/group/createSerGroup";
                    OkHttpClient client = new OkHttpClient();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("userId", userId);
                        jsonObject.put("coathId", coachId);
                        jsonObject.put("courseAddress", address);
                        jsonObject.put("beginDate", beginDateStr);
                        jsonObject.put("endDate", endDateStr);
                        jsonObject.put("learnBeginStr", learnBeginTimeStr);
                        jsonObject.put("courseLeavel", courselevel);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    String json = jsonObject.toString();
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    RequestBody requestBody = RequestBody.create(JSON, json);
                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(path)
                            .post(requestBody)
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
                            AddGroupResult addGroupResult = jsonUtil.json2Object(objStr, AddGroupResult.class);

                            if (addGroupResult.getStatus() != null && !addGroupResult.getStatus().equals("")) {
                                if (addGroupResult.getStatus().equals("1")) {
                                    if (addGroupResult.getCode() != null && !addGroupResult.getCode().equals("")) {
                                        EventBus.getDefault().post(new JumpToGalleryFragmentEvent(addGroupResult.getCode()));
                                    }
                                    else {
                                        Looper.prepare();
//                                        Toast.makeText(getActivity(), "创建分组成功!", Toast.LENGTH_SHORT).show();
                                        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_addgroup);
                                        navController.navigate(R.id.nav_gallery);
                                        Looper.loop();
                                }
                            }
                                else {
                                    Looper.prepare();
                                    Toast.makeText(getActivity(), "创建分组失败!", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            }
                        }
                    });
                }
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

        //级别栏点击事件
        final LinearLayout layout6 = getActivity().findViewById(R.id.add_group_linearLayout6);
        layout6.setOnClickListener(new View.OnClickListener() {
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
                        EventBus.getDefault().post(new AddGroupLevelEvent(sysDictInfo));
                    }
                });
            }
        });

        //开始时间点击事件
        final LinearLayout layout3 = getActivity().findViewById(R.id.add_group_linearLayout3);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {//选中事件回调
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    TextView begin_date_choice_valueView = getActivity().findViewById(R.id.begin_date_choice_value);
                    TextView begin_date_choice_value_attView = getActivity().findViewById(R.id.begin_date_choice_value_att);
                    begin_date_choice_valueView.setText(sdf.format(date));
                    begin_date_choice_value_attView.setText(sdf.format(date));
                }
            }).build();
            pvTime.setDate(Calendar.getInstance());
            pvTime.show();
            }
        });

        //结束时间点击事件
        final LinearLayout layout4 = getActivity().findViewById(R.id.add_group_linearLayout4);
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        TextView end_date_choice_valueView = getActivity().findViewById(R.id.end_date_choice_value);
                        TextView end_date_choice_value_attView = getActivity().findViewById(R.id.end_date_choice_value_att);
                        end_date_choice_valueView.setText(sdf.format(date));
                        end_date_choice_value_attView.setText(sdf.format(date));
                    }
                }).build();
                pvTime.setDate(Calendar.getInstance());
                pvTime.show();
            }
        });

        //上课开始时间点击事件
        final LinearLayout layout5 = getActivity().findViewById(R.id.add_group_linearLayout5);
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        TextView learn_begin_choice_valueView = getActivity().findViewById(R.id.learn_begin_choice_value);
                        TextView learn_begin_choice_value_attView = getActivity().findViewById(R.id.learn_begin_choice_value_att);
                        learn_begin_choice_valueView.setText(sdf.format(date));
                        learn_begin_choice_value_attView.setText(sdf.format(date).replaceAll(":", ""));
                    }
                }).setType(new boolean[]{false, false, false, true, true, false}).build();
                pvTime.setDate(Calendar.getInstance());
                pvTime.show();
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

    /**
     * onEvent事件，设置地址选择器
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddGroupAddressEvent event) {
        SysDictInfo s = event.getSysDictInfo();
        final TextView address_choice_valueView = getView().findViewById(R.id.address_choice_value);
        final List<SysDictInfo.dictList> dicts = s.getDictLists();
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String value = dicts.get(options1).getValue();
                String tx = dicts.get(options1).getLabel() ;
                address_choice_valueView.setText(tx);
                final TextView addressValue_View = getActivity().findViewById(R.id.address_choice_value_att);
                addressValue_View.setText(value);
            }
        }).build();
        List<String> nameList = new ArrayList<String>();
        for (SysDictInfo.dictList dict: dicts) {
            nameList.add(dict.getLabel());
        }
        pvOptions.setPicker(nameList);
        pvOptions.show();
    }

    /**
     * onEvent事件，设置地址选择器
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddGroupLevelEvent event) {
        SysDictInfo s = event.getSysDictInfo();
        final TextView course_level_choice_valueView = getView().findViewById(R.id.course_level_choice_value);
        final List<SysDictInfo.dictList> dicts = s.getDictLists();
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String value = dicts.get(options1).getValue();
                String tx = dicts.get(options1).getLabel() ;
                course_level_choice_valueView.setText(tx);
                final TextView levaelValue_View = getActivity().findViewById(R.id.course_level_choice_value_att);
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

    /**
     * onEvent事件，跳转到分组列表分页
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(JumpToGalleryFragmentEvent event) {

        Toast.makeText(getActivity(), "创建分组成功! 分组编号为:" + event.getCode(), Toast.LENGTH_SHORT).show();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_gallery);
    }

}
