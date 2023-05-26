package com.rongtuoyouxuan.chatlive.crtbiz2.model.login.response;


import androidx.annotation.Keep;

import com.rongtuoyouxuan.chatlive.crtbiz2.constanst.Config;
import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;

import java.io.Serializable;

@Keep
public class UserInfo extends BaseModel implements Serializable {
    private String token;
    private int is_write_off;
    private UserInfoBean user_info = new UserInfoBean();
    private boolean is_register;//是否是注册
    private String login_type;

    public String getLogin_type() {
        return login_type == null ? "" : login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public boolean is_register() {
        return is_register;
    }

    public void setIs_register(boolean is_register) {
        this.is_register = is_register;
    }

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIs_write_off() {
        return is_write_off;
    }

    public void setIs_write_off(int is_write_off) {
        this.is_write_off = is_write_off;
    }

    public UserInfoBean getUser_info() {
        return user_info;
    }

    public void setUser_info(UserInfoBean user_info) {
        this.user_info = user_info;
    }

    public static class UserInfoBean {
        private long user_id = 0;
        private String username;
        private String region_code;
        private String mobile;
        private String email;
        private String nickname;
        private int status;
        private int gender;
        private String avatar;
        private String birthday;
        private int has_password;
        private String write_off_at;
        private String show_id;
        private String role;
        private String signature;
        private long user_level;
        private long anchor_level;
        public int realcert_status;//0是普通用户，1主播

        public long getUser_level() {
            return user_level;
        }

        public void setUser_level(long user_level) {
            this.user_level = user_level;
        }

        public long getAnchor_level() {
            return anchor_level;
        }

        public void setAnchor_level(long anchor_level) {
            this.anchor_level = anchor_level;
        }

        public String getSignature() {
            return signature == null ? "" : signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getRole() {
            return role == null ? "" : role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public long getUserId() {
            return user_id;
        }

        public void setUserId(Long user_id) {
            this.user_id = user_id;
        }

        public String getUsername() {
            return username == null ? "" : username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRegion_code() {
            return region_code == null ? "" : region_code;
        }

        public void setRegion_code(String region_code) {
            this.region_code = region_code;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email == null ? "" : email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNickname() {
            return nickname == null ? "" : nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getAvatar() {
            return avatar == null ? "" : avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBirthday() {
            return birthday == null ? "" : birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getHas_password() {
            return has_password;
        }

        public void setHas_password(int has_password) {
            this.has_password = has_password;
        }

        public String getWrite_off_at() {
            return write_off_at == null ? "" : write_off_at;
        }

        public void setWrite_off_at(String write_off_at) {
            this.write_off_at = write_off_at;
        }

        public String getShow_id() {
            return show_id == null ? "" : show_id;
        }

        public void setShow_id(String show_id) {
            this.show_id = show_id;
        }

        //是否是游客
        public boolean isVisitor() {
            return getRole().equalsIgnoreCase(Config.GUEST);
        }
    }
}
