package com.cooksys.groupfinal.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import com.cooksys.groupfinal.dtos.AnnouncementRequestDto;
import com.cooksys.groupfinal.entities.Announcement;
import com.cooksys.groupfinal.entities.Credentials;
import com.cooksys.groupfinal.entities.Company;
import com.cooksys.groupfinal.entities.User;
import com.cooksys.groupfinal.exceptions.BadRequestException;
import com.cooksys.groupfinal.mappers.AnnouncementMapper;
import com.cooksys.groupfinal.mappers.CredentialsMapper;
import com.cooksys.groupfinal.repositories.AnnouncementRepository;
import com.cooksys.groupfinal.repositories.CompanyRepository;
import com.cooksys.groupfinal.repositories.UserRepository;
import com.cooksys.groupfinal.services.AnnouncementService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
	
	private final AnnouncementMapper announcementMapper;
	private final AnnouncementRepository announcementRepository;
	private final CredentialsMapper credentialsMapper;
	private final CompanyRepository companyRepository;
	private final UserRepository userRepository;
	
	//POST announcement
	@Override
	public AnnouncementDto addAnnouncement(AnnouncementRequestDto announcementRequestDto) {
		//check if title is provided in dto
        if(announcementRequestDto.getTitle() == null) {
        	throw new BadRequestException("provided title is null");
        }
		
        //check if message is provided in dto
        if(announcementRequestDto.getMessage() == null) {
        	throw new BadRequestException("provided message is null");
        }
        
		//check if credentials are provided in dto
        if(announcementRequestDto.getAuthor().getCredentials() == null) {
        	throw new BadRequestException("provided credentials are null");
        }
        
		//verify author credentials
		Credentials credentials = credentialsMapper.dtoToEntity(announcementRequestDto.getAuthor().getCredentials());
        
		//check if user exists in DB with provided username
		Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndActiveTrue(credentials.getUsername());
		if(optionalUser.isEmpty()) {
			throw new BadRequestException("user with provided username not found");
		}
		User user = optionalUser.get();
		
		//check if password matches
		if(!(credentials.getPassword().equals(user.getCredentials().getPassword()))) {
			throw new BadRequestException("incorrect password provided for user");
		}
		
		//convert dto to entity and set values before saving new announcement
		Announcement newAnnouncement = announcementMapper.requestDtoToEntity(announcementRequestDto);
		newAnnouncement.setAuthor(user);
		
		if (user.isAdmin()) {
			//find out which company id they are currently logged in with
			//optional parameter in announcement request DTO -> must be present for admin user
			
			//check that current company name was provided
			if(announcementRequestDto.getCompanyName() == null) {
				throw new BadRequestException("current company of admin user not provided in request body");
			}
			
			//get company from repository based on provided company name
			Optional<Company> optionalCompany = companyRepository.findByName(announcementRequestDto.getCompanyName());
			
			//check that current company name exists in DB
			if(optionalCompany.isEmpty()) {
				throw new BadRequestException("provided current company of admin user does not exists in database");
			}
			
			//check that user belongs to provided company
			Company curCompany = optionalCompany.get();
			if(!(user.getCompanies().contains(curCompany))) {
				throw new BadRequestException("user does not belong to provided company");
			}
			
			newAnnouncement.setCompany(curCompany);
		} else {
			//user only belongs to one company -> get from list
			if(user.getCompanies().isEmpty()) {
				throw new BadRequestException("user is not assigned to any companies. cannot post announcement");
			}
			newAnnouncement.setCompany(user.getCompanies().iterator().next());
		}
		
		//save announcement to repository and return dto
		return announcementMapper.entityToDto(announcementRepository.saveAndFlush(newAnnouncement));
	}
	
	//PATCH announcement
	public AnnouncementDto patchAnnouncement(Long id, AnnouncementRequestDto announcementRequestDto) {
		//check if title is provided in dto
        if(announcementRequestDto.getTitle() == null) {
        	throw new BadRequestException("provided title is null");
        }
		
        //check if message is provided in dto
        if(announcementRequestDto.getMessage() == null) {
        	throw new BadRequestException("provided message is null");
        }
        
		//check if credentials are provided in dto
        if(announcementRequestDto.getAuthor().getCredentials() == null) {
        	throw new BadRequestException("provided credentials are null");
        }
        
		//verify author credentials
		Credentials credentials = credentialsMapper.dtoToEntity(announcementRequestDto.getAuthor().getCredentials());
        
		//check if user exists in DB with provided username
		Optional<User> optionalUser = userRepository.findByCredentialsUsername(credentials.getUsername());
		if(optionalUser.isEmpty()) {
			throw new BadRequestException("user with provided username not found");
		}
		User user = optionalUser.get();
		
		//check if password matches
		if(!(credentials.getPassword().equals(user.getCredentials().getPassword()))) {
			throw new BadRequestException("incorrect password provided for user");
		}
		
		//check if announcement with provided ID exists
		Optional<Announcement> optionalAnnouncement = announcementRepository.findById(id);
		if(optionalAnnouncement.isEmpty()) {
			throw new BadRequestException("announcement with provided ID not found in database");
		}
		Announcement announcement = optionalAnnouncement.get();
		
		//only original author or an admin can patch an announcement
		//check if author of patch request is an admin
		Announcement newAnnouncement = announcementMapper.requestDtoToEntity(announcementRequestDto);
		if(user.isAdmin()) {
			//apply changes to announcement title and/or message
			announcement.setTitle(newAnnouncement.getTitle());
			announcement.setMessage(newAnnouncement.getMessage());
			
			//save announcement to repository and return dto
			return announcementMapper.entityToDto(announcementRepository.saveAndFlush(announcement));
			
		//check that author of announcement matches author of patch announcement
		} else if(!(announcement.getAuthor().getCredentials().equals(newAnnouncement.getAuthor().getCredentials()))) {
			throw new BadRequestException("provided credentials do not match announcement author's credentials. "
										+ "only the author of an announcement can make changes to the announcement");
		}
		
		//apply changes to announcement title and/or message
		announcement.setTitle(newAnnouncement.getTitle());
		announcement.setMessage(newAnnouncement.getMessage());
		
		//save announcement to repository and return dto
		return announcementMapper.entityToDto(announcementRepository.saveAndFlush(announcement));
	}

}