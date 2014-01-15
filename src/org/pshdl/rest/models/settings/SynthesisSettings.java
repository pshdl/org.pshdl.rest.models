/*******************************************************************************
 * PSHDL is a library and (trans-)compiler for PSHDL input. It generates
 *     output suitable for implementation or simulation of it.
 *     
 *     Copyright (C) 2014 Karsten Becker (feedback (at) pshdl (dot) org)
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
package org.pshdl.rest.models.settings;

import java.util.*;

import org.pshdl.rest.models.settings.BoardSpecSettings.PinSpec;

import com.fasterxml.jackson.annotation.*;

public class SynthesisSettings extends Settings {
	public static final String VERSION = "0.1";
	public static final String SYNTHESIS = "Synthesis";
	@JsonProperty
	public final BoardSpecSettings board;
	@JsonProperty
	public final String topModule;
	@JsonProperty
	public final List<PinSpec> overrides;

	public SynthesisSettings() {
		this(null, null, null);
	}

	public SynthesisSettings(BoardSpecSettings board, String topModule, List<PinSpec> overrides) {
		super(SYNTHESIS, VERSION);
		this.board = board;
		this.topModule = topModule;
		this.overrides = overrides;
	}
}
