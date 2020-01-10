package com.kid.kidswim.ui.rollcall;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.kid.kidswim.LoginActivity;
import com.kid.kidswim.R;
import com.kid.kidswim.command.GroupDetailsSituationInfo;
import com.kid.kidswim.command.IdAndName;
import com.kid.kidswim.command.RollBeginTimeShow;
import com.kid.kidswim.command.RollCallCommand;
import com.kid.kidswim.command.RollCallShowSaleInfo;
import com.kid.kidswim.command.SysDictInfo;
import com.kid.kidswim.command.SysSaleUserInfo;
import com.kid.kidswim.enums.KidswimAttEnum;
import com.kid.kidswim.events.AddGroupAddressEvent;
import com.kid.kidswim.events.GroupDetailsShowEvent;
import com.kid.kidswim.events.InsertGroupLevelEvent;
import com.kid.kidswim.events.InsertSaleUserDetailsShowEvent;
import com.kid.kidswim.events.JumpToGalleryFragmentEvent;
import com.kid.kidswim.events.JumpToHomeFragmentEvent;
import com.kid.kidswim.events.RollCallAddressEvent;
import com.kid.kidswim.events.RollCallShowEvent;
import com.kid.kidswim.events.RollCallStatusEvent;
import com.kid.kidswim.events.RollShowBeginTimeEvent;
import com.kid.kidswim.result.AddGroupResult;
import com.kid.kidswim.result.RollCallResult;
import com.kid.kidswim.ui.insertgroup.InsertGroupViewModel;
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
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class RollCallFragment extends Fragment {

    private RollCallViewModel rollCallViewModel;
    private final Map<String, RollCallCommand> checkIdandNameMap = new HashMap<String, RollCallCommand>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        rollCallViewModel = ViewModelProviders.of(this).get(RollCallViewModel.class);
        View root = inflater.inflate(R.layout.fragment_roll_call, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //在此尝试获取SharedPreferences对象信息
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginUserToken", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        final String userId = sharedPreferences.getString("loginId", "");

        if ((sharedPreferences.getString("rollCallAddressValue", "") != null
                && !sharedPreferences.getString("rollCallAddressValue", "").equals("")) &&

                sharedPreferences.getString("rollCallAddressName", "") != null
                && !sharedPreferences.getString("rollCallAddressName", "").equals("") &&

                sharedPreferences.getString("rollCallBeginTtimeValue", "") != null
                && !sharedPreferences.getString("rollCallBeginTtimeValue", "").equals("") &&

                sharedPreferences.getString("rollCallBeginTtimeName", "") != null
                && !sharedPreferences.getString("rollCallBeginTtimeName", "").equals("") &&

                sharedPreferences.getString("rollCallDateValue", "") != null
                && !sharedPreferences.getString("rollCallDateValue", "").equals("") &&

                sharedPreferences.getString("rollCallDateName", "") != null
                && !sharedPreferences.getString("rollCallDateName", "").equals("")
        ) {
            String rollCallAddressValue = sharedPreferences.getString("rollCallAddressValue", "");
            String rollCallAddressName = sharedPreferences.getString("rollCallAddressName", "");
            String rollCallBeginTtimeValue = sharedPreferences.getString("rollCallBeginTtimeValue", "");
            String rollCallBeginTtimeName = sharedPreferences.getString("rollCallBeginTtimeName", "");
            String rollCallDateValue = sharedPreferences.getString("rollCallDateValue", "");
            String rollCallDateName = sharedPreferences.getString("rollCallDateName", "");

            TextView roll_call_address_choice_value_view = getActivity().findViewById(R.id.roll_call_address_choice_value);
            roll_call_address_choice_value_view.setText(rollCallAddressName);

            TextView roll_call_address_choice_value_att_view = getActivity().findViewById(R.id.roll_call_address_choice_value_att);
            roll_call_address_choice_value_att_view.setText(rollCallAddressValue);

            TextView roll_call_begin_date_choice_value_view = getActivity().findViewById(R.id.roll_call_begin_date_choice_value);
            roll_call_begin_date_choice_value_view.setText(rollCallDateName);

            TextView roll_call_begin_date_choice_value_att_view = getActivity().findViewById(R.id.roll_call_begin_date_choice_value_att);
            roll_call_begin_date_choice_value_att_view.setText(rollCallDateValue);

            TextView roll_call_begin_time_choice_value_view = getActivity().findViewById(R.id.roll_call_begin_time_choice_value);
            roll_call_begin_time_choice_value_view.setText(rollCallBeginTtimeName);

            TextView roll_call_begin_time_choice_value_att_view = getActivity().findViewById(R.id.roll_call_begin_time_choice_value_att);
            roll_call_begin_time_choice_value_att_view.setText(rollCallBeginTtimeValue);
        }

        final Button confimBtn = getActivity().findViewById(R.id.roll_call_confirm_button);
        confimBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIdandNameMap.size() == 0) {
                    Toast.makeText(getActivity(), "请至少对一名学员进行点名！", Toast.LENGTH_SHORT).show();
                }
                else {
                    JSONArray jsonArray = new JSONArray();
                    for (Map.Entry<String, RollCallCommand> c : checkIdandNameMap.entrySet()) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("courseDetailsId", c.getValue().getCourseDetailsId());
                        jsonObject.put("studentId", c.getValue().getStudentId());
                        jsonObject.put("rollCallStatusFlag", c.getValue().getStatus());
                        jsonArray.add(jsonObject);
                    }
                    //获取网络上的servlet路径
                    String path="http://120.79.137.103:10080/kidswim/att/rollcall/rollCall";
                    OkHttpClient client = new OkHttpClient();
                    JSONObject jsonObject = new JSONObject();

                    try {
                        jsonObject.put("userId", userId);
                        jsonObject.put("rollCallCommandList", jsonArray);
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
                            RollCallResult rollCallResult = jsonUtil.json2Object(objStr, RollCallResult.class);

                            //此处调用接口
                            if (rollCallResult.getStatus() != null && !rollCallResult.getStatus().equals("")) {
                                if (rollCallResult.getStatus().equals("1")) {
                                    Looper.prepare();
                                    EventBus.getDefault().post(new JumpToHomeFragmentEvent());
                                    Toast.makeText(getActivity(), "点名成功! 本次点名人数为:" + rollCallResult.getRollCallNum(), Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                                else {
                                    Looper.prepare();
                                    Toast.makeText(getActivity(), "点名失败!", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            }
                        }
                    });
                }
            }
        });

        //查询按钮事件
        final Button queryBtn = getActivity().findViewById(R.id.roll_call_query_button);
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView roll_call_address_choice_value_att_view = getActivity().findViewById(R.id.roll_call_address_choice_value_att);
                final TextView roll_call_begin_date_choice_value_att_view = getActivity().findViewById(R.id.roll_call_begin_date_choice_value_att);
                final TextView roll_call_begin_time_choice_value_att_view = getActivity().findViewById(R.id.roll_call_begin_time_choice_value_att);
                String addressStr = roll_call_address_choice_value_att_view.getText().toString().trim();
                String beginDateStr = roll_call_begin_date_choice_value_att_view.getText().toString().trim();
                String beginTimeStr = roll_call_begin_time_choice_value_att_view.getText().toString().trim();
                if(addressStr == null || addressStr.equals("")) {
                    Toast.makeText(getActivity(), "请选择泳池！", Toast.LENGTH_SHORT).show();
                }
                else if(beginDateStr == null || beginDateStr.equals("")) {
                    Toast.makeText(getActivity(), "请选择开始日期！", Toast.LENGTH_SHORT).show();
                }
                else if(beginTimeStr == null || beginTimeStr.equals("")) {
                    Toast.makeText(getActivity(), "请选择上课开始时间！", Toast.LENGTH_SHORT).show();
                }
                else {
                    //获取网络上的servlet路径
                    String path="http://120.79.137.103:10080/kidswim/att/rollcall/findRollCallSaleStudentList";
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("courseAddress", addressStr)
                            .add("dateStr", beginDateStr)
                            .add("beginTimeStr", beginTimeStr)
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
                            RollCallShowSaleInfo rollCallShowSaleInfo = jsonUtil.json2Object(objStr, RollCallShowSaleInfo.class);
                            if (Integer.parseInt(rollCallShowSaleInfo.getSize()) == 0) {
                                Looper.prepare();
                                Toast.makeText(getActivity(), "查询成功，未找到符合条件的学员名单！", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new RollCallShowEvent(rollCallShowSaleInfo));
                                Looper.loop();
                            }
                            else {
                                Looper.prepare();
                                Toast.makeText(getActivity(), "查询成功！结果如下所示", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new RollCallShowEvent(rollCallShowSaleInfo));
                                Looper.loop();
                            }
                        }
                    });
                }
            }
        });

        //地址栏点击事件
        final LinearLayout roll_call_linearLayout1_view = getActivity().findViewById(R.id.roll_call_linearLayout1);
        roll_call_linearLayout1_view.setOnClickListener(new View.OnClickListener() {
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
                        EventBus.getDefault().post(new RollCallAddressEvent(sysDictInfo));
                    }
                });
            }
        });

        //开始日期选择事件
        final LinearLayout roll_call_linearLayout2_view = getActivity().findViewById(R.id.roll_call_linearLayout2);
        roll_call_linearLayout2_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        TextView roll_call_begin_date_choice_value_view = getActivity().findViewById(R.id.roll_call_begin_date_choice_value);
                        TextView roll_call_begin_date_choice_value_att_view = getActivity().findViewById(R.id.roll_call_begin_date_choice_value_att);

                        //在此尝试获取SharedPreferences对象信息
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginUserToken", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("rollCallDateValue", sdf.format(date));
                        editor.putString("rollCallDateName", sdf.format(date));
                        editor.commit();

                        roll_call_begin_date_choice_value_view.setText(sdf.format(date));
                        roll_call_begin_date_choice_value_att_view.setText(sdf.format(date));
                    }
                }).build();
                pvTime.setDate(Calendar.getInstance());
                pvTime.show();
            }
        });

        //上课开始时间点击事件
        final LinearLayout roll_call_linearLayout3_view = getActivity().findViewById(R.id.roll_call_linearLayout3);
        roll_call_linearLayout3_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView roll_call_address_choice_value_att_view = getActivity().findViewById(R.id.roll_call_address_choice_value_att);
                TextView roll_call_begin_date_choice_value_att_view = getActivity().findViewById(R.id.roll_call_begin_date_choice_value_att);
                if (roll_call_address_choice_value_att_view.getText() == null||roll_call_address_choice_value_att_view.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "请选择泳池！", Toast.LENGTH_SHORT).show();
                }
                else if (roll_call_begin_date_choice_value_att_view.getText() == null || roll_call_begin_date_choice_value_att_view.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "请选择开始日期！", Toast.LENGTH_SHORT).show();
                }
                else {
                    String courseAddress = roll_call_address_choice_value_att_view.getText().toString().trim();
                    String dateStr = roll_call_begin_date_choice_value_att_view.getText().toString().trim();
                    //获取网络上的servlet路径
                    String path="http://120.79.137.103:10080/kidswim/att/rollcall/findAllBeginTimeByAddressAndDate";
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("courseAddress", courseAddress)
                            .add("dateStr", dateStr)
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
                            RollBeginTimeShow rollBeginTimeShow = jsonUtil.json2Object(objStr, RollBeginTimeShow.class);
                            EventBus.getDefault().post(new RollShowBeginTimeEvent(rollBeginTimeShow));
                        }
                    });
                }
            }
        });

    }

    /**
     * onEvent事件，显示开始时间选择
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RollShowBeginTimeEvent event) {
        RollBeginTimeShow rollBeginTimeShow = event.getRollBeginTimeShow();
        final TextView roll_call_begin_time_choice_value_view = getView().findViewById(R.id.roll_call_begin_time_choice_value);
        final List<RollBeginTimeShow.BeginTimeListInfo> timeList = rollBeginTimeShow.getBeginTimeList();
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String value = timeList.get(options1).getSelectTime();
                String tx = timeList.get(options1).getShowTime() ;

                //在此尝试获取SharedPreferences对象信息
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginUserToken", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("rollCallBeginTtimeValue", value);
                editor.putString("rollCallBeginTtimeName", tx);
                editor.commit();

                roll_call_begin_time_choice_value_view.setText(tx);
                final TextView roll_call_begin_time_choice_value_att_view = getActivity().findViewById(R.id.roll_call_begin_time_choice_value_att);
                roll_call_begin_time_choice_value_att_view.setText(value);
            }
        }).build();
        List<String> nameList = new ArrayList<String>();
        for (RollBeginTimeShow.BeginTimeListInfo time: timeList) {
            nameList.add(time.getShowTime());
        }
        pvOptions.setPicker(nameList);
        pvOptions.show();
    }

    /**
     * onEvent事件，查询响应事件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RollCallShowEvent event) {
        final RollCallShowSaleInfo rollCallShowSaleInfo =  event.getRollCallShowSaleInfo();
        LinearLayout roll_call_linearLayout1_show_layout_view = getActivity().findViewById(R.id.roll_call_linearLayout1_show_layout);
        roll_call_linearLayout1_show_layout_view.removeAllViews();
        for (int i = 0; i < rollCallShowSaleInfo.getRollCallShowList().size(); i++) {
            final Button btn = new Button(getActivity());
            btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
            StringBuffer strBuff = new StringBuffer();
            strBuff.append("级别:");
            strBuff.append(rollCallShowSaleInfo.getRollCallShowList().get(i).getCourseLevel());
            strBuff.append("-");
            strBuff.append(rollCallShowSaleInfo.getRollCallShowList().get(i).getStudentName());
            strBuff.append("-");
            strBuff.append("点名状态:");

            if (rollCallShowSaleInfo.getRollCallShowList().get(i).getRollCallStatusFlag().equals(KidswimAttEnum.rollCallStatusFlag.未点名.getName())) {
                btn.setBackgroundColor(Color.parseColor("#F0F0F0"));
                btn.setTextColor(Color.parseColor("#000000"));
            }
            else if (rollCallShowSaleInfo.getRollCallShowList().get(i).getRollCallStatusFlag().equals(KidswimAttEnum.rollCallStatusFlag.出席.getName())) {
                btn.setBackgroundColor(Color.parseColor("#33FF33"));
                btn.setTextColor(Color.parseColor("#000000"));
            }
            else if (rollCallShowSaleInfo.getRollCallShowList().get(i).getRollCallStatusFlag().equals(KidswimAttEnum.rollCallStatusFlag.缺席.getName())) {
                btn.setBackgroundColor(Color.parseColor("#C80000"));
                btn.setTextColor(Color.parseColor("#FFFFFF"));
            }
            else if (rollCallShowSaleInfo.getRollCallShowList().get(i).getRollCallStatusFlag().equals(KidswimAttEnum.rollCallStatusFlag.请假.getName())) {
                btn.setBackgroundColor(Color.parseColor("#0033FF"));
                btn.setTextColor(Color.parseColor("#FFFFFF"));
            }
            else {
                //事故
                btn.setBackgroundColor(Color.parseColor("#000000"));
                btn.setTextColor(Color.parseColor("#FFFFFF"));
            }
            strBuff.append(rollCallShowSaleInfo.getRollCallShowList().get(i).getRollCallStatusName());
            btn.setText(strBuff.toString());
            ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(btn.getLayoutParams());
            margin.setMargins(margin.leftMargin, 20, margin.rightMargin, margin.bottomMargin);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
            btn.setLayoutParams(layoutParams);
            btn.setId(i);
            roll_call_linearLayout1_show_layout_view.addView(btn);
            if (rollCallShowSaleInfo.getRollCallShowList().get(i).getRollCallStatusFlag().equals(KidswimAttEnum.rollCallStatusFlag.未点名.getName())) {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //获取网络上的servlet路径
                        String path = "http://120.79.137.103:10080/kidswim/att/base/findDictListByType";
                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = new FormBody.Builder()
                                .add("type", KidswimAttEnum.kidswimFlag.點名類別.getName())
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

                                String objStr = message.obj.toString();
                                JsonUtil jsonUtil = new JsonUtil();
                                SysDictInfo sysDictInfo = jsonUtil.json2Object(objStr, SysDictInfo.class);
                                EventBus.getDefault().post(new RollCallStatusEvent(rollCallShowSaleInfo.getRollCallShowList().get(btn.getId()),
                                        sysDictInfo, btn));
                            }
                        });
                    }
                });
            }
            else {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "已经完成点名的无法再次点名！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RollCallStatusEvent event) {
        final SysDictInfo s = event.getSysDictInfo();
        final Button btn = event.getButton();
        final RollCallShowSaleInfo.RollCallShowInfo rollCallShowInfo = event.getRollCallShowInfo();
        final List<SysDictInfo.dictList> dicts = s.getDictLists();
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String value = dicts.get(options1).getValue();
                String tx = dicts.get(options1).getLabel();
                String forString = btn.getText().toString();
                if (!forString.contains("(")) {
                    String nowString = forString + "(" + tx + ")";
                    btn.setText(nowString);
                }
                else {
                    String subString = forString.substring(0, forString.indexOf("("));
                    String nowString = subString + "(" + tx + ")";
                    btn.setText(nowString);
                }
                btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                btn.setBackgroundColor(Color.parseColor("#FFFF33"));
                btn.setTextColor(Color.parseColor("#000000"));

                ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(btn.getLayoutParams());
                margin.setMargins(margin.leftMargin, 20, margin.rightMargin, margin.bottomMargin);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
                btn.setLayoutParams(layoutParams);

                checkIdandNameMap.put(rollCallShowInfo.getStudentId(), new RollCallCommand(rollCallShowInfo.getCourseDetailsId(),
                        rollCallShowInfo.getStudentId(), value));
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
    public void onEvent(RollCallAddressEvent event) {
        SysDictInfo s = event.getSysDictInfo();
        final TextView roll_call_address_choice_value_view = getView().findViewById(R.id.roll_call_address_choice_value);
        final List<SysDictInfo.dictList> dicts = s.getDictLists();
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String value = dicts.get(options1).getValue();
                String tx = dicts.get(options1).getLabel() ;

                //在此尝试获取SharedPreferences对象信息
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginUserToken", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("rollCallAddressValue", value);
                editor.putString("rollCallAddressName", tx);
                editor.commit();

                roll_call_address_choice_value_view.setText(tx);
                final TextView roll_call_address_choice_value_att_view = getActivity().findViewById(R.id.roll_call_address_choice_value_att);
                roll_call_address_choice_value_att_view.setText(value);
            }
        }).build();
        List<String> nameList = new ArrayList<String>();
        for (SysDictInfo.dictList dict: dicts) {
            nameList.add(dict.getLabel());
        }
        pvOptions.setPicker(nameList);
        pvOptions.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(JumpToHomeFragmentEvent event) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_home);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
