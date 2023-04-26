package com.rongtuoyouxuan.qfzego;

public final class KeyCenter {


    private static KeyCenter instance = new KeyCenter();
    // Developers can get appID from admin console.
    // https://console.zego.im/dashboard
    // for example:
    //     private long _appID = 123456789L;
    private long _appID = 1582659105;
    private String _appSign = "94a77dfc0ae6613451673d8a7cef05faa3f8a16288707773f54aff8df369efdc";

    // Developers should customize a user ID.
    // for example:
    //     private String _userID = "zego_benjamin";
    // Developers can get token from admin console.
    // https://console.zego.im/dashboard
    // Note: The user ID used to generate the token needs to be the same as the userID filled in above!
    // for example:
    //     private String _token = "04AAAAAxxxxxxxxxxxxxx";
    public String _token = "04AAAAAGNEDCMAEHlkdmMzZzZkNHI4ZTZ6ZGIAoCTf8MCvFaSTIdu3rRlIwE5cmfzffa5YbKtqy/dkeVdHpddtIHZmz1bjsHeXLn7TvDJx0kKxwSTHJgJJ4YHj5ECsp8V2ExU3q50wy/sS1kVNcnVgf5I6JhwNMM9L//o7IRjXMO0bBkCDf0SB488F2xxz5w3oPkEzlguaye7uJFRx5KodujWc5UzUxDCW7ZOl5QBnVcO6E6bQzI33HOYKqlU=";
    private KeyCenter() {}

    public static KeyCenter getInstance() {
        return instance;
    }

    public long getAppID() {
        return _appID;
    }

    public String getAppSign() {
        return _appSign;
    }

    public final String TENCENT_WB_APP_ID = "TIDA76E3";
    public final String TENCENT_WB_KEY_LICENCE = "NlPnOFnVULw6YUMrQTV3yctzNbqI8V7q3GFHtcvc9v1jp6LBi6pS9oRh5wCe2THHEk1Qcx9j3dyVEJg5hhn4MFsi6F2edork24DY/CR7HzQo18FLum4t9dbbk12mzqQWzI1CAgsOSaC7t7HFpR6HHZ8MXDQ49JQbp8kq7EPHWBulhGi4EiqOIM9PrDeRd2W409UKUSltW5H8Op8Mis4l5yJ2aWxjgUI0Cdkht/9HPiwANqCOt3fR+ZvY6xnzvwZz/Iv80CNLRsdbIOvU09v+pkHS+lggvu/H53UNtzN/aqxDxgHHjY/ZDmDIar3DoVW1ugLUCLfrnQsX0J+4cyHbgw==";
    public final String TENCENT_WB_VERSION= "1.0.0";


}
