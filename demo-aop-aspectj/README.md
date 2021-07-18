# AspectJ

AOP虽然是方法论，但是就好像OOP中的JAVA一样，一些先行者开发了一套语言来支持AOP。目前来说，AspectJ是最常用的，他是一种几乎和JAVA一样的语言，完全兼容JAVA。

除了AspectJ特殊的语言外，Aspect还支持原生的JAVA，只要加上AspectJ注解就好，所以，使用AspectJ有两种方法：

- 完全使用AspectJ的语言。这语言一点都不难，和JAVA几乎一样，也可以在AspectJ中调用JAVA的任何类。AspectJ中只是多了一些关键字罢了。
- 或者使用纯JAVA语言开发，然是使用AspectJ注解，简称 `@AspectJ`

此dmo中使用Springboot项目引入AspectJ依赖的方式实现

## AOP 使用场景

权限控制、异常处理、缓存、事务管理、日志记录、数据校验等等

## AspectJ 相关概念

- Advice（通知）：注入到class文件中的代码，除了在方法中注入代码，也可能会对代码左其他修改，比如在class中增加字段或者接口

- Joint point（连接点）：程序中可能作为代码注意目标的特定的点，例如一个方法调用或者方法入口

- Pointcut（切入点）：告诉代码注入工具，在何处注入一段代码的表达式

  
## 关于连接点的相关概念

pointcuts 遵循特定的语法用于捕获每一个种类的可使用连接点，主要的种类包含如下内容

- 方法执行：execution(MethodSignature)
- 方法调用：call(MethodSignature)
- 构造器执行：execution(ConstructorSignature)
- 构造器调用：call(ConstructorSignature)
- 类初始化：staticinitialization(TypeSignature)
- 属性读操作：get(FieldSignature)
- 属性写操作：set(FieldSignature)
- 例外处理执行：handler(TypeSignature)
- 对象初始化：initialization(ConstructorSignature)
- 对象预先初始化：preinitialization(ConstructorSignature)

## 切入点相关注解

### `@Pointcut`切入点

Pointcut的定义包括两部分

- **Pointcut表达式：**就是Pointcut后面的参数内容
- **Pointcut签名：**`@Pointcut`所在的方法名称

***关于pointcut表达式：***

（添加每种表达式的内容详解）

- `execution`：一般用于指定方法的执行用的最多

  ```tex
  execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)throws-pattern?) 
  ```

  - **各参数内容**

    后面跟着 `?` 的时可选项

    |        参数内容        |     描述     |
    | :--------------------: | :----------: |
    |   modifiers-pattern    |  访问修饰符  |
    |    ret-type-pattern    |  返回值类型  |
    | declaring-type-pattern |  类路径匹配  |
    |      name-patter       |  方法名匹配  |
    |     param-pattern      |   参数匹配   |
    |     throws-pattern     | 异常类型匹配 |

- `within`：指定某些类型的全部方法执行，也可以用来指定一个包

  ```tex
  within(类型表达式)
  ```

  - **表达式内容**

    | 模式                                 | 描述                                                         |
    | ------------------------------------ | ------------------------------------------------------------ |
    | within(cn.javass..*)                 | cn.javass包及子包下的任何方法执行                            |
    | within(cn.javass..IPointcutService+) | cn.javass包或所有子包下IPointcutService类型及子类型的任何方法 |
    | within(@cn.javass..Secure *)         | 持有cn.javass..Secure注解的任何类型的任何方法                |

    

- `this`：Spring Aop是基于代理的，生成的bean也是一个代理对象，this就是这个代理对象，当这个代理对象可以转换为指定的类型时，对应的切入点就是它了，Spring Aop将生效

  ```tex
  this(类型全限定名)
  ```

  - **表达式内容**

    this中使用的表达式必须是类型全限定名，不支持统配符

    | 模式                                                         | 描述                                                 |
    | ------------------------------------------------------------ | ---------------------------------------------------- |
    | this(cn.javass.spring.chapter6.service.IPointcutService)     | 当前AOP对象实现了 IPointcutService接口的任何方法     |
    | this(cn.javass.spring.chapter6.service.IIntroductionService) | 当前AOP对象实现了 IIntroductionService接口的任何方法 |

- `target`：当被代理的对象可以转换为指定的类型时，对应的切入点就是它了，SpringAop将生效。

  ```tex
  target(类型全限定名)
  ```

  - **表达式内容**

    target中使用的表达式必须是类型全限定名，不支持统配符

    | 模式                                                         | 描述                                                         |
    | ------------------------------------------------------------ | ------------------------------------------------------------ |
    | target(cn.javass.spring.chapter6.service.IPointcutService)   | 当前目标对象（非AOP对象）实现了 IPointcutService接口的任何方法 |
    | target(cn.javass.spring.chapter6.service.IIntroductionService) | 当前目标对象（非AOP对象） 实现了IIntroductionService 接口的任何方法 |

- `args`：当执行的方法的参数时指定的类型时生效。

  ```tex
  args(参数类型匹配)
  ```

  - **表达式内容**

    参数类型中的参数必须是类型全限定名，通配符不支持

    args属于动态切入点，这种切入点开销非常大，非特殊情况下不要使用

    | 模式                           | 描述                                                         |
    | ------------------------------ | ------------------------------------------------------------ |
    | args (java.io.Serializable,..) | 任何一个以接受“传入参数类型为 java.io.Serializable” 开头，且其后可跟任意个任意类型的参数的方法执行，args指定的参数类型是在运行时动态匹配的 |

