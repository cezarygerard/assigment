package com.cgz.user.search;


import com.cgz.dto.UserDto;
import com.cgz.dto.UserNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserRepository {

    private UserStoreClient userStoreClient;

    Page<UserDto> findAll(Pageable pageable) {

        List<User> allUsers = userStoreClient.getAllUsers();

        List<UserDto> sortedUsers = allUsers.stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .sorted(new UserComparator(pageable.getSort()))
                .map(User::dto)
                .collect(Collectors.toList());

        return new PageImpl<>(sortedUsers, pageable,  allUsers.size());
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
