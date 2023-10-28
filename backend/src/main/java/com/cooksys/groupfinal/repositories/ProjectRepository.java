package com.cooksys.groupfinal.repositories;

import com.cooksys.groupfinal.entities.Project;
import com.cooksys.groupfinal.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {


    List<Project> findByTeam(Team team);
}