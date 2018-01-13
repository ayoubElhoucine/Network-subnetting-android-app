package com.tofaha.vlsmtofaha.util;

/**
 * Created by Ayoubb on 13/11/2017.
 */

public class Util {

    private static final String ip4Regex ="^(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9]{1,2})(\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9]{1,2})){3}$";

    public static boolean isValidIp(String ip){
        return ip.matches(ip4Regex);
    }

}
