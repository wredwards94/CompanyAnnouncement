package com.cooksys.groupfinal.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BasicUserDto {
	
	private Long id;

    private ProfileDto profile;
    
    private boolean admin;
    
    private boolean active;
    
    private String status;

}