# spring boot 中的基础配置内容

## 配置文件 `application`

springboot 中的配置文件有两种不同的格式，一种是properties，另外一种是yaml

- properties：比较常见，出现的时间比较早
- yaml：更加简洁且有序，在一些需要路径匹配的配置中，顺序就尤为重要

### 配置文件的存放位置

1. 当前项目的根目录下的config目录下
1. 当前项目的根目录下
1. resource目录下的config目录下
1. resource目录下

***按照如上顺序，四个配置文件的优先级依次降低***

这四个位置是默认位置，我们也可以在项目启动时自定义配置配置文件位置

通过 `Edit configurations...` -> 环境变量，中配置 `spring.config.location` 属性来手动的指定配置文件位置。

如果项目已经打包成了jar，在启动命令中加入位置参数即可：`java -jar XXXXX.jar --spring.config.location=classpath:/XXXX/`

### 配置文件的文件名

对于application.properties来说，它的名字是可以修改的

指定方式与修改存放位置一致，只不过此时属性key为 `spring.config.name`

### 普通的属性注入

属性注入可以使用 `@Value` 注解，配置内容如下：

```properties
user.name=blaife
```
```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class userForBasic() {

    @Value("${userForBasic.name}")
    private String name;

    /* 省略 set 方法 */

}
```
**需要注意的是：要注入的对象本身也需要交给 `Spring` 容器去管理**

这种属性初始化的注入并不建议放置在application.properties配置文件中，我们可以自定义相应的配置文件进行处理。
```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:XXX.properties")
public class userForBasic() {

    @Value("${userForBasic.name}")
    private String name;

    /* get,set */
}
```

** 注意：不要再无参构造器中，进行new对象的操作。否则就会造成@Value注解失败,需要使用@Autowired进行取值 **  
如下：
```java
import com.blaife.config.entity.UserForBasic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BasicConfigApplicationTests {

    @Autowired
    UserForBasic userForBasic;

    /**
     * 基本的属性注入
     */
    @Test
    void propertiesInto() {
        System.out.println(userForBasic.toString());
    }
}

```

此普通的属性注入是 `spring` 的一个简单用法，与 `spring boot` 没有任何关系

### 类型安全的属性注入

如果采用Spring中的配置方式，当配置的属性非常多的时候，工作量就会很大，而且还很容易出错，这时候就要使用过类型安全的属性注入

主要是引用 `@ConfigurationProperties(prefix="userForSafety")`当配置了属性的前缀，此时会自动将数据注入到对应的对象属性中。

```properties
user.safety.name=blaife
user.safety.age=19
user.safety.sex=男
```
```java
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:user.properties")
@ConfigurationProperties(prefix = "user.safety")
public class UserForSafety {

    private String name;
    private String age;
    private String sex;

    /* get,set */
}
```

### 数组注入

yaml支持数组注入，但是目前不支持 `@PropertySource` 注解

```yaml
my:
  user:
    - name: blaife
      sex: 男
    - name: power
      sex: 女
```

```java
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "my")
public class UserForArray {

    private List<User> user = new ArrayList<>();

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
}
```

## 静态资源存放位置

在 `spring boot` 中，默认的情况下，一共有5个位置可以放静态资源，五个路径分别是如下5个：

1. classpath:/META-INF/resources/
1. classpath:/resources/
1. classpath:/static/
1. classpath:/public/
1. /

前四个目录都好理解，分别对应了resources目录下的不同的目录，而第五个 `/` 标识的是webapp目录。

目录的优先级也是按照上述的顺序。

### 自定义配置
```properties
spring.resources.static-locations=classpath:/
spring.mvc.static-path-pattern=/**
```
第一行配置表示定义资源位置，第二行配置表示定义请求URL规则
如果我们如上定义，那么可以将静态资源放置在resource目录下的任何位置，访问的时候也需要完整的路径

## WebMvcConfigurer 配置

