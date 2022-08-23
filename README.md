<div align="center" style="text-align: center;">
    <h1>spring-boot-starter-idempotent</h1>
    <h3>一个基于 Spring Boot Redis 的 幂等</h3>
    <a target="_blank" href="https://github.com/996icu/996.ICU/blob/master/LICENSE">
        <img alt="License-Anti" src="https://img.shields.io/badge/License-Anti 996-blue.svg">
    </a>
    <a target="_blank" href="https://996.icu/#/zh_CN">
        <img alt="Link-996" src="https://img.shields.io/badge/Link-996.icu-red.svg">
    </a>
</div>

<p align="center" style="text-align: center;">
  为简化开发工作、提高生产率而生
</p>

<p align="center" style="text-align: center;">

  <a target="_blank" href="https://blog.csdn.net/qq_32596527">
    <img alt="CSDN 码龄" src="https://img.shields.io/badge/dynamic/xml?color=orange&label=CSDN&query=%2F%2Fdiv%5B%40class%3D%27person-code-age%27%5D%5B1%5D%2Fspan%5B1%5D%2Ftext%28%29%5B1%5D&url=https%3A%2F%2Fblog.csdn.net%2Fqq_32596527">
  </a>

  <a target="_blank" href="https://blog.csdn.net/qq_32596527">
    <img alt="CSDN 粉丝" src="https://img.shields.io/badge/dynamic/xml?color=orange&label=CSDN&prefix=%E7%B2%89%E4%B8%9D&query=%2F%2Fli%5B4%5D%2Fa%5B1%5D%2Fdiv%5B%40class%3D%27user-profile-statistics-num%27%5D%5B1%5D%2Ftext%28%29%5B1%5D&url=https%3A%2F%2Fblog.csdn.net%2Fqq_32596527">
  </a>

  <a target="_blank" href="https://blog.csdn.net/qq_32596527">
    <img alt="CSDN 访问" src="https://img.shields.io/badge/dynamic/xml?color=orange&label=CSDN&prefix=%E8%AE%BF%E9%97%AE&query=%2F%2Fli%5B1%5D%2Fdiv%5B%40class%3D%27user-profile-statistics-num%27%5D%5B1%5D%2Ftext%28%29%5B1%5D&url=https%3A%2F%2Fblog.csdn.net%2Fqq_32596527">
  </a>

  <a target="_blank" href="https://blog.csdn.net/qq_32596527">
    <img alt="CSDN 博客" src="https://img.shields.io/badge/dynamic/json?color=orange&label=CSDN&prefix=%E5%8D%9A%E5%AE%A2&query=%24.data.blog&suffix=%E7%AF%87&url=https%3A%2F%2Fblog.csdn.net%2Fcommunity%2Fhome-api%2Fv1%2Fget-tab-total%3Fusername%3Dqq_32596527">
  </a>

  <a target="_blank" href="https://www.jetbrains.com/idea">
    <img alt="IntelliJ IDEA" src="https://img.shields.io/static/v1?logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAAAAAByaaZbAAAABGdBTUEAALGPC/xhBQAAACBjSFJNAAB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAAAmJLR0QA/4ePzL8AAAAHdElNRQfmBRkBICRBfW8eAAABPklEQVRIx+2UTStEYRiGr/kqZWaKMoyQUsoCWdn4AZKPlZSF5fgPVhYWStlZqPkB7GynWSg/QCmhUFOUGCPNZBrlnNtijmneOYZzykrn2p2e536f577P2wsBPvmQlACQg13Mr4b9CCTpuMunQIdGT8gQRCBZAQRPu0B6aRjsvqKXCRcAJO8lTTf3/GQJKF8Bz5481CfMvEnnxtrRduKhPET7R6GWka+UrBW//8Hej3haqZQFYgNz8VDmYbNNT8iSFDdMM1iSyh3uWHsAesNgVc1D7k4hMeISrN0uAOtAwTYF6Smg2UQUYDYbO8qdjS0Cua9CahsgPd8NleuW3WOFRiTV+nTj8mnL5XbixinVlnEJ7L1vkuzcuJT0ejDufL98UTjZmWyJsqFJvT9aBAT8J5zrrXYFF788xn8gCPDCJ2cr3I1zqSjOAAAAJXRFWHRkYXRlOmNyZWF0ZQAyMDIyLTA1LTI1VDAxOjMyOjM2KzAwOjAwH/0yeQAAACV0RVh0ZGF0ZTptb2RpZnkAMjAyMi0wNS0yNVQwMTozMjozNiswMDowMG6gisUAAAAASUVORK5CYII=&message=IntelliJ IDEA">
  </a>

  <a target="_blank" href="https://search.maven.org/artifact/cn.com.xuxiaowei.boot/spring-boot-starter-idempotent">
    <img alt="maven" src="https://img.shields.io/maven-central/v/cn.com.xuxiaowei.boot/spring-boot-starter-idempotent.svg?style=flat-square">
  </a>

  <a target="_blank" href="https://github.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="GitHub stars" src="https://img.shields.io/github/stars/xuxiaowei-com-cn/spring-boot-starter-idempotent?logo=github">
  </a>

  <a target="_blank" href="https://github.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="GitHub forks" src="https://img.shields.io/github/forks/xuxiaowei-com-cn/spring-boot-starter-idempotent?logo=github">
  </a>

  <a target="_blank" href="https://github.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="GitHub watchers" src="https://img.shields.io/github/watchers/xuxiaowei-com-cn/spring-boot-starter-idempotent?logo=github">
  </a>

  <a target="_blank" href="https://gitee.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="码云Gitee stars" src="https://gitee.com/xuxiaowei-com-cn/spring-boot-starter-idempotent/badge/star.svg?theme=blue">
  </a>

  <a target="_blank" href="https://gitee.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="码云Gitee forks" src="https://gitee.com/xuxiaowei-com-cn/spring-boot-starter-idempotent/badge/fork.svg?theme=blue">
  </a>

  <a target="_blank" href="https://gitlab.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="Gitlab stars" src="https://badgen.net/gitlab/stars/xuxiaowei-com-cn/spring-boot-starter-idempotent?icon=gitlab">
  </a>

  <a target="_blank" href="https://gitlab.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="Gitlab forks" src="https://badgen.net/gitlab/forks/xuxiaowei-com-cn/spring-boot-starter-idempotent?icon=gitlab">
  </a>

  <a target="_blank" href="https://github.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="OSCS Status" src="https://www.oscs1024.com/platform/badge/xuxiaowei-com-cn/spring-boot-starter-idempotent.svg?size=small">
  </a>

  <a target="_blank" href="https://github.com/xuxiaowei-com-cn/spring-boot-starter-idempotent">
    <img alt="total lines" src="https://tokei.rs/b1/github/xuxiaowei-com-cn/spring-boot-starter-idempotent">
  </a>

  <a target="_blank" href="https://github.com/xuxiaowei-com-cn/spring-boot-starter-idempotent/blob/main/pom.xml">
    <img alt="Spring Boot" src="https://img.shields.io/static/v1?logo=Spring Boot&message=2.4.0">
  </a>

  <a target="_blank" href="https://github.com/xuxiaowei-com-cn/spring-boot-starter-idempotent/blob/main/pom.xml">
    <img alt="Spring Boot Redis" src="https://img.shields.io/static/v1?logo=Redis&message=2.4.0">
  </a>

  <a target="_blank" href="https://www.apache.org/licenses/LICENSE-2.0">
    <img alt="code style" src="https://img.shields.io/badge/license-Apache 2-blue">
  </a>
