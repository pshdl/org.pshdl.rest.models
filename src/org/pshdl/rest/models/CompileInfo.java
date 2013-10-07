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

	private List<FileRecord> files = Lists.newLinkedList();

	private List<ProblemInfo> problems = Lists.newLinkedList();

	private long created;

	private String creator;

	public CompileInfo() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + (int) (created ^ (created >>> 32));
		result = (prime * result) + ((creator == null) ? 0 : creator.hashCode());
		result = (prime * result) + ((files == null) ? 0 : files.hashCode());
		result = (prime * result) + ((problems == null) ? 0 : problems.hashCode());
		return result;
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
		if (created != other.created)
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (files == null) {
			if (other.files != null)
				return false;
		} else if (!files.equals(other.files))
			return false;
		if (problems == null) {
			if (other.problems != null)
				return false;
		} else if (!problems.equals(other.problems))
			return false;
		return true;
	}

	@JsonProperty
	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	@JsonProperty
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@JsonProperty
	public List<FileRecord> getFiles() {
		return files;
	}

	@JsonProperty
	public List<ProblemInfo> getProblems() {
		return problems;
	}

	@Override
	public String toString() {
		return "CompileInfo [files=" + files + ", problems=" + problems + ", created=" + created + ", creator=" + creator + "]";
	}

	public void setProblems(List<ProblemInfo> problems) {
		this.problems = problems;
	}

	public void setFiles(List<FileRecord> files) {
		this.files = files;
	}

}