- `@within`：当目标对象的类或者父类上有指定的注解时生效

  ```tex
  @within(注解类型)
  ```

  - **表达式内容**

    注解类型必须是全限定名

    必须是在目标对象上声明这个注解，在接口上声明的对它不起作用

    | 模式                                      | 描述                                           |
    | ----------------------------------------- | ---------------------------------------------- |
    | @within(cm.javass.spring.chapter6.Secure) | 任何目标对象对应的类型上持有Secure注解的类方法 |

- `@target`：当代理的目标对象上拥有指定的注解时生效

  ```
  @target(注解类型)
  ```

  - **表达式内容**

    注解类型必须是全限定名

    必须是在目标对象上声明这个注解，在接口上声明的对它不起作用

    | 模式                                      | 描述                               |
    | ----------------------------------------- | ---------------------------------- |
    | @target(cn.javass.spring.chapter6.Secure) | 任何目标对象持有Secure注解的类方法 |

- `@args`：当执行的方法参数类型上拥有指定的注解时生效

  ```tex
  @args(注解列表)
  ```

  - **表达式内容**

    注解类型必须是全限定名

    | 模式                                     | 描述                                                         |
    | ---------------------------------------- | ------------------------------------------------------------ |
    | @args (cn.javass.spring.chapter6.Secure) | 任何一个只接受一个参数的方法，且方法运行时传入的参数持有注解 cn.javass.spring.chapter6.Secure；动态切入点，类似于arg指示符； |

- `@annotation`：当执行的方法上拥有指定的注解时生效

  ```
  @annotation(注解类型)
  ```

  - **表达式内容**

    注解类型也必须是全限定类型名

    | 模式                                           | 描述                                                         |
    | ---------------------------------------------- | ------------------------------------------------------------ |
    | @annotation(cn.javass.spring.chapter6.Secure ) | 当前执行方法上持有注解 cn.javass.spring.chapter6.Secure将被匹配 |

- `bean`：当调用的方法是指定的bean的方法时生效

  ```tex
  bean(Bean id或名字通配符)
  ```

  - 表达式内容

    | 模式           | 描述                                        |
    | -------------- | ------------------------------------------- |
    | bean(*Service) | 匹配所有以Service命名（id或name）结尾的Bean |

---

***组合使用***

AspectJ使用 且（&&）、或（||）、非（！）来组合切入点表达式。

在Schema风格下，由于在XML中使用“&&”需要使用转义字符“&amp;&amp;”来代替之，所以很不方便，因此Spring ASP 提供了and、or、not来代替&&、||、！。



## 通知类型内容

通知类型包含如下内容
- 前置通知（before advice）
- 后置通知（after advice）
- 环绕通知（around advice）
- 事后返回通知（afterRetuming advice ）
- 异常通知（afterThrowing advice）

## 通知参数

- JoinPoint：提供访问当前被通知方法的目标对象、代理对象、方法参数等数据
- ProceedingJoinPoint： 用于环绕通知，使用proceed()方法来执行目标方法
- JoinPoint.StaticPart：提供访问连接点的静态部分，如被通知方法签名、连接点类型等
- 自动获取（argNames）：通过切入点表达式可以将相应的参数自动传递给通知方法，例如前边章节讲过的返回值和异常是如何传递给通知方法的。


## 关于Advice的执行顺序

- 通知类型的执行顺序
  - 无异常情况：Around->Before->自己的method->Around->After->AfterReturning
  - 异常情况：Around->Before->自己的method->Around->After->AfterThrowing

- 当多个AspectJ作用与一个方法上，如何指定每个aspect的执行顺序？方法有两种：
  - 实现`org.springframework.core.Ordered`接口，实现他的getOrder()方法
  - 给aspect添加@Order注解，该注解全称为`org.springframework.core.annotation.Order`

***注意***

- 如果在同一个aspectJ类中，针对同一个pointcut，定义了两个相同的advice（比如，定义了两个@Before），那么这两个advice的顺序是无法确定的，哪怕你给这两个advice添加了@Order这个注解,也是不行的。
- 对于@Around这个advice，不管它有没有返回值，但是需要要方法内部调用一下pip.proceed();否则，Controller中的接口将没有机会被执行，从而导致这个advice不会被触发。

## 基本配置流程

### pom依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

此依赖中包含了`org.aspectj:aspectjweaver`依赖。

### 业务代码

业务代码随便写，具体可查看连接点相关内容

### 切面类配置

```java
package com.blaife.aspectj.aspect;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author blaife
 * @description TODO
 * @data 2021/6/27 18:52
 */
@Aspect
@Component
@Log
public class LogAspect {
    @Pointcut("execution(* com.blaife.aspectj.service.impl.AspectjServiceImpl.testAspectJ(..))")

    public void stringParam(){

    }


    @Before(value="execution(* testAspectJ(*)) && args(param)", argNames="param")
    public void before1(String param) {
        System.out.println("===param:" + param);
    }

    @Before("stringParam()")
    public void stringParamBefore(JoinPoint joinPoint) {
        System.out.println(joinPoint.getArgs().length);
        for (Object thisParm : joinPoint.getArgs()) {
            System.out.println(thisParm);
        }
    }
}

```


## 案例








## 注意事项

@Aspect不再能修饰接口，而只能是类
