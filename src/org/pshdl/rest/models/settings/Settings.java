package org.pshdl.rest.models.settings;

import com.fasterxml.jackson.annotation.*;

public class Settings {

	@JsonProperty
	public final String category;
	@JsonProperty
	public final String version;

	public Settings(String category, String version) {
		super();
		this.category = category;
		this.version = version;
	}

}
