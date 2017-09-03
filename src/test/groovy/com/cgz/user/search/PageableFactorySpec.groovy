package com.cgz.user.search

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import spock.lang.Specification

import static PageableFactory.SORT_SEPARATOR

class PageableFactorySpec extends Specification {

    PageableFactory pageableCreator = new PageableFactory()

    int ANY_PAGE_NUMBER = 1

    int ANY_PAGE_SIZE = 4

    String idSortProperty  = 'id'

    String nameSortProperty  = 'name'


    def "accepts null sorting params"() {
        given:
            int pageNumber = 2
            int pageSize = 3
        when:
            Pageable pageable = pageableCreator.from(pageNumber, pageSize, null)
        then:
            pageable.pageNumber == pageNumber
            pageable.pageSize == pageSize
    }

    def "creates pageable.sort with single property and default sort"() {
        given:
            List<String> sortingParams = [idSortProperty]
        when:
            Pageable pageable = pageableCreator.from(ANY_PAGE_NUMBER, ANY_PAGE_SIZE, sortingParams)
        then:
            sortDirectionForPropertyAt(0, pageable) == Sort.Direction.ASC
            sortPropertyAt(0, pageable) == idSortProperty
    }

    def "creates pageable.sort with multiple properties and sort directions"() {
        given:
            String idSortAndDirection  = "$idSortProperty${SORT_SEPARATOR}DESC"
            String nameSortAndDirection  = "$nameSortProperty${SORT_SEPARATOR}asc"
            List<String> sortingParams = [nameSortAndDirection,idSortAndDirection]
        when:
            Pageable pageable = pageableCreator.from(ANY_PAGE_NUMBER, ANY_PAGE_SIZE, sortingParams)
        then:
            sortDirectionForPropertyAt( 0, pageable) == Sort.Direction.ASC
            sortPropertyAt(0, pageable) == nameSortProperty
            sortDirectionForPropertyAt(1, pageable) == Sort.Direction.DESC
            sortPropertyAt(1, pageable) == idSortProperty
    }

    def "ignores not supported properties"() {
        given:
          String notSupportedProperty  = 'someProperty'
          List<String> sortingParams = [notSupportedProperty]

        when:
            Pageable pageable = pageableCreator.from(ANY_PAGE_NUMBER, ANY_PAGE_SIZE, sortingParams)
        then:
            pageable.sort == null
    }


    String sortPropertyAt(int index, Pageable pageable) {
        pageable.sort.iterator().getAt(index).getProperty()
    }

    Sort.Direction sortDirectionForPropertyAt(int index, Pageable pageable) {
        pageable.sort.iterator().getAt(index).direction
    }

}
