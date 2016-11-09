package com.citycon.dao;
import com.citycon.model.systemunits.entities.*;

/**
 * Created by Vojts on 09.11.2016.
 */
public interface DAO {
    int create(Entity newElement);
    int read(Entity readElement);
    int update(Entity updateElement);
    void delete(Entity deleteElement);
}
