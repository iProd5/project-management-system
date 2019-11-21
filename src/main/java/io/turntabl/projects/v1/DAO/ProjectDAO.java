package io.turntabl.projects.v1.DAO;

import io.turntabl.projects.v1.Transfers.ProjectTO;
import java.util.List;
import java.util.Map;

public interface ProjectDAO {
    public void addProject(Map<String,String> requestData);
    public void deleteProject(Integer projectID);
    public List<io.turntabl.projects.v1.Transfers.ProjectTO> getAllProject();
    public List<ProjectTO> searchProjectByName(String projectName);
    public void updateProject(Map<String,String> requestData);
}
