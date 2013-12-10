package org.pshdl.rest.models.settings;

import java.math.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.*;

import com.fasterxml.jackson.annotation.*;
import com.google.common.collect.*;

public class BoardSpecSettings extends Settings {
	public static class FPGASpec {
		@JsonProperty
		public final String vendor;
		@JsonProperty
		public final String family;
		@JsonProperty
		public final String partNumber;

		public FPGASpec() {
			this(null, null, null);
		}

		public FPGASpec(String vendor, String family, String partNumber) {
			super();
			this.vendor = vendor;
			this.family = family;
			this.partNumber = partNumber;
		}
	}

	public static class PinSpecGroup {
		@JsonProperty
		public final String name;
		@JsonProperty
		public final String description;
		@JsonProperty
		public final List<PinSpec> pins;

		public PinSpecGroup(String name, String description, PinSpec... pins) {
			super();
			this.name = name;
			this.description = description;
			this.pins = Lists.newArrayList(pins);
		}
	}

	public static class PinSpec {
		public static class TimeSpec {
			@JsonProperty
			public final String time;
			@JsonProperty
			public final String unit;

			public TimeSpec(BigDecimal time, TimeUnit unit) {
				this(time.toPlainString(), asAbbr(unit));
			}

			private static String asAbbr(TimeUnit unit) {
				switch (unit) {
				case DAYS:
					return "d";
				case HOURS:
					return "h";
				case MICROSECONDS:
					return "us";
				case MILLISECONDS:
					return "ms";
				case MINUTES:
					return "m";
				case NANOSECONDS:
					return "ns";
				case SECONDS:
					return "s";
				}
				throw new IllegalArgumentException();
			}

			public TimeSpec(String time, String unit) {
				this.time = time;
				this.unit = unit;
			}

			public TimeSpec() {
				this((String) null, null);
			}
		}

		public static enum Polarity {
			active_low, active_high;
		}

		@JsonProperty
		public final String description;
		@JsonProperty
		public final String portName;
		@JsonProperty
		public final String pinLocation;
		@JsonProperty
		public final Map<String, String> attributes;
		@JsonProperty
		public final TimeSpec timeSpec;
		@JsonProperty
		public String assignedSignal;
		@JsonProperty
		public final Polarity polarity;

		public final static String NO_VALUE = "#NO_VALUE";

		public PinSpec() {
			this(null, null, null, Maps.<String, String> newHashMap(), null, null);
		}

		public PinSpec(String portName, String pinLocation, String description, Map<String, String> attributes, TimeSpec timeSpec, Polarity polarity) {
			super();
			this.description = description;
			this.portName = portName;
			this.attributes = attributes;
			this.timeSpec = timeSpec;
			this.pinLocation = pinLocation;
			this.polarity = polarity;
		}
	}

	@JsonProperty
	public final String boardName;
	@JsonProperty
	public final String description;
	@JsonProperty
	public final FPGASpec fpga;
	@JsonProperty
	public final List<PinSpecGroup> pinGroups;

	public final static String BOARD_CATEGORY = "Board";
	public final static String VERSION = "0.1";

	public BoardSpecSettings() {
		this(null, null, null);
	}

	public BoardSpecSettings(String boardName, String description, FPGASpec fpga, PinSpecGroup... pins) {
		super(BOARD_CATEGORY, VERSION);
		this.boardName = boardName;
		this.description = description;
		this.fpga = fpga;
		this.pinGroups = Lists.newArrayList(pins);
	}

	public String toUCF(String clockName, String rstName) {
		final Formatter f = new Formatter();
		for (final PinSpecGroup pg : pinGroups) {
			for (final PinSpec ps : pg.pins) {
				if (ps.assignedSignal != null) {
					if (ps.assignedSignal.equals("$clk") && (clockName != null)) {
						ps.assignedSignal = clockName;
						writeSpec(f, ps);
					} else if (ps.assignedSignal.equals("$rst") && (rstName != null)) {
						ps.assignedSignal = rstName;
						writeSpec(f, ps);
					} else {
						writeSpec(f, ps);
					}
				}
			}
		}
		final String string = f.toString();
		return string;
	}

	private void writeSpec(Formatter f, PinSpec ps) {
		f.format("# %s\n", ps.portName);
		f.format("NET \"%s\" LOC=%s ", ps.assignedSignal, ps.pinLocation);
		for (final Entry<String, String> e : ps.attributes.entrySet()) {
			if (PinSpec.NO_VALUE.equals(e.getValue())) {
				f.format("| %s ", e.getKey());
			} else {
				f.format("| %s = %s ", e.getKey(), e.getValue());
			}
		}
		if (ps.timeSpec != null) {
			f.format("| TNM_NET = \"%s\"", ps.assignedSignal);
		}
		f.format(";\n");
		if (ps.timeSpec != null) {
			f.format("TIMESPEC \"ts_%s\" = PERIOD \"%<s\" %s %s;\n", ps.assignedSignal, ps.timeSpec.time, ps.timeSpec.unit);
		}
	}

}
