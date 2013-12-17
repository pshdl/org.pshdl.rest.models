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

	public SynthesisSettings(BoardSpecSettings board, String topModule, List<PinSpec> overrides) {
		super(SYNTHESIS, VERSION);
		this.board = board;
		this.topModule = topModule;
		this.overrides = overrides;
	}
}
