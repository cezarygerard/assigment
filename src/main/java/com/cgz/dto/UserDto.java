package com.cgz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@AllArgsConstructor
@Builder
@ToString
public class UserDto {

    public long id;

    public String name;

}
