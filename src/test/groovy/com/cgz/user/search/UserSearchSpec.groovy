package com.cgz.user.search

import com.cgz.dto.UserDto
import com.cgz.dto.UserNotFoundException
import org.springframework.data.domain.Page
import spock.lang.Specification

class UserSearchSpec extends Specification {

    UserStoreClient mockUserStore = Mock(UserStoreClient)

    UserFacade userFacade = new UserSearchConfiguration().userFacade(mockUserStore)

    String ANY_NAME = 'A'

    long ANY_ID = 1

    int ANY_PAGE_SIZE = 5

    long ANY_PAGE_NUMBER = 3

    List<String> NO_SORTING = null

    def "Finds single user by Id"() {
        given:
            long existingUserId = 1
            String existingUserName = 'anyName'
            mockUserStore.getAllUsers() >> Collections.singletonList(
                        new User(existingUserId, existingUserName)
                    )
        when:
            UserDto userDto = userFacade.findUserById(existingUserId)

        then:
            userDto.id == existingUserId
            userDto.name == existingUserName
    }

    def "throws UserNotFoundExeption when user does not exist"() {
        given:
            mockUserStore.getAllUsers() >> Collections.EMPTY_LIST
            long nonExistingUserId = -1

        when:
            userFacade.findUserById(nonExistingUserId)

        then:
            thrown(UserNotFoundException)
    }

    def "returns pageable result"() {
        given:
            int totalElements=100
            int pageNumber = 2
            int pageSize = 5
            List<User> users = new ArrayList<>(totalElements)
            (1..totalElements).each {
                users.add(new User(it, ANY_NAME))
            }
            mockUserStore.getAllUsers() >> users
        when:
            Page<UserDto> pagedResult = userFacade.findAll(pageNumber, pageSize, NO_SORTING)

        then:
            pagedResult.getNumberOfElements() == pageSize
            pagedResult.getTotalPages() == totalElements / pageSize
            pagedResult.getNumber() == pageNumber
    }


    def "returns sorted result"() {
        given:
            List<User> users = new ArrayList<>()
            ('a'..'z').each {
                users.add(new User(ANY_ID, it))
            }
            mockUserStore.getAllUsers() >> users
            List<String> sortParam = ['name,desc', 'id,asc']
        when:
            Page<UserDto> pagedResult = userFacade.findAll(ANY_PAGE_NUMBER, ANY_PAGE_SIZE, sortParam)
        then:
            firstResult(pagedResult).name > scondResult(pagedResult).name
    }

    def firstResult(Page<UserDto> userDtos) {
        userDtos.getContent().get(0)
    }

    UserDto scondResult(Page<UserDto> userDtos) {
        userDtos.getContent().get(1)
    }
}
