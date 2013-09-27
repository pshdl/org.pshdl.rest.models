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

	private AdviseInfo advise;

	private LocationInfo location;

	private String errorCode;

	private String info;

	private boolean isSyntax;

	private int pid;

	private ProblemSeverity severity;

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

	@JsonProperty
	@ApiModelProperty(value = "An advise on how to fix this problem. Also contains a more human readable description of the problem")
	public AdviseInfo getAdvise() {
		return this.advise;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The unique id of this problem. This is equivalent to the error class name and the name of the error")
	public String getErrorCode() {
		return errorCode;
	}

	@JsonProperty
	@ApiModelProperty(value = "For some errors a clarifying info is available")
	public String getInfo() {
		return info;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The location of the problem")
	public LocationInfo getLocation() {
		return this.location;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "A unique id for that problem")
	public int getPid() {
		return pid;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The severity of the problem")
	public ProblemSeverity getSeverity() {
		return severity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + pid;
		return result;
	}

	public boolean isSyntax() {
		return isSyntax;
	}

	public void setAdvise(final AdviseInfo advise) {
		this.advise = advise;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setFromProblem(final Problem problem) {
		final HDLAdvise advise = HDLValidator.advise(problem);
		if (advise != null) {
			final AdviseInfo ad = new AdviseInfo();
			ad.setFromAdvise(advise);
			this.setAdvise(ad);
		}
		this.setErrorCode(((problem.getClass().getName() + ".") + problem.code.name()));
		this.setSeverity(problem.code.getSeverity());
		this.setInfo(problem.info);
		this.setPid(problem.pid);
		final LocationInfo loc = new LocationInfo();
		loc.setFromProblem(problem);
		this.setLocation(loc);
		this.setSyntax(problem.isSyntax);
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setLocation(final LocationInfo location) {
		this.location = location;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public void setSeverity(ProblemSeverity severity) {
		this.severity = severity;
	}

	public void setSyntax(boolean isSyntax) {
		this.isSyntax = isSyntax;
	}

	@Override
	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("ProblemInfo [_location=");
		stringBuilder.append(getLocation());
		stringBuilder.append(", errorCode=");
		stringBuilder.append(getErrorCode());
		stringBuilder.append(", info=");
		stringBuilder.append(getInfo());
		stringBuilder.append(", pid=");
		stringBuilder.append(getPid());
		stringBuilder.append(", severity=");
		stringBuilder.append(getSeverity());
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
}
