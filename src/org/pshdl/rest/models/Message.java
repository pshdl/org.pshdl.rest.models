package org.pshdl.rest.models;

import javax.xml.bind.annotation.*;

import com.fasterxml.jackson.annotation.*;
import com.google.common.collect.*;
import com.wordnik.swagger.annotations.*;

@ApiModel("This message object is for communication inbetween workspace clients")
@XmlRootElement
public class Message<T> implements Comparable<Message<?>> {
	public static final String VENDOR = "P";
	public static final String WORKSPACE = VENDOR + ":WORKSPACE";
	public static final String DELETED = WORKSPACE + ":DELETED";
	public static final String ADDED = WORKSPACE + ":ADDED";
	public static final String UPDATED = WORKSPACE + ":UPDATED";
	public static final String CREATED_WORKSPACE = WORKSPACE + ":CREATED_WORKSPACE";
	public static final String COMPILER = VENDOR + ":COMPILER";
	public static final String VHDL = COMPILER + ":VHDL";
	public static final String PSEX = COMPILER + ":PSEX";
	public static final String C_CODE = COMPILER + ":C";
	public static final String DART_CODE = COMPILER + ":DART";
	public static final String JAVA_CODE = COMPILER + ":JAVA";
	public static final String JAVA_SCRIPT_CODE = COMPILER + ":JAVASCRIPT";

	private String subject;
	private String msgType;
	private long timeStamp;
	private T contents;

	public Message() {
	}

	public Message(String msgType, String subject, T content) {
		this.msgType = msgType;
		this.subject = subject;
		this.contents = content;
		this.setTimeStamp(System.currentTimeMillis());
	}

	@JsonProperty
	@ApiModelProperty(required = true, value = "A subject composed of the format vendor:topic:operation")
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@JsonProperty
	@ApiModelProperty(value = "The class of the information, use 'String' if no underlying type is used")
	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	@JsonProperty
	@ApiModelProperty(value = "The time when this message was created")
	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	@JsonProperty
	@ApiModelProperty(value = "The actual payload of this message")
	public T getContents() {
		return contents;
	}

	public void setContents(T contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Message [subject=");
		builder.append(getSubject());
		builder.append(", msgType=");
		builder.append(getMsgType());
		builder.append(", timeStamp=");
		builder.append(getTimeStamp());
		builder.append(", contents=");
		builder.append(getContents());
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int compareTo(Message<?> arg0) {
		return ComparisonChain.start().compare(timeStamp, arg0.timeStamp).compare(subject, arg0.subject).result();
	}

}
