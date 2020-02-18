package com.project.search.adapter.data;

import java.io.IOException;

public interface IDataLoadAdapterBase {

    /**
     * @param entity - Entity to be loaded
     * @return - return the JSON data of the entity
     */
    public String load(String entity) throws IOException;
}
