# Logback - 日志案例

主要是关于logback日志的配置内容案例

> SpringBoot 对 logback 提供了默认的配置文件 base.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>

<!--
Base logback configuration provided for compatibility with Spring Boot 1.1
-->

<included>
	<include resource="org/springframework/boot/logging/logback/defaults.xml" />
	<property name="LOG_FILE" value="${LOG_FILE:-${LOG_PATH:-${LOG_TEMP:-${java.io.tmpdir:-/tmp}}}/spring.log}"/>
	<include resource="org/springframework/boot/logging/logback/console-appender.xml" />
	<include resource="org/springframework/boot/logging/logback/file-appender.xml" />
	<root level="INFO">
		<appender-ref ref="CONSOLE" />
		<appender-ref ref="FILE" />
	</root>
</included>
```

## 自定义 logback 的配置文件    

**根据不同的日志系统，按照指定的规则组织配置文件名，就能被正确加载**
- Logback: logback-spring.xml、 logback-spring.groovy、 logback.xml、 logback.groovy
- Log4j: log4j-spring.properties、log4j-spring.xml、 log4j.properties、 log4j.xml
- Log4j2: log4j2-spring.xml、 log4j2.properties、 log4j2.xml
- JDK: logging.properties

### 1. 根节点 `<configuration>`

```xml
<configuration scan="true" scanPeriod="60 seconds" debug="false">
    <!-- 其他配置信息省略 -->
</configuration>
```

- scan：当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认为true。
- scanPeriod：设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位为毫秒。当scan为true时，此属性生效，默认间隔为1分钟。 
- debug：当此属性设置为true时，将打印出logback内部日志信息，实施查看logback的运行状态，默认为false。

### 2. 子节点 `<contextName>`

```xml
<contextName>XXX</contextName>

<layout class="XXXXXXXXXX.XXXXXX">
    <pattern>
        <pattern>%d{HH:mm:ss.SSS} %contextName [%thread] %-5level %logger{36} - %msg%n</pattern>
    </pattern>
