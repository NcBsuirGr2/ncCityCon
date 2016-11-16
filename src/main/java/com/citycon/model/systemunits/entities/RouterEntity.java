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
	private int cityId;

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
	public void isActive(boolean isActive) {
		this.isActive = isActive;
	}

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

	public int getCityId() {
		return cityId;
	}
}