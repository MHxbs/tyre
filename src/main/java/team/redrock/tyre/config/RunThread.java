package team.redrock.tyre.config;

import java.util.Random;

public class RunThread {
    public static void main(String[] args) {
        int t = 100000;
        Random random=new Random();

        for(int i=0;i<t;i++){
            int stu_num= random.nextInt(999)+2017210001;
            SubThreads subThreads = new SubThreads(i,String.valueOf(stu_num));
            subThreads.start();
        }
    }
}