</p>

## 功能说明

- 支持一键代码开启全局幂等配置（`cn.com.xuxiaowei.boot.idempotent.annotation.EnableIdempotent`）
- 支持一键开启多次获取请求流（`xxw.idempotent.input-stream-filter: true`）
- 支持自定义全局接口幂等放入Redis中的key值前缀（`xxw.idempotent.prefix`）
- 支持自定义全局接口幂等调用`记录`放入Redis中的key值前缀（`xxw.idempotent.record`）
- 支持自定义全局接口幂等调用`结果`放入Redis中的key值前缀（`xxw.idempotent.result`）
- 支持自定义接口幂等放入Redis中的key值（`cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent#key()`）
- 支持自定义接口幂等Token从header中获取（第一优先级`cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent#header()`）
- 支持自定义接口幂等Token从param中获取（第二优先级`cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent#param()`）
- 支持自定义接口幂等Token从stream中获取（第三优先级`cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent#stream()`，需要开启多次获取请求流配置）
- 支持自定义接口幂等Token名称
- 支持自定义幂等过期时间（`cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent#expireTime()`）
- 支持自定义幂等过期时间单位（`cn.com.xuxiaowei.boot.idempotent.annotation.Idempotent#expireUnit()`）
- 支持自定义接口超时时直接响应空结果（响应header中响应调用记录）与自定义响应结果
    - [TimeoutExceptionService](https://github.com/xuxiaowei-com-cn/spring-boot-starter-idempotent/blob/main/src/main/java/cn/com/xuxiaowei/boot/idempotent/service/TimeoutExceptionService.java)
- 支持严格模式（未找到Token时抛出异常，使用`@org.springframework.web.bind.annotation.ControllerAdvice`统一拦截异常进行处理）
- 支持将调用记录放入响应header中

## 使用示例

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

## 鸣谢

1. 感谢 [![jetbrains](./static/jetbrains.ico)](https://www.jetbrains.com/)
   提供开发工具 [![IDEA](./static/idea.svg)](https://www.jetbrains.com/idea) 的免费授权

## Stargazers over time

[![Stargazers over time](https://starchart.cc/xuxiaowei-com-cn/spring-boot-starter-idempotent.svg)](https://starchart.cc/xuxiaowei-com-cn/spring-boot-starter-idempotent)
