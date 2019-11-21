package io.turntabl.projects.v1.Implementors;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.turntabl.projects.v1.DAO.ProjectDAO;
import io.turntabl.projects.v1.Transfers.ProjectTO;
import io.turntabl.projects.v1.DAO.ProjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Api
@RestController
public class ProjectDAOImpl implements ProjectDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @ApiOperation("Add Project")
    @Override
    @PostMapping("/v1/api/project")
    public void addProject(@RequestBody Map<String,String> requestData) {
       jdbcTemplate.update(
               "insert into projects(project_name,project_description) values(?,?,?,?)",
               new Object[]{requestData.get("project_name"),requestData.get("project_description")}
       );
       System.out.println("Project Added Successfully");
    }



    @ApiOperation("Delete Project")
    @Override
    @DeleteMapping("/v1/api/project/{id}")
    public void deleteProject(@PathVariable Integer id) {
        jdbcTemplate.update(
                "delete from Projects where project_id = ?",
                new Object[]{id}
        );
        System.out.println("Project Deleted Successfully");
    }

    @ApiOperation("Get All Projects")
    @Override
    @GetMapping("/v1/api/projects")
    public List<ProjectTO> getAllProject() {
        List<ProjectTO> projects = jdbcTemplate.query(
                "select * from Projects",
                BeanPropertyRowMapper.newInstance(ProjectTO.class)
        );
        return projects;
    }

    @ApiOperation("Search Project By Name")
    @Override
    @GetMapping("/v1/api/project/search/{name}")
    public List<ProjectTO> searchProjectByName(@PathVariable  String name) {
        Optional<List<ProjectTO>> projects = Optional.ofNullable(jdbcTemplate.query(
                "select * from projects where project_name like ?",
                new Object[]{name + "%"},
                BeanPropertyRowMapper.newInstance(ProjectTO.class)
        ));
        if (projects.isPresent()){
            return projects.get();

        }else {
            return new ArrayList<>();
        }
    }

    public Optional<List<ProjectTO>> searchProjectByID(Integer projectID) {
        Optional<List<ProjectTO>> project = Optional.ofNullable(jdbcTemplate.query(
                "select * from projects where project_id = ?",
                new Object[]{projectID},
                BeanPropertyRowMapper.newInstance(ProjectTO.class)
        ));
        return project;
    }

    @ApiOperation("Update Project Details")
    @Override
    @PutMapping("/v1/api/project")
    public void updateProject(@RequestBody Map<String, String> requestData) {
        Integer id = Integer.parseInt(requestData.get("project_id"));
        Optional<List<ProjectTO>> project = searchProjectByID(Integer.parseInt(requestData.get("project_id")));

        if (project.isPresent()){
            String project_name;
            String project_description;
            Integer project_id;
            //String client_email;
            if (requestData.get("project_name").isEmpty()){
                project_name = project.get().get(0).getProject_name();
            }else{
                project_name = requestData.get("project_name");
            }
            if (requestData.get("project_description").isEmpty()){
                project_description = project.get().get(0).getProject_description();
            }else{
                project_description = requestData.get("project_description");
            }
            /*if (requestData.get("project_id").isEmpty()){
                project_id = project.get().get(0).getProject_id();
            }else{
                project_id = requestData.get("project_id");
            }*?
            /*if (requestData.get("client_email").isEmpty()){
                client_email = client.get().get(0).getClient_email();
            }else{
                client_email = requestData.get("client_email");
            }*/

            jdbcTemplate.update(
                    "update projects set project_name = ?, project_description = ?, where project_id = ?",
                    new Object[]{project_name,project_description}
            );
            System.out.println("Project Details Updated Successfully");

        }else {
            System.out.println("Project with this "+ id +" ID does not exist");
        }
    }
}
