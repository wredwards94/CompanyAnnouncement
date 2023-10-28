package com.cooksys.groupfinal.services;

import com.cooksys.groupfinal.dtos.AnnouncementDto;
import com.cooksys.groupfinal.dtos.AnnouncementRequestDto;

public interface AnnouncementService {

	AnnouncementDto addAnnouncement(AnnouncementRequestDto announcementRequestDto);

	AnnouncementDto patchAnnouncement(Long id, AnnouncementRequestDto announcementRequestDto);

}
