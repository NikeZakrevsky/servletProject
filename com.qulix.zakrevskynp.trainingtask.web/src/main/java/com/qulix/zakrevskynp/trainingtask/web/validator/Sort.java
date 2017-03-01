package com.qulix.zakrevskynp.trainingtask.web.validator;

import java.util.List;

public interface Sort<T> {
    List<T> sort(List<T> listName, String sortFieldName);
}
