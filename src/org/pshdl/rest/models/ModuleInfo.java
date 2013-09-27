package org.pshdl.rest.models;

import java.util.*;

import com.fasterxml.jackson.annotation.*;
import com.google.common.collect.*;
import com.wordnik.swagger.annotations.*;

@ApiModel("Information about an instance")
public class ModuleInfo {

	private String name;

	private List<ModuleInfo> instances;

	public ModuleInfo() {
		instances = Lists.newLinkedList();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ModuleInfo other = (ModuleInfo) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (instances == null) {
			if (other.instances != null)
				return false;
		} else if (!instances.equals(other.instances))
			return false;
		return true;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The name of the instance")
	public String getName() {
		return name;
	}

	@JsonProperty
	@ApiModelProperty(required = false, value = "A list of instances that are instantiated")
	public List<ModuleInfo> getInstances() {
		return instances;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
		result = (prime * result) + ((instances == null) ? 0 : instances.hashCode());
		return result;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInstances(List<ModuleInfo> sub) {
		this.instances = sub;
	}

	@Override
	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[name=");
		stringBuilder.append(getName());
		stringBuilder.append(", sub=");
		stringBuilder.append(getInstances());
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
}
