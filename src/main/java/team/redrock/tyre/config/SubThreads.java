package team.redrock.tyre.config;

import team.redrock.tyre.util.SendUrl;

import java.lang.Thread;

public class SubThreads extends Thread {

    int name;
    String stu_num;

    public SubThreads(int name, String stu_num){
        this.name = name;
        this.stu_num=stu_num;
    }

    @Override
    public void run(){
        while(true){
            Long begin =System.currentTimeMillis();
            String data=SendUrl.getDataByPOST("http://localhost:8080/kebiao","stu_num="+stu_num+"&forceFetch=true");
            Long end=System.currentTimeMillis();
            System.out.println("开始时间："+begin+"线程："+name+"stu_num:"+stu_num+"节数时间："+end+"时间"+(end-begin));


        }
    }
}

