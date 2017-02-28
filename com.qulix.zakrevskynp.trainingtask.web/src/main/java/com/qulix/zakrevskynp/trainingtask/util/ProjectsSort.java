package com.qulix.zakrevskynp.trainingtask.util;

import java.util.Comparator;
import java.util.List;

import com.qulix.zakrevskynp.trainingtask.model.Project;

public class ProjectsSort implements Sort<Project> {
    
    @Override
    public List<Project> sort(List<Project> projects, String sortField) {
        Comparator comparator = null;
        if (sortField == null) {
            sortField = "name";
        }
        switch(sortField) {
            case "name":
                comparator = Comparator.comparing(Project::getName);
                break;
            case "shortname":
                comparator = Comparator.comparing(Project::getShortName);
                break;
            case "description":
                comparator = Comparator.comparing(Project::getDescription);
                break;
            default:
                comparator = Comparator.comparing(Project::getName);
                break;
        }
        projects.sort(comparator);
        return projects;
    }
}
