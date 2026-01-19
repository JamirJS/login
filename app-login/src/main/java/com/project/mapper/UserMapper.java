package com.project.mapper;

import com.project.dto.UserCreationDTO;
import com.project.dto.UserDTO;
import com.project.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity UserDTOToEntity(UserDTO userDTO);
    UserEntity UserCreationDTOToEntity(UserCreationDTO userCreationDTO);

    UserDTO UserEntityToUserDTO(UserEntity userEntity);
}
