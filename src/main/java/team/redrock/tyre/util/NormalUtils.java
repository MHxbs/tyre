package team.redrock.tyre.util;



import lombok.extern.slf4j.Slf4j;
import team.redrock.tyre.domain.response.EmptyResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class NormalUtils {
    private static Map<String, String> types;

    /**
     * 空教室参数转换
     *
     * @param SectionNum
     * @param emptyResponse
     * @return
     */
    public String getSection(String SectionNum, EmptyResponse emptyResponse){
        String returnChar = null;
        if(isInteger(SectionNum)){

        switch(SectionNum){
            case "0": returnChar = "12";
            break;
            case "1": returnChar = "34";
            break;
            case "2": returnChar = "56";
            break;
            case "3": returnChar = "78";
            break;
            case "4": returnChar = "90";
            break;
            case "5": returnChar = "ab";
            break;
            default: returnChar = "null";
                break;
        }
        }else{
            returnChar = "null";
            emptyResponse.setStatus(-1);
        }
        return returnChar;
    }

    /**
     * 判断是否是数字
     *
     * @param str
     * @return
     */
    public  boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 筛选出几教
     * @param buildNum
     * @param rooms
     * @return
     */
    public List<String> selectBuild(String buildNum,List<String> rooms){
        List<String> selectedrooms = new ArrayList<>();
        for (String room: rooms) {
            if(room.substring(0,1).equals(buildNum)){

                selectedrooms.add(room);
            }
        }
        return selectedrooms;
    }

    /**
     * 获取文件类型
     *
     * @param contentType
     * @return
     */
    public String getFileType(String contentType) {

        String suffix = null;
        suffix = getTypeByContentType(contentType);
        if (suffix != null) {
            return suffix;
        } else {
            log.error("后缀名不符合");
        }
        return null;
    }

    /**
     * 初始化文件类型
     */
    private static void initContentType() {
        types = new HashMap<String, String>();
        types.put("application/pdf", ".pdf");
        types.put("application/msword", ".doc");
        types.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", ".docx");
        types.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ".xlsx");
        types.put("text/plain", ".txt");
        types.put("application/x-xls", ".xls");
        types.put("application/-excel", ".xls");
        types.put("application/vnd.openxmlformats-officedocument.presentationml.presentation", ".pptx");
        types.put("application/zip", ".zip");
        types.put("text/html", ".html");
        types.put("image/jpeg", ".jpg");
        types.put("application/x-rar", ".rar");
        types.put("image/jpeg", ".jpeg");
        types.put("application/x-rtf", ".rtf");
        types.put("message/rfc822", ".mht");
        types.put("application/x-ppt", ".ppt");
        types.put("application/xml", ".xml");
        types.put("audio/x-flac", ".flac");
        types.put("application/vnd.rn-realmedia", ".rmvb");
        types.put("video/mp4", ".mp4");
        types.put("audio/mpeg", ".mp3");

    }

    /***
     * 获取文件后缀
     * @param contentType
     * @return
     */
    private static String getTypeByContentType(String contentType) {
        if (types.containsKey(contentType))
            return types.get(contentType);
        return null;
    }

    /**
     * 遍历文件夹查询文件
     * @param filename
     * @param savedPath
     * @return
     */
    public  String traverseFolder(String filename,String savedPath) {
        File[] files =null;
        String path = savedPath;
        File file = new File(path);

        if (file.exists()) {
            files = file.listFiles();
            if (files.length == 0) {
                log.warn("文件夹是空的!");
                return null;
            } else {
                for (File file2 : files) {
                    if(file2.getName().startsWith(filename)){
                        log.info("找到文件:" + file2.getPath());
                        return file2.getPath();
                    }

                }
            }
        } else {
            log.error("文件不存在!");
        }
        return null;
    }
}
