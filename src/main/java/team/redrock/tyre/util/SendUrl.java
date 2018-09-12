package team.redrock.tyre.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendUrl {

    // 发送url得到教务处课表页面的源代码
    public static String getDataByPOST(String strUrl,String param)  {

        PrintWriter out=null;
        BufferedReader reader=null;
        HttpURLConnection connection=null;
        StringBuilder builder=null;
        try {
            //创建一个url

            URL url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//发送请求参数类型
            connection.setDoOutput(true);
            connection.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(connection.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();

            //得到连接中输入流，缓存到bufferedReader
            reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"));
            builder = new StringBuilder();
            String line = "";
            //每行每行地读出
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            out.close();
            reader.close();
            connection.disconnect();
        }catch (IOException e){
            e.printStackTrace();
        }finally {

        }

        //返回字符串
        return builder.toString();
    }


    public static String getDataByGet(String strUrl,String param) throws IOException {

        //创建一个url
        if (!param.equals("")){
            strUrl+="?"+param;
        }
        URL url = new URL(strUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //得到连接中输入流，缓存到bufferedReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "UTf-8"));
        StringBuilder builder = new StringBuilder();
        String line = null;
        //每行每行地读出
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        connection.disconnect();
        //返回字符串
        return builder.toString();

    }
}
