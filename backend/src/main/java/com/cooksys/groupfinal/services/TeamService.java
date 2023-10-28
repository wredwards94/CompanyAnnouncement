package com.cooksys.groupfinal.services;

import com.cooksys.groupfinal.dtos.BasicUserDto;
import com.cooksys.groupfinal.dtos.TeamDto;
import com.cooksys.groupfinal.dtos.TeamRequestDto;

import java.util.Set;

public interface TeamService {

    TeamDto createTeam(TeamRequestDto teamRequestDto);

    TeamDto editTeamByTeamId(Long teamId, TeamRequestDto teamRequestDto);

    Set<BasicUserDto> getAllTeamMembers(Long id);

    TeamDto getTeamInfo(Long id);
}
