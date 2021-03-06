# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\AS_SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-dontwarn
-dontskipnonpubliclibraryclassmembers
-ignorewarnings
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init> (android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init> (android.content.Context, android.util.AttributeSet, int);
}

# 泛型与反射
-keepattributes Signature
-keepattributes EnclosingMethod
-keepattributes *Annotation*
# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#四大组件
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

-keep class com.lrx.router.lib.interfaces.** {*;}
-keep class com.lrx.router.lib.helper.ResourcesHelper {*;}
-keep class com.lrx.router.lib.core.RouterException {*;}
-keep class com.lrx.router.lib.utils.LogUtil {*;}
-keep class com.lrx.router.lib.utils.ErrorCode {*;}
-keep class com.lrx.router.lib.core.Router$LoadType {*;}
-keep class com.lrx.router.lib.activitys.ActivityStub {*;}
-keep class com.lrx.router.lib.core.PluginActivity {*;}
-keep class com.lrx.router.lib.core.Router {
    public *** get*();
    protected *** get*();
    public *** is*();
    public void setPluginDexPath(***);
    public void setSync(boolean);
    public void setAvailable(boolean);
    public void setImpClassName(java.lang.String);
    public void setErrorProxy(***);
    private void createProxy();
    private void createPluginDexProxy(com.lrx.router.lib.interfaces.RegisterPluginCallback);
}

-keep class com.lrx.router.lib.core.RouterManager {
    public <methods>;
}



#app混淆代码
-keep class * extends com.lrx.router.lib.core.PluginActivity {*;}
-keep class * extends com.lrx.router.lib.activitys.ActivityStub {*;}
#作为接口类的不能混淆 （getImpClassName()方法内指明的类名）