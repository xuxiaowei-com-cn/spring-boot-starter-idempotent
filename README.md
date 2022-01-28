<div align="center">
    <h1>spring-boot-starter-idempotent</h1>
    <h3>一个基于 Spring Boot Redis 的 幂等</h3>
</div>

<p align="center">
  为简化开发工作、提高生产率而生
</p>

<p align="center">
  <a href="https://search.maven.org/artifact/cn.com.xuxiaowei.boot/spring-boot-starter-idempotent">
    <img alt="maven" src="https://img.shields.io/maven-central/v/cn.com.xuxiaowei.boot/spring-boot-starter-idempotent.svg?style=flat-square">
  </a>

  <a href="https://github.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="GitHub" src="https://img.shields.io/github/stars/xuxiaowei-com-cn/spring-boot-starter-idempotent?logo=github&style=flat">
  </a>

  <a href="https://gitee.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="码云Gitee" src="https://gitee.com/xuxiaowei-com-cn/spring-boot-starter-idempotent/badge/star.svg?theme=blue">
  </a>

  <a href="https://github.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="total lines" src="https://tokei.rs/b1/github/xuxiaowei-com-cn/spring-boot-starter-idempotent">
  </a>

  <a href="./pom.xml">
    <img alt="Spring Boot" src="https://raster.shields.io/badge/spring boot-2.3.8.RELEASE+-red.svg">
  </a>

  <a href="https://www.apache.org/licenses/LICENSE-2.0">
    <img alt="code style" src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square">
  </a>
</p>

## 使用

- 由于各大 maven 仓库（如：阿里云）非实时同步，推荐在项目中添加下列仓库

```xml

<repositories>
    <repository>
        <id>releases</id>
        <url>https://s01.oss.sonatype.org/content/repositories/releases/</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
    <repository>
        <id>snapshots</id>
        <url>https://s01.oss.sonatype.org/content/repositories/snapshots/</url>
        <releases>
            <enabled>false</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>
```

## 依赖

- spring-boot-starter-aop

- spring-boot-starter-data-redis

- spring-boot-starter-web

- spring-boot-configuration-processor

- lombok
