package com.ajavac.dao;

import com.ajavac.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户dao层接口
 * Created by wyp0596 on 21/03/2017.
 */
public interface UserDao {

    int insert(@Param("pojo") User pojo);

    int updatePasswordById(@Param("updatedPassword")String updatedPassword,@Param("id")Long id);

    List<User> findByUsername(@Param("username")String username);

    List<User> find();

	int deleteById(@Param("id")Long id);


    int insertSelective(@Param("pojo") User pojo);

    int insertList(@Param("pojos") List<User> pojo);

    int update(@Param("pojo") User pojo);






}
