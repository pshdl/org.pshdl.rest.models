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

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel("All information about a file")
public class FileInfo implements Comparable<FileInfo> {

	@JsonProperty
	@ApiModelProperty("If this file has been compiled or verified the information is stored here")
	public CompileInfo info;

	@JsonProperty
	@ApiModelProperty(required = true, value = "A sub specification of for example json files, which can be settings")
	public String category;

	@JsonProperty
	@ApiModelProperty(required = true, value = "Information about this file")
	public FileRecord record;

	@JsonProperty
	@ApiModelProperty(required = true, value = "The syntax status of this file")
	public CheckType syntax;

	@JsonProperty
	@ApiModelProperty(required = true, value = "The type of this file")
	public FileType type;

	@JsonProperty
	public List<ModuleInformation> moduleInfos = Lists.newArrayList();

	public FileInfo() {
	}

	public void setFromFile(File f, CheckType syntax, String wid, File relDir) throws IOException {
		this.record = new FileRecord(f, relDir, wid);
		this.syntax = syntax;
		this.type = FileType.of(f.getName());
	}

	@Override
	public int compareTo(FileInfo o) {
		return record.relPath.compareTo(o.record.relPath);
	}

	public void setInfo(CompileInfo ci) {
		info = ci;
	}
}
