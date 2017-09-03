package com.cgz.user.search

import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class FetchAllUsersCommandTest extends Specification {

    def "returns empty collection on exception"() {
        given:
            RestTemplate restTemplate = Mock(RestTemplate)
            restTemplate.getForObject(*_) >> { throw new RuntimeException() }
        and:
            String SOME_ENDPOINT = "proto://as"
            FetchAllUsersCommand command =
                new FetchAllUsersCommand(
                        SOME_ENDPOINT,
                        restTemplate)
        when:
            def cardsResponse = command.execute()
        then:
            cardsResponse.isEmpty()
    }
}
