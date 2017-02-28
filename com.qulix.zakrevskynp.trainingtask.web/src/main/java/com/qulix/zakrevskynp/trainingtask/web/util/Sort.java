package com.qulix.zakrevskynp.trainingtask.web.util;

import java.util.List;

public interface Sort<T> {
    List<T> sort(List<T> listName, String sortFieldName);
}
