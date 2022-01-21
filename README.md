# sherlocky-commons
[![Build Status](https://github.com/sherlocky/sherlocky-commons/workflows/Java%20CI/badge.svg)](https://github.com/sherlocky/sherlocky-commons/actions)
[![license](https://img.shields.io/github/license/sherlocky/sherlocky-commons.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![maven](https://img.shields.io/maven-central/v/com.sherlocky/sherlocky-commons.svg)](https://search.maven.org/search?q=com.sherlocky)

![sherlocky](logo.png "sherlocky")

#### 感谢 JetBrains 提供开源许可证支持
[![JetBrains](jetbrains-variant-4.svg)](https://www.jetbrains.com/?from=sherlocky-commons)

#### 简介
Sherlock 自用公共基础包

#### 软件架构
基于 JDK 8 + Gradle 5.2.1 构建

#### 安装
已发布到Maven Central，[maven 地址](https://mvnrepository.com/artifact/com.sherlocky/sherlocky-commons)

##### maven
```xml
<dependency>
  <groupId>com.sherlocky</groupId>
  <artifactId>sherlocky-commons</artifactId>
  <version>0.2.0</version>
</dependency>
```

snapshot 版：
```xml
<dependency>
  <groupId>com.sherlocky</groupId>
  <artifactId>sherlocky-commons</artifactId>
  <version>0.7.0-SNAPSHOT</version>
</dependency>
```

##### gradle
```groovy
implementation 'com.sherlocky:sherlocky-commons:0.2.0'
```

snapshot 版：
```groovy
implementation 'com.sherlocky:sherlocky-commons:0.7.0-SNAPSHOT'
```

> snapshot版需要配置仓库源：
> https://oss.sonatype.org/content/repositories/snapshots/

#### TODO
- [ ] 单例工具类
- [ ] 集合工具类
- [ ] Stream相关工具类
- [ ] 多线程相关工具类