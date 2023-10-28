package com.cooksys.groupfinal.services.impl;

import com.cooksys.groupfinal.dtos.ProjectDto;
import com.cooksys.groupfinal.entities.Company;
import com.cooksys.groupfinal.entities.Project;
import com.cooksys.groupfinal.entities.Team;
import com.cooksys.groupfinal.exceptions.BadRequestException;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.mappers.ProjectMapper;
import com.cooksys.groupfinal.repositories.CompanyRepository;
import com.cooksys.groupfinal.repositories.ProjectRepository;
import com.cooksys.groupfinal.repositories.TeamRepository;
import com.cooksys.groupfinal.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private  final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final TeamRepository teamRepository;
    private final ProjectMapper projectMapper;


    private Company findCompanyById(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId);

        if (company.isEmpty()) {
            throw new NotFoundException("Could not find the corresponding company");
        }

        return company.get();
    }

    private Team findTeamById(Long teamId) {
        Optional<Team> team = teamRepository.findById(teamId);

        if (team.isEmpty()) {
            throw new NotFoundException("Could not find the corresponding team");
        }

        return team.get();
    }
    @Override
    public ProjectDto addProjectToTheTeam(Long companyId, Long teamId, ProjectDto projectDto) {

        Company company = findCompanyById(companyId);
        Team team = findTeamById(teamId);

        if(!company.getTeams().contains(team)){
            throw new NotFoundException("Could not find the corresponding team on this company ");

        }
        // Check if the request body has the corresponding data
        if (projectDto == null || projectDto.getName() == null || projectDto.getDescription() == null ) {
            throw new BadRequestException("A name and a description are required for the project.");
        }

        // Transform the ProjectDto into an entity
        Project projectToSave = projectMapper.dtoToEntity(projectDto);

//        Set active to true
        projectToSave.setActive(true);
        // Set the Team field in the Project to the corresponding Team
        projectToSave.setTeam(team);

        // Add the project to the team's list of projects and save the team
//        team.get().getProjects().add(projectToSave);
//        teamRepository.saveAndFlush(team.get());

        // Save the project and return the DTO
        return projectMapper.entityToDto(projectRepository.saveAndFlush(projectToSave));

    }

    @Override
    public ProjectDto editProject(Long companyId, Long teamId, Long projectId, ProjectDto projectDto) {
        Company company = findCompanyById(companyId);
        Team team = findTeamById(teamId);


        if(!company.getTeams().contains(team)){
            throw new NotFoundException("Could not find the corresponding team on this company ");

        }

        Optional<Project> projectToEdit = projectRepository.findById(projectId);
        if(projectToEdit.isEmpty()){
            throw new NotFoundException("Could not find the corresponding project");

        }
//        Get the existing project
        Project existingProject = projectToEdit.get();
//        Check if the project belongs to the specified team
        if(!existingProject.getTeam().getId().equals(team.getId())){
            throw new NotFoundException("The specified project does not belong to the team");

        }

        // Update the project's fields with values from the DTO
        if (projectDto.getName() != null) {
            existingProject.setName(projectDto.getName());
        }
        if (projectDto.getDescription() != null) {
            existingProject.setDescription(projectDto.getDescription());
        }
//        Update the active field
        existingProject.setActive(projectDto.isActive());
//        Update the team
//        existingProject.setTeam(team);
// Save and return the updated project

        return projectMapper.entityToDto(projectRepository.saveAndFlush(existingProject));
    }

    @Override
    public List<ProjectDto> getAllProjectsByTeam(Long companyId, Long teamId) {

        Company company = findCompanyById(companyId);
        Team team = findTeamById(teamId);
        if(!company.getTeams().contains(team)){
            throw new NotFoundException("Could not find the corresponding team on this company ");
        }

        // Retrieve all projects associated with the specified team
        List<Project> projects = projectRepository.findByTeam(team);

        // Create a list to store the resulting ProjectDto objects
        List<ProjectDto> projectDtos = new ArrayList<>();

        // Iterate through the list of projects and map each one to ProjectDto
        for (Project project : projects) {
            projectDtos.add(projectMapper.entityToDto(project));
        }

        // return in reverse creation order (most recently-created first)
        Collections.reverse(projectDtos);
        return projectDtos;
    }
}