</layout>
```

每个日志都关联到日志上下文，默认上下文名称为”default“，但是可以采用<contextName>设置成其他名称，用于区分不同应用程序的记录。可以通过%contextName来打印日志上下文名称。

### 3. 子节点 `<property>`

```xml
<property name="LOG_FILE_PATH" value="${LOG_FILE:-${LOG_PATH:-${LOG_TEMP:-${java.io.tmpdir:-/tmp}}}/logs}"></property>
```

用来定义变量值的标签，<property>有两个属性，name和value；name：变量的名称；value：变量定义的值；通过定制的值会被插入到logger上下文中，可以使用 '${}' 来使用比那辆

**多环境配置下，通过application.yml传递参数过来，<property>获取不到环境参数，需要使用<springProperty>**

```xml
<springProperty scope="context" name="APP_NAME" source="spring.application.name" defaultValue="springBoot"/>
```

### 4. 子节点 `<appender>`

appender 用来格式化日志输出节点，有两个属性name和class，class用来指定哪种输出策略，常用的就是控制台输出策略和文件输出策略。

- `ConsoleAppender`：将日志打印到控制台上，更加准确的说，使用`System.out`或者`System.error`的方式进行输出，主要的子标签有：`encoder`、`target`。
- `FileAppender`：用于将日志输出到文件中，主要子标签有：`append`，`encoder`、`file`。
- `RollingFileAppender`：从名字我们可以得出，`FileAppender`是`RollingFileAppender`的父类。能够动态的创建一个文件，到满足一定的条件，就会创建一个新的文件，然后将日志写入到新的文件。
有两个重要的标签与RollingFileAppender进行交互：`RollingPolicy`、 `TriggeringPolicy`。主要子标签：`file`、`append`、`encoder`、`rollingPolicy`、`triggerPolicy`

#### 对应子标签

- `file`：被写入的文件名，可以是相对目录，也可以是绝对目录，如果上级目录不存在会自动创建，没有默认值
- `target`：设置一System.out还是System.err方式输出。默认值为System.out
- `append`：如果是true，日志被 追加到文件结尾，如果是false，清空现存文件，默认是true。
- `prudent`：
    - `FileAppender`：如果是true，日志会被安全的写入文件，即使其他的FileAppender也在向此文件做写入操作，效率低，默认是false
    - `RollingFileAppender`：如果是true，不支持`FixedWindowRollingPolicy`，支持`TimeBasedRollingPolicy`，当时有两个限制《1：不支持也不允许文件压缩，2：不能设置file属性，必须留空。》
- `layout` 和 `encoder`：
    - 都可以将事件转换为格式化后的日志记录，但是控制台使用`layout`，文件输出使用`encoder`。（**0.9.19**之后，`FileAppender`和它的子类是期望使用`encoder`，不再使用`layout`）
    ```xml
    <!--输出到控制台 ConsoleAppender-->
    <appender name="consoleLog1" class="ch.qos.logback.core.ConsoleAppender">
        <layout>
            <pattern>${FILE_LOG_PATTERN}</pattern>
        </layout>
    </appender>
    
    <!--输出到控制台 ConsoleAppender-->
    <appender name="consoleLog2" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d -2 %msg%n</pattern>
        </encoder>
    </appender>
    ```

##### `layout` 和 `encode` 的区别

1. `encoder`：主要工作有两个，①：将一个event事件转换成一组byte数组、②将转换后的字节输出到文件中。
2. `encoder` 组件是0.9.19版本后才引进来的，再之前的版本中，appender是使用`layout`（将一个event事件装换成一个字符串），然后使用 `[java.io.writer]` 对象将字符串写入到文件中。
3. 自从0.9.19版本之后，`FileAppender` 和它的子类是期望使用`encoder`，不再使用`layout`。
4. 其中`layout`仅仅完成了将一个`event`事件转换成一个字符串这一功能，此外，`layout`不能控制将字符串写出到文件，`layout`不能整合`event`事件到一组中。与`encoder`相比，不仅仅能按照格式进行转换，而且还能将数据写入到文件中。

##### 几个重要的`encoder`

- `PatternLayoutEncoder`（最常用、默认的）：
    1. 考虑到`PatternLayout`是`layout`中最常用的组件，所以`logback`人员开发出了`patternLayoutEncoder`类，这个类是`LayoutWrappingEncoder`的扩展，这个类包含了`PatternLayout`。
    2. `immediateFlush`标签与`LayoutWrappingEncoder`是一样的。默认值为【true】。这样的话，在已存在的项目就算没有正常情况下的关闭，也能记录所有的日志信息到磁盘上，不会丢失任何日志信息。因为是立即刷新。如果将【`immediateFlush`】设置为【false】，可能就是五倍的原来的logger接入量。但是在没有正常关闭项目的情况下可能会丢失日志信息:
    ```xml
    <appender name="FILE" class="ch.qos.logback.core.FileAppender"> 
      <file>foo.log</file>
      <encoder><!--默认就是PatternLayoutEncoder类-->
        <pattern>%d %-5level [%thread] %logger{0}: %msg%n</pattern>
        <!-- this quadruples logging throughput -->
        <immediateFlush>false</immediateFlush>
      </encoder> 
    </appender>
    ```
    3. 如果想在文件的开头打印出日志的格式信息：即打印日志的模式。使用【outputPatternAsHeader】标签，并设置为【true】.默认值为【false】。例如：
    ```xml
    <!--输出到控制台 ConsoleAppender-->
    <appender name="consoleLog2" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d -2 %msg%n</pattern>
            <outputPatternAsHeader>true</outputPatternAsHeader>
        </encoder>
    </appender>
    ```
- `LayoutWrappingEncoder`（不常用）：
    1. 在0.9.19版本之前，都是使用layout来控制输出的格式。那就存在大量的layout接口（自定义）的代码。在0.9.19就变成了使用encoder来控制，如果我们想使用以前的layout怎么办？这个LayoutWrappingEncoder就是为了encoder能够操作内部layout存在的。即这个类在encoder与layout之间提供一个桥梁。这个类实现了encoder类，又包含了layout将evnet事件装换成字符串的功能。
    2. 原理：使用layout将输入的evnet事件转换成一个字符串，然后将字符串按照用户指定的编码转换成byte数组。最后将byte数据写入到文件中去。
    3. 在默认的情况下，输出流是立即刷新的。除非immediateFlush属性值为false，就不会立即刷新，但是为提高logger接入量。

### 5. `filter`

logback具有过滤器支持，logback允许给日志记录器`appender`配置一个或多个Filter（或者给整体配置一个或多个`TurboFilter`），当满足过滤器指定的的条件时，才记录日志（不满足条件时，拒绝记录日志）；  
logback支持自定义过滤器，logback也自带了一些常用的过滤器，在绝大多数时候，自带的过滤器其实就足够了，一般是不需要自定义过滤器的。

- `logback` 提供的过滤器支持主要分为两大类
    - `ch.qos.logback.core.filter.Filter`
    - `ch.qos.logback.classic.turbo.TurboFilter`
    
- `Filter` 与 `TurboFilter` 自带的集中常用过滤器


| 过滤器                 | 来源        | 说明                                                         | 相对常用 |
| ---------------------- | ----------- | ------------------------------------------------------------ | -------- |
| LevelFilter            | Filter      | 对指定level的日志进行记录（或不记录），对不等于指定level的日志不记录（或进行记录） | 是       |
| ThresholdFilter        | Filter      | 对大于或等于指定level的日志进行记录（或不记录），对小于指定level的日志不记录（或进行记录） | 是       |
| EvaluatorFilter        | Filter      | 对满足表达式的日志进行记录（或不记录），对不满足指定表达式的日志不记录（或进行记录） | 是       |
| MDCFilter              | TurboFilter | 若MDC域中存在指定的key-value，则进行记录，否则不做记录       | 是       |
| DuplicateMessageFilter | TurboFilter | 根据配置不记录多余的重复日志                                 | 是       |
| MakerFilter            | TurboFilter | 针对带有指定标记的日志，进行记录（或不记录）                 | 否       |

**TurboFilter的性能是由于Filter的，这是因为TurboFilter的作用时机是在创建日志事件ILoggingEvent对象之前，而Filter的作用事件是在创建之后**  
如果一个日志注定是被过滤掉不记录的，那么常见ILoggingEvent对象（包括后续的参数组方法调用等）这个步骤无疑是非常消耗性能的。


#### `ThresholdFilter`
ThresholdFilter为系统定义的拦截器，例如我们用ThresholdFilter来过滤掉ERROR级别一下的日志不输出到文件中
```xml
<filter class="ch.qos.logback.classic.filter.ThresholdFilter">
    <level>ERROR</level>
