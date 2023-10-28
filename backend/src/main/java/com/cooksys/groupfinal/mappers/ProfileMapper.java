package com.cooksys.groupfinal.mappers;

import com.cooksys.groupfinal.dtos.ProfileDto;
import com.cooksys.groupfinal.entities.Profile;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    Profile dtoToEntity(ProfileDto profileDto);

    ProfileDto entityToDto(Profile profile);

}
