package com.blaife.jasypt.mapper;

import com.blaife.jasypt.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author blaife
 * @date 2021/5/9 10:03
 */
@Mapper
public interface UserMapper {

    /**
     * 通过用户名获取用户信息
     * @param id 用户ID
     * @return 用户实体
     */
    @Select("select name, age, sex from users where `id` = #{id}")
    public User findUserById(long id);
}
