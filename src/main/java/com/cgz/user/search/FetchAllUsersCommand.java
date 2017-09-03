package com.cgz.user.search;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.java.Log;

import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Log
class FetchAllUsersCommand extends HystrixCommand<List<FetchAllUsersCommand.UserServiceResponse>> {

    static final String USERS_PATH = "/user";

    private final String endpoint;

    private final RestTemplate restTemplate;

    public FetchAllUsersCommand(String endpoint, RestTemplate restTemplate) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("FetchAllUsers")));
        this.endpoint = endpoint + USERS_PATH;
        this.restTemplate = restTemplate;
    }

    @Override
    protected List<UserServiceResponse> run() throws Exception {
        UserServiceResponse[] response = restTemplate.getForObject(
                endpoint,
                UserServiceResponse[].class
        );

        return Arrays.asList(response);
    }

    @Override
    protected List<UserServiceResponse> getFallback() {
        log.warning("Fetching users FAILED. Returning empty object.");
        return Collections.emptyList();
    }

    static class UserServiceResponse{
        public long id;
        public String name;
    }
}
