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

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ComparisonChain;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel("This message object is for communication inbetween workspace clients")
@XmlRootElement
public class Message<T> implements Comparable<Message<?>> {
	public static final String VENDOR = "P";
	public static final String WORKSPACE = VENDOR + ":WORKSPACE";
	/**
	 * The msgType is the {@link FileInfo} that was removed
	 */
	public static final String WORK_DELETED = WORKSPACE + ":DELETED";
	/**
	 * The msgType is an array of {@link FileInfo} that was added
	 */
	public static final String WORK_ADDED = WORKSPACE + ":ADDED";
	/**
	 * The msgType is an array of {@link FileInfo} that was updated
	 */
	public static final String WORK_UPDATED = WORKSPACE + ":UPDATED";
	/**
	 * The msgTyps is the {@link RepoInfo} of the newly created workspace
	 */
	public static final String WORK_CREATED_WORKSPACE = WORKSPACE + ":CREATED_WORKSPACE";
	public static final String COMPILER = VENDOR + ":COMPILER";
	public static final String COMP_VHDL = COMPILER + ":VHDL";
	public static final String COMP_PSEX = COMPILER + ":PSEX";
	public static final String COMP_C_CODE = COMPILER + ":C";
	public static final String COMP_DART_CODE = COMPILER + ":DART";
	public static final String COMP_JAVA_CODE = COMPILER + ":JAVA";
	public static final String COMP_JAVA_SCRIPT_CODE = COMPILER + ":JAVASCRIPT";
	public static final String COMP_SYNTHESIS = COMPILER + ":SYNTHESIS";
	public static final String SERVICE = VENDOR + ":SERVICE";
	public static final String SERVICE_DISCOVER = SERVICE + ":DISCOVER";
	public static final String SYNTHESIS = VENDOR + ":SYNTHESIS";
	public static final String SYNTHESIS_RUN = SYNTHESIS + ":RUN";
	public static final String SYNTHESIS_PROGRESS = SYNTHESIS + ":PROGRESS";
	public static final String SYNTHESIS_AVAILABLE = SYNTHESIS + ":AVAILABLE";
	public static final String BOARD = VENDOR + ":BOARD";
	public static final String BOARD_AVAILABLE = BOARD + ":AVAILABLE";
	public static final String BOARD_CONFIGURE = BOARD + ":CONFIGURE";
	public static final String BOARD_PROGRESS = BOARD + ":PROGRESS";
	public static final String CLIENT = VENDOR + ":CLIENT";
	public static final String CLIENT_CONNECTED = CLIENT + ":CONNECTED";
	public static final String CLIENT_DISCONNECTED = CLIENT + ":DISCONNECTED";

	@JsonProperty
	@ApiModelProperty(required = true, value = "A subject composed of the format vendor:topic:operation")
	public final String subject;
	@JsonProperty
	@ApiModelProperty(value = "The clientID that send this request. Events from server side have no clientID")
	public final String clientID;
	@JsonProperty
	@ApiModelProperty(value = "The class of the information, use 'String' if no underlying type is used")
	public final String msgType;
	@JsonProperty
	@ApiModelProperty(value = "The time when this message was created")
	public final long timeStamp;
	@JsonProperty
	@ApiModelProperty(value = "The actual payload of this message")
	public final T contents;

	public Message() {
		this(null, null, null, null);
	}

	public Message(String msgType, String subject, T content, String clientID) {
		this.msgType = msgType;
		this.subject = subject;
		this.contents = content;
		this.timeStamp = System.currentTimeMillis();
		this.clientID = clientID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((clientID == null) ? 0 : clientID.hashCode());
		result = (prime * result) + ((contents == null) ? 0 : contents.hashCode());
		result = (prime * result) + ((msgType == null) ? 0 : msgType.hashCode());
		result = (prime * result) + ((subject == null) ? 0 : subject.hashCode());
		result = (prime * result) + (int) (timeStamp ^ (timeStamp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Message<?> other = (Message<?>) obj;
		if (clientID == null) {
			if (other.clientID != null)
				return false;
		} else if (!clientID.equals(other.clientID))
			return false;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		if (msgType == null) {
			if (other.msgType != null)
				return false;
		} else if (!msgType.equals(other.msgType))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (timeStamp != other.timeStamp)
			return false;
		return true;
	}

	@Override
	public int compareTo(Message<?> arg0) {
		return ComparisonChain.start().compare(timeStamp, arg0.timeStamp).compare(subject, arg0.subject).result();
	}

}
