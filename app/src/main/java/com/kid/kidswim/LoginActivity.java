package com.kid.kidswim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.kid.kidswim.DBC.Global;
import com.kid.kidswim.command.UserInfo;
import com.kid.kidswim.enums.KidswimAttEnum;
import com.kid.kidswim.utlis.JsonUtil;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class LoginActivity extends AppCompatActivity {
    private EditText account;
    private EditText password;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取用户名的id
        account = findViewById(R.id.uname);
        //获取密码的id
        password = findViewById(R.id.upass);
    }

    public void LoginAsyncHttpClient(View view){
        //获取用户名的值
        String name=account.getText().toString().trim();
        //获取密码的值
        String pass=password.getText().toString().trim();

        //获取网络上的servlet路径
        String path= Global.GlobalConfiguration.服务器链接.getName() +  "/att/hall/login";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("userLoginName", name)
                .add("userPassword", pass)
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(path)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = Message.obtain();
                message.what = 0;
                message.obj = e.getMessage();
                Looper.prepare();
                Toast.makeText(LoginActivity.this, "登入失败！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                Message message = Message.obtain();
                message.what = 1;
                message.obj = response.body().string();

                String objStr =  message.obj.toString();
                JsonUtil jsonUtil = new JsonUtil();
                UserInfo userInfo = jsonUtil.json2Object(objStr, UserInfo.class);
                if (userInfo.getStatus().equals(KidswimAttEnum.successOrFail.成功.getName())) {
                    String loginNameStr =  userInfo.getUser().getUserName();
                    String loginId = userInfo.getUser().getId();
                    String loginPhone = userInfo.getUser().getPhone();
                    //保存用户信息，创建一个SharedPreferences对象
                    SharedPreferences sharedPreferences = getSharedPreferences("loginUserToken", Context.MODE_PRIVATE);
                    //实例化对象
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //将用户名称(手机号码)保存
                    editor.putString("loginNameStr", loginNameStr);
                    editor.putString("loginId", loginId);
                    editor.putString("loginPhone", loginPhone);
                    //提交数据
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this, "密码或者登录名错误，登入失败！", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }
}
