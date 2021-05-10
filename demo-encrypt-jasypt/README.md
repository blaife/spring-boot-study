# jasypt

## 1. 项目初搭建
- 数据库sql
    ```sql
    DROP TABLE IF EXISTS `users`;
    CREATE TABLE `users` (
      `id` int(9) NOT NULL AUTO_INCREMENT,
      `name` varchar(255) DEFAULT NULL,
      `age` int(3) DEFAULT NULL,
      `sex` int(1) DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

    -- ----------------------------
    -- Records of users
    -- ----------------------------
    INSERT INTO `users` VALUES ('1', '张三', '29', '1');
    INSERT INTO `users` VALUES ('2', '李四', '27', '2');
    ```
- pom依赖
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>3.3.0</version>
    </dependency>

    <dependency>
        <groupId>cn.hutool</groupId>
        <artifactId>hutool-core</artifactId>
        <version>5.6.3</version>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
    ```
- 配置文件
    ```properties
    ## application.properties

    server.port=8080

    # mybatis-plus:PO类位置
    mybatis-plus.type-aliases-package=com.blaife.jasypt.entity
    # mybatis-plus:配置文件位置
    mybatis-plus.config-location=classpath:mybatis/mybatis-config.xml
    # mybatis-plus:mapper映射文件位置
    mybatis-plus.mapper-locations=classpath:mybatis/mapper/*.xml

    spring.datasource.url=jdbc:mysql://XXX.XXX.XXX.XXX:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true
    spring.datasource.username=root
    spring.datasource.password=password
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    ```
    ```
    ## mybatis-config.xml

    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">

    <configuration>
        <settings>
            <!--配置sql调试-->
            <setting name="logImpl" value="STDOUT_LOGGING" />
        </settings>

        <!--配置java与mySql中的数据类型对比-->
        <typeAliases>
            <typeAlias alias="Integer" type="java.lang.Integer" />
            <typeAlias alias="Long" type="java.lang.Long" />
            <typeAlias alias="HashMap" type="java.util.HashMap" />
            <typeAlias alias="LinkedHashMap" type="java.util.LinkedHashMap" />
            <typeAlias alias="ArrayList" type="java.util.ArrayList" />
            <typeAlias alias="LinkedList" type="java.util.LinkedList" />
        </typeAliases>
    </configuration>
    ```
- 编码内容
    - entity
    ```java
    package com.blaife.jasypt.entity;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    /**
    * @author blaife
    * @description TODO
    * @data 2021/5/9 9:59
    */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class User {

        private long id;
        private String name;
        private int age;
        private int sex;

    }
    ```
    - controller
    ```java
    package com.blaife.jasypt.controller;

    import com.blaife.jasypt.entity.User;
    import com.blaife.jasypt.mapper.UserMapper;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    /**
    * @author blaife
    * @description TODO
    * @data 2021/5/9 9:58
    */
    @RestController
    @RequestMapping("/jasypt")
    public class UserController {

        @Autowired
        private UserMapper userMapper;

        @RequestMapping("/findUserById")
        public User findUserById() {
            return userMapper.findUserById(1);
        }
    }
    ```
    - mapper
    ```java
    package com.blaife.jasypt.mapper;

    import com.blaife.jasypt.entity.User;
    import org.apache.ibatis.annotations.Mapper;
    import org.apache.ibatis.annotations.Select;

    /**
    * @author blaife
    * @description TODO
    * @data 2021/5/9 10:03
    */
    @Mapper
    public interface UserMapper {

        /**
        * 通过用户名获取用户信息
        * @param id 用户ID
        * @return 用户实体
        */
        @Select("SELECT name, age, sex FROM users WHERE `id` = #{id}")
        public User findUserById(long id);
    }
    ```
## 2. pom依赖
```xml
<dependency>
    <groupId>com.github.ulisesbocchio</groupId>
    <artifactId>jasypt-spring-boot-starter</artifactId>
    <version>3.0.2</version>
</dependency>
```
## 3. 配置文件
```properties
# 加密盐值  不应在这里这是展示
jasypt.encryptor.password=blaife

# 不指定前缀后缀, 默认为ENC();
jasypt.encryptor.property.prefix=BLAIFE(
jasypt.encryptor.property.suffix=)
```
## 4. 测试类
```
@Test
public void encryptPwd() {
    String plaintext = "XXXXXX";
    String ciphertext = stringEncryptor.encrypt(plaintext);
    System.out.println("==================");
    System.out.println(plaintext + " 加密后为：" + ciphertext);
}

@Test
public void decryptPwd() {
    String ciphertext = "XXXXXXXXXXXXXXXXXXXXXXX";
    String plaintext = stringEncryptor.decrypt(ciphertext);
    System.out.println("==================");
    System.out.println(ciphertext + " 解密后为：" + plaintext);
}
```
执行方法得到加密后字符串

- 也可以使用如下指令进行获取
    ```text
    java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI password=G0CvDz7oJn6 algorithm=PBEWithMD5AndDES  
    
    password:application.yml配置文件中配置的加密盐  
    algorithm:加密算法  
    input:待加密参数(如账号、密码等敏感信息)   
    ```
## 5. 启动测试
1. 修改数据库密码配置
  ```
  spring.datasource.password=BLAIFE(XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX)
  ```
2. 启动项目调用接口查看项目是否可以正常运行
## 6. 自定义加密
```java
public class BlaifeEncryptorCfg {

    @Bean(name = "blaifeEncryptor")
    public StringEncryptor blaifeStringEncryptor() {

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("Blaife");
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        return encryptor;
    }
}
```
## 7. 隐藏 `jasypt.encryptor.password`
1. 在IDEA中配置  
   Run->Edit Configrations...
   ![123](resources/Edit%20Configrations.png)
2. 作为程序启动时的命令行参数来带入  
   `java -jar yourproject.jar --jasypt.encryptor.password=blaife`
3. 作为程序启动时的应用环境变量来带入  
   `java -Djasypt.encryptor.password=blaife -jar yourproject.jar`
4. 作为系统环境变量的方式来带入  
   `jasypt.encryptor.password=${JASYPT_ENCRYPTOR_PASSWORD:}`
