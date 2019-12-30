package com.kid.kidswim.ui.addgroup;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
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
import com.kid.kidswim.command.IdAndName;
import com.kid.kidswim.command.SysCoachListInfo;
import com.kid.kidswim.command.SysDictInfo;
import com.kid.kidswim.command.SysSaleUserInfo;
import com.kid.kidswim.command.UserInfo;
import com.kid.kidswim.enums.KidswimAttEnum;
import com.kid.kidswim.events.AddGroupAddressEvent;
import com.kid.kidswim.events.AddGroupEvent;
import com.kid.kidswim.events.AddGroupLevelEvent;
import com.kid.kidswim.events.JumpToGalleryFragmentEvent;
import com.kid.kidswim.events.RemoveSaleUserDetailsInfoEvent;
import com.kid.kidswim.events.SaleUserDetailsShowEvent;
import com.kid.kidswim.result.AddGroupResult;
import com.kid.kidswim.ui.gallery.GalleryFragment;
import com.kid.kidswim.ui.gallery.GalleryViewModel;
import com.kid.kidswim.utlis.JsonUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Array;
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

public class AddGroupFragment extends Fragment {

    private AddGroupViewModel addGroupViewModel;
    private final Map<String, IdAndName> checkIdandNameMap = new HashMap<String, IdAndName>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        addGroupViewModel = ViewModelProviders.of(this).get(AddGroupViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_group, container, false);
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

