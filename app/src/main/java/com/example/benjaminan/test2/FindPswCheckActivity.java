package com.example.benjaminan.test2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import Http.HttpUtlis;

public class FindPswCheckActivity extends AppCompatActivity {
    private TextView account;//用户手机或邮箱
    private TextView inputCode;//验证码
    private Button getCode;
    private Button next;
    private String sAccount;
    private String sCode;
    private boolean codeFlag;

    public static final String TAG="FindPswCheckActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw_check);

        account = (TextView) findViewById(R.id.et_user_phone);
        inputCode = (TextView) findViewById(R.id.et_user_code);
        getCode = (Button) findViewById(R.id.btn_code);
        next = (Button) findViewById(R.id.btn_next);
        codeFlag = false;
        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sAccount = account.getText().toString().trim();
                if(TextUtils.isEmpty(sAccount)){
                    Toast.makeText(FindPswCheckActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = "http://123.207.36.58/getEmail.php?phone=" + sAccount + "&key=phone";
                new AsyncTask<String, Float, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        HttpUtlis http = new HttpUtlis();
                        return http.getRequest(params[0],"utf-8");
                    }

                    @Override
                    protected void onPostExecute(String response) {
                        if(response.equals("nosuchuser")){
                            Toast.makeText(FindPswCheckActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                            return;
                        }else if(response.equals("noemail")){
                            Toast.makeText(FindPswCheckActivity.this, "您未绑定邮箱，无法找回密码", Toast.LENGTH_SHORT).show();
                            return;
                        }else if(response.equals("databaseerror")){
                            Toast.makeText(FindPswCheckActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
                            return;
                        }else {
                            new AsyncTask<String, Float, String>(){
                                @Override
                                protected String doInBackground(String... strings) {
                                    String urlE = "http://123.207.36.58/sendEmail.php?email=" + strings[0] + "&length=6";
                                    HttpUtlis http = new HttpUtlis();
                                    return http.getRequest(urlE,"utf-8");
                                }

                                @Override
                                protected void onPostExecute(String s) {
                                    if(s.equals("fail")){
                                        Toast.makeText(FindPswCheckActivity.this, "服務器網絡異常", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    else {
                                        Toast.makeText(FindPswCheckActivity.this, "验证码已发送至您的邮箱，请查收并于60秒内填入", Toast.LENGTH_SHORT).show();
                                        sCode = s;
                                        codeFlag = true;
                                    }
                                    super.onPostExecute(s);
                                }
                            }.execute(response);
                        }
                        super.onPostExecute(response);
                    }
                }.execute(url);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!codeFlag) {
                    Toast.makeText(FindPswCheckActivity.this, "请先输入账号/获取验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    String code = inputCode.getText().toString().trim();
                    if(code.length() != 6){
                        Log.e(TAG,"Length:" + String.valueOf(code.length()) + "(" + code + ")");
                        Toast.makeText(FindPswCheckActivity.this, "请输入完整验证码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(code.equals(sCode)){
                        Toast.makeText(FindPswCheckActivity.this, "正确验证码", Toast.LENGTH_SHORT).show();
                        codeFlag = false;
                        Intent data=new Intent(FindPswCheckActivity.this, FindPswResetActivity.class);
                        data.putExtra("phone",sAccount);
                        data.putExtra("key","phone");
                        startActivity(data);
                        return;
                    }else{
                        Toast.makeText(FindPswCheckActivity.this, "错误验证码", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }

    private static String getCode(int length){
        char []str = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for(int i = 0; i < length; ++i)
            sb.append(str[random.nextInt(36)]);
        return sb.toString();
    }
}
