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

@ApiModel("All information from the compilation")
public class CompileInfo {

	private ModuleInfo tree;

	private List<OutputInfo> addOutputs;

	private List<ProblemInfo> problems;

	private String file;

	private String fileURI;

	private long created;

	public CompileInfo() {
		addOutputs = Lists.newLinkedList();
		problems = Lists.newLinkedList();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final CompileInfo other = (CompileInfo) obj;
		if (fileURI == null) {
			if (other.fileURI != null)
				return false;
		} else if (!fileURI.equals(other.fileURI))
			return false;
		return true;
	}

	@JsonProperty
	@ApiModelProperty(value = "If additional outputs have been generated, such as output from generators, it will be listed here")
	public List<OutputInfo> getAddOutputs() {
		return this.addOutputs;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The timestamp when this file was last compiled")
	public long getCreated() {
		return created;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The name of the file")
	public String getFile() {
		return file;
	}

	@JsonProperty
	@ApiModelProperty(value = "The URI of the primary output file")
	public String getFileURI() {
		return fileURI;
	}

	@JsonProperty
	@ApiModelProperty(value = "A list of problems that have been found with this file during compilation")
	public List<ProblemInfo> getProblems() {
		return this.problems;
	}

	@JsonProperty
	@ApiModelProperty(value = "An instantiation tree for displaying outline information (not implemented yet)")
	public ModuleInfo getTree() {
		return tree;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((fileURI == null) ? 0 : fileURI.hashCode());
		return result;
	}

	public void setAddOutputs(final List<OutputInfo> addOutputs) {
		this.addOutputs = addOutputs;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void setFileURI(String fileURI) {
		this.fileURI = fileURI;
	}

	public void setProblems(final List<ProblemInfo> problems) {
		this.problems = problems;
	}

	public void setTree(ModuleInfo tree) {
		this.tree = tree;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("CompileInfo [tree=");
		builder.append(getTree());
		builder.append(", addOutputs=");
		builder.append(getAddOutputs());
		builder.append(", problems=");
		builder.append(getProblems());
		builder.append(", file=");
		builder.append(getFile());
		builder.append(", fileURI=");
		builder.append(getFileURI());
		builder.append("]");
		return builder.toString();
	}
}
