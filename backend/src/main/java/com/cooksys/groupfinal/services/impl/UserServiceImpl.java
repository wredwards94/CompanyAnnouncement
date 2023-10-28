package com.cooksys.groupfinal.services.impl;

import com.cooksys.groupfinal.dtos.CredentialsDto;
import com.cooksys.groupfinal.dtos.FullUserDto;
import com.cooksys.groupfinal.dtos.ProfileDto;
import com.cooksys.groupfinal.dtos.UserRequestDto;
import com.cooksys.groupfinal.entities.Credentials;
import com.cooksys.groupfinal.entities.User;
import com.cooksys.groupfinal.exceptions.BadRequestException;
import com.cooksys.groupfinal.exceptions.NotAuthorizedException;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.mappers.BasicUserMapper;
import com.cooksys.groupfinal.mappers.CredentialsMapper;
import com.cooksys.groupfinal.mappers.FullUserMapper;
import com.cooksys.groupfinal.mappers.ProfileMapper;
import com.cooksys.groupfinal.repositories.UserRepository;
import com.cooksys.groupfinal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final FullUserMapper fullUserMapper;
	private final BasicUserMapper basicUserMapper;
	private final CredentialsMapper credentialsMapper;
	private final ProfileMapper profileMapper;
	
	private User findUser(String username) {
        Optional<User> user = userRepository.findByCredentialsUsernameAndActiveTrue(username);
        if (user.isEmpty()) {
            throw new NotFoundException("The username provided does not belong to an active user.");
        }
        return user.get();
    }
	
	private void credentialsErrorChecking(CredentialsDto credentialsDto) {
		if (credentialsDto == null || credentialsDto.getUsername() == null || credentialsDto.getPassword() == null) {
            throw new BadRequestException("A username and password are required.");
        }
	}
	
	private boolean validateUsername(String username) {
        Optional<User> user = userRepository.findByCredentialsUsername(username);
        return user.isPresent();
	}
	
	private boolean validateCredentials(CredentialsDto credentialsDto) {
		credentialsErrorChecking(credentialsDto);
        Credentials credentialsToValidate = credentialsMapper.dtoToEntity(credentialsDto);
        User userToValidate = findUser(credentialsDto.getUsername());
        if (!userToValidate.getCredentials().equals(credentialsToValidate)) {
            throw new NotAuthorizedException("The provided credentials are invalid.");
        }
        return true;
	}
	
	@Override
	public FullUserDto login(CredentialsDto credentialsDto) {
		if (!validateCredentials(credentialsDto)) {
			return null;
		}
        User userToValidate = findUser(credentialsDto.getUsername());
        if (userToValidate.getStatus().equals("PENDING")) {
        	userToValidate.setStatus("JOINED");
        	userRepository.saveAndFlush(userToValidate);
        }
        return fullUserMapper.entityToFullUserDto(userToValidate);
	}

	@Override
	public FullUserDto getUser(String username) {
		return fullUserMapper.entityToFullUserDto(findUser(username));
	}

	@Override
	public boolean validateUserCredentials(CredentialsDto credentialsDto) {
		return validateCredentials(credentialsDto);
	}

	@Override
	public FullUserDto createUser(UserRequestDto userRequestDto) {
		credentialsErrorChecking(userRequestDto.getCredentials());
		if (validateUsername(userRequestDto.getCredentials().getUsername())) {
			throw new BadRequestException("A user with this username already exists.");
		}
		User newUser = basicUserMapper.requestDtoToEntity(userRequestDto);
		newUser.setActive(true);
		return fullUserMapper.entityToFullUserDto(userRepository.saveAndFlush(newUser));
	}

	@Override
	public FullUserDto editUserProfile(String username, ProfileDto profileDto) {
		User user = findUser(username);
		user.setProfile(profileMapper.dtoToEntity(profileDto));
		return fullUserMapper.entityToFullUserDto(userRepository.saveAndFlush(user));
	}

	@Override
	public FullUserDto editUserCredentials(String username, CredentialsDto credentialsDto) {
		credentialsErrorChecking(credentialsDto);
		User user = findUser(username);
		if (username != credentialsDto.getUsername() && validateUsername(credentialsDto.getUsername())) {
			throw new BadRequestException("A user with the newly entered username already exists.");
		}
		user.setCredentials(credentialsMapper.dtoToEntity(credentialsDto));
		return fullUserMapper.entityToFullUserDto(userRepository.saveAndFlush(user));
	}

	@Override
	public FullUserDto editUserAdmin(String username, boolean adminStatus) {
		User user = findUser(username);
		user.setAdmin(adminStatus);
		return fullUserMapper.entityToFullUserDto(userRepository.saveAndFlush(user));
	}

	@Override
	public FullUserDto editUserActive(String username, boolean activeStatus) {
		Optional<User> user = userRepository.findByCredentialsUsername(username);
        if (user.isEmpty()) {
            throw new NotFoundException("The username provided does not belong to an active user.");
        }
		user.get().setActive(activeStatus);
		return fullUserMapper.entityToFullUserDto(userRepository.saveAndFlush(user.get()));
	}

	@Override
	public CredentialsDto getUserCredentials(String username) {
		// Find the user by username
		User user = findUser(username);

		// Extract the credentials from the user entity
		Credentials credentials = user.getCredentials();

		// Create a new CredentialsDto and populate its properties
		CredentialsDto credentialsDto = new CredentialsDto();
		credentialsDto.setUsername(credentials.getUsername());
		credentialsDto.setPassword(credentials.getPassword());

		// Return the populated CredentialsDto
		return credentialsDto;
	}
}