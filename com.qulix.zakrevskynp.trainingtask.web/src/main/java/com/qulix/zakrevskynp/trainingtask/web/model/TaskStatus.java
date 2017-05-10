package com.qulix.zakrevskynp.trainingtask.web.model;

/**
 * TaskStatus entity
 *
 * @author Q-NZA
 */
public enum TaskStatus {
    NOT_STARTED("Не начата"), IN_PROCESS("В процессе"), COMPLETED("Завершена"), DELAYED("Отложена");

    private final String status;

    TaskStatus(final String status) {
        this.status = status;
    }

    /**
     * Gets status
     *
     * @return status of the task
     */
    @Override
    public String toString() {
        return status;
    }

    /**
     * Converts String to the enum type
     *
     * @param text text for converting
     * @return enum object
     */
    public static TaskStatus fromString(String text) {
        for (TaskStatus taskStatus : TaskStatus.values()) {
            if (taskStatus.status.equalsIgnoreCase(text)) {
                return taskStatus;
            }
        }
        return null;
    }
}
