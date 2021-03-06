/*******************************************************************************
 * PSHDL is a library and (trans-)compiler for PSHDL input. It generates
 *     output suitable for implementation or simulation of it.
 *
 *     Copyright (C) 2013 Karsten Becker (feedback (at) pshdl (dot) org)
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

import java.util.Set;

import javax.xml.bind.annotation.XmlEnum;

import org.pshdl.model.validation.Problem;

@XmlEnum(String.class)
public enum CheckType {
    unknown, syntax_ok, ok, warnings, errors, syntax_error;

    public static CheckType fromProblems(Set<Problem> problems) {
        CheckType res = ok;
        for (final Problem p : problems) {
            if (p.isSyntax) {
                res = syntax_error;
            } else {
                switch (p.severity) {
                case ERROR:
                    res = errors;
                    break;
                case WARNING:
                    if (res != errors) {
                        res = warnings;
                    }
                    break;
                default:
                    break;
                }
            }
        }
        return res;
    }
}
