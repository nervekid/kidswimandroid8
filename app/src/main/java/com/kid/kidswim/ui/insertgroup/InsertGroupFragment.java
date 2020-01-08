package com.kid.kidswim.ui.insertgroup;

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
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.kid.kidswim.R;
import com.kid.kidswim.command.IdAndName;
import com.kid.kidswim.command.SysDictInfo;
import com.kid.kidswim.command.SysSaleUserInfo;
import com.kid.kidswim.enums.KidswimAttEnum;
import com.kid.kidswim.events.InsertGroupLevelEvent;
import com.kid.kidswim.events.InsertRemoveSaleUserDetailsInfoEvent;
import com.kid.kidswim.events.InsertSaleUserDetailsShowEvent;
import com.kid.kidswim.events.JumpToGalleryFragmentEvent;
import com.kid.kidswim.events.RemoveSaleUserDetailsInfoEvent;
import com.kid.kidswim.result.AddGroupResult;
import com.kid.kidswim.utlis.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class InsertGroupFragment extends Fragment {

    private InsertGroupViewModel insertGroupViewModel;
    private final Map<String, IdAndName> checkIdandNameMap = new HashMap<String, IdAndName>();

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
        SharedPreferences.Editor editor = sharedPreferences.edit();


        final String userId = sharedPreferences.getString("loginId", "");

        final String codeValue = (String) getArguments().get("code");
        final String coachNameValue = (String) getArguments().get("coachName");
        final String courseAddressValue = (String) getArguments().get("courseAddress");
        final String courseAddressNameValue = (String) getArguments().get("courseAddressName");
        final String groupBeginTimeStrValue = (String) getArguments().get("groupBeginTimeStr");
        final String groupLearnBeginTimeValue = (String) getArguments().get("groupLearnBeginTime");
        final String groupLearnBeginTimeStrValue = (String) getArguments().get("groupLearnBeginTimeStr");

        editor.putString("courseAddressValue", courseAddressValue);
        editor.putString("groupBeginTimeStrValue", groupBeginTimeStrValue);
        editor.putString("groupLearnBeginTimeValue", groupLearnBeginTimeValue);
        editor.putString("courseAddressName", courseAddressNameValue);
        editor.putString("groupLearnBeginTimeStrValue", groupLearnBeginTimeStrValue);
        editor.commit();

        TextView insert_group_code_show_value_view = getActivity().findViewById(R.id.insert_group_code_show_value);
        insert_group_code_show_value_view.setText(codeValue);
        TextView insert_group_coach_show_value_view = getActivity().findViewById(R.id.insert_group_coach_show_value);
        insert_group_coach_show_value_view.setText(coachNameValue);
        TextView insert_group_address_show_value_view = getActivity().findViewById(R.id.insert_group_address_show_value);
        insert_group_address_show_value_view.setText(courseAddressNameValue);
        TextView insert_group_begin_date_show_value_view = getActivity().findViewById(R.id.insert_group_begin_date_show_value);
        insert_group_begin_date_show_value_view.setText(groupBeginTimeStrValue);
        TextView insert_group_learn_begin_date_show_value_view = getActivity().findViewById(R.id.insert_group_learn_begin_date_show_value);
        insert_group_learn_begin_date_show_value_view.setText(groupLearnBeginTimeStrValue);

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
                TextView insert_group_level_choice_value_att_view = getActivity().findViewById(R.id.insert_group_level_choice_value_att);
                String levelStr = (String) insert_group_level_choice_value_att_view.getText().toString();
                //获取网络上的servlet路径
                String path="http://120.79.137.103:10080/kidswim/att/group/findSaleStudentList";
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("courseAddress", courseAddressValue)
                        .add("learnBeginTime", groupLearnBeginTimeValue)
                        .add("beginDateStr", groupBeginTimeStrValue)
                        .add("courseLevel", levelStr)
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
                        SysSaleUserInfo sysSaleUserInfo = jsonUtil.json2Object(objStr, SysSaleUserInfo.class);
                        EventBus.getDefault().post(new InsertSaleUserDetailsShowEvent(sysSaleUserInfo));
                    }
                });
            }
        });

        Button confirmBtn = getActivity().findViewById(R.id.insert_group_button);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkIdandNameMap.isEmpty() || checkIdandNameMap.size() == 0) {
                    Toast.makeText(getActivity(), "请选择至少一个学员！", Toast.LENGTH_SHORT).show();
                }
                else {
                    List<String> saleIds = new ArrayList<String>();
                    for (Map.Entry<String, IdAndName> idname : checkIdandNameMap.entrySet()) {
                        saleIds.add(idname.getKey());
                    }
                    //获取网络上的servlet路径
                    String path="http://120.79.137.103:10080/kidswim/att/group/inertSerGroupDetails";
                    OkHttpClient client = new OkHttpClient();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("userId", userId);
                        jsonObject.put("groupCode", codeValue);
                        jsonObject.put("saleIds", saleIds);
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

                            //此处调用接口
                            if (addGroupResult.getStatus() != null && !addGroupResult.getStatus().equals("")) {
                                if (addGroupResult.getStatus().equals("1")) {
                                    Looper.prepare();
                                    EventBus.getDefault().post(new JumpToGalleryFragmentEvent(addGroupResult.getCode()));
                                    Toast.makeText(getActivity(), "加入分组成功!" , Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                                else {
                                    Looper.prepare();
                                    Toast.makeText(getActivity(), "加入分组失败!", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            }
                        }
                    });
                }
            }
        });
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

    /**
     * onEvent事件，刷新未选择的销售单明细
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(InsertSaleUserDetailsShowEvent event) {
        final SysSaleUserInfo sysSaleUserInfo = event.getSysSaleUserInfo();
        RadioGroup radioGropu = getActivity().findViewById(R.id.insert_group_radiogroup_layout_1);
        radioGropu.removeAllViews();
        for (int i = 0; i < sysSaleUserInfo.getTpcSaleStudentCommandList().size(); i++) {
            CheckBox cbo = new CheckBox(getActivity());
            String name = sysSaleUserInfo.getTpcSaleStudentCommandList().get(i).getStuName() + "-"
                    + sysSaleUserInfo.getTpcSaleStudentCommandList().get(i).getStuCode() + "-"
                    + sysSaleUserInfo.getTpcSaleStudentCommandList().get(i).getCourseLevelValue();
            cbo.setText(name);
            cbo.setId(i);
            radioGropu.addView(cbo);

            cbo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    if (cb.isChecked()) {
                        String id = sysSaleUserInfo.getTpcSaleStudentCommandList().get(cb.getId()).getSaleId();
                        IdAndName idAndName = new IdAndName(id, cb.getText().toString());
                        checkIdandNameMap.put(id, idAndName);
                        LinearLayout insert_group_linearLayout8_show_layout_view = getActivity().findViewById(R.id.insert_group_linearLayout8_show_layout);
                        insert_group_linearLayout8_show_layout_view.removeAllViews();
                        for (Map.Entry<String, IdAndName> idname : checkIdandNameMap.entrySet()) {
                            final String saleId = idname.getKey();
                            Button btn = new Button(getActivity());
                            btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                            btn.setBackgroundColor(Color.parseColor("#79FF79"));
                            btn.setTextColor(Color.parseColor("#000000"));
                            btn.setText(idname.getValue().getName());
                            ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(btn.getLayoutParams());
                            margin.setMargins(margin.leftMargin, 10, margin.rightMargin, margin.bottomMargin);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
                            btn.setLayoutParams(layoutParams);
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    EventBus.getDefault().post(new InsertRemoveSaleUserDetailsInfoEvent(saleId));
                                }
                            });
                            insert_group_linearLayout8_show_layout_view.addView(btn);
                        }
                        TextView grllery_selected_sale_students_num_view = getActivity().findViewById(R.id.insert_selected_sale_students_num);
                        grllery_selected_sale_students_num_view.setText("已选择" + checkIdandNameMap.size() + "人");
                    }
                    else {
                        String id = sysSaleUserInfo.getTpcSaleStudentCommandList().get(cb.getId()).getSaleId();
                        checkIdandNameMap.remove(id);
                        LinearLayout insert_group_linearLayout8_show_layout_view = getActivity().findViewById(R.id.insert_group_linearLayout8_show_layout);
                        insert_group_linearLayout8_show_layout_view.removeAllViews();
                        for (Map.Entry<String, IdAndName> idname : checkIdandNameMap.entrySet()) {
                            final String saleId = idname.getKey();
                            Button btn = new Button(getActivity());
                            btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                            btn.setBackgroundColor(Color.parseColor("#79FF79"));
                            btn.setTextColor(Color.parseColor("#000000"));
                            btn.setText(idname.getValue().getName());
                            ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(btn.getLayoutParams());
                            margin.setMargins(margin.leftMargin, 10, margin.rightMargin, margin.bottomMargin);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
                            btn.setLayoutParams(layoutParams);
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    EventBus.getDefault().post(new InsertRemoveSaleUserDetailsInfoEvent(saleId));
                                }
                            });
                            insert_group_linearLayout8_show_layout_view.addView(btn);
                        }
                        TextView grllery_selected_sale_students_num_view = getActivity().findViewById(R.id.insert_selected_sale_students_num);
                        grllery_selected_sale_students_num_view.setText("已选择" + checkIdandNameMap.size() + "人");
                    }
                }
            });
        }
    }

    /**
     * onEvent事件，删除销售单用户明细信息
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(InsertRemoveSaleUserDetailsInfoEvent event) {
        String saleId = event.getId();
        checkIdandNameMap.remove(saleId);
        this.structureAddGroupLinearLayout5ShowLayout();
        TextView grllery_selected_sale_students_num_view = getActivity().findViewById(R.id.insert_selected_sale_students_num);
        grllery_selected_sale_students_num_view.setText("已选择" + checkIdandNameMap.size() + "人");
    }

    /**
     * 重新构造页面
     * @return
     */
    private void structureAddGroupLinearLayout5ShowLayout() {
        LinearLayout insert_group_linearLayout8_show_layout_view = getActivity().findViewById(R.id.insert_group_linearLayout8_show_layout);
        insert_group_linearLayout8_show_layout_view.removeAllViews();
        for (Map.Entry<String, IdAndName> idname : checkIdandNameMap.entrySet()) {
            final String saleId = idname.getKey();
            Button btn = new Button(getActivity());
            btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
            btn.setBackgroundColor(Color.parseColor("#79FF79"));
            btn.setTextColor(Color.parseColor("#000000"));
            btn.setText(idname.getValue().getName());
            ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(btn.getLayoutParams());
            margin.setMargins(margin.leftMargin, 10, margin.rightMargin, margin.bottomMargin);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
            btn.setLayoutParams(layoutParams);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new RemoveSaleUserDetailsInfoEvent(saleId));
                }
            });
            insert_group_linearLayout8_show_layout_view.addView(btn);
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
