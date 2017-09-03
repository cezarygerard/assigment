package com.cgz.user.search;

import java.util.Arrays;
import java.util.List;

class UserStoreClient {

    //TODO Hystrix command
    //TODO cache with expiration

    List<User> getAllUsers(){
        return  Arrays.asList(new User(1, "a"), new User(2, "b"));
    }
}
