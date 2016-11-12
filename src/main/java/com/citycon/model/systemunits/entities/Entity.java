package com.citycon.model.systemunits.entities;
/**
 * Common interface for system entities. System entities are the plain
 * java beans. They must be obtained from DAO layer and be used in jsp-pages 
 * to display entity information.
 *
 * @author Mike
 * @version 1.1
 */
public abstract class Entity {
	protected int id;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}