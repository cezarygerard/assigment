package com.cgz;

import com.cgz.dto.UserDto;
import com.cgz.user.search.UserFacade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserReadOnlyController {

    private UserFacade userFacade;

    @GetMapping("/user/{id}")
    public UserDto findOne(@PathVariable long id){
        return userFacade.findUserById(id);
    }

    @GetMapping("/users")
    public Iterable<UserDto> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(name = "sort", required = false) List<String> sortingParams){

        //TODO implement HATEOS links for paging
        return Collections.singletonList(new UserDto(1,"a"));
    }

}
