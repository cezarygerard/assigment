package com.cgz.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "users", path = "users", exported = true)
class UserRepository implements PagingAndSortingRepository<UserDto, Long>{

    @Override
    public Iterable<UserDto> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    @RestResource(exported = false)
    public <S extends UserDto> S save(S entity) {
        return null;
    }

    @Override
    @RestResource(exported = false)
    public <S extends UserDto> Iterable<S> save(Iterable<S> entities) {
        return null;
    }

    @Override
    public UserDto findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public Iterable<UserDto> findAll() {
        return null;
    }

    @Override
    public Iterable<UserDto> findAll(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    @RestResource(exported = false)
    public void delete(Long aLong) {

    }

    @Override
    @RestResource(exported = false)
    public void delete(UserDto entity) {

    }

    @Override
    @RestResource(exported = false)
    public void delete(Iterable<? extends UserDto> entities) {

    }

    @Override
    @RestResource(exported = false)
    public void deleteAll() {

    }

}
