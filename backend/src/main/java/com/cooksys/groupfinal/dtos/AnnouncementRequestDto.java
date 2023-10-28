package com.cooksys.groupfinal.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AnnouncementRequestDto {
	
	private String title;
	
	private String message;
	
	private UserRequestDto author;
	
	//only required if user is admin -> must specify current company they are viewing
	private String companyName;
}
