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
import com.wordnik.swagger.annotations.*;

@ApiModel("All information about a file")
public class FileInfo implements Comparable<FileInfo> {

	private CompileInfo info;

	private long lastModified;

	private String name;

	private long size;

	private CheckType syntax;

	private FileType type;

	public FileInfo() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FileInfo other = (FileInfo) obj;
		final String name = getName();
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@JsonProperty
	@ApiModelProperty("If this file has been compiled or verified the information is stored here")
	public CompileInfo getInfo() {
		return this.info;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "A timestamp when the file has been last modified")
	public long getLastModified() {
		return lastModified;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The name of the file")
	public String getName() {
		return name;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The size of the file")
	public long getSize() {
		return size;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The syntax status of this file")
	public CheckType getSyntax() {
		return syntax;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The type of this file")
	public FileType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		final String name = getName();
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public void setFromFile(File f, CheckType syntax) {
		this.setName(f.getName());
		this.setSize(f.length());
		this.setLastModified(f.lastModified());
		this.setSyntax(syntax);
		this.setType(FileType.of(this.name));
	}

	public void setInfo(final CompileInfo info) {
		this.info = info;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setSyntax(CheckType syntax) {
		this.syntax = syntax;
	}

	public void setType(FileType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("FileInfo [info=");
		builder.append(getInfo());
		builder.append(", lastModified=");
		builder.append(getLastModified());
		builder.append(", name=");
		builder.append(getName());
		builder.append(", size=");
		builder.append(getSize());
		builder.append(", syntax=");
		builder.append(getSyntax());
		builder.append(", type=");
		builder.append(getType());
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(FileInfo o) {
		return name.compareTo(o.name);
	}
}
