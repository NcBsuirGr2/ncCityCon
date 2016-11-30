package com.citycon.model.systemunits.entities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import javax.validation.Valid;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.NotBlank;

import com.citycon.model.systemunits.entities.validationgroups.RouterAddGroup;
import com.citycon.model.systemunits.entities.validationgroups.ConnectionGroup;

/**
 * Represents all necessary information about router. It is the plain
 * java bean which must be obtained from DAO layer and be used in jsp-pages 
 * to display router information.
 *
 * @author Mike
 * @version 1.2
 */
public class RouterEntity extends Entity {
	@NotBlank(message="Router name can not be blank")
	@Size(min=3, max=30, message="Router name must be {min}..{max} in length")
	@Pattern(regexp="^[a-z-_a-z0-9]+$", flags=Pattern.Flag.CASE_INSENSITIVE, message="Invalid router name: ${validatedValue}")
	private String name;

	@NotBlank(message="Router SN can not be blank", groups={RouterAddGroup.class, ConnectionGroup.class})
	@Size(min=3, max=30, message="Router SN must be {min}..{max} in length", 
		groups={RouterAddGroup.class, ConnectionGroup.class})
	@Pattern(regexp="^[-_#:a-z0-9]+$", flags=Pattern.Flag.CASE_INSENSITIVE, 
		message="Invalid SN: ${validatedValue}", groups={RouterAddGroup.class, ConnectionGroup.class})
	private String SN;

	@Min(value=0, message="Ports number of router must be positive", groups={RouterAddGroup.class})
	@Max(value=4, message="Ports number of router must no higher then 4", groups={RouterAddGroup.class})
	private int portsNum;

	private int usedPortsNum;

	private boolean isActive;

	@Valid
	private CityEntity city = new CityEntity();

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
	public CityEntity getCity() {
		return city;
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
	public void setCity(CityEntity city) {
		this.city = city;
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
		routerString.append("), city(");
		routerString.append(city);
		routerString.append(")");
		return routerString.toString();
	}
}