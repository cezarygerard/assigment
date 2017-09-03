package com.cgz.user.search;

import com.cgz.user.search.FetchAllUsersCommand.UserServiceResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
class UserStoreClient {

    private final RestTemplate restTemplate;

    private final String clientStoreEndpoint;

    //TODO implement @Cacheable if possible
    List<User> getAllUsers(){
        List<UserServiceResponse> commandResponse =
                new FetchAllUsersCommand(clientStoreEndpoint, restTemplate).execute();

        return mapToDomainUserObject(commandResponse);
    }

    private List<User> mapToDomainUserObject(List<UserServiceResponse> commandResponse) {
        return commandResponse.stream()
                .map(response -> new User(response.id, response.name))
                .collect(Collectors.toList());
    }
}
