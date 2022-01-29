<div align="center">
    <h1>spring-boot-starter-idempotent</h1>
    <h3>一个基于 Spring Boot Redis 的 幂等</h3>
</div>

<p align="center">
  为简化开发工作、提高生产率而生
</p>

<p align="center">

  <a href="https://blog.csdn.net/qq_32596527">
    <img alt="CSDN 码龄" src="https://img.shields.io/badge/dynamic/xml?color=orange&label=CSDN&query=%2F%2Fdiv%5B%40class%3D%27person-code-age%27%5D%5B1%5D%2Fspan%5B1%5D%2Ftext%28%29%5B1%5D&url=https%3A%2F%2Fblog.csdn.net%2Fqq_32596527">
  </a>

  <a href="https://blog.csdn.net/qq_32596527">
    <img alt="CSDN 粉丝" src="https://img.shields.io/badge/dynamic/xml?color=orange&label=CSDN&prefix=%E7%B2%89%E4%B8%9D&query=%2F%2Fli%5B4%5D%2Fa%5B1%5D%2Fdiv%5B%40class%3D%27user-profile-statistics-num%27%5D%5B1%5D%2Ftext%28%29%5B1%5D&url=https%3A%2F%2Fblog.csdn.net%2Fqq_32596527">
  </a>

  <a href="https://blog.csdn.net/qq_32596527">
    <img alt="CSDN 访问" src="https://img.shields.io/badge/dynamic/xml?color=orange&label=CSDN&prefix=%E8%AE%BF%E9%97%AE&query=%2F%2Fli%5B1%5D%2Fdiv%5B%40class%3D%27user-profile-statistics-num%27%5D%5B1%5D%2Ftext%28%29%5B1%5D&url=https%3A%2F%2Fblog.csdn.net%2Fqq_32596527">
  </a>

  <a href="https://blog.csdn.net/qq_32596527">
    <img alt="CSDN 博客" src="https://img.shields.io/badge/dynamic/json?color=orange&label=CSDN&prefix=%E5%8D%9A%E5%AE%A2&query=%24.data.blog&suffix=%E7%AF%87&url=https%3A%2F%2Fblog.csdn.net%2Fcommunity%2Fhome-api%2Fv1%2Fget-tab-total%3Fusername%3Dqq_32596527">
  </a>

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
    <img alt="Spring Boot" src="https://img.shields.io/static/v1?logo=Spring Boot&message=2.3.8.RELEASE">
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
