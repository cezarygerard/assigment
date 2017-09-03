package com.cgz.controller

import com.cgz.Application
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasSize
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest( classes = [Application, UserSearchController, PagedUserFactory],
    webEnvironment = MOCK
)
class UserSearchControllerIntegrationSpec extends Specification {

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build()
    }

    def "searches for single user"() {
        given:
            int existingUserId = 12
        expect:
            mockMvc.perform(get("/user/$existingUserId"))
                .andExpect(status().isOk())
                .andExpect(content().json('{"id":12,"name":"Tutti frutti"}'))
    }

    def "search for non existing user single user results in 404"() {
        given:
            int noNexistingUserId = -1
        expect:
             mockMvc.perform(get("/user/$noNexistingUserId"))
                .andExpect(status().isNotFound())
    }

    def "search for all users with sorting and paging works"() {
        expect:
        mockMvc.perform(get("/users?page=1&size=3&sort=name.desc&sort=id.desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('users', hasSize(3)))
                .andExpect(jsonPath('links[0].href', equalTo("/users?page=2&size=3&sort=name.DESC&sort=id.DESC")))
                .andExpect(jsonPath('.links[1].href', equalTo("/users?page=0&size=3&sort=name.DESC&sort=id.DESC")))
    }



}
