package team.redrock.tyre.config;

import team.redrock.tyre.util.SendUrl;

import java.util.Random;

public class RunThread {
    public static void main(String[] args) {
        int t = 2000;
        Random random=new Random();
        for(int i=0;i<t;i++){
            int stu_num= random.nextInt(999)+2017210001;

            SubThreads subThreads = new SubThreads(i,String.valueOf(stu_num));
            subThreads.start();
        }

        /*for (int i=0;i<t;i++) {
            Long begin = System.currentTimeMillis();
            String data = SendUrl.getDataByPOST("http://localhost:8080/kebiao", "stu_num=" + 2017210247 + "&forceFetch=true");
            Long end = System.currentTimeMillis();
            System.out.println("开始时间：" + begin + " 节数时间：" + end + "时间" + (end - begin));
        }*/
    }
}
