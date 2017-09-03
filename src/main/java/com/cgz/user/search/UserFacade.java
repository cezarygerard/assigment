package com.cgz.user.search;

import com.cgz.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@AllArgsConstructor
public class UserFacade {

    private final UserRepository userRepository;

    private final PageableFactory pageableFactory;

    public UserDto findUserById(long id){
        return userRepository.findOne(id);
    }

    public Page<UserDto> findAll(int pageNumber, int pageSize,  List<String> sortingParams) {
        Pageable pageable = pageableFactory.from(pageNumber, pageSize, sortingParams);

        return userRepository.findAll(pageable);
    }



}
