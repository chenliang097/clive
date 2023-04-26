package com.rongtuoyouxuan.chatlive.util;

public class VersionCompareUtil {

    public static int versionCompare(String version1, String version2) {
        String[] versionArr1 = version1.split("\\.");
        String[] versionArr2 = version2.split("\\.");
        int minLen = Math.min(versionArr1.length, versionArr2.length);
//        System.out.println("minLen ---> " + minLen);
        int diff = 0;
        for (int i = 0; i < minLen; i++) {
            String v1 = versionArr1[i];
            String v2 = versionArr2[i];
            diff = v1.length() - v2.length();
//            System.out.println("v1 ---> " + v1 + " v2 ---> " + v2 + " diff ---> " + diff);
            if (diff == 0) {
                diff = v1.compareTo(v2);
//                System.out.println(" diff ---> " + diff);
            }
            if (diff != 0) {
                break;
            }
        }
        diff = (diff != 0) ? diff : (versionArr1.length - versionArr2.length);
        return diff;
    }

    public static void main(String[] args) {
        System.out.println(versionCompare("1.2", "1.3"));
        System.out.println(versionCompare("1.2", "1.1"));
        System.out.println(versionCompare("1.2", "1.1.4"));
        System.out.println(versionCompare("9.9", "10.8"));
        System.out.println(versionCompare("9.a", "9.b"));

        System.out.println(versionCompare("1.0", "1.0.0.1"));
        System.out.println(versionCompare("9.9", "10.8.2"));
    }

}
