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

import org.pshdl.model.validation.HDLValidator.HDLAdvise;

import com.fasterxml.jackson.annotation.*;
import com.google.common.collect.*;
import com.wordnik.swagger.annotations.*;

@ApiModel(description = "Contains information on how a problem could be fixed")
public class AdviseInfo {

	private String explanation;

	private String message;

	private List<String> solutions;

	public AdviseInfo() {
		solutions = Lists.newLinkedList();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AdviseInfo other = (AdviseInfo) obj;
		if (explanation == null) {
			if (other.explanation != null)
				return false;
		} else if (!explanation.equals(other.explanation))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}

	/**
	 * A human readable explanation of why this problem was caused that goes a
	 * little more in-depth than the {@link #getMessage()}.
	 * 
	 * @return non-null explanation
	 */
	@JsonProperty
	@ApiModelProperty(required = true, value = "A human readable explanation of why this problem was caused that goes a little more in-depth than the message")
	public String getExplanation() {
		return this.explanation;
	}

	/**
	 * A human readable explanation of why this problem was caused
	 * 
	 * @return non-null message
	 */
	@JsonProperty
	@ApiModelProperty(required = true, value = "A human readable explanation of why this problem was caused")
	public String getMessage() {
		return this.message;
	}

	/**
	 * A list of possible human readable solutions to solve that problem
	 * 
	 * @return non-null list of possible solutions
	 */
	@JsonProperty
	@ApiModelProperty(required = true, value = "A list of possible human readable solutions to solve that problem")
	public List<String> getSolutions() {
		return this.solutions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((explanation == null) ? 0 : explanation.hashCode());
		result = (prime * result) + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	public void setExplanation(final String explanation) {
		this.explanation = explanation;
	}

	public void setFromAdvise(HDLAdvise advise) {
		this.setExplanation(advise.explanation);
		this.setMessage(advise.message);
		this.setSolutions(Arrays.asList(advise.solutions));
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void setSolutions(final List<String> solutions) {
		this.solutions = solutions;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("AdviseInfo [message=");
		builder.append(getMessage());
		builder.append("]");
		return builder.toString();
	}
}
