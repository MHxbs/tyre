package team.redrock.tyre.util;

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
//    @Value("${jwzx.verify}")
//    private String verUrl;


String verUrl = "http://hongyan.cqupt.edu.cn/api/verify";
    public Boolean verifyIdentity(String stuNum,String idNum){

        System.out.println("调用此处");
        PrintWriter out=null;
        BufferedReader reader=null;
        HttpURLConnection connection=null;
        StringBuilder builder=null;
        try {
            //创建一个url
            System.out.println("url:"+verUrl);
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
//            // 发送请求参数
//            out.print(page);
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

        System.out.println("身份验证");
        String JsonStr = builder.toString();
        System.out.println("JSONSTR:"+JsonStr);
        Pattern pattern = Pattern.compile("\\\"status\\\":(.*?),");
        Matcher matcher = pattern.matcher(JsonStr);
        String status = null;
        while (matcher.find()) {

             String[] strs =  matcher.group(0).split(":");
             String str = strs[1].replace(",","");
             status = str;
//            System.out.println("status:"+status);
        }
        if(status.equals("200"))
            return true;
        else{
            return false;
        }
//        return JSONObject.parseObject(JsonStr);

    }



}
