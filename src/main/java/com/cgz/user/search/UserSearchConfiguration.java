package com.cgz.user.search;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class UserSearchConfiguration {

    private PageableFactory pageableFactory = new PageableFactory();

    UserFacade userFacade(UserStoreClient userStoreClient){
        UserRepository userRepository = new UserRepository(userStoreClient);
        return new UserFacade(userRepository, pageableFactory);
    }

    @Bean
    UserFacade userFacade(RestTemplate restTemplate,
                          @Value("${user.store.endpoint}") String userStoreEndpoint) {

        UserStoreClient userStoreClient = new UserStoreClient(restTemplate,userStoreEndpoint);
        UserRepository userRepository = new UserRepository(userStoreClient);
        return new UserFacade(userRepository, pageableFactory);
    }

}