        Button createBtn = getActivity().findViewById(R.id.add_group_create_group_button);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView add_group_coath_choice_value_att_view = getActivity().findViewById(R.id.add_group_coath_choice_value_att);
                TextView add_group_level_choice_value_att_view = getActivity().findViewById(R.id.add_group_level_choice_value_att);
                String codchIdStr = (String) add_group_coath_choice_value_att_view.getText().toString();
                String levelStr = (String) add_group_level_choice_value_att_view.getText().toString();
                if(checkIdandNameMap.isEmpty() || checkIdandNameMap.size() == 0) {
                    Toast.makeText(getActivity(), "请选择至少一个学员！", Toast.LENGTH_SHORT).show();
                }
                if (codchIdStr == null || codchIdStr.equals("")) {
                    Toast.makeText(getActivity(), "请选择教练员！", Toast.LENGTH_SHORT).show();
                }
                else if (levelStr == null || levelStr.equals("")) {
                    Toast.makeText(getActivity(), "请选择课程级别！", Toast.LENGTH_SHORT).show();
                }
                else {
                    List<String> saleIds = new ArrayList<String>();
                    for (Map.Entry<String, IdAndName> idname : checkIdandNameMap.entrySet()) {
                        saleIds.add(idname.getKey());
                    }
                    //获取网络上的servlet路径
                    String path="http://120.79.137.103:10080/kidswim/att/group/createSerGroup";
                    OkHttpClient client = new OkHttpClient();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("userId", userId);
                        jsonObject.put("coathId", codchIdStr);
                        jsonObject.put("courseAddress", addressStr);
                        jsonObject.put("beginDate", groupBeginDateStr);
                        jsonObject.put("learnBeginStr", learnBeginTimeStr);
                        jsonObject.put("courseLeavel", levelStr);
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
                                    Toast.makeText(getActivity(), "创建分组成功! 分组编号为:" + addGroupResult.getCode(), Toast.LENGTH_SHORT).show();
                                    Looper.loop();
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

        Button queryBtn = getActivity().findViewById(R.id.add_group_query_button18);
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView add_group_level_choice_value_att_view = getActivity().findViewById(R.id.add_group_level_choice_value_att);
                String levelStr = (String) add_group_level_choice_value_att_view.getText().toString();
                //获取网络上的servlet路径
                String path="http://120.79.137.103:10080/kidswim/att/group/findSaleStudentList";
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("courseAddress", addressStr)
                        .add("learnBeginTime", learnBeginTimeStr)
                        .add("beginDateStr", groupBeginDateStr)
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
                        EventBus.getDefault().post(new SaleUserDetailsShowEvent(sysSaleUserInfo));
                    }
                });
            }
        });

        //教练栏点击事件
        final LinearLayout add_group_linearLayout1_view = getActivity().findViewById(R.id.add_group_linearLayout1);
        add_group_linearLayout1_view.setOnClickListener(new View.OnClickListener() {
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

        //级别栏点击事件
        final LinearLayout add_group_linearLayout2_view = getActivity().findViewById(R.id.add_group_linearLayout2);
        add_group_linearLayout2_view.setOnClickListener(new View.OnClickListener() {
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
    }

    /**
     * onEvent事件，设置教练选择器
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddGroupEvent event) {
        SysCoachListInfo s = event.getSysCoachListInfo();
        final TextView coath_choice_valueView = getView().findViewById(R.id.add_group_coath_choice_value);
        final List<SysCoachListInfo.SysBaseCoachs> coachs = s.getSysBaseCoachslist();
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String coachId= coachs.get(options1).getId();
                String tx = coachs.get(options1).getNameCn() ;
                coath_choice_valueView.setText(tx);
                final TextView coachId_View = getActivity().findViewById(R.id.add_group_coath_choice_value_att);
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
     * onEvent事件，设置级别选择器
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddGroupLevelEvent event) {
        SysDictInfo s = event.getSysDictInfo();
        final TextView course_level_choice_valueView = getView().findViewById(R.id.add_group_level_choice_value);
        final List<SysDictInfo.dictList> dicts = s.getDictLists();
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String value = dicts.get(options1).getValue();
                String tx = dicts.get(options1).getLabel() ;
                course_level_choice_valueView.setText(tx);
                final TextView levaelValue_View = getActivity().findViewById(R.id.add_group_level_choice_value_att);
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

    /**
     * onEvent事件，刷新未选择的销售单明细
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SaleUserDetailsShowEvent event) {
        final SysSaleUserInfo sysSaleUserInfo = event.getSysSaleUserInfo();
        RadioGroup radioGropu = getActivity().findViewById(R.id.add_group_radiogroup_layout_1);
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
                        LinearLayout add_group_linearLayout5_show_layout_view = getActivity().findViewById(R.id.add_group_linearLayout5_show_layout);
                        add_group_linearLayout5_show_layout_view.removeAllViews();
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
                            add_group_linearLayout5_show_layout_view.addView(btn);
                        }
                        TextView grllery_selected_sale_students_num_view = getActivity().findViewById(R.id.grllery_selected_sale_students_num);
                        grllery_selected_sale_students_num_view.setText("已选择" + checkIdandNameMap.size() + "人");
                    }
                    else {
                        String id = sysSaleUserInfo.getTpcSaleStudentCommandList().get(cb.getId()).getSaleId();
                        checkIdandNameMap.remove(id);
                        LinearLayout add_group_linearLayout5_show_layout_view = getActivity().findViewById(R.id.add_group_linearLayout5_show_layout);
                        add_group_linearLayout5_show_layout_view.removeAllViews();
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
                            add_group_linearLayout5_show_layout_view.addView(btn);
                        }
                        TextView grllery_selected_sale_students_num_view = getActivity().findViewById(R.id.grllery_selected_sale_students_num);
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
    public void onEvent(RemoveSaleUserDetailsInfoEvent event) {
        String saleId = event.getId();
        checkIdandNameMap.remove(saleId);
        this.structureAddGroupLinearLayout5ShowLayout();
        TextView grllery_selected_sale_students_num_view = getActivity().findViewById(R.id.grllery_selected_sale_students_num);
        grllery_selected_sale_students_num_view.setText("已选择" + checkIdandNameMap.size() + "人");
    }

    /**
     * 重新构造页面
     * @return
     */
    private void structureAddGroupLinearLayout5ShowLayout() {
        LinearLayout add_group_linearLayout5_show_layout_view = getActivity().findViewById(R.id.add_group_linearLayout5_show_layout);
        add_group_linearLayout5_show_layout_view.removeAllViews();
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
            add_group_linearLayout5_show_layout_view.addView(btn);
        }
    }

}
