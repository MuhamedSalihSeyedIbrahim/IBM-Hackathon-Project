package com.cognizant.authenticationservice.Service;

import com.cognizant.authenticationservice.Dao.DaoOperation;
import com.cognizant.authenticationservice.Model.AppUser;
import com.cognizant.authenticationservice.Model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AppUserDetailsService implements UserDetailsService {


    @Autowired
    DaoOperation daoOperation;

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        log.info("Start");
        User user = daoOperation.loadUserByUsername(emailId);

        if (user == null)
            throw new UsernameNotFoundException("user name not found exception.");

        AppUser appUser = new AppUser(user);
        log.info("authorities:" + appUser.getAuthorities());
        return appUser;

    }

}
