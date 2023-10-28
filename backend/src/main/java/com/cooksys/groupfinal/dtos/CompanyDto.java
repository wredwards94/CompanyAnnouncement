package com.cooksys.groupfinal.dtos;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CompanyDto {
	
	private Long id;
    
    private String name;
    
    private String description;
    
    private Set<TeamDto> teams;
    
    private Set<BasicUserDto> employees;

}