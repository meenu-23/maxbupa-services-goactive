package com.maxbupa.apiservices.authentication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public class OAuthDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public UserEntity getUserDetails(String username)
    {
        Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
        Query q = new Query();
        new Criteria();
        q.addCriteria(Criteria.where("username").is(username));
        UserEntity user = mongoTemplate.findOne(q, UserEntity.class, "Users");
        if(user != null) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_SYSTEMADMIN");
            grantedAuthoritiesList.add(grantedAuthority);
            user.setGrantedAuthoritiesList(grantedAuthoritiesList);
            return user;
        }
        return null;
    }

}