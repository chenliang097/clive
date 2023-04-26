# Coding-Guidelines

## 1.架构
架构设计请查看 README.MD
1. 层级之间由上到下单向依赖。
2. 同层级不可以依赖（libres, libdatabus, librouter 除外，折中方案），现存依赖找时机要解耦。

*注意：业务层的同级依赖就彻底失去了组件化的意义（虽然我们的项目算不上绝对的组件化，但组件化是方向）。

## 2.代码命名 （Java&Kotlin）
1. 项目名全部小写，包名全部小写。
2. 类名首字母大写，如果类名由多个单词组成，每个单词的首字母都要大写。
3. 变量名、方法名首字母小写，如果名称由多个单词组成，每个单词的首字母都要大写（驼峰命名法）。
4. 用final static 等方式定义的常量名全部大写，如果由多个单词组成用 “_” 分隔开。

## 3.资源位置
1. 公共图片、颜色、xml 放在libres。
2. 业务相关的图片、颜色、xml 资源放在对应业务模块下。
3. 文案统一放在libres。

## 4.资源命名
##### 命名规则
1. 命名中只能含有英语版下划线 "\_" ，数字和小写字母（其它符号都是非法符号）。
2. 并且不能以下划线 "\_" 或者数字开头。

##### layout xml 命名参考模板：
    模块名称_[开发人员自行定义] //（模块名称不加lib）
    例如: libmain模块 main_activity_splash、main_layout_shortvideo
    
##### 非 layout（图片、颜色、xml drawable等）资源命名参考模板：
    资源类型_[common]_[功能部分]_[资源描述]

注：以“资源类型”开头，如果是公共资源请在合适的位置加入"common" 标识(可以加在“功能部分”后面)，  第二个位置应该是 "common" 或 "功能模块" 二选其一，后面可以根据实际情况任意组合。
"common" 标识用来区分公共资源，还是业务特有资源。

    资源类型
    icon : icon  //小图标 （menu、菜单栏等图标），
    bg : background
    color : color
    imgage : image //大图资源
    button : button
    divider : divider
    selector : selector
    animation : anim
    ......
    
    功能部分 (具体业务名称)
    login: 登录
    live:直播
    profile: 我的
    弹幕
    礼物
    ......
    
    资源描述
    颜色：white, orange
    形状: corners,  circle
    状态：nomal, press, select, unselect
    大小: small, large
    ....

##### 图片资源举例
    bg_common_orange //通用资源
    bg_common_circle_white  //通用资源
    bg_common_corners_white_small //通用资源
    icon_live_follow
    image_live_game_saizi //图片资源
    image_voice_room_apply
    
#####  颜色资源举例
    color_common_primary //app 主题色
    color_common_second //app 次要主题色
    color_common_accent //app 强调色
    color_common_title
    color_live_host_name
    color_voice_room_host_bg
    color_qinmidu_bg
    color_qinmidu_text
    color_live_id
## 4.代码格式
使用 Android Studio 默认工具格式化。可以设置ide格式化改动过的代码，也可以选中部分代码格式化。  
Windows 快捷键：Ctrl + Alt +L  
Mac 快捷键：OPTION + CMD + L  

## 其他
1. Model (json 序列化对象) 类使用kotlin class ,尽量不要使用java, 暂时不可以使用data class。
