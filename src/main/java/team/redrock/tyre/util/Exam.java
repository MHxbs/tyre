package team.redrock.tyre.util;

import java.io.IOException;

public class Exam {

    public static String getExam(String stuNun){
        String url="http://jwzx.cqupt.edu.cn/ksap/showKsap.php?type=stu&id=";
        url+=stuNun;
        String data="";

        try {
            data=SendUrl.getDataByGet(url,"");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(data);
        return "";
    }
}
