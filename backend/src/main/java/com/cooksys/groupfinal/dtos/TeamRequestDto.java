package com.cooksys.groupfinal.dtos;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamRequestDto {

    private String name;

    private String description;

    private Set<Long> teammateIds;

    private Long companyId;

    //private CredentialsDto userCredentials;

}