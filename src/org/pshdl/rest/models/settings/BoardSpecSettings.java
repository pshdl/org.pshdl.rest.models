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
package org.pshdl.rest.models.settings;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.pshdl.model.HDLVariableDeclaration.HDLDirection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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

        public PinSpecGroup() {
            this(null, null);
        }

        public PinSpecGroup(String name, String description, PinSpec... pins) {
            super();
            this.name = name;
            this.description = description;
            this.pins = Lists.newArrayList(pins);
        }
    }

    public static class PinSpec {
        public static final String IOSTANDARD = "IOSTANDARD";
        public final static String NO_VALUE = "#NO_VALUE";
        public static final String PULL_UP = "UP";
        public static final String PULL_DOWN = "DOWN";
        public static final String PULL = "PULL";
        public static final String INVERT = "INVERT";

        public static final String SIG_NONE = "#NONE";
        public static final String SIG_OPEN = "#OPEN";
        public static final String SIG_ALLONE = "#ALLONE";
        public static final String SIG_ALLZERO = "#ALLZERO";

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
        @JsonProperty
        public final HDLDirection direction;

        public PinSpec() {
            this(null, null, null, Maps.<String, String> newLinkedHashMap(), null, null, null);
        }

        public PinSpec(String portName, String pinLocation, String description, Map<String, String> attributes, TimeSpec timeSpec, Polarity polarity, HDLDirection direction) {
            super();
            this.description = description;
            this.portName = portName;
            this.attributes = attributes;
            this.timeSpec = timeSpec;
            this.pinLocation = pinLocation;
            this.polarity = polarity;
            this.direction = direction;
        }

        @Override
        public String toString() {
            return "PinSpec [ portName=" + portName + ", pinLocation=" + pinLocation + ", assignedSignal=" + assignedSignal + ", attributes=" + attributes + ", timeSpec="
                    + timeSpec + ", polarity=" + polarity + ", direction=" + direction + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = (prime * result) + ((pinLocation == null) ? 0 : pinLocation.hashCode());
            result = (prime * result) + ((portName == null) ? 0 : portName.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final PinSpec other = (PinSpec) obj;
            if (pinLocation == null) {
                if (other.pinLocation != null) {
                    return false;
                }
            } else if (!pinLocation.equals(other.pinLocation)) {
                return false;
            }
            if (portName == null) {
                if (other.portName != null) {
                    return false;
                }
            } else if (!portName.equals(other.portName)) {
                return false;
            }
            return true;
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
    @JsonProperty
    public final InteractiveMap map;

    public final static String BOARD_CATEGORY = "Board";
    public final static String VERSION = "0.1";

    public BoardSpecSettings() {
        this(null, null, null, null);
    }

    public BoardSpecSettings(String boardName, String description, FPGASpec fpga, InteractiveMap map, PinSpecGroup... pins) {
        super(BOARD_CATEGORY, VERSION);
        this.boardName = boardName;
        this.description = description;
        this.fpga = fpga;
        this.pinGroups = Lists.newArrayList(pins);
        this.map = map;
    }
}
