# AAPlan
##简介
这是一套基于Java的饭团管理系统，使用Spring、Spring MVC、MyBatis框架。

##所需环境  
- jdk6+  
- tomcat6+  
- maven  
  
##部署
- 修改配置文件"src\main\resources\servers_cfg.properties"中关于数据库与管理员的配置内容。  
- 源码使用Maven构建，确保安装Maven的情况下，在文件目录运行  


    mvn clean package -DskipTests  


- 把war包拷到tomcat目录下的Webapp目录下，运行tomcat。
