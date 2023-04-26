package com.rongtuoyouxuan.chatlive.databus.login;

public interface IloginListener {
        public void onErro(String msg);
        void onSuccess();
        public void onRegisterSuccess();
}
