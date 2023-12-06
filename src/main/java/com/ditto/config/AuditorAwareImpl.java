package com.ditto.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "";
        if(authentication != null){
            //인증 객체가 있으면 인증객체에서 유저이름을 가져온다.
            userId = authentication.getName();
        }
        //유저ID 가 포함된 옵셔널 객체를 리턴
        return Optional.of(userId);
    }
}
