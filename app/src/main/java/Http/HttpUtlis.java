package Http;

/**
 * Created by BenjaminAn on 2018/10/3.
 */

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static com.example.benjaminan.test2.LoginActivity.TAG;

public class HttpUtlis {
    /**
     *get请求封装
     */
    public String getRequest(/*callBack callback,*/ String sb, String encode) {

        try {
            URL path = new URL(sb.toString());
            Log.e(TAG, "URL: " + path);
            if (path!=null) {
                HttpURLConnection con = (HttpURLConnection) path.openConnection();
                con.setRequestMethod("GET");    //设置请求方式
                con.setConnectTimeout(3000);    //链接超时3秒
                con.setDoOutput(true);
                con.setDoInput(true);
                OutputStream os = con.getOutputStream();
                os.write(sb.toString().getBytes(encode));
                os.close();
                if (con.getResponseCode() == 200) {    //应答码200表示请求成功
                    //callback.event(onSucessResopond(encode, con));
                    return onSucessResopond(encode, con);
                }
            }
        } catch (Exception error) {
            error.printStackTrace();
            //callback.event("netfail");
            return  "netfail";
        }
        return "netfail";
    }

    /**
     * POST请求
     */
    public void postRequest(callBack callback, Map<String,String> params, String encode){
        StringBuffer sb = new StringBuffer();
        if (params!=null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {    //增强for遍历循环添加拼接请求内容
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        try {
            URL path = new URL(sb.toString());
            if (path!=null){
                HttpURLConnection con = (HttpURLConnection) path.openConnection();
                con.setRequestMethod("POST");   //设置请求方法POST
                con.setConnectTimeout(3000);
                con.setDoOutput(true);
                con.setDoInput(true);
                byte[] bytes = sb.toString().getBytes();
                OutputStream outputStream = con.getOutputStream();
                outputStream.write(bytes);
                outputStream.close();
                if (con.getResponseCode()==200){
                    onSucessResopond(encode,  con);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            callback.event("netfail");
        }
    }

    private String onSucessResopond(String encode, HttpURLConnection con) throws IOException {
        InputStream inputStream = con.getInputStream();
        String str = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//创建内存输出流
        int len = 0;
        byte[] bytes = new byte[1024];
        if (inputStream != null) {
            while ((len = inputStream.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            str = new String(baos.toByteArray(), encode);
        }
        return str;
    }

}

