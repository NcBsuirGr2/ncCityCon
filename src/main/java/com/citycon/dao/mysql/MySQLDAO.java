package com.citycon.dao.mysql;

import com.citycon.dao.DAO;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Vojts on 09.11.2016.
 */
public abstract class MySQLDAO implements DAO {
    static ArrayList<Connection> connections;

    public abstract DAO getInstance();
}
