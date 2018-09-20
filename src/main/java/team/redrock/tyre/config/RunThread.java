package team.redrock.tyre.config;

import team.redrock.tyre.util.SendUrl;

import java.util.Random;

public class RunThread {
    public static void main(String[] args) {
        int t = 999;
        Random random=new Random();

        for(int i=0;i<t;i++){
            Long begin =System.currentTimeMillis();
            //int stu_num= random.nextInt(999)+2017210001;
            int stu_num=2017210001+i;
            SubThreads subThreads = new SubThreads(i,String.valueOf(stu_num));
            subThreads.start();

            Long end=System.currentTimeMillis();
            System.out.println("开始时间："+begin+" 线程："+i+" stu_num:"+stu_num+" 节数时间："+end+"时间"+(end-begin));

        }

        /*for (int i=0;i<t;i++) {
            Long begin = System.currentTimeMillis();
            String data = SendUrl.getDataByPOST("http://localhost:8080/kebiao", "stu_num=" + 2017210247);
            Long end = System.currentTimeMillis();
            System.out.println("开始时间：" + begin + " 节数时间：" + end + "时间" + (end - begin));
        }*/
    }
}
