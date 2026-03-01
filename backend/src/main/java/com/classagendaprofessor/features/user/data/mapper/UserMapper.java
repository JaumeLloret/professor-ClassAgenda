package com.classagendaprofessor.features.user.data.mapper;

import com.classagendaprofessor.features.user.data.local.entity.UserEntity;
import com.classagendaprofessor.features.user.domain.model.User;

public final class UserMapper {
    private  UserMapper() {}
    public static UserEntity toEntity(User user) {
        if (user == null)
            return null;
        return new UserEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

    public static User toDomain(UserEntity userEntity) {
        if (userEntity == null)
            return null;
        return new User(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getCreatedAt()
        );
    }
}
