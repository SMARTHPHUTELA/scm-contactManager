package com.scm.contactmanger.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.contactmanger.entity.providers;
import com.scm.contactmanger.entity.user;
import com.scm.contactmanger.helper.constants;
import com.scm.contactmanger.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class oAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
    Logger logger=org.slf4j.LoggerFactory.getLogger(oAuthAuthenticationSuccessHandler.class);
    
    @Autowired
    private UserRepo userRepo;
    @SuppressWarnings("null")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
                logger.info("oAuthAuthenticationSuccessHandler");

                var oAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
                String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
                logger.info(authorizedClientRegistrationId);

                DefaultOAuth2User usr=(DefaultOAuth2User)authentication.getPrincipal();
                user user1=new user();
                user1.setEmail_verified(true);
                user1.setId(UUID.randomUUID().toString());
                user1.setPassword("appleid*");
                user1.setRoleList(List.of(constants.role_user));
                if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
                    String email=usr.getAttribute("email").toString();
                    String name=usr.getAttribute("name").toString();
                    String picture=usr.getAttribute("picture").toString();
                    user1.setEmail(email);
                    user1.setUser_name(name);
                    user1.setProfilepic(picture);
                    user1.setProvider(providers.GOOGLE);
                    user1.setProviderUserId(usr.getName());
                    user1.setAbout("User from GOOGLE");

                }
                else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
                    String email=usr.getAttribute("email")!=null ?usr.getAttribute("email").toString(): usr.getAttribute("login").toString()+"@gmail.com";
                    String picture=usr.getAttribute("avatar_url").toString();
                    String name=usr.getAttribute("login").toString();
                    user1.setEmail(email);
                    user1.setProfilepic(picture);
                    user1.setUser_name(name);
                    user1.setProvider(providers.GITHUB);
                    user1.setProviderUserId(usr.getName());
                    user1.setAbout("User from GITHUB");


                }
                else{
                    logger.info("unknown Provider");
                }

                // saving data to data-base
                // DefaultOAuth2User usr=(DefaultOAuth2User)authentication.getPrincipal();
                // String email=usr.getAttribute("email").toString();
                // String name=usr.getAttribute("name").toString();
                // String picture=usr.getAttribute("picture").toString();
                // user user1=new user();
                // user1.setEmail(email);
                // user1.setUser_name(name);
                // user1.setProfilepic(picture);
                // user1.setProvider(providers.GOOGLE);
                // user1.setEmail_verified(true);
                // user1.setEnabled(true);
                // user1.setRoleList(List.of(constants.role_user));
                // user1.setPassword("appleid*");
                // user1.setProviderUserId(usr.getName());
                // user1.setAbout("hey i am from google");
                // user1.setId(UUID.randomUUID().toString());
                


                user foundUser=userRepo.findUserByEmail(user1.getEmail()).orElse(null);
                if(foundUser==null){
                    userRepo.save(user1);
                    logger.info("user saved");
                }

                
                new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
        
    }


}
