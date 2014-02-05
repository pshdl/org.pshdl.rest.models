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
import java.util.Map.Entry;

import org.pshdl.rest.models.settings.BoardSpecSettings.PinSpec;
import org.pshdl.rest.models.settings.BoardSpecSettings.PinSpecGroup;

import com.fasterxml.jackson.annotation.*;
import com.google.common.collect.*;

public class SynthesisSettings extends Settings {
	public static final String VERSION = "0.1";
	public static final String SYNTHESIS = "Synthesis";
	@JsonProperty
	public final String board;
	@JsonProperty
	public final String topModule;
	@JsonProperty
	public final List<PinSpec> overrides;
	@JsonProperty
	public final Map<String, String> overrideParameters;

	public SynthesisSettings() {
		this(null, null, null, null);
	}

	public SynthesisSettings(String board, String topModule, List<PinSpec> overrides, Map<String, String> overrideParameters) {
		super(SYNTHESIS, VERSION);
		this.board = board;
		this.topModule = topModule;
		this.overrides = overrides;
		this.overrideParameters = overrideParameters;
	}

	public static interface IOutputWriter {
		public void append(Formatter f, PinSpec ps);
	}

	public String toString(String clockName, String rstName, BoardSpecSettings board, IOutputWriter writer) {
		final Formatter f = new Formatter();
		final Set<PinSpec> pinSpec = Sets.newLinkedHashSet();
		for (final PinSpecGroup pg : board.pinGroups) {
			pinSpec.addAll(pg.pins);
		}
		pinSpec.addAll(overrides);
		for (final PinSpec ps : pinSpec) {
			if (ps.assignedSignal != null) {
				if (ps.assignedSignal.equals("$clk") && (clockName != null)) {
					ps.assignedSignal = clockName;
					writer.append(f, ps);
				} else if (ps.assignedSignal.equals("$rst") && (rstName != null)) {
					ps.assignedSignal = rstName;
					writer.append(f, ps);
				} else {
					writer.append(f, ps);
				}
			}
		}
		final String string = f.toString();
		return string;
	}

	public static class PDCWriter implements IOutputWriter {

		@Override
		public void append(Formatter f, PinSpec ps) {
			f.format("# %s\n", ps.portName);
			f.format("set_io {%s} -pinname %s ", ps.assignedSignal, ps.pinLocation);
			for (final Entry<String, String> e : ps.attributes.entrySet()) {
				if (PinSpec.PULL.equals(e.getKey())) {
					if (PinSpec.PULL_DOWN.equals(e.getValue())) {
						f.format("-RES_PULL Down");
					}
					if (PinSpec.PULL_UP.equals(e.getValue())) {
						f.format("-RES_PULL Up");
					}
				}
				if (PinSpec.IOSTANDARD.equals(e.getKey())) {
					f.format("-iostd %s ", e.getValue());
				} else if (PinSpec.NO_VALUE.equals(e.getValue())) {
					f.format("-%s ", e.getKey());
				} else {
					f.format("-%s %s ", e.getKey(), e.getValue());
				}
			}
			f.format("\n");
		}

	}

	public static class UCFWriter implements IOutputWriter {

		@Override
		public void append(Formatter f, PinSpec ps) {
			f.format("# %s\n", ps.portName);
			f.format("NET \"%s\" LOC=%s ", ps.assignedSignal, ps.pinLocation);
			for (final Entry<String, String> e : ps.attributes.entrySet()) {
				if (PinSpec.PULL.equals(e.getKey())) {
					if (PinSpec.PULL_DOWN.equals(e.getValue())) {
						f.format("| PULLDOWN ");
					}
					if (PinSpec.PULL_UP.equals(e.getValue())) {
						f.format("| PULLUP ");
					}
				} else if (PinSpec.NO_VALUE.equals(e.getValue())) {
					f.format("| %s ", e.getKey());
				} else {
					f.format("| %s = %s ", e.getKey(), e.getValue());
				}
			}
			if (ps.timeSpec != null) {
				f.format("| TNM_NET = \"%s\"", ps.assignedSignal);
			}
			f.format(";\n");
			if (ps.timeSpec != null) {
				f.format("TIMESPEC \"ts_%s\" = PERIOD \"%<s\" %s %s;\n", ps.assignedSignal, ps.timeSpec.time, ps.timeSpec.unit);
			}
		}

	}

}
