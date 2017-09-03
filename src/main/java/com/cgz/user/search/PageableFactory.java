package com.cgz.user.search;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PageableFactory {

    private static final String  SEPARATOR = ",";

    private static final String  ASC_DIRECTION_PARAMETER = "ASC";

    private static final String  DESC_DIRECTION_PARAMETER = "DESC";

    private static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.ASC;

    //TODO make it more generic
    private static final List<String> SORTABLE_FIELDS = Arrays.asList("id","name");

    public Pageable from(int pageNumber, int pageSize, @Nullable List<String> sortingParams) {

        return createSort(sortingParams)
                .map(sort -> new PageRequest(pageNumber, pageSize, sort))
                .orElseGet(()-> new PageRequest(pageNumber, pageSize));
    }

    private Optional<Sort> createSort(List<String> sortingParams) {

        List<Sort.Order> sortOrders = Optional.ofNullable(sortingParams)
                .map(Collection::stream)
                .orElse(Stream.empty())
                .filter(this::isParamValid)
                .map(this::createOrder)
                .collect(Collectors.toList());

        if (sortOrders.size()>0) {
            return Optional.of(new Sort(sortOrders));
        }
        return Optional.empty();
    }

    private boolean isParamValid(String singleSortParameter) {
        return SORTABLE_FIELDS.contains(
                singleSortParameter.split(SEPARATOR)[0]
        );
    }

    private Sort.Order createOrder(String singleSortParameter) {
        String[] paramAndDirection = singleSortParameter.split(SEPARATOR);
        Sort.Direction direction = parseDirection(paramAndDirection);
        return new Sort.Order(direction, paramAndDirection[0]);
    }

    private Sort.Direction parseDirection(String[] paramAndDirection) {
        if(paramAndDirection.length == 2 && paramAndDirection[1].equalsIgnoreCase(DESC_DIRECTION_PARAMETER)){
            return Sort.Direction.DESC;
        }
        return DEFAULT_DIRECTION;
    }


}
