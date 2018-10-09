package com.example.benjaminan.test2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Http.HttpUtlis;

public class FindPswResetActivity extends AppCompatActivity {

    private EditText newPsw, newPswAgain;
    private Button confirm;
    private String key;
    private String account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw_reset);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        account = intent.getStringExtra(key);

        newPsw = (EditText) findViewById(R.id.et_new_psw);
        newPswAgain = (EditText) findViewById(R.id.et_new_psw_again);
        confirm = (Button) findViewById(R.id.btn_confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newP = newPsw.getText().toString().trim();
                String newPA = newPswAgain.getText().toString().trim();
                if(TextUtils.isEmpty(newP) || TextUtils.isEmpty(newPA)){
                    Toast.makeText(FindPswResetActivity.this, "请输入新密碼並重複輸入確認", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!newP.equals(newPA)){
                    Toast.makeText(FindPswResetActivity.this, "兩次輸入密碼不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    String md5Psw= MD5.md5(newP);
                    String url = "http://123.207.36.58/resetPsw.php?key=" + key + "&" + key + "=" + account + "&new=" + md5Psw;
                    new AsyncTask<String, Float, String>(){
                        @Override
                        protected String doInBackground(String... strings) {
                            HttpUtlis http = new HttpUtlis();
                            return http.getRequest(strings[0],"utf-8");
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            if(s.equals("fail")){
                                Toast.makeText(FindPswResetActivity.this, "數據庫異常", Toast.LENGTH_SHORT).show();
                                return;
                            }else if(s.equals("success")){
                                Toast.makeText(FindPswResetActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(FindPswResetActivity.this,LoginActivity.class));
                                FindPswResetActivity.this.finish();
                            }else{
                                Toast.makeText(FindPswResetActivity.this, "服務器異常", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            super.onPostExecute(s);
                        }
                    }.execute(url);
                }
            }
        });

    }
}
