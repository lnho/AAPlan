package com.lnho.aaplan.commons.mybatis.util;

/**
 * User: wangj
 * Date: 13-11-20
 * Time: 上午11:05
 */
public class IPUtil {

    public static long ip2long(String ip) {
        String[] ips = ip.split("[.]");
        long num =  16777216L*Long.parseLong(ips[0]) + 65536L*Long.parseLong(ips[1]) + 256*Long.parseLong(ips[2]) + Long.parseLong(ips[3]);
        return num;
    }

    /**
     * 整数转成ip地址.
     * @param ipLong
     * @return
     */
    public static String long2ip(long ipLong) {
        //long ipLong = 1037591503;
        long mask[] = {0x000000FF,0x0000FF00,0x00FF0000,0xFF000000};
        long num = 0;
        StringBuffer ipInfo = new StringBuffer();
        for(int i=0;i<4;i++){
            num = (ipLong & mask[i])>>(i*8);
            if(i>0) ipInfo.insert(0,".");
            ipInfo.insert(0,Long.toString(num,10));
        }
        return ipInfo.toString();
    }
}