</filter>
```

#### `LevelFilter`
如果只是想要Info级别的日志，只是过滤info还是会输出Error日志，因为Error的级别比较高，可以使用如下的策略，来避免输出ERROR的日志
```xml
<filter class="ch.qos.logback.classic.filter.LevelFilter">
    <!--过滤 Error-->
    <level>ERROR</level>
    <!--匹配到就禁止-->
    <onMatch>DENY</onMatch>
    <!--没有匹配到就允许-->
    <onMismatch>ACCEPT</onMismatch>
</filter>
```

FilterReply 的三个枚举值
- `DENY`：表示不需要看后面的过滤器了，这里就给拒绝了
- `NEUTRAL`：表示需不需要记录，还需要看后面的过滤器。若所有的过滤器返回的全部都是 `NEUTRAL`, 那么需要记录日志
- `ACCEPT`：表示不用看后面的过滤器了，这楼里就直接同意了，需要记录

### 6. `rollingPolicy`

#### `TimeBasedRollingPolicy`

根据时间来指定滚动策略，时间滚动策略可以基于时间滚动按时间生成日志
```xml
<configuration>
    <appender name="FILE" class="ch.qos.logback.core.RollingFileAppender">
        <file>logFile.log</file>
        <!-- 支持多个JVM写入同一个文件 -->
        <prudent>true</prudent>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBaseRollingPolicy">
            <fileNamePattern>logFile.%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>30</maxHistory>
            <totalSizeCap>3GB</totalSizeCap>
        </rollingPolicy>
        
        <encoder>
            <pattern>%-4relative [%thread] %-5level %logger{35} - %msg&n</pattern>
        </encoder>
    </appender>
    
    <root level="DEBUG">
        <appender-ref ref="FILE" />
    </root>
</configuration>
```

#### `SizeAndTimeBasedRollingPolicy`

基于大小和时间的滚动策略，
这个策略出现的原因就是对时间滚动策略的一个补充，使其不仅按时间进行生成而且考虑到文件大小的原因，
因为在基于时间的滚动策略生成的日志文件，只是对一段时间的总的日志大小做了限定，但是没有对每个日志文件的大小做限定，这就造成个别日志文件过大，后期传递、阅读困难的问题
```xml
<configuration>
    <appender name="FILE" class="ch.qos.logback.core.RollingFileAppender">
        <file>mylog.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <fileNamePattern>mylog-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <maxFileSize>100M</maxFileSize>
            <maxHistory>60</maxHistory>
            <totalSizeCap>20GB</totalSizeCap>
        </rollingPolicy>
        
        <encoder>
            <pattern>%msg&n</pattern>
        </encoder>
    </appender>
    
    <root level="DEBUG">
        <appender-ref ref="ROLLING" />
    </root>
