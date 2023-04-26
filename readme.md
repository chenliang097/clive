# 起凡互娱文档
## 架构

1：项目采用Mvvm架构，组件化（不完全）开发。

2 ：  module  依赖关系
         |-app module 作为程序入口（壳子）

                |-libmain

                    |- common_lib
                    |- base_lib

                |- lib_login


3:   lib


4：  包结构按照功能分包（Function）
        |- view
             |-activity
             |-fragment
             |-layout
             |-adapter
        |- model (公用的model 放在libbiz2 module内)

        |-viewModel


5: 每个module下必须配置资源文件前缀（build.gradle内配置resourcePrefix  例：resourcePrefix "qf_libmain_"）
![结构图](http://v-gitlab.jieyou.shop/gitlab-instance-65748eeb/boboo_android/-/blob/master/qf_android.png)
