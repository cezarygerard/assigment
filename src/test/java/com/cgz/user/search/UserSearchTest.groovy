package com.cgz.user.search

import com.cgz.dto.UserDto
import com.cgz.dto.UserNotFoundException
import spock.lang.Specification

class UserSearchTest extends Specification {

    UserStoreClient mockUserStore = Mock(UserStoreClient)
    UserFacade userFacade = new UserSearchConfiguration().userFacade(mockUserStore)

    def "Finds single user by Id"() {
        given:
            long existingUserId = 1
            long existingUserName = 'anyName'
            mockUserStore.allUsers() >> Collections.singletonList(
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
            mockUserStore.allUsers() >> Collections.EMPTY_LIST
            long nonExistingUserId = -1

        when:
            userFacade.findUserById(nonExistingUserId)

        then:
            thrown(UserNotFoundException)
    }
}
