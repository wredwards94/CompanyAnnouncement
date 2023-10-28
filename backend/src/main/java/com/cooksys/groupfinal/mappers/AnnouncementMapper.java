package com.cooksys.groupfinal.mappers;

import java.util.Set;

import org.mapstruct.Mapper;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import com.cooksys.groupfinal.dtos.AnnouncementRequestDto;
import com.cooksys.groupfinal.entities.Announcement;

@Mapper(componentModel = "spring", uses = { BasicUserMapper.class })
public interface AnnouncementMapper {
	
	AnnouncementDto entityToDto(Announcement announcement);

	Set<AnnouncementDto> entitiesToDtos(Set<Announcement> announcement);
	
	Announcement requestDtoToEntity(AnnouncementRequestDto announcementRequestDto);
    
}
