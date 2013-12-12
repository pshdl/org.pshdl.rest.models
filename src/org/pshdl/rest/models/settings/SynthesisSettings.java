package org.pshdl.rest.models.settings;

import com.fasterxml.jackson.annotation.*;

public class SynthesisSettings extends Settings {
	public static final String VERSION = "0.1";
	public static final String SYNTHESIS = "Synthesis";
	@JsonProperty
	public final BoardSpecSettings board;
	@JsonProperty
	public final String topModule;

	public SynthesisSettings(BoardSpecSettings board, String topModule) {
		super(SYNTHESIS, VERSION);
		this.board = board;
		this.topModule = topModule;
	}
}
