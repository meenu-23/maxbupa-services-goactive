package com.maxbupa.apiservices.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomDetailsServiceImpl.class);

    @Autowired
    OAuthDao oauthDao;

    @Override
    public CustomUser loadUserByUsername(final String username) throws UsernameNotFoundException {
        UserEntity userEntity = null;
        CustomUser customUser = null;
        LOGGER.debug("username in CustomDetailsServiceImpl.loadUserByUsername() : {}",username);
        try {
            userEntity = oauthDao.getUserDetails(username);
            customUser = new CustomUser(userEntity);
        } catch (Exception e) {
            LOGGER.error("Exception in CustomDetailsServiceImpl.loadUserByUsername() : ",e);
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
      return customUser;
    }
}
