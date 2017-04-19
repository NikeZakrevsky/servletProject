package com.qulix.zakrevskynp.trainingtask.web.dao.project;

import java.util.List;

import com.qulix.zakrevskynp.trainingtask.web.model.Project;
import com.qulix.zakrevskynp.trainingtask.web.model.Task;

/**
 * Interface describing the operation on @{{@link Project}} of the DAO pattern
 * @author Q-NZA
 */
public interface ProjectDAO {

    List getProjectsList();

    void addProject(Project project, List<Task> tasks);

    void removeProject(int id);

    void updateProject(Project project);

    Project getProjectById(int id);
}
