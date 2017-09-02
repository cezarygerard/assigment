package com.cgz.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserRepositoryConfiguration {

    @Bean
    UserRepository userRepository(){
        return new UserRepository();
    }
}
