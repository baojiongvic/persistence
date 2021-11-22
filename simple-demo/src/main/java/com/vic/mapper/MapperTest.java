package com.vic.mapper;

import com.vic.mapper.entity.User;
import com.vic.persistence.io.Resource;
import com.vic.persistence.session.SqlSession;
import com.vic.persistence.session.SqlSessionFactory;
import com.vic.persistence.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * @author vic
 * @date 2021-11-22 22:34:57
 **/
public class MapperTest {

    @Test
    public void test() throws Exception {
        InputStream is = Resource.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(1);
        user.setName("张三");
        User result = sqlSession.selectOne("user.selectOne", user);
        System.out.println(result.toString());

        List<User> list = sqlSession.selectList("user.selectList", null);
        System.out.println(list.toString());

    }

}
