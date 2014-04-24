package org.pshdl.rest.models.settings;

import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class InteractiveMap {

	@JsonProperty
	public final String svg;

	@JsonProperty
	public final Map<String, LinkedList<String>> idMaps;

	public InteractiveMap() {
		this(null, null);
	}

	public InteractiveMap(String svg, Map<String, ? extends Iterable<String>> idMaps) {
		super();
		this.svg = svg;
		if (idMaps != null) {
			this.idMaps = Maps.newHashMap();
			for (final Entry<String, ? extends Iterable<String>> e : idMaps.entrySet()) {
				this.idMaps.put(e.getKey(), Lists.newLinkedList(e.getValue()));
			}
		} else {
			this.idMaps = null;
		}
	}

}
