package com.cgz.user.search;

import com.cgz.dto.UserDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
class User {

    long id;

    String name;

    UserDto dto(){
        return new UserDto(id,name);
    }
}
