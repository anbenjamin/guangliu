package com.example.benjaminan.test2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import Http.HttpUtlis;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private TextView tv_main_title;//标题
    private TextView tv_back;//返回按钮
    private Button btn_register;//注册按钮
    //用户名，密码，再次输入的密码的控件
    private EditText et_user_name,et_user_phone,et_psw,et_psw_again,et_user_email;
    //用户名，密码，再次输入的密码的控件的获取值
    private String userName,userPhone,psw,pswAgain,email;
    //标题布局
    private RelativeLayout rl_title_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置页面布局 ,注册界面
        setContentView(R.layout.activity_register);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        //从main_title_bar.xml 页面布局中获取对应的UI控件
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        tv_back=findViewById(R.id.tv_back);
        //布局根元素
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);
        //从activity_register.xml 页面中获取对应的UI控件
        btn_register=findViewById(R.id.btn_register);
        et_user_name=findViewById(R.id.et_user_name);
        et_user_phone=findViewById(R.id.et_user_phone);
        et_psw=findViewById(R.id.et_psw);
        et_psw_again=findViewById(R.id.et_psw_again);
        et_user_email = findViewById(R.id.et_user_email);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回键
                RegisterActivity.this.finish();
            }
        });
        //注册按钮
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入在相应控件中的字符串
                getEditString();
                //判断输入框内容
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this, "请输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(userPhone)){
                    Toast.makeText(RegisterActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "请输入郵箱", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(pswAgain)){
                    Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!psw.equals(pswAgain)){
                    Toast.makeText(RegisterActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    String md5Psw= MD5.md5(psw);
                    SimpleDateFormat df = new SimpleDateFormat("MMddHHmmss");
                    String url = "http://123.207.36.58/newUser.php?phone=" + userPhone + "&password=" + md5Psw + "&email=" + email + "&nickname=" + userName + "&UID=" + df.format(new Date()) + "&anti_level=2&anti_time=60";

                    new AsyncTask<String, Float, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            HttpUtlis http = new HttpUtlis();
                            return http.getRequest(params[0],"utf-8");
                        }

                        @Override
                        protected void onPostExecute(String response) {
                            Log.e(TAG, "Response: " + response );
                            if(response.equals("userexist")){
                                Toast.makeText(RegisterActivity.this, "用户已存在", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(response.equals("fail")){
                                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(response.equals("netfail")){
                                Toast.makeText(RegisterActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(response.equals("databaseerror")){
                                Toast.makeText(RegisterActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
                                return;
                            }else {
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                                Intent data = new Intent();
                                data.putExtra("userPhone", userPhone);
                                setResult(RESULT_OK, data);
                                //RESULT_OK为Activity系统常量，状态码为-1，
                                // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                                RegisterActivity.this.finish();
                            }
                            super.onPostExecute(response);
                        }
                    }.execute(url);
                }
            }
        });
    }
    /**
     * 获取控件中的字符串
     */
    private void getEditString(){
        email = et_user_email.getText().toString().trim();
        userName=et_user_name.getText().toString().trim();
        userPhone=et_user_phone.getText().toString().trim();
        psw=et_psw.getText().toString().trim();
        pswAgain=et_psw_again.getText().toString().trim();
    }
    /**
     * 从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
     */
    private boolean isExistUserName(String userPhone){
        boolean has_userPhone=false;
        //mode_private SharedPreferences sp = getSharedPreferences( );
        // "loginInfo", MODE_PRIVATE
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取密码
        String spPsw=sp.getString(userPhone, "");//传入用户名获取密码
        //如果密码不为空则确实保存过这个用户名
        if(!TextUtils.isEmpty(spPsw)) {
            has_userPhone=true;
        }
        return has_userPhone;
    }
    /**
     * 保存账号和密码到SharedPreferences中SharedPreferences
     */
    private void saveRegisterInfo( String userName,String userPhone,String psw){
        String md5Psw = MD5.md5(psw);//把密码用MD5加密
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor=sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString(userPhone, md5Psw);
        editor.putString(userPhone+"name",userName);
        editor.putString(userPhone+"name",userPhone);
        //提交修改 editor.commit();
        editor.commit();
    }
}