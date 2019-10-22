package com.micro.fast.oauth.impl;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.micro.fast.oauth.dto.UpmsUser;
import com.micro.fast.oauth.feign.UpmsUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * 获取用户的信息
 * @author lsy
 */
@Component("msUserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UpmsUserService upmsUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServerResponse<UpmsUser> userByUserName = upmsUserService.getUserByUserName(username);
        UpmsUser upmsUser = userByUserName.getData();
        //判断用户是否存在
        if (upmsUser!=null){
          if( StringUtils.equals(username,upmsUser.getUsername())){
              Byte locked = upmsUser.getLocked();
              boolean enable = false;
              if (locked == 0){
                  enable =true;
              }
              //刷新令牌必须有ROLE_USER权限
              User user = new User(username,upmsUser.getPassword()
                      ,enable,true,true,enable
                          , AuthorityUtils.createAuthorityList("admin","ROLE_USER"));
              return user;
          }
        }
        return null;
    }
}
