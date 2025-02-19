package com.scm.contactmanger.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;


public class Helper {
    @SuppressWarnings("null")
    public static String getEmailOfLoggedUser(Authentication authentication){
        if(authentication instanceof OAuth2AuthenticationToken){
            OAuth2AuthenticationToken oAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
            String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            String username="";
            var user=(DefaultOAuth2User)authentication.getPrincipal();
            if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
                username=user.getAttribute("email").toString();

            }
            else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
                username=user.getAttribute("email")!=null?user.getAttribute("email").toString():user.getAttribute("login").toString()+"@gmail.com";
            }
            
            
            return username;
        }
        else{
            System.out.println("getting data from local database from helper without Oauth2");
            return authentication.getName();
        }

        
    }
}
