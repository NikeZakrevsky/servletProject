package com.qulix.zakrevskynp.trainingtask.web.model;

/**
 * TaskStatus entity model
 *
 * @author Q-NZA
 */
public enum TaskStatus {
    NOT_STARTED("Не начата"), IN_PROCESS("В процессе"), COMPLETED("Завершена"), DELAYED("Отложена");

    private final String status;

    TaskStatus(final String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

    public static TaskStatus fromString(String text) {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            System.out.println(taskStatus.status + " " + text);
            if (taskStatus.status.equalsIgnoreCase(text)) {
                return taskStatus;
            }
        }
        return null;
    }
}
