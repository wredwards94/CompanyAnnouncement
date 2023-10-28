package com.cooksys.groupfinal.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
public class ProjectDto {
	
	private Long id;
    
    private String name;
    
    private String description;

    @Getter
    @Setter
    private boolean active;
    
    private TeamDto team;


}