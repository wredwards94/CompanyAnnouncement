package com.cooksys.groupfinal.services.impl;

import com.cooksys.groupfinal.dtos.BasicUserDto;
import com.cooksys.groupfinal.dtos.TeamDto;
import com.cooksys.groupfinal.entities.Team;
import org.springframework.stereotype.Service;
import com.cooksys.groupfinal.dtos.TeamRequestDto;
import com.cooksys.groupfinal.entities.Company;
import com.cooksys.groupfinal.exceptions.BadRequestException;
import com.cooksys.groupfinal.exceptions.NotAuthorizedException;
import com.cooksys.groupfinal.exceptions.NotFoundException;
import com.cooksys.groupfinal.mappers.TeamMapper;
import com.cooksys.groupfinal.mappers.BasicUserMapper;
import com.cooksys.groupfinal.repositories.CompanyRepository;
import com.cooksys.groupfinal.repositories.TeamRepository;
import com.cooksys.groupfinal.repositories.UserRepository;
import com.cooksys.groupfinal.entities.User;
import com.cooksys.groupfinal.services.TeamService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamMapper teamMapper;
    private final TeamRepository teamRepository;
    private final CompanyRepository companyRepository;
    private final BasicUserMapper basicUserMapper;
    private final UserRepository userRepository;

    @Override
    public Set<BasicUserDto> getAllTeamMembers(Long id) {
        Optional<Team> foundTeam = teamRepository.findById(id);

        if (foundTeam.isEmpty()) {
            throw new NotFoundException("this team does not exists");
        }

        return basicUserMapper.entitiesToBasicUserDtos(foundTeam.get().getTeammates());
    }


    @Override
    public TeamDto createTeam(TeamRequestDto teamRequestDto) {
        // Checks the incoming request body for all required fields.
        if (teamRequestDto.getDescription() == null || teamRequestDto.getName() == null || teamRequestDto.getName().isEmpty()
                || teamRequestDto.getTeammateIds() == null) {
            throw new BadRequestException("Teams must have a name, description and at least 1 member");
        }
//        if (teamRequestDto.getUserCredentials() == null) {
//            throw new BadRequestException("User credentials are required.");
//        }
        if (teamRequestDto.getCompanyId() == null) {
            throw new BadRequestException("Company Id is required.");
        }

        // Checks to see if the user that is creating the team is Authenticated and Active.
//        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndActiveTrue(teamRequestDto
//                .getUserCredentials().getUsername());
//        if (optionalUser.isEmpty()) {
//            throw new NotFoundException("User with provided username not found.");
//        }
//        User creatingUser = optionalUser.get();

        Optional<Company> optionalCompany = companyRepository.findById(teamRequestDto.getCompanyId());
        if (optionalCompany.isEmpty()) {
            throw new NotFoundException("Company not found");
        }

        Company company = optionalCompany.get();
//        if (!creatingUser.getCompanies().contains(company)) {
//            throw new NotAuthorizedException("User not authorized to create a team in this company.");
//        }
//
//        if (!creatingUser.getCredentials().getPassword().equals(teamRequestDto.getUserCredentials().getPassword())) {
//            throw new NotAuthorizedException("Incorrect password.");
//        }
//
//        if (!(creatingUser.isAdmin())) {
//            throw new NotAuthorizedException("Admin privileges needed to create a team.");
//        }

        Team teamToCreate = teamMapper.requestDtoToEntity(teamRequestDto);

        Set<User> usersToAdd = new HashSet<>(userRepository.findAllById(teamRequestDto.getTeammateIds()));
        if (usersToAdd.size() != teamRequestDto.getTeammateIds().size()) {
            throw new BadRequestException("One or more users not found by provided IDs");
        }

        for (User user : usersToAdd) {
            if (!user.isActive()) {
                throw new BadRequestException("Cannot add an inactive user to a team");
            }
        }
        teamToCreate.setTeammates(usersToAdd);
        teamToCreate.setCompany(company);
        teamRepository.saveAndFlush(teamToCreate);

        for (User user : teamToCreate.getTeammates()) {
            user.getCompanies().add(teamToCreate.getCompany());
            user.getTeams().add(teamToCreate);
            userRepository.saveAndFlush(user);
        }
        return teamMapper.entityToDto(teamToCreate);
    }

    @Override
    public TeamDto getTeamInfo(Long id) {
        Optional<Team> foundTeam = teamRepository.findById(id);

        if (foundTeam.isEmpty()) {
            throw new NotFoundException("This team does not exist");
        }

        return teamMapper.entityToDto(foundTeam.get());
    }

    public TeamDto editTeamByTeamId(Long teamId, TeamRequestDto teamRequestDto) {
        Optional<Team> optionalTeamToEdit = teamRepository.findById(teamId);
        if (optionalTeamToEdit.isEmpty()) {
            throw new NotFoundException("Team with this ID not found.");
        }
        Team teamToEdit = optionalTeamToEdit.get();
        // Edit the team based on provided request details.
        if (teamRequestDto.getName() != null) {
            teamToEdit.setName(teamRequestDto.getName());
        }
        if (teamRequestDto.getDescription() != null) {
            teamToEdit.setDescription(teamRequestDto.getDescription());
        }
        // Ensure teammates are part of the company
        Company usersCompany = teamToEdit.getCompany();
        if (teamRequestDto.getTeammateIds() != null) {
            for (Long userId : teamRequestDto.getTeammateIds()) {
                Optional<User> optUser = userRepository.findById(userId);
                if (optUser.isEmpty() || !optUser.get().getCompanies().contains(usersCompany)) {
                    throw new NotAuthorizedException("One or more users are not part of the team's company.");
                }
            }
            Set<User> teammates = new HashSet<>(userRepository.findAllById(teamRequestDto.getTeammateIds()));
            teamToEdit.setTeammates(teammates);
        }

//        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndActiveTrue(teamRequestDto.getUserCredentials().getUsername());
//        if (optionalUser.isEmpty()) {
//            throw new NotFoundException("User with provided username not found.");
//        }
//
//        User editingUser = optionalUser.get();
//        // Check the editingUser's credentials
//        if (!editingUser.getCredentials().getPassword().equals(teamRequestDto.getUserCredentials().getPassword())) {
//            throw new NotAuthorizedException("Incorrect password.");
//        }
//
//        // Check if the user is an admin
//        if (!(editingUser.isAdmin())) {
//            throw new NotAuthorizedException("Admin privileges are needed to edit a team.");
//        }
//
//        // Check that the admin editing the team is part of the company that the team belongs to
//        Company teamCompany = teamToEdit.getCompany();
//        if (!editingUser.getCompanies().contains(teamCompany)) {
//            throw new NotAuthorizedException("User not authorized to edit a team in this company.");
//        }

        Team updatedTeam = teamRepository.save(teamToEdit);
        return teamMapper.entityToDto(updatedTeam);
    }


}
