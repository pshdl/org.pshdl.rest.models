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

import javax.xml.bind.annotation.*;

@XmlEnum(String.class)
public enum FileType {
	pshdl("lang-pshdl", "text/plain"), //
	vhdl("lang-vhdl", "text/plain"), //
	markdown("lang-text", "text/plain"), //
	cpp("lang-cpp", "text/plain"), //
	html("lang-html", "text/html"), //
	cHeader("lang-cpp", "text/plain"), //
	javascript("lang-javascript", "application/javascript"), //
	dart("lang-dart", "application/dart"), //
	unknown("lang-text", "text/plain");

	public static FileType of(String name) {
		final String extension = name.substring(name.lastIndexOf('.') + 1, name.length());
		if ("pshdl".equals(extension))
			return pshdl;
		if ("vhd".equals(extension))
			return vhdl;
		if ("vhdl".equals(extension))
			return vhdl;
		if ("md".equals(extension))
			return markdown;
		if ("markdown".equals(extension))
			return markdown;
		if ("c".equals(extension))
			return cpp;
		if ("cpp".equals(extension))
			return cpp;
		if ("h".equals(extension))
			return cHeader;
		if ("htm".equals(extension))
			return html;
		if ("html".equals(extension))
			return html;
		if ("dart".equals(extension))
			return dart;
		if ("js".equals(extension))
			return javascript;
		return unknown;
	}

	public final String prettify;
	public final String mime;

	FileType(String prettify, String mime) {
		this.prettify = prettify;
		this.mime = mime;
	}
}
