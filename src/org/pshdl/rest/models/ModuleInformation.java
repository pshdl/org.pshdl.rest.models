/*******************************************************************************
 * PSHDL is a library and (trans-)compiler for PSHDL input. It generates
 *     output suitable for implementation or simulation of it.
 *     
 *     Copyright (C) 2014 Karsten Becker (feedback (at) pshdl (dot) org)
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 *     This License does not grant permission to use the trade names, trademarks,
 *     service marks, or product names of the Licensor, except as required for 
 *     reasonable and customary use in describing the origin of the Work.
 * 
 * Contributors:
 *     Karsten Becker - initial API and implementation
 ******************************************************************************/
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

		public Port() {
			this(null, null, null, null, -1, null, null);
		}

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

	public ModuleInformation() {
		this(null, null, null, null);
	}

	public ModuleInformation(String name, List<Port> ports, List<String> instances, UnitType type) {
		super();
		this.name = name;
		this.ports = ports;
		this.instances = instances;
		this.type = type;
	}
}
