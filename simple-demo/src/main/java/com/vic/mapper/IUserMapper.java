package com.vic.mapper;

import com.vic.mapper.entity.User;

import java.util.List;

/**
 * @author vic
 * @date 2021/11/23 10:33 下午
 **/
public interface IUserMapper {

    /**
     * 查询一个
     *
     * @param user 用户
     * @return {@link User}
     */
    User selectOne(User user);

    /**
     * 查询列表
     *
     * @return {@link List}<{@link User}>
     */
    List<User> selectList();
}
