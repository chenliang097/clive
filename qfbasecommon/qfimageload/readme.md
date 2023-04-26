使用建议
======================
Fresco 框架已经从项目中剔除，统一使用Glide。 

# 建议使用方式

ImgLoader
------------------
建议使用 [ImgLoader](https://gitee.com/BL-Android/Boboo/blob/remove_fresco/libimage/src/main/java/com/haiwaizj/chatlive/image/ImgLoader.java) 加载图片。

封装Glide目的是为了项目隔离三方代码,方便替换。

使用Glide时尽量传入准确的上下文Context, Glide会和当前的Context生命周期绑定，避免内存泄漏。

比如在Fragment最好传入Fragment实例，不要传入getContext(), 因为getContext()得到是当前Activity实例。如果在Adapter中加载图片，不方便传入Activity&Fragemtn,可以以直接使用 [ImgLoader.load(ImageView, url)](https://gitee.com/BL-Android/Boboo/blob/remove_fresco/libimage/src/main/java/com/haiwaizj/chatlive/image/ImgLoader.java)，Glide会在当前ImageView的Activity递归查找是属于哪个Fragment或Activity,并绑定生命周期。
```java
ImgLoader.load(Activity, ImageView, url);
ImgLoader.load(Fragemtn, ImageView, url);
ImgLoader.load(ImageView, url);
//RequestManager使用方式
ImgLoader.with(Context).xxx().xxx()
ImgLoader.with(ImageView).xxx().xxx()
ImgLoader.with(Activity).xxx().xxx()

ImgLoader.with(this)
        .load(url)
        .fitCenter()
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.error)
        .xxx() //可以扩展
        .into(view);

注：项目依然可以使用GlideApp.with(), Glide.with()。

Glide Generated
------------------
项目中使用了Glide Generated API，这样API调用更友好。更多方法可以直接使用链式调用。如果现有方法不能满足需求建议使用GlideExtension扩展（https://muyangmin.github.io/glide-docs-cn/doc/generatedapi.html#glideextension）。

例：
```java
GlideApp.with(this)
        .load(url)
        .fitCenter()
        .placeholder(R.drawable.placeholder)
        .error(R.drawable.error)
        .xxx()//扩展方法
        .into(view);


Placeholder
------------------
Glide 中placeholder不支持Transformation（圆角、圆形等处理操作），建议站位图片使用处理好的图片（比如圆形占位图片），圆角可以交给View处理（CardView）。

特殊情况使用Glide处理Placeholder圆角：
```java
GlideApp.with(this)
        .load(url)
        .thumbnail(GlideApp.with(this).load(R.id.placeholder_view).transform(RoundedCorners)))
        .fitCenter()
        .error(R.drawable.error)
        .into(view);


Glide Transformations
------------------
项目中集成了Glide Transformations库，支持更多特效处理。

https://github.com/wasabeef/glide-transformations

# Glide替换Fresco实现方式
保留Fresco相关包名、类名。SimpleDraweeView改为继承AppCompatImageView，原Fresco相关参数由GenericDraweeHierarchy相关类解析，转交给Glide实现图片加载。

保留的Fresco的相关类都不建议使用，随着版本迭代会剔除。

### 保留的包名、类名
```java
//都已标注为@Deprecated
com.facebook.drawee.view.SimpleDraweeView
com.facebook.drawee.generic.GenericDraweeHierarchy
com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
com.facebook.drawee.generic.GenericDraweeHierarchyInflater
com.facebook.drawee.generic.RoundingParams
com.facebook.drawee.drawable.ScalingUtils


### FrescoLoadUtil
已废弃，不建议使用。


