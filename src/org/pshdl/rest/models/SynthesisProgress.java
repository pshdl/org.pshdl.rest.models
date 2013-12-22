package org.pshdl.rest.models;

import com.fasterxml.jackson.annotation.*;

public class SynthesisProgress {
	public static enum ProgressType {
		report, progress, output, done, information, warning, error
	}

	@JsonProperty
	public final ProgressType type;
	@JsonProperty
	public final Double progress;
	@JsonProperty
	public final long timeStamp;
	@JsonProperty
	public final String message;

	public SynthesisProgress() {
		this(null, null, 0, null);
	}

	public SynthesisProgress(ProgressType type, Double progress, long timeStamp, String message) {
		super();
		this.type = type;
		this.progress = progress;
		this.timeStamp = timeStamp;
		this.message = message;
	}
}
