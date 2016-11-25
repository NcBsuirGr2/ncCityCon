package com.citycon.model.systemunits.entities;

/**
 * Represents all necessary information about router. It is the plain
 * java bean which must be obtained from DAO layer and be used in jsp-pages 
 * to display router information.
 *
 * @author Mike
 * @version 1.2
 */
public class RouterEntity extends Entity {
	private String name;
	private String SN;
	private int portsNum;
	private int usedPortsNum;
	private boolean isActive;
	private int cityId;
	private String cityName;
	private String countryName;

	// ----- Getters -----
	public String getName() {
		return name;
	}
	public String getSN() {
		return SN;
	}
	public int getPortsNum() {
		return portsNum;
	}
	public int getUsedPortsNum() {
		return usedPortsNum;
	}
	public boolean isActive() {
		return isActive;
	}
	public String getCityName() {
		return cityName;
	}
	public String getCountryName() {
		return countryName;
	}
	public int getCityId() {
		return cityId;
	}

	// ----- Setters -----
	public void setName(String name) {
		this.name = name;
	}
	public void setSN(String SN) {
		this.SN = SN;
	}
	public void setPortsNum(int portsNum) {
		this.portsNum = portsNum;
	}
	public void setUsedPortsNum(int usedPortsNum) {
		this.usedPortsNum = usedPortsNum;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
    public void setCityId(int cityId) {
        this.cityId = cityId;
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
		routerString.append("), usedPortsNum(");
		routerString.append(usedPortsNum);
		routerString.append("), isActive(");
		routerString.append(isActive);
		routerString.append("), cityId(");
		routerString.append(cityId);
		routerString.append("), cityName(");
		routerString.append(cityName);
		routerString.append("), countryName(");
		routerString.append(countryName);
		routerString.append(")");
		return routerString.toString();
	}
}