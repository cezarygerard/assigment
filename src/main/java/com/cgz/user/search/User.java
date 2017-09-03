package com.cgz.user.search;

import com.cgz.dto.UserDto;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@ToString
class User {

    long id;

    String name;

    UserDto dto(){
        return new UserDto(id,name);
    }
}
