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

import javax.xml.bind.annotation.XmlEnum;

import com.google.common.io.Files;

@XmlEnum(String.class)
public enum FileType {
	pshdl("lang-pshdl", "text/plain", ".pshdl"), //
	vhdl("lang-vhdl", "text/plain", ".vhdl"), //
	verilog("lang-verilog", "text/plain", ".v"), //
	markdown("lang-text", "text/plain", ".md"), //
	c("lang-c", "text/plain", ".c"), //
	cpp("lang-cpp", "text/plain", ".cpp"), //
	html("lang-html", "text/html", ".html"), //
	cHeader("lang-cpp", "text/plain", ".h"), //
	javascript("lang-javascript", "application/javascript", ".js"), //
	json("lang-javascript", "application/javascript", ".json"), //
	dart("lang-dart", "application/dart", ".dart"), //
	unknown("lang-text", "text/plain", null);

	public static FileType of(String name) {
		final String extension = Files.getFileExtension(name);
		switch (extension) {
		case "pshdl":
			return pshdl;
		case "vhdl":
		case "vhd":
			return vhdl;
		case "v":
		case "sv":
			return verilog;
		case "md":
		case "markdown":
			return markdown;
		case "c":
			return c;
		case "cpp":
			return cpp;
		case "h":
			return cHeader;
		case "htm":
		case "html":
			return html;
		case "dart":
			return dart;
		case "json":
			return json;
		case "js":
			return javascript;
		default:
			return unknown;
		}
	}

	public final String prettify;
	public final String mime;
	public final String extension;

	FileType(String prettify, String mime, String extension) {
		this.prettify = prettify;
		this.mime = mime;
		this.extension = extension;
	}
}
