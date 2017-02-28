package com.qulix.zakrevskynp.trainingtask.web.util;

import java.util.Comparator;
import java.util.List;


import com.qulix.zakrevskynp.trainingtask.web.model.Task;

public class TasksSort implements Sort<Task> {
    @Override
    public List<Task> sort(List<Task> tasks, String sortField) {
        Comparator comparator;
        if (sortField == null) {
            sortField = "name";
        }
        switch(sortField) {
            case "name":
                comparator = Comparator.comparing(Task::getName);
                break;
            case "projectShortName":
                comparator = Comparator.comparing(Task::getProjectShortName);
                break;
            case "startDate":
                comparator = Comparator.comparing(Task::getStartDate);
                break;
            case "endDate":
                comparator = Comparator.comparing(Task::getEndDate);
                break;
            case "performer":
                comparator = Comparator.comparing(Task::getPerformer);
                break;
            case "status":
                comparator = Comparator.comparing(Task::getStatus);
                break;
            default:
                comparator = Comparator.comparing(Task::getName);
                break;
        }
        tasks.sort(comparator);
        return tasks;
    }
}
