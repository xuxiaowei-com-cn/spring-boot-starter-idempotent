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
    <img alt="GitHub stars" src="https://img.shields.io/github/stars/xuxiaowei-com-cn/spring-boot-starter-idempotent?logo=github">
  </a>

  <a href="https://github.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="GitHub forks" src="https://img.shields.io/github/forks/xuxiaowei-com-cn/spring-boot-starter-idempotent?logo=github">
  </a>

  <a href="https://github.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="GitHub watchers" src="https://img.shields.io/github/watchers/xuxiaowei-com-cn/spring-boot-starter-idempotent?logo=github">
  </a>

  <a href="https://gitee.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="码云Gitee stars" src="https://gitee.com/xuxiaowei-com-cn/spring-boot-starter-idempotent/badge/star.svg?theme=blue">
  </a>

  <a href="https://gitee.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="码云Gitee forks" src="https://gitee.com/xuxiaowei-com-cn/spring-boot-starter-idempotent/badge/fork.svg?theme=blue">
  </a>

  <a href="https://gitlab.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="Gitlab stars" src="https://badgen.net/gitlab/stars/xuxiaowei-com-cn/spring-boot-starter-idempotent?icon=gitlab">
  </a>

  <a href="https://gitlab.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="Gitlab forks" src="https://badgen.net/gitlab/forks/xuxiaowei-com-cn/spring-boot-starter-idempotent?icon=gitlab">
  </a>

  <a href="https://github.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="total lines" src="https://tokei.rs/b1/github/xuxiaowei-com-cn/spring-boot-starter-idempotent">
  </a>

  <a href="./pom.xml">
    <img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-2.3.8.RELEASE-blue">
  </a>

  <a href="https://www.apache.org/licenses/LICENSE-2.0">
    <img alt="code style" src="https://img.shields.io/badge/license-Apache%202-4EB1BA.svg?style=flat-square">
  </a>
</p>

## 使用

- [幂等使用示例 idempotent-example](https://gitee.com/xuxiaowei-com-cn/idempotent-example)

```xml
<!-- https://search.maven.org/artifact/cn.com.xuxiaowei.boot/spring-boot-starter-idempotent -->
<!-- https://mvnrepository.com/artifact/cn.com.xuxiaowei.boot/spring-boot-starter-idempotent -->
<dependency>
    <groupId>cn.com.xuxiaowei.boot</groupId>
    <artifactId>spring-boot-starter-idempotent</artifactId>
    <version>最新版</version>
</dependency>
```

- 由于各大 maven 仓库（如：阿里云）非实时同步，推荐在项目 `pom.xml` 中添加下列仓库

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
