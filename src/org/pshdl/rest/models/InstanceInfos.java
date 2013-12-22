package org.pshdl.rest.models;

import java.util.*;

import com.fasterxml.jackson.annotation.*;

public class InstanceInfos {
	public static class FileInstances {
		@JsonProperty
		public final String fileName;
		@JsonProperty
		public final List<ModuleInformation> infos;

		public FileInstances() {
			this(null, null);
		}

		public FileInstances(String fileName, List<ModuleInformation> infos) {
			super();
			this.fileName = fileName;
			this.infos = infos;
		}

	}

	@JsonProperty
	public final List<FileInstances> infos;

	public InstanceInfos() {
		this(null);
	}

	public InstanceInfos(List<FileInstances> infos) {
		super();
		this.infos = infos;
	}

}