</configuration>
```

####  `FixedWindowRollingPolicy`

基于固定窗口算法重命名文件的滚动策略
```xml
<configuration>
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>test.log</file>
        
        <rollingPolicy class="ch.qos.logback.core.rolling.FixedWindowRollingPolicy">
            <fileNamePattern>tests.%i.log.zip</fileNamePattern>
            <minIndex>1</minIndex>
            <maxIndex>3</maxIndex>
        </rollingPolicy>
        
        <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
            <maxFileSize>5MB</maxFileSize>
        </triggeringPolicy>
        <encoder>
            <pattern>%-4relative [%thread] %-5level %logger{35} - %msg%n</pattern>
        </encoder>
    </appender>
        
    <root level="DEBUG">
        <appender-ref ref="FILE" />
    </root>
</configuration>
```

#### 对应标签内容总结

- fileNamePatterm：这是一个强制的标签。他的值可以包含：文件的名称、适当的%d转换说明符。
这个%d说明符可以包含一个【日期和时间】的模式。其中【模式】类似于【SimpleDateFormat】类。
如果这个【模式】没有写的话，默认就是【yyyy-MM-dd】的模式。 转换文件的名称从fileNamePattern中得到
- `maxHistory`：这是一个可选的标签。以异步方式删除较旧的文件,例如，如果您指定每月滚动，并将maxHistory设置为6，则将保留6个月的归档文件，并删除6个月以上的文件
- `totalSizeCap`：这是一个可选的标签。这是所有日志文件的总大小空间。当日志文件的空间超过了设置的最大空间数量，就会删除旧的文件。**注意：这个标签必须和maxHistory标签一起使用**
- `cleanHistoryOnStart`：如果设置为true，则将在追加程序启动时执行归档删除。默认情况下，此属性设置为false。
- `minIndex`：窗口索引最小值
- `maxIndex`：窗口索引最大值，当用户指定的窗口过大时，会自动将窗口设置为20。
- `maxFileSize`：指定文件最大尺寸，达到该尺寸，就触发rollingPolicy对应的策略，maxFileSize属性指定文件大小

### 7. 子节点 `<loger>`

用来设置某一个包或者具体某一个类的日志打印级别、以及指定`<appender>`，`<loger>`仅有一个name属性、一个可选的level和一个可选的addtivity属性

- `name`：用来指定受此loger约束的某一个包或者具体的某一个类
- `level`：用来设置打印级别，大小写无关：TRACE、DEBUG、INFO、WARE、ERROR、ALL和OFF，还有一个特殊值INHERITED或者同义词NULL，代表执行上级的级别。
如果没有设置此属性，那么当前loger将会集成上级的级别。
- `addvitity`：是否向上级loger传递打印信息，默认是true。

#### `<loger>` 在实际使用中的两种情况

- 带有loger的配置，不指定级别，不指定appender

  ```xml
  <logger name="com.xf.controller.TestLogController"></logger>
  ```

  > 将TestController类的日志进行打印。但是并没有设置打印级别，所以继承它的上级的日志级别 `info` ;没有设置addtivity，默认为true，将此loger的打印信息向上级传递；没有设置appender，此loger本身不打印任何信息

  ---

- 带有多个loger的配置，指定级别，指定appender

  ```xml
  <configuration>
  	<logger name="con.xf.controller.TestLogController" level="WARN" additivity="false">
      	<appender-ref ref="console" />
      </logger>
  </configuration>
  ```

  > 指定了名字为 `console` 的appender；如果将 `additivity="false"` 改为 `additivity="true"` 的话，就会打印两次，因为打印信息向上级传递

### 8. 子节点 `<root>`

root节点是必选节点，用来指定最基础的日志输出级别，只有一个 `level` 属性，缺省值是 `DEBUG` 。

`level`：用来设置打印级别，大小写无关：TRACE、DEBUG、INFO、WARE、ERROR、ALL和OFF，不能设置为INHERITED或者同义词NULL。

可以包含零个或多个元素，标识这个 `appender` 将会添加到这个loger。

```xml
<root level="debug">
    <appender-ref ref="console" />
    <appender-ref ref="file" />
</root>
```

### 9. 多环境配置

springProfile 标签允许你自由的包含或排除基于激活的 spring profiles 的配置的一部分。在 <configuration 元素的任何地方都支持Profile部分。使用 name 属性来指定哪一个profile几首配置。多个profile可以用一个逗号分割的列表来指定

```xml
<springProfile name="staging">
    <!-- configuration to be enabled when the "staging" profile is active -->
</springProfile>

<springProfile name="dev, staging">
    <!-- configuration to be enabled when the "dev" or "staging" profiles are active -->
</springProfile>

<springProfile name="!production">
    <!-- configuration to be enabled when the "production" profile is not active -->
</springProfile>
```

