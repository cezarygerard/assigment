package com.cgz.controller;

import com.cgz.dto.PagedUserDto;
import com.cgz.dto.UserDto;
import com.google.common.collect.ImmutableMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

import static com.cgz.controller.UserSearchController.ALL_USERS_PATH;

//TODO tests smell. Split into smaller classes
@Component
class PagedUserFactory {

    static final String RELATION = "rel";
    static final String NEXT = "next";
    static final String PREVIOUS = "prev";
    static final String HREF = "href";
    static final String SORT_FORMAT = "&sort=%s.%s";
    static final String SIZE_PARAM = "&size=";
    static final String PAGE_PARAM = "?page=";

    PagedUserDto from(Page<UserDto> pagedUsers) {

        ArrayList<Map<String, String>> navigationLinks = new ArrayList<>();

        linkToNext(pagedUsers).ifPresent(
                link -> navigationLinks.add(ImmutableMap.of(RELATION, NEXT, HREF, link))
        );

        linkToPrev(pagedUsers).ifPresent(
                link -> navigationLinks.add(ImmutableMap.of(RELATION, PREVIOUS, HREF, link))
        );


        return PagedUserDto.builder()
                .users(pagedUsers.getContent())
                .links(navigationLinks)
                .build();
    }

    private Optional<String> linkToPrev(Page<UserDto> pagedUsers) {
        if(pagedUsers.isFirst()){
            return Optional.empty();
        }

        String link = ALL_USERS_PATH +
                        page(pagedUsers.getNumber()-1) +
                        size(pagedUsers.getSize()) +
                        sort(pagedUsers.getSort());


        return Optional.of(link);
    }

    private Optional<String> linkToNext(Page<UserDto> pagedUsers) {
        if(pagedUsers.isLast()){
            return Optional.empty();
        }

        String link = ALL_USERS_PATH +
                        page(pagedUsers.getNumber()+1) +
                        size(pagedUsers.getSize()) +
                        sort(pagedUsers.getSort());
        return Optional.of(link);
    }


    private String sort(Sort sort) {
        if(sort == null){
            return "";
        }
        Stack<Sort.Order> reverse = new Stack<>();
        sort.iterator().forEachRemaining(reverse::push);

        return reverse.stream()
                .map(order ->
                        String.format(SORT_FORMAT, order.getProperty(), order.getDirection())
                )
                .collect(Collectors.joining());
    }

    private String size(int size) {
        return SIZE_PARAM +size;
    }

    private String page(int page) {
        return PAGE_PARAM + page;
    }


}
