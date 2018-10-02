package team.redrock.tyre.util;




import com.alibaba.fastjson.JSONObject;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsUtils {
    NormalUtils normalUtils = new NormalUtils();


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

    /**
     * 转换时间格式
     * @param originDate
     * @return
     */
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

    /**
     * 下载新闻附加文件的工具类
     * @param urlStr
     * @param savePath
     * @param fileName
     * @throws IOException
     */
    public String downloadFile(String urlStr,String savePath,String fileName) throws IOException {     //返回文件的后缀名
        URL url = new URL(urlStr);
        File file = null;
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//        conn.setChunkedStreamingMode(0);

        InputStream inputStream = conn.getInputStream();

        System.out.println(conn.getContentType());
        String contentType = conn.getContentType();
        String suffix = normalUtils.getFileType(contentType);                                   //通过contentType获取后缀名
        byte[] getData = readInputStream(inputStream);

        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        if(suffix!=null){
            file = new File(saveDir+File.separator+fileName+suffix);
        }else{
            file = new File(saveDir+File.separator+fileName);
        }

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }

        if(suffix!=null){
            return suffix;
        }else{
            return "";
        }

    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
