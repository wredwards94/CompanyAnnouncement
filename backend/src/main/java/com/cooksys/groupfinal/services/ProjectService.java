package com.cooksys.groupfinal.services;

import com.cooksys.groupfinal.dtos.ProjectDto;

import java.util.List;

public interface ProjectService {

    ProjectDto addProjectToTheTeam(Long companyId, Long teamId, ProjectDto projectDto);

    ProjectDto editProject(Long companyId, Long teamId, Long projectId, ProjectDto projectDto);

    List<ProjectDto> getAllProjectsByTeam(Long companyId, Long teamId);
}
