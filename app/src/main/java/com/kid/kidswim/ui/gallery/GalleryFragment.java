package com.kid.kidswim.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.kid.kidswim.DBC.Global;
import com.kid.kidswim.R;
import com.kid.kidswim.command.CourseBeginTimeInfo;
import com.kid.kidswim.command.GroupDetailsSituationInfo;
import com.kid.kidswim.command.RollBeginTimeShow;
import com.kid.kidswim.command.SysDictInfo;
import com.kid.kidswim.enums.KidswimAttEnum;
import com.kid.kidswim.events.AddGroupAddressEvent;
import com.kid.kidswim.events.GroupDetailsShowEvent;
import com.kid.kidswim.events.JumpToAddGroupFragmentEvent;
import com.kid.kidswim.events.RollShowBeginTimeEvent;
import com.kid.kidswim.events.ShowCourseBeginTimeEvent;
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

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginUserToken", Context.MODE_PRIVATE);

        if ((sharedPreferences.getString("courseAddressValue", "") != null && !sharedPreferences.getString("courseAddressValue", "").equals("")) &&
                (sharedPreferences.getString("groupBeginTimeStrValue", "") != null && !sharedPreferences.getString("groupBeginTimeStrValue", "").equals("")) &&
                (sharedPreferences.getString("groupLearnBeginTimeValue", "") != null && !sharedPreferences.getString("groupLearnBeginTimeValue", "").equals("")) &&
                (sharedPreferences.getString("courseAddressName", "") != null && !sharedPreferences.getString("courseAddressName", "").equals("")) &&
                (sharedPreferences.getString("groupLearnBeginTimeStrValue", "") != null && !sharedPreferences.getString("groupLearnBeginTimeStrValue", "").equals(""))) {

            final String courseAddressValue = sharedPreferences.getString("courseAddressValue", "");
            final String groupBeginTimeStrValue = sharedPreferences.getString("groupBeginTimeStrValue", "");
            final String courseAddressNameValue = sharedPreferences.getString("courseAddressName", "");
            final String groupLearnBeginTimeValue = sharedPreferences.getString("groupLearnBeginTimeValue", "");
            final String groupLearnBeginTimeStrValue = sharedPreferences.getString("groupLearnBeginTimeStrValue", "");
            TextView grllery_address_choice_value_view = getActivity().findViewById(R.id.grllery_address_choice_value);
            grllery_address_choice_value_view.setText(courseAddressNameValue);
            TextView grllery_address_choice_value_att_view = getActivity().findViewById(R.id.grllery_address_choice_value_att);
            grllery_address_choice_value_att_view.setText(courseAddressValue);
            TextView grllery_begin_date_choice_value_view = getActivity().findViewById(R.id.grllery_begin_date_choice_value);
            grllery_begin_date_choice_value_view.setText(groupBeginTimeStrValue);
            TextView grllery_begin_date_choice_value_att_view = getActivity().findViewById(R.id.grllery_begin_date_choice_value_att);
            grllery_begin_date_choice_value_att_view.setText(groupBeginTimeStrValue);
            TextView grllery_lean_begin_choice_value_view = getActivity().findViewById(R.id.grllery_lean_begin_choice_value);
            grllery_lean_begin_choice_value_view.setText(groupLearnBeginTimeStrValue);
            TextView grllery_lean_begin_choice_value_att_view = getActivity().findViewById(R.id.grllery_lean_begin_choice_value_att);
            grllery_lean_begin_choice_value_att_view.setText(groupLearnBeginTimeValue);
            final Button queryBtn = getActivity().findViewById(R.id.gallery_query_button18);
        }

        final Button queryBtn = getActivity().findViewById(R.id.gallery_query_button18);
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView grllery_address_choice_value_att_view = getActivity().findViewById(R.id.grllery_address_choice_value_att);
                final TextView grllery_begin_date_choice_value_att_view = getActivity().findViewById(R.id.grllery_begin_date_choice_value_att);
                final TextView grllery_lean_begin_choice_value_att_view = getActivity().findViewById(R.id.grllery_lean_begin_choice_value_att);
                String addressStr = grllery_address_choice_value_att_view.getText().toString().trim();
                String groupBeginDateStr = grllery_begin_date_choice_value_att_view.getText().toString().trim();
                String learnBeginTimeStr = grllery_lean_begin_choice_value_att_view.getText().toString().trim();
                if(addressStr == null || addressStr.equals("")) {
                    Toast.makeText(getActivity(), "请选择泳池！", Toast.LENGTH_SHORT).show();
                }
                else if(groupBeginDateStr == null || groupBeginDateStr.equals("")) {
                        Toast.makeText(getActivity(), "请选择分组开始日期！", Toast.LENGTH_SHORT).show();
                    }
                    else if(learnBeginTimeStr == null || learnBeginTimeStr.equals("")) {
                        Toast.makeText(getActivity(), "请选择上课开始时间！", Toast.LENGTH_SHORT).show();
                    }
                    else {
                    //获取网络上的servlet路径
                    String path= Global.GlobalConfiguration.服务器链接.getName() +  "/att/group/findCourseCorrespondSaleSituation";
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("courseAddress", addressStr)
                            .add("learnBeginTime", learnBeginTimeStr)
                            .add("beginDateStr", groupBeginDateStr)
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
                            GroupDetailsSituationInfo groupDetailsSituationInfo = jsonUtil.json2Object(objStr, GroupDetailsSituationInfo.class);
                            if (groupDetailsSituationInfo.getSituationInfo().getGroupList().isEmpty() && groupDetailsSituationInfo.getSituationInfo().getGroupDetailsInfos().isEmpty()) {
                                Looper.prepare();
                                Toast.makeText(getActivity(), "查询成功，未分组学员数为0，已建分组为0！", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new GroupDetailsShowEvent(groupDetailsSituationInfo));
                                Looper.loop();
                            }
                            else if (!groupDetailsSituationInfo.getSituationInfo().getGroupList().isEmpty() && groupDetailsSituationInfo.getSituationInfo().getGroupDetailsInfos().isEmpty()){
                                Looper.prepare();
                                Toast.makeText(getActivity(), "查询成功，已建分组为0！", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new GroupDetailsShowEvent(groupDetailsSituationInfo));
                                Looper.loop();
                            }
                            else if (groupDetailsSituationInfo.getSituationInfo().getGroupList().isEmpty() && !groupDetailsSituationInfo.getSituationInfo().getGroupDetailsInfos().isEmpty()){
                                Looper.prepare();
                                Toast.makeText(getActivity(), "查询成功，未分组学员数为0，！", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new GroupDetailsShowEvent(groupDetailsSituationInfo));
                                Looper.loop();
                            }
                            else {
                                Looper.prepare();
                                Toast.makeText(getActivity(), "查询成功！结果如下所示", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().post(new GroupDetailsShowEvent(groupDetailsSituationInfo));
                                Looper.loop();
                            }
                        }
                    });

                }
            }
        });


        //地址栏点击事件
        final LinearLayout address_layout = getActivity().findViewById(R.id.grllery_linearLayout1);
        address_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取网络上的servlet路径
                String path = Global.GlobalConfiguration.服务器链接.getName() + "/att/base/findDictListByType";
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

        //开始时间点击事件
        final LinearLayout group_begin_date_layout = getActivity().findViewById(R.id.grllery_linearLayout2);
        group_begin_date_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        TextView begin_date_choice_valueView = getActivity().findViewById(R.id.grllery_begin_date_choice_value);
                        TextView begin_date_choice_value_attView = getActivity().findViewById(R.id.grllery_begin_date_choice_value_att);
                        begin_date_choice_valueView.setText(sdf.format(date));
                        begin_date_choice_value_attView.setText(sdf.format(date));
                    }
                }).build();
                pvTime.setDate(Calendar.getInstance());
                pvTime.show();
            }
        });

        //上课开始时间点击事件
        final LinearLayout learn_begin_layout = getActivity().findViewById(R.id.grllery_linearLayout3);
        learn_begin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView grllery_address_choice_value_att_view = getActivity().findViewById(R.id.grllery_address_choice_value_att);
                TextView grllery_begin_date_choice_value_att_view = getActivity().findViewById(R.id.grllery_begin_date_choice_value_att);
                if (grllery_address_choice_value_att_view.getText() == null||grllery_address_choice_value_att_view.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "请选择泳池！", Toast.LENGTH_SHORT).show();
                }
                else if (grllery_begin_date_choice_value_att_view.getText() == null || grllery_begin_date_choice_value_att_view.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "请选择开始日期！", Toast.LENGTH_SHORT).show();
                }
                else {
                    String courseAddress = grllery_address_choice_value_att_view.getText().toString().trim();
                    String dateStr = grllery_begin_date_choice_value_att_view.getText().toString().trim();
                    //获取网络上的servlet路径
                    String path= Global.GlobalConfiguration.服务器链接.getName() + "/att/group/findCourseBeginTimeList";
                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("courseAddress", courseAddress)
                            .add("beginDateStr", dateStr)
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
                            CourseBeginTimeInfo CourseBeginTimeInfo = jsonUtil.json2Object(objStr, CourseBeginTimeInfo.class);
                            EventBus.getDefault().post(new ShowCourseBeginTimeEvent(CourseBeginTimeInfo));
                        }
                    });
                }

            }
        });

        Button createGroupBtn = getActivity().findViewById(R.id.grllery_create_group_button);
        createGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TextView grllery_address_choice_value_att_view = getActivity().findViewById(R.id.grllery_address_choice_value_att);
                final TextView grllery_begin_date_choice_value_att_view = getActivity().findViewById(R.id.grllery_begin_date_choice_value_att);
                final TextView grllery_lean_begin_choice_value_att_view = getActivity().findViewById(R.id.grllery_lean_begin_choice_value_att);
                final TextView grllery_address_choice_value_view = getActivity().findViewById(R.id.grllery_address_choice_value);
                final TextView grllery_lean_begin_choice_value_view = getActivity().findViewById(R.id.grllery_lean_begin_choice_value);
                String addressStr = grllery_address_choice_value_att_view.getText().toString().trim();
                String groupBeginDateStr = grllery_begin_date_choice_value_att_view.getText().toString().trim();
                String learnBeginTimeStr = grllery_lean_begin_choice_value_att_view.getText().toString().trim();
                String addressName = grllery_address_choice_value_view.getText().toString().trim();
                String learnBeginTimeStrShow = grllery_lean_begin_choice_value_view.getText().toString().trim();
                EventBus.getDefault().post(new JumpToAddGroupFragmentEvent(addressStr, groupBeginDateStr, learnBeginTimeStr, addressName, learnBeginTimeStrShow));
            }
        });
    }

    /**
     * onEvent事件，跳转到增加分组的页面
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(JumpToAddGroupFragmentEvent event) {
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        Bundle bundle = new Bundle();
        bundle.putString("addressStr", event.getAddressStr());
        bundle.putString("groupBeginDateStr", event.getGroupBeginDateStr());
        bundle.putString("learnBeginTimeStr", event.getLearnBeginTimeStr());
        bundle.putString("addressName", event.getAddressName());
        bundle.putString("learnBeginTimeStrShow", event.getLearnBeginTimeStrShow());
        navController.navigate(R.id.nav_addgroup, bundle);
    }

    /**
     * onEvent事件，设置地址选择器
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddGroupAddressEvent event) {
        SysDictInfo s = event.getSysDictInfo();
        final TextView address_choice_valueView = getView().findViewById(R.id.grllery_address_choice_value);
        final List<SysDictInfo.dictList> dicts = s.getDictLists();
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
                String value = dicts.get(options1).getValue();
                String tx = dicts.get(options1).getLabel() ;
                address_choice_valueView.setText(tx);
                final TextView addressValue_View = getActivity().findViewById(R.id.grllery_address_choice_value_att);
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
     * onEvent事件，设置教练选择器
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GroupDetailsShowEvent event) {
        final GroupDetailsSituationInfo info = event.getGroupDetailsSituationInfo();
        //在此初始化未分组人数模块及已建分组模块数据显示
        LinearLayout grllery_linearLayout4_show_layout_view = getActivity().findViewById(R.id.grllery_linearLayout4_show_layout);
        grllery_linearLayout4_show_layout_view.removeAllViews();
        LinearLayout grllery_linearLayout5_show_layout_view = getActivity().findViewById(R.id.grllery_linearLayout5_show_layout);
        grllery_linearLayout5_show_layout_view.removeAllViews();
        for (int i = 0; i < info.getSituationInfo().getGroupList().size(); i++) {
            Button btn = new Button(getActivity());
            btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
            btn.setBackgroundColor(Color.parseColor("#BBFFFF"));
            btn.setTextColor(Color.parseColor("#000000"));
            btn.setText(info.getSituationInfo().getGroupList().get(i).getLeavel() + ":" + info.getSituationInfo().getGroupList().get(i).getCountNum() + "人");
            ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(btn.getLayoutParams());
            margin.setMargins(margin.leftMargin, 10, margin.rightMargin, margin.bottomMargin);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
            btn.setLayoutParams(layoutParams);
            grllery_linearLayout4_show_layout_view.addView(btn);
        }

        for (int i = 0; i < info.getSituationInfo().getGroupDetailsInfos().size(); i++) {
            Button btn = new Button(getActivity());
            btn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
            btn.setBackgroundColor(Color.parseColor("#79FF79"));
            btn.setTextColor(Color.parseColor("#000000"));
            btn.setText(info.getSituationInfo().getGroupDetailsInfos().get(i).getCodeAndNumShow());
            btn.setId(i);
            ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(btn.getLayoutParams());
            margin.setMargins(margin.leftMargin, 10, margin.rightMargin, margin.bottomMargin);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
            btn.setLayoutParams(layoutParams);
            grllery_linearLayout5_show_layout_view.addView(btn);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = ((Button) v).getId();
                    GroupDetailsSituationInfo.SituationInfo.GroupDetailsInfos g = info.getSituationInfo().getGroupDetailsInfos().get(i);
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    Bundle bundle = new Bundle();
                    bundle.putString("code", g.getCode());
                    bundle.putString("coachName", g.getCoachName());
                    bundle.putString("courseAddress", g.getCourseAddress());
                    bundle.putString("courseAddressName", g.getCourseAddressName());
                    bundle.putString("groupBeginTimeStr", g.getGroupBeginTimeStr());
                    bundle.putString("groupLearnBeginTime", g.getGroupLearnBeginTime());
                    bundle.putString("groupLearnBeginTimeStr", g.getGroupLearnBeginTimeStr());
                    navController.navigate(R.id.nav_insertgroup, bundle);
                }
            });
        }
    }

    /**
     * onEvent事件，显示开始时间选择
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ShowCourseBeginTimeEvent event) {
        CourseBeginTimeInfo info = event.getCourseBeginTimeInfo();

        if (info.getInfoList().size() > 0) {
            for (int i = 0; i < info.getInfoList().size(); i++) {
                String beginTimStr = info.getInfoList().get(i).getBeginTimeStr();
                String beginTimShowStr = beginTimStr.substring(0, 2) + ":" + beginTimStr.substring(2, 4);
                info.getInfoList().get(i).setShowBeginTimeStr(beginTimShowStr);
            }

            final TextView grllery_lean_begin_choice_value_view = getView().findViewById(R.id.grllery_lean_begin_choice_value);
            final List<CourseBeginTimeInfo.CourseBeginTimeDetailsInfo> timeList = info.getInfoList();
            OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                    //返回的分别是三个级别的选中位置
                    String value = timeList.get(options1).getBeginTimeStr();
                    String tx = timeList.get(options1).getShowBeginTimeStr() ;

                    //在此尝试获取SharedPreferences对象信息
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("loginUserToken", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("groupLearnBeginTimeValue", value);
                    editor.putString("groupLearnBeginTimeStrValue", tx);
                    editor.commit();

                    grllery_lean_begin_choice_value_view.setText(tx);
                    final TextView grllery_lean_begin_choice_value_att_view = getActivity().findViewById(R.id.grllery_lean_begin_choice_value_att);
                    grllery_lean_begin_choice_value_att_view.setText(value);
                }
            }).build();
            List<String> nameList = new ArrayList<String>();
            for (CourseBeginTimeInfo.CourseBeginTimeDetailsInfo time: timeList) {
                nameList.add(time.getShowBeginTimeStr());
            }
            pvOptions.setPicker(nameList);
            pvOptions.show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}