### 资源放行

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/aaa/");
    }
}
```

注意：此配置可以与上述的配置文件中的静态资源配置同时存在

### 跨域问题解决

这个使用跨域解决方案是 `CORS` （跨域源资源共享）, 也就是 `Cross-origin resource sharing`, 
这是一个W3C标准，是一份浏览器技术的规范，提供了Web服务从不同网域传来沙盒脚本的方法

```java
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("*").allowedMethods("*").allowedHeaders("*");
    }
}
```

## @ControllerAdvice 注解

此注解非常重要，它是一个增强的Controller，可以实现三个方面的功能：

1. 全局异常处理
2. 全局数据绑定
3. 全局数据预处理

### 全局异常处理

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String customerException(Exception e) {
        e.printStackTrace();
        return "系统异常";
    }
}
```

可以同时定义多个方法接收异常，且不需要管理其顺序，异常的捕获会自动去找到优先级最高的方法进行处理

### 全局数据绑定

```java
@RestController
public class GlobalDataBindingController {

    @RequestMapping("/getLogData")
    public Map getLogData(Model model) {
        return (Map) model.asMap().get("logData");
    }

}
```
```java
@RestController
public class GlobalDataBindingController {

    @RequestMapping("/getLogData")
    public Map getLogData(Model model) {
        return (Map) model.asMap().get("logData");
    }

    @RequestMapping("/getLogData2")
    public Map getLogData2(Model model) {
        return (Map) model.getAttribute("logData");
    }

}
```

@ModelAttribute注解标记的方法返回的是一个全局数据，默认的key是返回类型的驼峰形式，如 返回的类型是 Map，则key就是 map  
可以通过@ModelAttribute(name="logData")来修改全局变量的数据的key值

在Controller中取值要加上Model参数，用来接收全局数据。
- 使用mode的getAttribute方法
- 使用asMap 方法转成一个Map，再使用key值从map中获取数据

### 全局数据预处理

当接收的参数中存在 UserForBasic.class 和 UserForSafety.class 参数

此时当你传递参数 name 时就会存在问题，因为两个实体中都存在一个name属性，无法区分。

通过全局数据预处理可以解决这个问题

```java
@ControllerAdvice
public class GlobalDataPreprocess {

    @InitBinder("b")
    public void b(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("b.");
    }

    @InitBinder("s")
    public void s(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("s.");
    }

}
```
```java
@RestController
public class ColbalDataPreprocessController {

    @RequestMapping("toUser")
    public void toUser(@ModelAttribute("b") UserForBasic basic, @ModelAttribute("s") UserForSafety safety) {
        System.out.println(basic);
        System.out.println(safety);
    }

}
```

## 自定异常处理

默认情况下，完整的异常信息就是下面这5条

- path：异常请求地址
- error：异常类型
- message：异常信息
- timestamp：异常发生时间
- status：请求状态

这5条数据定义在 `import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;` 类中的 `getErrorAttributes` 方法里

`DefaultErrorAttributes` 类本身是在 `org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration` 异常自动配置类中定义的  
如果开发者没有自己提供一个 `ErrorAttributes` 的实例的话，Spring boot 会自动提供一个实例，也就是 `DefaultErrorAttribute`

自定义 `ErrorAttributes` 有两种方式：
- 直接实现 `ErrorAttribute` 接口
- 继承 `DefaultErrorAttributes` (推荐)，因为 `DefaultErrorAttributes` 中对异常处理数据的处理已经完成，开发者可以直接使用

```java
@Component
public class BasicErrorAttribute extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, options);
        if ((Integer) map.get("status") == 500) {
            map.put("message", "服务器内部错误");
        }
        return map;
    }
}
```

当写了 `@ExceptionHandler` 此方法会失效，因为此时，请求返回结果都是 200, 不会进入异常界面

这种方式适合前后端不分离的状态，直接展示出错误页面，http请求code不进行修改。若要进行测试，可以暂时把 `@ExceptionHandler` 内容全部注释




