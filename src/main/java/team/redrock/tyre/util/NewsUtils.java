package team.redrock.tyre.util;




import com.alibaba.fastjson.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsUtils {
    /**网络请求获取新闻列表
     *
     * @param newsurl
     * @return
     */
    public JSONObject getNewsList(String newsurl){
        PrintWriter out=null;
        BufferedReader reader=null;
        HttpURLConnection connection=null;
        StringBuilder builder=null;
        try {


            URL url = new URL(newsurl);
            connection = (HttpURLConnection) url.openConnection();
            // 发送POST请求必须设置如下两行
            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");//发送请求参数类型
            connection.setConnectTimeout(1000);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(connection.getOutputStream());

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
        return JSONObject.parseObject(JsonStr);

    }

    public String getDate(String originDate){
        String[] datetimes =  originDate.split("\\.");
        String datetime  = new String();
        for(int i=0;i<3;i++){
            if (datetimes[i].charAt(0)=="0".charAt(0)){
                datetimes[i] = datetimes[i].substring(1);
            }
        }
        datetime = datetimes[0]+"年"+datetimes[1]+"月"+datetimes[2]+"日";
        return datetime;
    }
}