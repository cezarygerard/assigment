package com.cgz.controller

import com.cgz.dto.PagedUserDto
import com.cgz.dto.UserDto
import org.springframework.data.domain.Page
import spock.lang.Specification

import static com.cgz.controller.PagedUserFactory.*
import static com.cgz.controller.UserSearchController.ALL_USERS_PATH

//TODO this class is difficult to test, probably PagedUserFactory has to many responsibilities, must be splitted
class PagedUserFactorySpec extends Specification {

    PagedUserFactory pagedUserFactory = new PagedUserFactory();

    int TOTAL_NUMBER_OF_USERS = 10

    List<UserDto> testUsers;

    def setup(){
        testUsers = new ArrayList<>(TOTAL_NUMBER_OF_USERS);
        (1..TOTAL_NUMBER_OF_USERS).each {testUsers.add(Mock(UserDto))}
    }

    def "copies userDtos from pageable object"(){
        given:
            UserDto user = Mock(UserDto)
            Page<UserDto> page = Mock(Page){
                getContent() >> testUsers
            }
        when:
            PagedUserDto result = pagedUserFactory.from(page)
        then:
            result.users == testUsers
    }

    def "creates previous link from pageable with no sort  "() {
        given:
            int pageNumber = 1
            int pageSize = 3
            Page<UserDto> page = Mock(Page){
                getNumber() >> pageNumber
                getSize() >> pageSize
            }
        when:
            PagedUserDto result = pagedUserFactory.from(page)
        then:
            result.links[1][HREF] == "$ALL_USERS_PATH$PAGE_PARAM${pageNumber-1}$SIZE_PARAM$pageSize"
    }

    def "creates next link from pageable with no sort  "() {
        given:
            int pageNumber = 1
            int pageSize = 3
            Page<UserDto> page = Mock(Page){
                getNumber() >> pageNumber
                getSize() >> pageSize
            }
        when:
            PagedUserDto result = pagedUserFactory.from(page)
        then:
            result.links[0][HREF] == "$ALL_USERS_PATH$PAGE_PARAM${pageNumber+1}$SIZE_PARAM$pageSize"
    }


    def "creates no previous link from first pageable"() {
        given:
            int pageNumber = 0
            int pageSize = 3
            Page<UserDto> page = Mock(Page) {
                isFirst() >> true
                isLast() >> true
                getNumber() >> pageNumber
                getSize() >> pageSize
            }
        when:
          PagedUserDto result = pagedUserFactory.from(page)
        then:
           result.links.empty
    }

}
