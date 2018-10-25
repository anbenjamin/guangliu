package com.example.benjaminan.test2;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import Http.HttpUtlis;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG="MainActivity";

    private TextView tv_main_title;//标题
    private TextView tv_back,tv_register,tv_find_psw;//返回键,显示的注册，找回密码
    private Button btn_login;//登录按钮
    private String userPhone,psw;//获取的用户名，密码，加密密码
    private EditText et_user_phone,et_psw;//编辑框

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        init();
    }
    //获取界面控件
    private void init() {
        //从main_title_bar中获取的id
        //tv_main_title=findViewById(R.id.tv_main_title);
        //tv_main_title.setText("登录");
        //tv_back=findViewById(R.id.tv_back);
        //从activity_login.xml中获取的
        tv_register=findViewById(R.id.tv_register);
        tv_find_psw=findViewById(R.id.tv_find_psw);
        btn_login=findViewById(R.id.btn_login);
        et_user_phone=findViewById(R.id.et_user_phone);
        et_psw=findViewById(R.id.et_psw);
        //返回键的点击事件
        //tv_back.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
                //登录界面销毁
        //       LoginActivity.this.finish();
        //   }
        //});
        //立即注册控件的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //为了跳转到注册界面，并实现注册功能
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        //找回密码控件的点击事件
        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, FindPswCheckActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        //登录按钮的点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始登录，获取用户名和密码 getText().toString().trim();
                userPhone=et_user_phone.getText().toString().trim();
                psw=et_psw.getText().toString().trim();
                // TextUtils.isEmpty
                if(TextUtils.isEmpty(userPhone)){
                    Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                    // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
                }else {
                    String md5Psw= MD5.md5(psw);
                    //Map<String,String> map=new HashMap<>();
                    //map.put("http://123.207.36.58/login.php","?");
                    //map.put("phone",userPhone);
                    //map.put("password",md5Psw);

                    String url = "http://123.207.36.58/login.php?phone=" + userPhone + "&password=" + md5Psw;

                    new AsyncTask<String, Float, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            HttpUtlis http = new HttpUtlis();
                            return http.getRequest(params[0],"utf-8");
                        }

                        @Override
                        protected void onPostExecute(String response) {
                            if(response.equals("nosuchuser")){
                                Toast.makeText(LoginActivity.this, "用6户不存在", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(response.equals("passworderror")){
                                Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(response.equals("netfail")){
                                Toast.makeText(LoginActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(response.equals("databaseerror")){
                                Toast.makeText(LoginActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
                                return;
                            }else {
                                Toast.makeText(LoginActivity.this, "登录成功，UID：" + response, Toast.LENGTH_SHORT).show();

                                //登录成功后关闭此页面进入主页
                                Intent data=new Intent(LoginActivity.this, MainActivity.class);
                                //datad.putExtra( ); Phone , value ;
                                data.putExtra("UID",response);
                                //RESULT_OK为Activity系统常量，状态码为-1
                                // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                                setResult(RESULT_OK,data);
                                //跳转到主界面，登录成功的状态传递到 MainActivity 中
                                startActivity(data);
                                //销毁登录界面
                                LoginActivity.this.finish();
                            }
                            super.onPostExecute(response);
                        }
                    }.execute(url);
                }
            }
        });
    }
    /**
     *从SharedPreferences中根据用户名读取密码
     */
    private String readPsw(String userPhone){
        //getSharedPreferences("loginInfo",MODE_PRIVATE);
        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //sp.getString() userPhone, "";
        return sp.getString(userPhone , "");
    }
    /**
     *保存登录状态和登录用户名到SharedPreferences中
     */
    private void saveLoginStatus(boolean status,String userPhone){
        //saveLoginStatus(true, userPhone);
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserPhone", userPhone);
        //提交修改
        editor.commit();
    }
    /**
     * 注册成功的数据返回至此
     * @param requestCode 请求码
     * @param resultCode 结果码
     * @param data 数据
     */
    @Override
    //显示数据， onActivityResult
    //startActivityForResult(intent, 1); 从注册界面中获取数据
    //int requestCode , int resultCode , Intent data
    // LoginActivity -> startActivityForResult -> onActivityResult();
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //是获取注册界面回传过来的用户名
            // getExtra().getString("***");
            String userPhone=data.getStringExtra("userPhone");
            if(!TextUtils.isEmpty(userPhone)){
                //设置用户名到 et_user_Phone 控件
                et_user_phone.setText(userPhone);
                //et_user_Phone控件的setSelection()方法来设置光标位置
                et_user_phone.setSelection(userPhone.length());
            }
        }
    }
}