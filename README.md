# 为Pinpoint开发的Dubbo插件

## 使用方法

编译项目

```
mvn package
```

输出的`$PLUGIN_HOME/agent/target/pinpoint-agent-$VERSION`目录作为agent

将`$PLUGIN_HOME/agent/target/pinpoint-agent-$VERSION/plugin/pinpoint-dubbo-plugin-$VERION.jar`拷贝到collector和web的lib目录中
