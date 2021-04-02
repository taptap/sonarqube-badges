简体中文 | [English](./README.md)

# sonarqube-badges

SonarQube 的项目质量分析结果徽章生成的项目，解决了 SonarQube 不能将徽章用于私人项目的问题。相关的背景链接如下：

- https://jira.sonarsource.com/browse/MMF-1942
- https://community.sonarsource.com/t/badges-on-private-projects/4894/20
- [SonarQube 私有项目徽章显示方案](http://www.kailing.pub/article/index/arcid/331.html)

## 快速开始

- 1、部署服务 
  
自己编译，或下载已编译的发行包。

```shell
wget https://github.com/TapTap/sonarqube-badges/releases/download/v0.1/sonarqube-badges-0.1.tar
```  

解压后，修改`conf.properties`配置文件，配置你的 sonarqube 的数据库信息。如：

```properties
server.port=8080
mybatis-plus.global-config.banner=false
#required
spring.datasource.url=jdbc:postgresql://qa-sonar.db.com:1921/sonarqube?ssl=false&currentSchema=public
spring.datasource.username=sonar
spring.datasource.password=sonar
#optional 用于 tools 接口，组装 badges 的请求地址
sonar.base-url=https://qa-sonar.dev.com
sonar.badges-base-url=http://localhost:8080
```

执行 `sstartup.sh` 启动应用

- 2、使用

> 2.1、measure 徽章接口

`GET` `/api/project_badges/measure`

| 请求参数 | 类型   | 说明                                                 |
| -------- | ------ | ---------------------------------------------------- |
| project   | string | 标识项目唯一ID，必填参数 |
| metric   | string | metric 标识，必填参数，可选（bugs、code_smells、sqale_index、vulnerabilities、security_rating、coverage、duplicated_lines_density、ncloc、sqale_rating、alert_status、reliability_rating） |
| branch   | string | 分支名称，可选参数 |

例如，请求：http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=bugs

响应： `svg` 徽章图片

> qualityGate 徽章接口

`GET` `/api/project_badges/quality_gate`


| 请求参数 | 类型   | 说明                                                 |
| -------- | ------ | ---------------------------------------------------- |
| project   | string | 标识项目唯一ID，必填参数 |
| branch   | string | 分支名称，可选参数 |

例如，请求：http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4

响应： `svg` 徽章图片

> 获取指定项目的所有 measure 徽章 `url` 集合接口

`GET` `/api/project_badges_tools/measure/url`

| 请求参数 | 类型   | 说明                                                 |
| -------- | ------ | ---------------------------------------------------- |
| project   | string | 标识项目唯一ID，必填参数 |
| branch   | string | 分支名称，可选参数 |

例如，请求： http://localhost:8080/api/project_badges_tools/measure/url?project=server_services_developer_AXaIXEx52AMDJFWZUd_4

响应：

```json
[
  {
    "bugs": "http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=bugs"
  },
  {
    "code_smells": "http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=code_smells"
  },
  {
    "coverage": "http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=coverage"
  },
  {
    "duplicated_lines_density": "http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=duplicated_lines_density"
  },
  {
    "ncloc": "http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=ncloc"
  },
  {
    "sqale_rating": "http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=sqale_rating"
  },
  {
    "alert_status": "http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=alert_status"
  },
  {
    "reliability_rating": "http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=reliability_rating"
  },
  {
    "security_rating": "http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=security_rating"
  },
  {
    "sqale_index": "http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=sqale_index"
  },
  {
    "vulnerabilities": "http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=vulnerabilities"
  }
]
```

> 获取指定项目的所有 measure 徽章 `markdown` 集合接口

`GET` `/api/project_badges_tools/measure/markdown`

| 请求参数 | 类型   | 说明                                                 |
| -------- | ------ | ---------------------------------------------------- |
| project   | string | 标识项目唯一ID，必填参数 |
| branch   | string | 分支名称，可选参数 |

例如，请求： http://localhost:8080/api/project_badges_tools/measure/markdown?project=server_services_developer_AXaIXEx52AMDJFWZUd_4

响应：

```json
[
  "[![bugs](http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=bugs)](https://qa-sonar.dev.com/dashboard?id=server_services_developer_AXaIXEx52AMDJFWZUd_4)",
  "[![code_smells](http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=code_smells)](https://qa-sonar.dev.com/dashboard?id=server_services_developer_AXaIXEx52AMDJFWZUd_4)",
  "[![coverage](http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=coverage)](https://qa-sonar.dev.com/dashboard?id=server_services_developer_AXaIXEx52AMDJFWZUd_4)",
  "[![duplicated_lines_density](http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=duplicated_lines_density)](https://qa-sonar.dev.com/dashboard?id=server_services_developer_AXaIXEx52AMDJFWZUd_4)",
  "[![ncloc](http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=ncloc)](https://qa-sonar.dev.com/dashboard?id=server_services_developer_AXaIXEx52AMDJFWZUd_4)",
  "[![sqale_rating](http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=sqale_rating)](https://qa-sonar.dev.com/dashboard?id=server_services_developer_AXaIXEx52AMDJFWZUd_4)",
  "[![alert_status](http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=alert_status)](https://qa-sonar.dev.com/dashboard?id=server_services_developer_AXaIXEx52AMDJFWZUd_4)",
  "[![reliability_rating](http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=reliability_rating)](https://qa-sonar.dev.com/dashboard?id=server_services_developer_AXaIXEx52AMDJFWZUd_4)",
  "[![security_rating](http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=security_rating)](https://qa-sonar.dev.com/dashboard?id=server_services_developer_AXaIXEx52AMDJFWZUd_4)",
  "[![sqale_index](http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=sqale_index)](https://qa-sonar.dev.com/dashboard?id=server_services_developer_AXaIXEx52AMDJFWZUd_4)",
  "[![vulnerabilities](http://localhost:8080/api/project_badges/measure?project=server_services_developer_AXaIXEx52AMDJFWZUd_4&metric=vulnerabilities)](https://qa-sonar.dev.com/dashboard?id=server_services_developer_AXaIXEx52AMDJFWZUd_4)"
]
```

## 开发编译指南

拉代码

```
git clone https://github.com/TapTap/sonarqube-badges.git
```

编译

```
./gradlew assemble
```

在项目的根目录下，执行上面的脚本，执行成功后，在根目录下会生成 build 目录，发行包在 `build/distributions/sonarqube-badges-0.1.tar`

## 原理简介

通过链接 sonarqube 的数据库，直接获取到 sonarqube 的项目分析数据。然后 sonarqube-badges 的 url 设计和 sonarqube 一致，`svg` 生成风格拷贝的 sonarqube
开源的代码，所以样式保持了和 sonarqube 一模一样