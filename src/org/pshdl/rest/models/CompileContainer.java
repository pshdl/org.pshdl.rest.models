/*******************************************************************************
 * PSHDL is a library and (trans-)compiler for PSHDL input. It generates
 *     output suitable for implementation or simulation of it.
 *     
 *     Copyright (C) 2013 Karsten Becker (feedback (at) pshdl (dot) org)
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

import com.fasterxml.jackson.annotation.*;
import com.google.common.collect.*;
import com.wordnik.swagger.annotations.*;

@ApiModel("A container for all CompileInfo objects")
public class CompileContainer {

	private Set<CompileInfo> compileResults;

	public CompileContainer() {
		compileResults = Sets.newLinkedHashSet();
	}

	public CompileContainer(List<CompileInfo> res) {
		setCompileResults(new HashSet<CompileInfo>(res));
	}

	@JsonProperty
	public Set<CompileInfo> getCompileResults() {
		return this.compileResults;
	}

	public void setCompileResults(final Set<CompileInfo> compileResults) {
		this.compileResults = compileResults;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("CompileContainer [compileResults=");
		builder.append(getCompileResults());
		builder.append("]");
		return builder.toString();
	}
}
