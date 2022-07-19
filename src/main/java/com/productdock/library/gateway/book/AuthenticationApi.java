package com.productdock.library.gateway.book;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-info")
public class AuthenticationApi {

    @GetMapping
    public Object getLoggedInUserInfo(
                            OAuth2AuthenticationToken authenticationToken){
        var user = (DefaultOidcUser) authenticationToken.getPrincipal();

        return new UserDto(
                user.getClaim("name"),
                user.getClaim("picture"),
                user.getClaim("email"));
    }

    @AllArgsConstructor
    class UserDto {
        public String name;
        public String imageUrl;
        public String email;
    }
}
