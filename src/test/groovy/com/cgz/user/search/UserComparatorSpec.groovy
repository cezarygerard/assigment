package com.cgz.user.search

import org.springframework.data.domain.Sort
import spock.lang.Shared
import spock.lang.Specification

import java.lang.reflect.Array

import static com.cgz.user.search.UserComparator.USER_ID_FIELD
import static com.cgz.user.search.UserComparator.USER_NAME_FIELD

class UserComparatorSpec extends Specification {

    @Shared
    Closure<Boolean> FIRST_BEFORE_SECOND = {it  < 0}

    @Shared
    Closure<Boolean> SECOND_BEFORE_FIRST = {it  > 0}

    @Shared
    Closure<Boolean> EQUAL = {it  == 0}

    def "Compares using single field"() {
        given:
            User user1 = new User(userId_1, userName_1);
            User user2 = new User(userId_2, userName_2);
            UserComparator userComparator = new UserComparator(new Sort(sortProperties));
        when:
            int result = userComparator.compare(user1, user2)
        then:
            expected(result)
        where:
            userId_1 | userName_1 | userId_2 | userName_2 | sortProperties   | expected
            3        | 'a'        | 2        | 'b'        | USER_ID_FIELD    | SECOND_BEFORE_FIRST
            1        | 'a'        | 9        | 'b'        | USER_ID_FIELD    | FIRST_BEFORE_SECOND
            1        | 'a'        | 9        | 'b'        | USER_NAME_FIELD  | FIRST_BEFORE_SECOND
            1        | 'z'        | 9        | 'b'        | USER_NAME_FIELD  | SECOND_BEFORE_FIRST
            1        | 'aa'       | 9        | 'aa'       | USER_NAME_FIELD  | EQUAL
    }

    def "Compares using multiple fields"() {
        given:
           User user1 = new User(userId_1, userName_1);
           User user2 = new User(userId_2, userName_2);
           UserComparator userComparator = new UserComparator(new Sort(sortProperties));
        when:
           int result = userComparator.compare(user1, user2)
        then:
          expected(result)
        where:
            userId_1 | userName_1 | userId_2 | userName_2 | sortProperties                               | expected
            3        | 'a'        | 3        | 'b'        | [USER_ID_FIELD, USER_NAME_FIELD] as String[] | FIRST_BEFORE_SECOND
            1        | 'z'        | 1        | 'b'        | [USER_ID_FIELD, USER_NAME_FIELD] as String[] | SECOND_BEFORE_FIRST
            1        | 'aa'       | 1        | 'aa'       | [USER_ID_FIELD, USER_NAME_FIELD] as String[] | EQUAL
    }

    def "Compares using reverse order fields"() {
        given:
            User user1 = new User(userId_1, userName_1);
            User user2 = new User(userId_2, userName_2);
            UserComparator userComparator = new UserComparator(new Sort(Sort.Direction.DESC, sortProperties));
        when:
          int result = userComparator.compare(user1, user2)
        then:
           expected(result)
        where:
            userId_1 | userName_1 | userId_2 | userName_2 | sortProperties                               | expected
            3        | 'a'        | 3        | 'b'        | [USER_ID_FIELD, USER_NAME_FIELD] as String[] | SECOND_BEFORE_FIRST
            1        | 'z'        | 1        | 'b'        | [USER_ID_FIELD, USER_NAME_FIELD] as String[] | FIRST_BEFORE_SECOND
            1        | 'aa'       | 1        | 'aa'       | [USER_ID_FIELD, USER_NAME_FIELD] as String[] | EQUAL
    }

}
