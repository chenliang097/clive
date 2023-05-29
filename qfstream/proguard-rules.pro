# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# 混淆时所用的算法
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*,!code/allocation/variable

#代码压缩级别
-optimizationpasses 5

#优化时允许访问并修改有修饰符的类和类的成员
-allowaccessmodification

#混淆时预校验
-dontpreverify

#使用大小写混合
-dontusemixedcaseclassnames

# 指定不去忽略非公共的库类。
-dontskipnonpubliclibraryclasses

#记录日志
-verbose

#忽略警告，避免打包时某些警告出现
-ignorewarnings

# 保护给定的可选属性
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod,LocalVariableTable,*JavascriptInterface*

# AndroidX 防止混淆
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

# 保持哪些类不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
#-------------------------------common-------------------------------


#--------------------org.apache.http---------------------------------------------------
-keep class android.net.http.** { *; }
-dontwarn android.net.http.**
-dontnote android.net.http.**

-keep class org.apache.** { *; }
-dontwarn org.apache.**
-dontnote org.apache.**

-keep class org.apache.commons.**{ *; }
-dontwarn org.apache.commons.**
-dontnote org.apache.commons.**

-keep class android.support.**{ *; }
-dontwarn android.support.**

-keep class org.apache.http.** { *; }
-dontwarn org.apache.http.**
-dontnote org.apache.http.**

#--------------------org.apache.http---------------------------------------------------

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class com.rongtuoyouxuan.chatlive.R$*{
public static final int *;
}

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

#-------------------------------通用混淆-------------------------------
-renamesourcefileattribute SourceFile

-keep class **.R
-keepclassmembers class **.R$* {
    public static <fields>;
}

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

#-------------------------------通用混淆-------------------------------

-dontshrink
-dontoptimize
-dontwarn android.support.v4.**
-dontwarn com.google.android.maps.**
-dontwarn android.webkit.WebView
-keep public class javax.**
-keep public class android.webkit.**
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

#---------------Begin: proguard configuration for Gson  ----------
#https://github.com/google/gson/blob/master/examples/android-proguard-example/proguard.cfg
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*umeng

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.rongtuoyouxuan.chatlive.crtbiz2.model.**{*;}
-keep class com.rongtuoyouxuan.chatlive.model.**{*;}
-keep class * implements com.rongtuoyouxuan.chatlive.crtnet.BaseModel{*;}
-keep class com.google.gson.**{*;}

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

##---------------End: proguard configuration for Gson  ----------

#-------------------------------okhttp3-------------------------------
#https://github.com/square/okhttp
-dontwarn okhttp3.**
-dontwarn okio.**
-dontnote okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
#-------------------------------okhttp3-------------------------------

#-------------------------------retrofit2-------------------------------
#https://github.com/square/retrofit
-keepattributes Signature
-keepclassmembernames,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#-------------------------------retrofit2-------------------------------

#-------------------------------路由模块-------------------------------
#https://github.com/alibaba/ARouter/blob/e81cc817fb3cb75f0562dba71a7337bfaaab47f0/app/proguard-rules.pro
-keep public class com.alibaba.android.arouter.routes.**{*;}
-dontwarn com.alibaba.android.arouter.**
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
#-------------------------------路由模块-------------------------------

#-------------------------------业务JavascriptInterface-------------------------------
-keep class com.rongtuoyouxuan.chatlive.webview.jsinterface.JsCallbackResult {*;}
#-------------------------------业务JavascriptInterface-------------------------------


#ucrop------------------------------------------------
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }
#ucrop------------------------------------------------

#Google支付-------------------------------------------
-keep class com.android.vending.billing.**
-keep public class com.rongtuoyouxuan.chatlive.crtpay.Bean.PurchaseBean.**{ *;}
#Google支付-------------------------------------------

-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {
    com.google.android.gms.ads.identifier.AdvertisingIdClient$Info getAdvertisingIdInfo(android.content.Context);
}
-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {
    java.lang.String getId();
    boolean isLimitAdTrackingEnabled();
}
-keep public class com.android.installreferrer.**{ *; }
#adjust-------------------------------------------


#glide-------------------------------------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#glide-------------------------------------------

#svga-------------------------------------------
-keep class com.squareup.wire.** { *; }
-keep class com.opensource.svgaplayer.proto.** { *; }
#svga-------------------------------------------

#zego-------------------------------------------
-keep class **.zego.**{*;}
#zego-------------------------------------------

#融云IM  start -------------------------------------------
-keepattributes Exceptions,InnerClasses

-keepattributes Signature

-keep class io.rong.** {*;}
-keep class cn.rongcloud.** {*;}
-keep class * implements io.rong.imlib.model.MessageContent {*;}
-dontwarn io.rong.push.**
-dontnote com.xiaomi.**
-dontnote com.google.android.gms.gcm.**
-dontnote io.rong.**

-ignorewarnings
#融云IM  end -------------------------------------------

#PAG
-keep class org.libpag.** {*;}
-keep class androidx.exifinterface.** {*;}

#liveeventbus
-dontwarn com.jeremyliao.liveeventbus.**
-keep class com.jeremyliao.liveeventbus.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.arch.core.** { *; }
#人脸识别sdk的混淆规则
-include kyc-cloud-face-consumer-proguard-rules.pro
