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

import org.pshdl.model.validation.*;
import org.pshdl.model.validation.HDLValidator.HDLAdvise;
import org.pshdl.model.validation.Problem.ProblemSeverity;

import com.fasterxml.jackson.annotation.*;
import com.wordnik.swagger.annotations.*;

@ApiModel("When a problem was found with the input file(s) this object contains all information about the problem")
public class ProblemInfo {

	@JsonProperty
	@ApiModelProperty(value = "An advise on how to fix this problem. Also contains a more human readable description of the problem")
	public AdviseInfo advise;
	@JsonProperty
	@ApiModelProperty(required = true, value = "The location of the problem")
	public LocationInfo location;

	@JsonProperty
	@ApiModelProperty(required = true, value = "The unique id of this problem. This is equivalent to the error class name and the name of the error")
	public String errorCode;

	@JsonProperty
	@ApiModelProperty(value = "For some errors a clarifying info is available")
	public String info;

	public boolean isSyntax;
	@JsonProperty
	@ApiModelProperty(required = true, value = "A unique id for that problem")
	public int pid;

	@JsonProperty
	@ApiModelProperty(required = true, value = "The severity of the problem")
	public ProblemSeverity severity;

	public ProblemInfo() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ProblemInfo other = (ProblemInfo) obj;
		if (pid != other.pid)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + pid;
		return result;
	}

	public void setFromProblem(final Problem problem) {
		final HDLAdvise advise = HDLValidator.advise(problem);
		if (advise != null) {
			final AdviseInfo ad = new AdviseInfo();
			ad.setFromAdvise(advise);
			this.advise = (ad);
		}
		this.errorCode = (((problem.getClass().getName() + ".") + problem.code.name()));
		this.severity = (problem.code.getSeverity());
		this.info = (problem.info);
		this.pid = (problem.pid);
		final LocationInfo loc = new LocationInfo();
		loc.setFromProblem(problem);
		this.location = (loc);
		this.isSyntax = (problem.isSyntax);
	}

}
