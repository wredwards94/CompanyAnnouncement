package com.cooksys.groupfinal.controllers;

import com.cooksys.groupfinal.dtos.CredentialsDto;
import com.cooksys.groupfinal.dtos.FullUserDto;
import com.cooksys.groupfinal.dtos.ProfileDto;
import com.cooksys.groupfinal.dtos.UserRequestDto;
import com.cooksys.groupfinal.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class UserController {
	
	private final UserService userService;


	@GetMapping("{username}/profile/credentials")
	@CrossOrigin(origins="*")
	public CredentialsDto getUserCredentials(@PathVariable String username){
		return userService.getUserCredentials(username);
	}
	@PostMapping("/login")
	@CrossOrigin(origins="*")
    public FullUserDto login(@RequestBody CredentialsDto credentialsDto) {
        return userService.login(credentialsDto);
    }
	
	@GetMapping("/{username}")
	public FullUserDto getUser(@PathVariable String username) {
		return userService.getUser(username);
	}
	
	@PostMapping("/validate")
	public boolean validateUserCredentials(@RequestBody CredentialsDto credentialsDto) {
		return userService.validateUserCredentials(credentialsDto);
	}
	
	@PostMapping("/new")
	public FullUserDto createUser(@RequestBody UserRequestDto userRequestDto) {
		return userService.createUser(userRequestDto);
	}
	
	@PatchMapping("/{username}/profile")
	public FullUserDto editUserProfile(@PathVariable String username, @RequestBody ProfileDto profileDto) {
		return userService.editUserProfile(username, profileDto);
	}
	
	@PatchMapping("/{username}/credentials")
	public FullUserDto editUserCredentials(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
		return userService.editUserCredentials(username, credentialsDto);
	}
	
	@PatchMapping("/{username}/admin/{adminStatus}")
	public FullUserDto editUserAdmin(@PathVariable String username, @PathVariable boolean adminStatus) {
		return userService.editUserAdmin(username, adminStatus);
	}
	
	@PatchMapping("/{username}/active/{activeStatus}")
	public FullUserDto editUserActive(@PathVariable String username, @PathVariable boolean activeStatus) {
		return userService.editUserActive(username, activeStatus);
	}
}
