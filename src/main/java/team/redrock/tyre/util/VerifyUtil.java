package team.redrock.tyre.util;

import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyUtil {

    /**
     * 查询成绩身份验证
     * @param stuNum
     * @param idNum
     * @return
     */
    public Boolean verifyIdentity(String verUrl,String stuNum,String idNum){


        PrintWriter out=null;
        BufferedReader reader=null;
        HttpURLConnection connection=null;
        StringBuilder builder=null;
        try {

            URL url = new URL(verUrl);
            connection = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//发送请求参数类型
            connection.setConnectTimeout(1000);
            connection.setDoOutput(true);
            connection.setDoInput(true);


            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(connection.getOutputStream());
            String content = "stuNum=" + URLEncoder.encode(stuNum, "utf8")+"&idNum="+ URLEncoder.encode(idNum, "utf8");
            out.print(content);

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


        String JsonStr = builder.toString();

        Pattern pattern = Pattern.compile("\\\"status\\\":(.*?),");
        Matcher matcher = pattern.matcher(JsonStr);
        String status = null;
        while (matcher.find()) {

             String[] strs =  matcher.group(0).split(":");
             String str = strs[1].replace(",","");
             status = str;

        }
        if(status.equals("200"))
            return true;
        else{
            return false;
        }


    }



}
