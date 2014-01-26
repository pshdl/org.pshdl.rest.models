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

import org.pshdl.rest.models.utils.*;

import com.fasterxml.jackson.annotation.*;
import com.google.common.hash.*;
import com.google.common.io.*;
import com.wordnik.swagger.annotations.*;

@ApiModel("Information about addtional output files")
public class FileRecord {
	@JsonProperty
	@ApiModelProperty(required = true, value = "The URL which can be used to retrieve the file")
	public String fileURI;

	@JsonProperty
	@ApiModelProperty(required = true, value = "The relative path where this file is located")
	public String relPath;

	@JsonProperty
	@ApiModelProperty(required = true, value = "The timestamp of the last modification")
	public long lastModified;

	@JsonProperty
	@ApiModelProperty(required = false, value = "The hash of the file")
	public String hash;

	public FileRecord() {
	}

	public FileRecord(File f, File relDir, String wid) {
		relPath = relDir.toURI().relativize(f.toURI()).getPath();
		lastModified = f.lastModified();
		fileURI = RestConstants.toWorkspaceURI(wid, relPath);
		HashCode hash;
		try {
			hash = Files.asByteSource(f).hash(Hashing.sha1());
			this.hash = hash.toString();
		} catch (final IOException e) {
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FileRecord other = (FileRecord) obj;
		if (fileURI == null) {
			if (other.fileURI != null)
				return false;
		} else if (!fileURI.equals(other.fileURI))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((fileURI == null) ? 0 : fileURI.hashCode());
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("FileRecord [");
		builder.append("fileURI=");
		builder.append(fileURI);
		builder.append(", relPath=");
		builder.append(relPath);
		builder.append("]");
		return builder.toString();
	}

}
