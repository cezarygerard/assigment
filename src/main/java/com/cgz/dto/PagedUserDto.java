package com.cgz.dto;

import lombok.Builder;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
@Builder
public class PagedUserDto {

    public List<UserDto> users;

    public List<Map<String, String>> links;
}
