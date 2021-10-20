# spring boot 中的基础配置内容

## 配置文件 `application`

springboot 中的配置文件有两种不同的格式，一种是properties，另外一种是yaml

- properties：比较常见，出现的时间比较早
- yaml：更加简洁且有序，在一些需要路径匹配的配置中，顺序就尤为重要

### 配置文件的存放位置

- 当前项目的根目录下的config目录下
- 当前项目的根目录下
- resource目录下的config目录下
- resource目录下

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

