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

import com.fasterxml.jackson.annotation.*;
import com.wordnik.swagger.annotations.*;

@ApiModel("Detailed position information for a problem")
public class LocationInfo {

	private int length;

	private int line;

	private int offsetInLine;

	private int totalOffset;

	public LocationInfo() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final LocationInfo other = (LocationInfo) obj;
		if (length != other.length)
			return false;
		if (line != other.line)
			return false;
		if (offsetInLine != other.offsetInLine)
			return false;
		if (totalOffset != other.totalOffset)
			return false;
		return true;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The length of the problem")
	public int getLength() {
		return length;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The line within the file in which the problem occured")
	public int getLine() {
		return line;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The offset within the line at which the problem occured")
	public int getOffsetInLine() {
		return offsetInLine;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The total character offset within the file at which the problem occured")
	public int getTotalOffset() {
		return totalOffset;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + length;
		result = (prime * result) + line;
		result = (prime * result) + offsetInLine;
		result = (prime * result) + totalOffset;
		return result;
	}

	public void setFromProblem(Problem p) {
		this.setLine(p.line);
		this.setLength(p.length);
		this.setOffsetInLine(p.offsetInLine);
		this.setTotalOffset(p.totalOffset);
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public void setOffsetInLine(int offsetInLine) {
		this.offsetInLine = offsetInLine;
	}

	public void setTotalOffset(int totalOffset) {
		this.totalOffset = totalOffset;
	}

	@Override
	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("LocationInfo [length=");
		stringBuilder.append(getLength());
		stringBuilder.append(", line=");
		stringBuilder.append(getLine());
		stringBuilder.append(", offsetInLine=");
		stringBuilder.append(getOffsetInLine());
		stringBuilder.append(", totalOffset=");
		stringBuilder.append(getTotalOffset());
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
}
