package org.pshdl.rest.models;

import java.util.*;

import org.pshdl.model.HDLPrimitive.HDLPrimitiveType;
import org.pshdl.model.HDLVariableDeclaration.HDLDirection;

import com.fasterxml.jackson.annotation.*;

public class ModuleInformation {
	public static class Port {
		@JsonProperty
		public final String name;
		@JsonProperty
		public final HDLDirection dir;
		@JsonProperty
		public final HDLPrimitiveType primitive;
		@JsonProperty
		public final String enumRef;
		@JsonProperty
		public final int width;
		@JsonProperty
		public final List<Integer> dimensions;
		@JsonProperty
		public final List<String> annotations;

		public Port(String name, HDLDirection dir, HDLPrimitiveType primitive, String enumRef, int width, List<Integer> dimensions, List<String> annotations) {
			super();
			this.name = name;
			this.dir = dir;
			this.primitive = primitive;
			this.enumRef = enumRef;
			this.width = width;
			this.dimensions = dimensions;
			this.annotations = annotations;
		}
	}

	public static enum UnitType {
		ENUM, MODULE, INTERFACE, TESTBENCH
	}

	@JsonProperty
	public final String name;
	@JsonProperty
	public final List<Port> ports;
	@JsonProperty
	public final List<String> instances;
	@JsonProperty
	public final UnitType type;

	public ModuleInformation(String name, List<Port> ports, List<String> instances, UnitType type) {
		super();
		this.name = name;
		this.ports = ports;
		this.instances = instances;
		this.type = type;
	}
}
