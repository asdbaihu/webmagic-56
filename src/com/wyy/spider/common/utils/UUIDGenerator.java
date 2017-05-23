package com.wyy.spider.common.utils;

import java.util.Random;
import java.util.UUID;

public class UUIDGenerator { 
    public UUIDGenerator() { 
    } 
    /** 
     * 获得一个UUID 
     * @return String UUID 
     */ 
    public synchronized static String getUUID(){ 
        String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
    } 
    
    /** 
     * 自定义规则生成32位编码 
     * @return string 
     */   
    public static String getUUIDByRules(String rules)   
    {   
      int rpoint = 0;   
        StringBuffer generateRandStr = new StringBuffer();   
        Random rand = new Random();   
        int length = 32;   
        for(int i=0;i<length;i++)   
        {   
            if(rules!=null){   
                rpoint = rules.length();   
                int randNum = rand.nextInt(rpoint);   
//                generateRandStr.append(radStr.substring(randNum,randNum+1));   
            }   
        }   
        return generateRandStr+"";   
    }  
    
    /** 
     * 获得指定数目的UUID 
     * @param number int 需要获得的UUID数量 
     * @return String[] UUID数组 
     */ 
    public static String[] getUUID(int number){ 
        if(number < 1){ 
            return null; 
        } 
        String[] ss = new String[number]; 
        for(int i=0;i<number;i++){ 
            ss[i] = getUUID(); 
        } 
        return ss; 
    } 
    public static void main(String[] args){ 
        String[] ss = getUUID(10); 
        for(int i=0;i<ss.length;i++){ 
            System.out.println(ss[i]); 
        } 
    } 
}  
