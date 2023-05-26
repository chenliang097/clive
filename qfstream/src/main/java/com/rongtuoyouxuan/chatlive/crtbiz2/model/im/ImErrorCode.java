package com.rongtuoyouxuan.chatlive.crtbiz2.model.im;

/**
 * @Description :IM中错误码常量
 * @Author : jianbo
 * @Date : 2022/8/3  22:51
 */
public enum ImErrorCode {

    e1001(1001, "Access token缺失"),
    e1002(1002, "Debug模式缺少uid参数"),
    e1003(1003, "无效的token"),
    e2001(2001, "非法的请求参数"),
    e2002(2002, "不被支持的请求方法"),
    e2003(2003, "没有匹配的请求处理器"),
    e2004(2004, "不被支持的MediaType"),
    e2005(2005, "非法的请求"),
    e2006(2006, "下载文件不存在"),
    e2007(2007, "群组禁止转发"),
    e2008(2008, "消息已被处理"),
    e3000(3000, "网络异常，请稍候再试"),
    e3001(3001, "调用远程服务失败"),
    e3002(3002, "POJO转换失败"),
    e3003(3003, "获取i18n message失败"),
    e3004(3004, "Spring mvc 标准异常"),
    e3005(3005, "调用远程OAUTH服务失败"),
    e3006(3006, "服务器正在玩儿命更新中...", "网络异常，请稍候再试"),
    e8000(8000, "网络中断,请检查你的网络", "网络中断,请检查你的网络"),
    e8001(8001, "HTTP请求错误"),
    e8002(8002, "读取HTTP响应错误"),
    e8003(8003, "解析json数据异常"),
    e8010(8010, "存储卡缺失"),
    e8011(8011, "数据库缺失"),
    e8012(8012, "数据库初始化错误"),
    e8015(8015, "数据库脚本执行错误"),
    e8016(8016, "数据库数据读取错误"),
    e8017(8017, "数据库数据保存错误"),
    e8018(8018, "数据库数据删除错误"),
    e8019(8019, "数据库数据修改错误"),
    e8020(8020, "文件缺失"),
    e8021(8021, "文件读取异常"),
    e9000(9000, "用户名或密码错误", "用户名或密码错误");

    public int code;
    private String content;
    private String desc;

    ImErrorCode(int code, String content, String desc) {
        this.code = code;
        this.desc = desc;
        this.content = content;
    }

    ImErrorCode(int code, String content) {
        this.code = code;
        this.content = content;
    }

    public int code() {
        return this.code;
    }

    public String content() {
        return this.content;
    }

    public String desc() {
        return this.desc == null ? "网络异常，请稍候再试" : this.desc;
    }

    public static String desc(int code) {
        ImErrorCode[] codes;
        int length = (codes = values()).length;

        for (int i = 0; i < length; ++i) {
            ImErrorCode error = codes[i];
            if (error.code == code) {
                return error.desc();
            }
        }
        return "未知错误";
    }
}
