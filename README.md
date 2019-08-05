# sherlocky-commons

#### 介绍
自用公共基础包

#### 软件架构
基于 Gradle 4.10.2 构建


#### 使用说明

maven 私服地址：``http://maven.sherlocky.com:8082/repository/sherlock/``
> 需配置 maven 私服域名 host。

##### maven
```xml
<dependency>
  <groupId>com.sherlocky</groupId>
  <artifactId>sherlocky-commons</artifactId>
  <version>0.0.1-SNAPSHOT</version>
</dependency>
```

##### gradle
```groovy
implementation 'com.sherlocky:sherlocky-commons:0.0.1-SNAPSHOT'
```
