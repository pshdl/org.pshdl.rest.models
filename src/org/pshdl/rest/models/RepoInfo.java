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

import javax.xml.bind.annotation.*;

import com.fasterxml.jackson.annotation.*;
import com.google.common.collect.*;
import com.wordnik.swagger.annotations.*;

@ApiModel("Information about this repository")
public class RepoInfo {

	private String eMail;

	private Set<FileInfo> files;

	private String id;

	private String name;

	private long lastValidation;

	private String jsonVersion;

	public RepoInfo() {
		files = Sets.newTreeSet();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final RepoInfo other = (RepoInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (eMail == null) {
			if (other.eMail != null)
				return false;
		} else if (!eMail.equals(other.eMail))
			return false;
		if (files == null) {
			if (other.files != null)
				return false;
		} else if (!files.equals(other.files))
			return false;
		return true;
	}

	@XmlTransient
	public String getEMail() {
		if (eMail == null)
			return "john@invalid";
		return this.eMail;
	}

	public FileInfo getFile(final String relPath) {
		for (final FileInfo fi : this.getFiles()) {
			if (fi.getRecord().getRelPath().equals(relPath))
				return fi;
		}
		return null;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "A list of all input files in this repository")
	public Set<FileInfo> getFiles() {
		return this.files;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The wid of this repository")
	public String getId() {
		return this.id;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "The timestamp of the last validation")
	public long getLastValidation() {
		return lastValidation;
	}

	@JsonProperty
	public String getJsonVersion() {
		return jsonVersion;
	}

	@XmlTransient
	public String getName() {
		if (this.name == null)
			return "John Doe";
		return this.name;
	}

	@XmlTransient
	public boolean isValidated() {
		final Set<FileInfo> allFiles = getFiles();
		for (final FileInfo fi : allFiles) {
			if (fi.getRecord().getLastModified() > lastValidation)
				return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((id == null) ? 0 : id.hashCode());
		result = (prime * result) + ((name == null) ? 0 : name.hashCode());
		result = (prime * result) + ((eMail == null) ? 0 : eMail.hashCode());
		result = (prime * result) + ((files == null) ? 0 : files.hashCode());
		return result;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	public void setFiles(Set<FileInfo> files) {
		this.files = files;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInfo(final String id, final String name, final String eMail, final FileInfo... files) {
		this.setId(id);
		if (files == null) {
			this.setFiles(new LinkedHashSet<FileInfo>());
		} else {
			this.setFiles(new LinkedHashSet<>(Arrays.asList(files)));
		}
		this.setName(name);
		this.setEMail(eMail);
	}

	public void setLastValidation(long lastValidation) {
		this.lastValidation = lastValidation;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setJsonVersion(String jsonVersion) {
		this.jsonVersion = jsonVersion;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("RepoInfo [eMail=");
		builder.append(getEMail());
		builder.append(", files=");
		builder.append(getFiles());
		builder.append(", id=");
		builder.append(getId());
		builder.append(", name=");
		builder.append(getName());
		builder.append(", version=");
		builder.append(getJsonVersion());
		builder.append("]");
		return builder.toString();
	}

}
