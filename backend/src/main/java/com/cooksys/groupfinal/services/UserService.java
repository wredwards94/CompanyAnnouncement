package com.cooksys.groupfinal.services;

import com.cooksys.groupfinal.dtos.CredentialsDto;
import com.cooksys.groupfinal.dtos.FullUserDto;
import com.cooksys.groupfinal.dtos.ProfileDto;
import com.cooksys.groupfinal.dtos.UserRequestDto;

public interface UserService {

	FullUserDto login(CredentialsDto credentialsDto);

	FullUserDto getUser(String username);

	boolean validateUserCredentials(CredentialsDto credentialsDto);

	FullUserDto createUser(UserRequestDto userRequestDto);

	FullUserDto editUserProfile(String username, ProfileDto profileDto);

	FullUserDto editUserCredentials(String username, CredentialsDto credentialsDto);

	FullUserDto editUserAdmin(String username, boolean adminStatus);

	FullUserDto editUserActive(String username, boolean activeStatus);

    CredentialsDto getUserCredentials(String username);
}