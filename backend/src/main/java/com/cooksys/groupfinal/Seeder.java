package com.cooksys.groupfinal;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cooksys.groupfinal.entities.Announcement;
import com.cooksys.groupfinal.entities.Company;
import com.cooksys.groupfinal.entities.Credentials;
import com.cooksys.groupfinal.entities.Profile;
import com.cooksys.groupfinal.entities.Project;
import com.cooksys.groupfinal.entities.Team;
import com.cooksys.groupfinal.entities.User;
import com.cooksys.groupfinal.repositories.AnnouncementRepository;
import com.cooksys.groupfinal.repositories.CompanyRepository;
import com.cooksys.groupfinal.repositories.ProjectRepository;
import com.cooksys.groupfinal.repositories.TeamRepository;
import com.cooksys.groupfinal.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {

  private final AnnouncementRepository announcementRepository;
  private final CompanyRepository companyRepository;
  private final ProjectRepository projectRepository;
  private final TeamRepository teamRepository;
  private final UserRepository userRepository;
  
  @Override
  public void run(String... args) throws Exception {
	  
	  Announcement announcement1 = new Announcement();
	  Announcement announcement2 = new Announcement();
	  Announcement announcement3 = new Announcement();
	  Announcement announcement4 = new Announcement();
	  Announcement announcement5 = new Announcement();
	  
	  Company company1 = new Company();
	  Company company2 = new Company();
	  
	  Project project1 = new Project();
	  Project project2 = new Project();
	  Project project3 = new Project();
	  
	  Team team1 = new Team();
	  Team team2 = new Team();
	  Team team3 = new Team();
	  Team team4 = new Team();
	  Team team5 = new Team();
	  Team team6 = new Team();
	  Team team7 = new Team();
	  
	  User user1 = new User();
	  Credentials creds1 = new Credentials();
	  creds1.setUsername("cousingreg");
	  creds1.setPassword("mosteligiblebachelor");
	  user1.setCredentials(creds1);
	  Profile profile1 = new Profile();
	  profile1.setFirstName("Greg");
	  profile1.setLastName("Hirsch");
	  profile1.setEmail("ghirsch@email.com");
	  profile1.setPhone("(000) 000-0000");
	  user1.setProfile(profile1);
	  user1.setActive(true);
	  
	  User user2 = new User();
	  Credentials creds2 = new Credentials();
	  creds2.setUsername("mrsiobhanroy");
	  creds2.setPassword("idontwanttogotojail");
	  user2.setCredentials(creds2);
	  Profile profile2 = new Profile();
	  profile2.setFirstName("Tom");
	  profile2.setLastName("Wambsgans");
	  profile2.setEmail("twambsgans@email.com");
	  profile2.setPhone("(111) 111-1111");
	  user2.setProfile(profile2);
	  user2.setActive(true);
	  
	  User user3 = new User();
	  Credentials creds3 = new Credentials();
	  creds3.setUsername("homeboykroy");
	  creds3.setPassword("ltotheog");
	  user3.setCredentials(creds3);
	  Profile profile3 = new Profile();
	  profile3.setFirstName("Kendall");
	  profile3.setLastName("Roy");
	  profile3.setEmail("kroy@email.com");
	  profile3.setPhone("(222) 222-2222");
	  user3.setProfile(profile3);
	  
	  User user4 = new User();
	  Credentials creds4 = new Credentials();
	  creds4.setUsername("romulusroy");
	  creds4.setPassword("iknownothingaboutsoccer");
	  user4.setCredentials(creds4);
	  Profile profile4 = new Profile();
	  profile4.setFirstName("Roman");
	  profile4.setLastName("Roy");
	  profile4.setEmail("rroy@email.com");
	  profile4.setPhone("(333) 333-3333");
	  user4.setProfile(profile4);
	  user4.setActive(true);
	  
	  User user5 = new User();
	  Credentials creds5 = new Credentials();
	  creds5.setUsername("pinky");
	  creds5.setPassword("futureceoofwaystar");
	  user5.setCredentials(creds5);
	  Profile profile5 = new Profile();
	  profile5.setFirstName("Siobhan");
	  profile5.setLastName("Roy");
	  profile5.setEmail("sroy@email.com");
	  profile5.setPhone("(444) 444-4444");
	  user5.setProfile(profile5);
	  user5.setActive(true);
	  
	  User user6 = new User();
	  Credentials creds6 = new Credentials();
	  creds6.setUsername("thisismycompany");
	  creds6.setPassword("getyourowncompany");
	  user6.setCredentials(creds6);
	  Profile profile6 = new Profile();
	  profile6.setFirstName("Logan");
	  profile6.setLastName("Roy");
	  profile6.setEmail("lroy@email.com");
	  profile6.setPhone("(555) 555-5555");
	  user6.setProfile(profile6);
	  user6.setActive(true);
	  user6.setAdmin(true);
	  
	  User user7 = new User();
	  Credentials creds7 = new Credentials();
	  creds7.setUsername("iamtheeldestson");
	  creds7.setPassword("interestedinpoliticsataveryyoungage");
	  user7.setCredentials(creds7);
	  Profile profile7 = new Profile();
	  profile7.setFirstName("Connor");
	  profile7.setLastName("Roy");
	  profile7.setEmail("croy@email.com");
	  profile7.setPhone("(666) 666-6666");
	  user7.setProfile(profile7);
	  
	  User user8 = new User();
	  Credentials creds8 = new Credentials();
	  creds8.setUsername("marciaroy");
	  creds8.setPassword("amir");
	  user8.setCredentials(creds8);
	  Profile profile8 = new Profile();
	  profile8.setFirstName("Marcia");
	  profile8.setLastName("Roy");
	  profile8.setEmail("mroy@email.com");
	  profile8.setPhone("(777) 777-7777");
	  user8.setProfile(profile8);
	  
	  User user9 = new User();
	  Credentials creds9 = new Credentials();
	  creds9.setUsername("temporaryceoofwaystar");
	  creds9.setPassword("idontgetpaidenoughforthis");
	  user9.setCredentials(creds9);
	  Profile profile9 = new Profile();
	  profile9.setFirstName("Gerri");
	  profile9.setLastName("Kellman");
	  profile9.setEmail("gkellman@email.com");
	  profile9.setPhone("(888) 888-8888");
	  user9.setProfile(profile9);
	  user9.setActive(true);
	  user9.setAdmin(true);
	  
	  User user10 = new User();
	  Credentials creds10 = new Credentials();
	  creds10.setUsername("helenaspassword");
	  creds10.setPassword("helenasusername");
	  user10.setCredentials(creds10);
	  Profile profile10 = new Profile();
	  profile10.setFirstName("Helena");
	  profile10.setLastName("Makendengue");
	  profile10.setEmail("helena@email.com");
	  profile10.setPhone("(000) 000-0000");
	  user10.setProfile(profile10);
	  user10.setActive(true);
	  
	  User user11 = new User();
	  Credentials creds11 = new Credentials();
	  creds11.setUsername("kennysusername");
	  creds11.setPassword("kennyspassword");
	  user11.setCredentials(creds11);
	  Profile profile11 = new Profile();
	  profile11.setFirstName("Kenny");
	  profile11.setLastName("Worth");
	  profile11.setEmail("kenny@email.com");
	  profile11.setPhone("(111) 111-1111");
	  user11.setProfile(profile11);
	  user11.setActive(true);
	  
	  User user12 = new User();
	  Credentials creds12 = new Credentials();
	  creds12.setUsername("willsusername");
	  creds12.setPassword("willspassword");
	  user12.setCredentials(creds12);
	  Profile profile12 = new Profile();
	  profile12.setFirstName("Will");
	  profile12.setLastName("Marttala");
	  profile12.setEmail("will@email.com");
	  profile12.setPhone("(222) 222-2222");
	  user12.setProfile(profile12);
	  user12.setActive(true);
	  user12.setAdmin(true);
	  
	  announcementRepository.saveAllAndFlush(Arrays.asList(
	  		new Announcement[] { announcement1, announcement2, announcement3, announcement4, announcement5 }));
	  companyRepository.saveAllAndFlush(Arrays.asList(new Company[] { company1, company2 }));
	  projectRepository.saveAllAndFlush(Arrays.asList(new Project[] { project1, project2, project3 }));
	  teamRepository.saveAllAndFlush(Arrays.asList(new Team[] { team1, team2, team3, team4, team5, team6, team7 }));
	  userRepository.saveAllAndFlush(Arrays.asList(
			  new User[] { user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12 }));
	  
	  announcement1.setTitle("Welcome to waystar|ROYCO!");
	  announcement1.setMessage("We are waystar|ROYCO, the future of media, entertainment, and theme parks!");
	  announcement1.setCompany(company1);
	  announcement1.setAuthor(user9);
	  
	  announcement2.setTitle("Launch of New ATN Slogan");
	  announcement2.setMessage("We here [sic] for you");
	  announcement2.setCompany(company1);
	  announcement2.setAuthor(user2);
	  
	  announcement3.setTitle("Welcome to Cook Systems!");
	  announcement3.setMessage("This dashboard is where you'll find all of the latest projects currently in developement.");
	  announcement3.setCompany(company2);
	  announcement3.setAuthor(user12);
	  
	  announcement4.setTitle("Congrats to Siobhan Roy & Tom Wambsgans!");
	  announcement4.setMessage("Congrats to our very own Siobhan Roy & Tom Wambsgans on their wedding this past weekend!");
	  announcement4.setCompany(company1);
	  announcement4.setAuthor(user9);
	  
	  announcement5.setTitle("New Angular Content");
	  announcement5.setMessage("New Angular video content and assignments are on the way!");
	  announcement5.setCompany(company2);
	  announcement5.setAuthor(user12);
	  
	  company1.setName("waystar|ROYCO");
	  company1.setDescription("waystar|ROYCO, the future of media, entertainment, and theme parks.");
	  company1.setAnnouncements(new HashSet<Announcement>(Arrays.asList(announcement1, announcement2)));
	  company1.setEmployees(new HashSet<User>(Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9)));
	  company1.setTeams(new HashSet<Team>(Arrays.asList(team1, team2, team3, team4, team5, team6)));
	  
	  company2.setName("Cook Systems");
	  company2.setDescription("We are Cook Systems.");
	  company2.setAnnouncements(new HashSet<Announcement>(Arrays.asList(announcement3)));
	  company2.setEmployees(new HashSet<User>(Arrays.asList(user10, user11, user12)));
	  company2.setTeams(new HashSet<Team>(Arrays.asList(team7)));
	  
	  project1.setName("Brightstar Theme Park Expansion");
	  project1.setDescription("Brightstar theme park expansion to be led by newly appointed head of waystar|ROYCO's amusement park and cruise division, Tom Wambsgans.");
	  project1.setActive(true);
	  project1.setTeam(team1);
	  
	  project2.setName("New Angular Content");
	  project2.setDescription("New Angular video content and assignments are on the way!");
	  project2.setActive(true);
	  project2.setTeam(team7);
	  
	  project3.setName("Shuttle Launch");
	  project3.setDescription("Shuttle launch in Japan to be led by COO, Roman Roy.");
	  project3.setActive(true);
	  project3.setTeam(team3);
	  
	  team1.setName("Team 1");
	  team1.setDescription("Greg & Tom");
	  team1.setCompany(company1);
	  team1.setTeammates(new HashSet<User>(Arrays.asList(user1, user2)));
	  team1.setProjects(new HashSet<Project>(Arrays.asList(project1)));
	  
	  team2.setName("Team 2");
	  team2.setDescription("Tom & Shiv");
	  team2.setCompany(company1);
	  team2.setTeammates(new HashSet<User>(Arrays.asList(user2, user5)));
	  
	  team3.setName("Team 3");
	  team3.setDescription("Roman & Gerri");
	  team3.setCompany(company1);
	  team3.setTeammates(new HashSet<User>(Arrays.asList(user4, user9)));
	  team3.setProjects(new HashSet<Project>(Arrays.asList(project3)));
	  
	  team4.setName("Team 4");
	  team4.setDescription("Greg & Kendall");
	  team4.setCompany(company1);
	  team4.setTeammates(new HashSet<User>(Arrays.asList(user1, user3)));
	  
	  team5.setName("Team 5");
	  team5.setDescription("Logan & Marcia");
	  team5.setCompany(company1);
	  team5.setTeammates(new HashSet<User>(Arrays.asList(user6, user8)));
	  
	  team6.setName("Team 6");
	  team6.setDescription("Kendall, Roman, Shiv, & Connor");
	  team6.setCompany(company1);
	  team6.setTeammates(new HashSet<User>(Arrays.asList(user3, user4, user5, user7)));
	  
	  team7.setName("Team 7");
	  team7.setDescription("Helena, Kenny, & Will");
	  team7.setCompany(company2);
	  team7.setTeammates(new HashSet<User>(Arrays.asList(user10, user11, user12)));
	  team7.setProjects(new HashSet<Project>(Arrays.asList(project2)));
	  
	  user1.setCompanies(new HashSet<Company>(Arrays.asList(company1)));
	  user1.setTeams(new HashSet<Team>(Arrays.asList(team1, team4)));

	  user2.setAnnouncements(new HashSet<Announcement>(Arrays.asList(announcement2)));
	  user2.setCompanies(new HashSet<Company>(Arrays.asList(company1)));
	  user2.setTeams(new HashSet<Team>(Arrays.asList(team1, team2)));

	  user3.setCompanies(new HashSet<Company>(Arrays.asList(company1)));
	  user3.setTeams(new HashSet<Team>(Arrays.asList(team4, team6)));

	  user4.setCompanies(new HashSet<Company>(Arrays.asList(company1)));
	  user4.setTeams(new HashSet<Team>(Arrays.asList(team3, team6)));
	  
	  user5.setCompanies(new HashSet<Company>(Arrays.asList(company1)));
	  user5.setTeams(new HashSet<Team>(Arrays.asList(team2, team6)));

	  user6.setCompanies(new HashSet<Company>(Arrays.asList(company1)));
	  user6.setTeams(new HashSet<Team>(Arrays.asList(team5)));

	  user7.setCompanies(new HashSet<Company>(Arrays.asList(company1)));
	  user7.setTeams(new HashSet<Team>(Arrays.asList(team6)));

	  user8.setCompanies(new HashSet<Company>(Arrays.asList(company1)));
	  user8.setTeams(new HashSet<Team>(Arrays.asList(team5)));

	  user9.setCompanies(new HashSet<Company>(Arrays.asList(company1)));
	  user9.setTeams(new HashSet<Team>(Arrays.asList(team3)));
	  
	  user10.setCompanies(new HashSet<Company>(Arrays.asList(company2)));
	  user10.setTeams(new HashSet<Team>(Arrays.asList(team7)));
	  
	  user11.setCompanies(new HashSet<Company>(Arrays.asList(company2)));
	  user11.setTeams(new HashSet<Team>(Arrays.asList(team7)));
	  
	  user12.setAnnouncements(new HashSet<Announcement>(Arrays.asList(announcement3)));
	  user12.setCompanies(new HashSet<Company>(Arrays.asList(company2)));
	  user12.setTeams(new HashSet<Team>(Arrays.asList(team7)));
	  
	  announcementRepository.saveAllAndFlush(Arrays.asList(
	  		new Announcement[] { announcement1, announcement2, announcement3, announcement4, announcement5  }));
	  companyRepository.saveAllAndFlush(Arrays.asList(new Company[] { company1, company2 }));
	  projectRepository.saveAllAndFlush(Arrays.asList(new Project[] { project1, project2, project3 }));
	  teamRepository.saveAllAndFlush(Arrays.asList(new Team[] { team1, team2, team3, team4, team5, team6, team7 }));
	  userRepository.saveAllAndFlush(Arrays.asList(
			  new User[] { user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11, user12 }));

  }

}