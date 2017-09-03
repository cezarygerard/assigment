package com.cgz.user.search;


import com.cgz.dto.UserDto;
import com.cgz.dto.UserNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserRepository {

    private UserStoreClient userStoreClient;

    Page<UserDto> findAll(Pageable pageable) {
        return null;
    }

    UserDto findOne(long id) {
        return userStoreClient.getAllUsers()
                .stream()
                .filter(user -> user.id == id)
                .map(User::dto)
                .findAny()
                .orElseThrow(() -> new UserNotFoundException(id));
                //TODO 404 error handler
    }

}
