package com.cgz.user.search;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.Optional;
import java.util.Stack;

class UserComparator implements Comparator<User> {

    private Stack<Sort.Order> reversedSortingProperties;

    static final String USER_ID_FIELD = "id";

    static final String USER_NAME_FIELD = "name";

    public UserComparator(Sort sortingProperties) {

        reversedSortingProperties = new Stack<Sort.Order>();
        Optional.ofNullable(sortingProperties)
                .map(Sort::iterator)
                .ifPresent(iterator ->
                        iterator.forEachRemaining(reversedSortingProperties::push)
                );
    }

    @Override
    public int compare(User user1, User user2) {
        CompareToBuilder compareToBuilder = new CompareToBuilder();
        reversedSortingProperties.stream().sequential().forEach(
                order -> appendCompareToBuilder(compareToBuilder, order, user1, user2)
        );

        return compareToBuilder.build();
    }

    private void appendCompareToBuilder(CompareToBuilder compareToBuilder, Sort.Order order, User user1, User user2) {
        if(order.getDirection() == Sort.Direction.DESC){
            User tmp = user1;
            user1 = user2;
            user2 = tmp;
        }

        if(order.getProperty().equals(USER_ID_FIELD)){
            compareToBuilder.append(user1.getId(), user2.getId());
        }
        if(order.getProperty().equals(USER_NAME_FIELD)){
            compareToBuilder.append(user1.getName(), user2.getName());
        }
    }


}

