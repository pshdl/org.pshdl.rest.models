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

import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.pshdl.rest.models.settings.BoardSpecSettings.PinSpec;
import org.pshdl.rest.models.settings.BoardSpecSettings.PinSpecGroup;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;

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
		pinSpec.removeAll(overrides);
		pinSpec.addAll(overrides);
		for (final PinSpec ps : pinSpec) {
			if (ps.assignedSignal != null) {
				switch (ps.assignedSignal) {
				case "$clk":
					if (clockName != null) {
						ps.assignedSignal = clockName;
						writer.append(f, ps);
					}
					break;
				case "$rst":
					if (rstName != null) {
						ps.assignedSignal = rstName;
						writer.append(f, ps);
					}
					break;
				default:
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
			f.format("# %s%n", ps.portName);
			final String assignedSignal = ps.assignedSignal.replace('{', '[').replace('}', ']');
			f.format("set_io {%s} -pinname %s ", assignedSignal, ps.pinLocation);
			if (ps.attributes != null) {
				for (final Entry<String, String> e : ps.attributes.entrySet()) {
					switch (e.getKey()) {
					case PinSpec.INVERT:
						break;
					case PinSpec.PULL:
						if (PinSpec.PULL_DOWN.equals(e.getValue())) {
							f.format("-RES_PULL Down ");
						}
						if (PinSpec.PULL_UP.equals(e.getValue())) {
							f.format("-RES_PULL Up ");
						}
						break;
					case PinSpec.IOSTANDARD:
						f.format("-iostd %s ", e.getValue());
						break;
					default:
						if (PinSpec.NO_VALUE.equals(e.getValue())) {
							f.format("-%s ", e.getKey());
						} else {
							f.format("-%s %s ", e.getKey(), e.getValue());
						}
					}
				}
			}
			f.format("%n");
		}

	}

	public static class UCFWriter implements IOutputWriter {

		@Override
		public void append(Formatter f, PinSpec ps) {
			if (ps.pinLocation.charAt(0) == '#') {
				f.format("# Not mapping %s%n", ps.assignedSignal);
				return;
			}
			f.format("# %s -> %s%n", ps.portName, ps.assignedSignal);
			final String assignedSignal = ps.assignedSignal.replace('{', '[').replace('}', ']');
			f.format("NET \"%s\" LOC=%s ", assignedSignal, ps.pinLocation);
			if (ps.attributes != null) {
				for (final Entry<String, String> e : ps.attributes.entrySet()) {
					switch (e.getKey()) {
					case PinSpec.INVERT:
						break;
					case PinSpec.PULL:
						if (PinSpec.PULL_DOWN.equals(e.getValue())) {
							f.format("| PULLDOWN ");
						}
						if (PinSpec.PULL_UP.equals(e.getValue())) {
							f.format("| PULLUP ");
						}
						break;
					default:
						if (PinSpec.NO_VALUE.equals(e.getValue())) {
							f.format("| %s ", e.getKey());
						} else {
							f.format("| %s = %s ", e.getKey(), e.getValue());
						}
					}
				}
			}
			if (ps.timeSpec != null) {
				f.format("| TNM_NET = \"%s\"", assignedSignal);
			}
			f.format(";%n");
			if (ps.timeSpec != null) {
				f.format("TIMESPEC \"ts_%s\" = PERIOD \"%<s\" %s %s;%n", assignedSignal, ps.timeSpec.time, ps.timeSpec.unit);
			}
		}
	}

}
