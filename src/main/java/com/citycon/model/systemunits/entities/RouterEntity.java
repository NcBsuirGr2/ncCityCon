package com.citycon.model.systemunits.entities;

import com.citycon.model.Grant;
/**
 * Represents all necessary information about router. It is the plain
 * java bean which must be obtained from DAO layer and be used in jsp-pages 
 * to display router information.
 *
 * @author Mike
 * @version 1.1
 */
public class RouterEntity extends Entity {
	private String name;
	private String SN;
	private int portsNum;
	private boolean isActive;

	public RouterEntity() {
		String names[] = {"Router1", "Router2", "CentralLidaRouter", "WestAmericaRouter", "TestRouter", "BSUIRRouter", "CreatedByMikeRouter"};
		String SNs[] = {"af3r2", "asd235", "63he7", "7dj4", "jg35hd8", "4f6j8", "345f44"};
		int randomName = (int)(Math.random()*names.length);
		int randomSN = (int)(Math.random()*SNs.length);
		
		name = names[randomName];
		SN = SNs[randomSN];
		portsNum = (int)(Math.random()*4+1);
		isActive = (Math.random() >= 0.5f);
	}

	public String getName() {
		return name;
	}
	public String getSN() {
		return SN;
	}
	public int getPortsNum() {
		return portsNum;
	}
	public boolean isActive() {
		return isActive;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setSN(String SN) {
		this.SN = SN;
	}
	public void setPortsNum(int portsNum) {
		this.portsNum = portsNum;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}