# NotNullGson

为解决解析后台数据过程中的各种异常，添加一些兜底方法。

比如常见的 `key:value` ，如果 `value` 为 `null` 的时候，初始化为 value 类型的默认值。防止调用的时候为空的情况

```groovy
implementation "com.xander.notnullgson:notnullgson:1.0.0"
```
