package com.cgz.user.search;

import com.cgz.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@AllArgsConstructor
public class UserFacade {

    private UserRepository userRepository;

    private PageableFactory pageableFactory;

    public UserDto findUserById(long id){
        return userRepository.findOne(id);
    }

    Page<UserDto> findAll(int pageNumber, int pageSize,  List<String> sortingParams) {
        Pageable pageable = pageableFactory.from(pageNumber, pageSize, sortingParams);

        return userRepository.findAll(pageable);
    }



}
