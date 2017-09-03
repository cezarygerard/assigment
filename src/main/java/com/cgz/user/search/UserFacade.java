package com.cgz.user.search;

import com.cgz.dto.UserDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserFacade {

    private UserRepository userRepository;

    public UserDto findUserById(long id){
        return userRepository.findOne(id);
    }




}
