package com.cgz.controller;

import com.cgz.dto.PagedUserDto;
import com.cgz.dto.UserDto;
import com.cgz.dto.UserNotFoundException;
import com.cgz.user.search.UserFacade;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserSearchController {

    private final UserFacade userFacade;

    private final PagedUserFactory pagedUserFactory;

    static final String ALL_USERS_PATH = "/users";

    @GetMapping("/user/{id}")
    public UserDto findOne(@PathVariable long id){
        return userFacade.findUserById(id);
    }

    @GetMapping(ALL_USERS_PATH)
    public PagedUserDto findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(name = "sort", required = false) List<String> sortingParams){

        Page<UserDto> pagedUsers = userFacade.findAll(page, size, sortingParams);
        return pagedUserFactory.from(pagedUsers);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(HttpServletResponse response, UserNotFoundException exception){
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return exception.getMessage();
    }


}
