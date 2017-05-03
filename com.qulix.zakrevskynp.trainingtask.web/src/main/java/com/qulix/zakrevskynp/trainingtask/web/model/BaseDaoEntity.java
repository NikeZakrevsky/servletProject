package com.qulix.zakrevskynp.trainingtask.web.model;

/**
 * Base dao entity with id field
 *
 * @author Q-NZA
 */
public class BaseDaoEntity {
    private Integer id;

    /**
     * Gets person id
     *
     * @return id of the person
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets person id
     *
     * @param id id of the person
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
