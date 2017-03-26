package com.ajavac.dao;

import com.ajavac.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户dao层接口
 * Created by wyp0596 on 21/03/2017.
 */
@Repository
public interface UserDao extends JpaRepository<User,Long>{

}
