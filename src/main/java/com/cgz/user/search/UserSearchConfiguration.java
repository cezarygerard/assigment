package com.cgz.user.search;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserSearchConfiguration {

    UserFacade userFacade(UserStoreClient userStoreClient){
        UserRepository userRepository = new UserRepository(userStoreClient);
        return new UserFacade(userRepository);
    }

    @Bean
    UserFacade userFacade(){
        UserStoreClient userStoreClient = new UserStoreClient();
        UserRepository userRepository = new UserRepository(userStoreClient);
        return new UserFacade(userRepository);
    }

}
