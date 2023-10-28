package com.cooksys.groupfinal.controllers;

import com.cooksys.groupfinal.dtos.ProjectDto;
import com.cooksys.groupfinal.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class ProjectController {
	
	private final ProjectService projectService;

	// duplicate endpoint; same function as an endpoint in CompanyController
	@GetMapping("/{companyId}/teams/{teamId}/projects/team")
	public List<ProjectDto> getAllProjectsByTeam(
			@PathVariable Long companyId,
			@PathVariable Long teamId
	){
		return projectService.getAllProjectsByTeam(companyId,teamId);
	}

	@PostMapping("/{companyId}/teams/{teamId}/projects")
	@ResponseStatus(HttpStatus.CREATED)
	public ProjectDto addProjectToTheTeam(@PathVariable Long companyId,
										  @PathVariable Long teamId,
										  @RequestBody ProjectDto projectDto ){
		return projectService.addProjectToTheTeam(companyId,teamId,projectDto);
	}

	@PatchMapping("/{companyId}/teams/{teamId}/projects/{projectId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ProjectDto editProject(@PathVariable Long companyId,
								  @PathVariable Long teamId,
								  @PathVariable Long projectId,
								  @RequestBody ProjectDto projectDto){

		return projectService.editProject(companyId,teamId,projectId,projectDto);
	}
}
