package com.classagendaprofessor.features.user.data.repository;

import com.classagendaprofessor.features.user.data.local.dao.UserDao;
import com.classagendaprofessor.features.user.data.local.entity.UserEntity;
import com.classagendaprofessor.features.user.data.mapper.UserMapper;
import com.classagendaprofessor.features.user.domain.model.User;
import com.classagendaprofessor.features.user.domain.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class JdbcUserRepository implements UserRepository {
    private final UserDao userDao;

    public JdbcUserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User save(User user) {
        UserEntity entityToSave = UserMapper.toEntity(user);

        UserEntity savedEntity;
        if (entityToSave.getId() == null) {
            savedEntity = userDao.insert(entityToSave);
            return UserMapper.toDomain(savedEntity);
        } else {
            userDao.update(entityToSave);
            return user;
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> entityOptional = userDao.findByEmail(email);
        return entityOptional.map(UserMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        List<UserEntity> entityList = userDao.findAll();

        return entityList.stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }
}
