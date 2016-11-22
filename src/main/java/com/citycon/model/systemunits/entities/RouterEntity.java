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
	private int availablePortsNum;
	private boolean isActive;
	private int cityId;
	private String cityName;

	public String getName() {
		return name;
	}
	public String getSN() {
		return SN;
	}
	public int getPortsNum() {
		return portsNum;
	}
	public int getAvailablePortsNum() {
		return availablePortsNum;
	}
	public boolean isActive() {
		return isActive;
	}
	public String getCityName() {
		return cityName;
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
	public void setAvailablePortsNum(int availablePortsNum) {
		this.availablePortsNum = availablePortsNum;
	}
	public void isActive(boolean isActive) {
		this.isActive = isActive;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

	public int getCityId() {
		return cityId;
	}
	public String toString() {
		StringBuilder routerString = new StringBuilder();
		routerString.append("Router: id(");
		routerString.append(id);
		routerString.append("), name(");
		routerString.append(name);
		routerString.append("), SN(");
		routerString.append(SN);
		routerString.append("), portsNum(");
		routerString.append(portsNum);
		routerString.append("), isActive(");
		routerString.append(isActive);
		routerString.append("), cityId(");
		routerString.append(cityId);
		routerString.append("), cityName(");
		routerString.append(cityName);
		routerString.append(")");
		return routerString.toString();
	}
}