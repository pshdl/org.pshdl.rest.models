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
package org.pshdl.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A container for providing feedback when interacting with tools
 *
 * @author Karsten Becker
 *
 */
public class ProgressFeedback {
	public static enum ProgressType {
		/**
		 * A new report is available that might be of interest to the user<br>
		 *
		 * {@link ProgressFeedback#progress} should be <code>null</code><br>
		 * {@link ProgressFeedback#message} contains {@link FileRecord} as JSON
		 */
		report,
		/**
		 * Some progress has occurred<br>
		 *
		 * {@link ProgressFeedback#progress} should be a number between (0..1)<br>
		 * {@link ProgressFeedback#message} should contain a human readable
		 * progress status i.e. what is done next
		 */
		progress,
		/**
		 * A tool produced some interesting/unexpected output<br>
		 *
		 * {@link ProgressFeedback#progress} should be <code>null</code><br>
		 * {@link ProgressFeedback#message} should contain the tool output
		 */
		output,
		/**
		 * The synthesis completed successfully<br>
		 *
		 * {@link ProgressFeedback#progress} should be <code>null</code><br>
		 * {@link ProgressFeedback#message} should contain the
		 * {@link FileRecord} as JSON
		 */
		done,
		/**
		 * Some information regarding the synthesis<br>
		 *
		 * {@link ProgressFeedback#progress} should be <code>null</code><br>
		 * {@link ProgressFeedback#message} should contain a human readable
		 * information
		 */
		information,
		/**
		 * A warning regarding the synthesis<br>
		 *
		 * {@link ProgressFeedback#progress} should be <code>null</code><br>
		 * {@link ProgressFeedback#message} should contain a human readable
		 * warning
		 */
		warning,
		/**
		 * An error occurred and the synthesis has been aborted<br>
		 *
		 * {@link ProgressFeedback#progress} should be <code>null</code><br>
		 * {@link ProgressFeedback#message} should contain a human readable
		 * error
		 */
		error
	}

	@JsonProperty
	public final ProgressType type;
	@JsonProperty
	public final Double progress;
	@JsonProperty
	public final long timeStamp;
	@JsonProperty
	public final String message;

	public ProgressFeedback() {
		this(null, null, 0, null);
	}

	public ProgressFeedback(ProgressType type, Double progress, long timeStamp, String message) {
		super();
		this.type = type;
		this.progress = progress;
		this.timeStamp = timeStamp;
		this.message = message;
	}
}
