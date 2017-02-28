package com.qulix.trainingtask.model;

import java.util.Arrays;
import java.util.List;

public enum Status {
    NOT_STARTED,
    IN_PROCESS,
    FINISHED,
    DELAYED;

    public List<Status> asList() {
        return Arrays.asList(values());
    }
}
