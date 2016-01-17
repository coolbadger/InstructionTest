package main;

import importData.ImportData;
import importData.PreStowageInfo;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by leko on 2016/1/16.
 */
public class SWGeneratePrestowageData extends SwingWorker {

    private static ArrayList<Integer[]>  allocation = new ArrayList<Integer[]>();
    private static Integer[] tier={2,4,6,8,10,12,14,16,18,20,82,84,86,88,90,92,94,96,98};
    private static Integer[] moveorderchange ={5,3,4,0,3,-3,2,-6,1,-9};
    private static Integer ROWnum = 10;
    private static ArrayList<PreStowageInfo> preStowageInfoArrayList = new ArrayList<PreStowageInfo>();

    @Override
    protected Object doInBackground() throws Exception {
        System.out.println("开始生成预配信息");
        {
            Integer[] a1={1,100,60};                        //第1个舱有100个大箱60个小箱
            Integer[] a2={3,40,80};
            Integer[] a3={4,60,60};
            Integer[] a4={7,140,100};
            Integer[] a5={8,100,160};
            Integer[] a6={9,100,60};
            Integer[] a7={10,60,80};
            Integer[] a8={12,70,80};
            Integer[] a9={14,80,68};
            allocation.add(a1);
            allocation.add(a2);
            allocation.add(a3);
            allocation.add(a4);
            allocation.add(a5);
            allocation.add(a6);
            allocation.add(a7);
            allocation.add(a8);
            allocation.add(a9);
        }
        PreStowageInfo newPrestowageInfo;
        for (Integer[] allo:allocation){
            Integer VHTID = allo[0];            //舱ID
            Integer cnt=1;
            //奇数倍
            Integer TIERnum1 = allo[2]/2/ROWnum;  //小箱占的层号
            if (allo[2]/2%ROWnum!=0)
            {
                TIERnum1++;
            }
            for (Integer i = (VHTID-1)*4+1;i<(VHTID*4);i+=2)
            {
                Integer VBYBAYID = i;           //倍ID
                for (Integer j=0;j<TIERnum1;j++)
                {
                    Integer VTRTIERNO = tier[j];       //层号
                    {
                        for (Integer k=1;k<=10;k++)
                        {
                            if (j*ROWnum+k>allo[2]/2){
                                System.out.println("break,"+j+" "+ROWnum+" "+allo[2]/2);
                                break;
                            }
                            Integer VRWROWNO = k;      //排号
                            Integer SIZE = 20;         //尺寸20
                            String GROUPID="G1";      //属性组
                            Integer WEIGHT=25;         //重量
                            Integer MOVEORDER=cnt++;   //移动顺序
                            if (VTRTIERNO>=82){
                                MOVEORDER = MOVEORDER + moveorderchange[k-1];
                            }
                            System.out.println(VHTID+" "+VBYBAYID+" "+VTRTIERNO+" "+VRWROWNO+" "+SIZE+" "+GROUPID+" "+WEIGHT+ " "+MOVEORDER);
                            newPrestowageInfo = new PreStowageInfo();
                            newPrestowageInfo.setVHT_ID(VHTID);
                            newPrestowageInfo.setVBY_BAYID(VBYBAYID);
                            newPrestowageInfo.setVTR_TIERNO(VTRTIERNO);
                            newPrestowageInfo.setVRW_ROWNO(VRWROWNO);
                            newPrestowageInfo.setSIZE(SIZE);
                            newPrestowageInfo.setGROUP_ID(GROUPID);
                            newPrestowageInfo.setWEIGHT(WEIGHT);
                            newPrestowageInfo.setMOVE_ORDER(MOVEORDER);
                            preStowageInfoArrayList.add(newPrestowageInfo);
                        }
                    }
                }
            }
            //偶数倍
            Integer VBYBAYID = 4*VHTID-2;       //倍ID
            Integer TIERnum = (allo[1]+allo[2]/2)/ROWnum;  //小箱+大箱占的层号
            if ((allo[1]+allo[2]/2)%ROWnum!=0)
            {
                TIERnum++;
            }
            //小箱排的不正好
            if ((allo[2]/2) % 10!=0){
                Integer breaktier = allo[2]/2/10;
                Integer VTRTIERNO = tier[breaktier];      //层号
                Integer breakk=allo[2]/2%10+1;
                for (Integer k=breakk;k<=10;k++)
                {
                    if (breaktier*ROWnum+k>allo[1]+allo[2]/2){
                        break;
                    }
                    Integer VRWROWNO = k;      //排号
                    Integer SIZE = 40;         //尺寸20
                    String GROUPID="G2";      //属性组
                    Integer WEIGHT=25;         //重量
                    Integer MOVEORDER=cnt++;   //移动顺序
                    if (VTRTIERNO>=82){
                        MOVEORDER = MOVEORDER + moveorderchange[k-1];
                    }
                    System.out.println(VHTID+" "+VBYBAYID+" "+VTRTIERNO+" "+VRWROWNO+" "+SIZE+" "+GROUPID+" "+WEIGHT+ " "+MOVEORDER);
                    newPrestowageInfo = new PreStowageInfo();
                    newPrestowageInfo.setVHT_ID(VHTID);
                    newPrestowageInfo.setVBY_BAYID(VBYBAYID);
                    newPrestowageInfo.setVTR_TIERNO(VTRTIERNO);
                    newPrestowageInfo.setVRW_ROWNO(VRWROWNO);
                    newPrestowageInfo.setSIZE(SIZE);
                    newPrestowageInfo.setGROUP_ID(GROUPID);
                    newPrestowageInfo.setWEIGHT(WEIGHT);
                    newPrestowageInfo.setMOVE_ORDER(MOVEORDER);
                    preStowageInfoArrayList.add(newPrestowageInfo);
                }
            }
            for (Integer j=TIERnum1;j<TIERnum;j++)
            {
                System.out.println("放置大箱");
                Integer VTRTIERNO = tier[j];       //层号
                {
                    for (Integer k=1;k<=10;k++)
                    {
                        if (j*ROWnum+k>(allo[1]+allo[2]/2)){
                            break;
                        }
                        Integer VRWROWNO = k;      //排号
                        Integer SIZE = 40;         //尺寸40
                        String GROUPID="G2";      //属性组
                        Integer WEIGHT=25;         //重量
                        Integer MOVEORDER=cnt++;   //移动顺序
                        if (VTRTIERNO>=82){
                            MOVEORDER = MOVEORDER + moveorderchange[k-1];
                        }
                        System.out.println(VHTID+" "+VBYBAYID+" "+VTRTIERNO+" "+VRWROWNO+" "+SIZE+" "+GROUPID+" "+WEIGHT+ " "+MOVEORDER);
                        newPrestowageInfo = new PreStowageInfo();
                        newPrestowageInfo.setVHT_ID(VHTID);
                        newPrestowageInfo.setVBY_BAYID(VBYBAYID);
                        newPrestowageInfo.setVTR_TIERNO(VTRTIERNO);
                        newPrestowageInfo.setVRW_ROWNO(VRWROWNO);
                        newPrestowageInfo.setSIZE(SIZE);
                        newPrestowageInfo.setGROUP_ID(GROUPID);
                        newPrestowageInfo.setWEIGHT(WEIGHT);
                        newPrestowageInfo.setMOVE_ORDER(MOVEORDER);
                        preStowageInfoArrayList.add(newPrestowageInfo);
                    }
                }
            }
        }
        ImportData.preStowageInfoArrayList = preStowageInfoArrayList;
        System.out.print("结束生成预配信息");
        return null;
    }
}
