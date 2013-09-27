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

import java.io.*;

import com.fasterxml.jackson.annotation.*;
import com.google.common.base.*;
import com.google.common.io.*;
import com.sun.jersey.core.util.*;
import com.wordnik.swagger.annotations.*;

@ApiModel("Information about addtional output files")
public class OutputInfo {

	private boolean isString;

	private String fileURI;

	private String relPath;

	private String file;

	public OutputInfo() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final OutputInfo other = (OutputInfo) obj;
		if (fileURI == null) {
			if (other.fileURI != null)
				return false;
		} else if (!fileURI.equals(other.fileURI))
			return false;
		return true;
	}

	public String readContents() throws IOException {
		return Files.toString(new File(file), Charsets.UTF_8);
	}

	public String getFile() {
		return file;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The URL which can be used to retrieve the file")
	public String getFileURI() {
		return fileURI;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The relative path where this file is located")
	public String getRelPath() {
		return relPath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((fileURI == null) ? 0 : fileURI.hashCode());
		return result;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "When <b>true</b> this file is not a binary")
	public boolean isString() {
		return isString;
	}

	public void setContents(String content) throws IOException {
		byte[] ba = content.getBytes(Charsets.UTF_8);
		if (!isString()) {
			ba = Base64.decode(ba);
		}
		Files.write(ba, new File(file));
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void setFileURI(String fileURI) {
		this.fileURI = fileURI;
	}

	public void setRelPath(String relPath) {
		this.relPath = relPath;
	}

	public void setString(boolean isString) {
		this.isString = isString;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("OutputInfo [isString=");
		builder.append(isString());
		builder.append(", fileURI=");
		builder.append(getFileURI());
		builder.append(", relPath=");
		builder.append(getRelPath());
		builder.append(", file=");
		builder.append(getFile());
		builder.append("]");
		return builder.toString();
	}

}
