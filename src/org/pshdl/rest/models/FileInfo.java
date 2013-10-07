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

	private FileRecord record;

	private CheckType syntax;

	private FileType type;

	public FileInfo() {
	}

	@JsonProperty
	@ApiModelProperty("If this file has been compiled or verified the information is stored here")
	public CompileInfo getInfo() {
		return this.info;
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

	public void setFromFile(File f, CheckType syntax, String wid, File relDir) {
		this.record = new FileRecord(f, relDir, wid);
		this.setSyntax(syntax);
		this.setType(FileType.of(f.getName()));
	}

	public void setInfo(final CompileInfo info) {
		this.info = info;
	}

	public void setSyntax(CheckType syntax) {
		this.syntax = syntax;
	}

	public void setType(FileType type) {
		this.type = type;
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "Information about this file")
	public FileRecord getRecord() {
		return record;
	}

	public void setRecord(FileRecord record) {
		this.record = record;
	}

	@Override
	public int compareTo(FileInfo o) {
		return record.getFile().compareTo(o.getRecord().getFile());
	}
}
