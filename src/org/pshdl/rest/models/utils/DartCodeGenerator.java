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
package org.pshdl.rest.models.utils;

import java.lang.reflect.*;
import java.util.*;

import org.pshdl.model.HDLPrimitive.HDLPrimitiveType;
import org.pshdl.model.HDLVariableDeclaration.HDLDirection;
import org.pshdl.model.validation.Problem.ProblemSeverity;
import org.pshdl.rest.models.*;
import org.pshdl.rest.models.InstanceInfos.FileInstances;
import org.pshdl.rest.models.ModuleInformation.Port;
import org.pshdl.rest.models.ModuleInformation.UnitType;
import org.pshdl.rest.models.ProgressFeedback.ProgressType;
import org.pshdl.rest.models.settings.*;
import org.pshdl.rest.models.settings.BoardSpecSettings.FPGASpec;
import org.pshdl.rest.models.settings.BoardSpecSettings.PinSpec;
import org.pshdl.rest.models.settings.BoardSpecSettings.PinSpec.Polarity;
import org.pshdl.rest.models.settings.BoardSpecSettings.PinSpec.TimeSpec;
import org.pshdl.rest.models.settings.BoardSpecSettings.PinSpecGroup;

import com.fasterxml.jackson.annotation.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public class DartCodeGenerator {

	public enum CodeTarget {
		VHDL, JAVA, C, DART, JAVA_SCRIPT, PSEX
	}

	public static void main(String[] args) {
		System.out.println(generateClass(RepoInfo.class));
		System.out.println(generateClass(AdviseInfo.class));
		System.out.println(generateClass(CompileInfo.class));
		System.out.println(generateClass(FileInfo.class));
		System.out.println(generateClass(FileRecord.class));
		System.out.println(generateClass(LocationInfo.class));
		System.out.println(generateClass(Message.class));
		System.out.println(generateClass(ProblemInfo.class));
		System.out.println(generateEnum(FileType.class));
		System.out.println(generateEnum(CheckType.class));
		System.out.println(generateEnum(ProblemSeverity.class));
		System.out.println(generateEnum(CodeTarget.class));

		System.out.println(generateClass(BoardSpecSettings.class));
		System.out.println(generateClass(InteractiveMap.class));
		System.out.println(generateClass(FPGASpec.class));
		System.out.println(generateClass(PinSpecGroup.class));
		System.out.println(generateClass(PinSpec.class));
		System.out.println(generateClass(TimeSpec.class));
		System.out.println(generateEnum(Polarity.class));

		System.out.println(generateClass(SynthesisSettings.class));

		System.out.println(generateClass(InstanceInfos.class));
		System.out.println(generateClass(FileInstances.class));
		System.out.println(generateClass(ModuleInformation.class));
		System.out.println(generateClass(Port.class));
		System.out.println(generateEnum(UnitType.class));
		System.out.println(generateEnum(HDLDirection.class));
		System.out.println(generateEnum(HDLPrimitiveType.class));

		System.out.println(generateClass(ProgressFeedback.class));
		System.out.println(generateEnum(ProgressType.class));
	}

	public static String generateEnum(Class<?> clazz) {
		final Formatter f = new Formatter();
		final String simpleName = clazz.getSimpleName();
		f.format("class %1$s {\n", simpleName);
		final Enum<?>[] enumConstants = (Enum<?>[]) clazz.getEnumConstants();
		final StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (final Enum<?> e : enumConstants) {
			f.format("  static const %1$s %2$s=const %1$s._create(%3$d,\"%2$s\");\n", simpleName, e.name(), e.ordinal());
			if (!first) {
				sb.append(", ");
			}
			first = false;
			sb.append(e.name());
		}
		f.format("  static final List<%s> _values=[%s];\n", simpleName, sb);
		f.format("  final int _id;\n"//
				+ "  final String _name;\n"//
				+ "  @reflectable\n"//
				+ "  int get ordinal=>_id;\n"//
				+ "  @reflectable\n"//
				+ "  String get name=>_name;\n"//
				+ "  \n"//
				+ "  const %1$s._create(this._id, this._name);\n"//
				+ "  \n"//
				+ "  String toString()=>_name;\n"//
				+ "  \n"//
				+ "  static %1$s fromString(String name){\n"//
				+ "    if (name==null) return null;\n"//
				+ "    return _values.firstWhere((%1$s ct)=>ct.name==name);\n"//
				+ "  }\n"//
				+ "}", simpleName);
		final String res = f.toString();
		f.close();
		return res;
	}

	public static String generateClass(Class<?> clazz) {
		try {
			clazz.newInstance();
		} catch (final Exception e) {
			System.err.println("DartCodeGenerator.generateClass() Class:" + clazz.getSimpleName() + " does not have a default constructor!");
		}
		final String simpleName = clazz.getSimpleName();
		System.err.println("DartCodeGenerator.generateClass() Class:" + simpleName);
		final Method[] methods = clazz.getDeclaredMethods();
		final Formatter abstractClass = new Formatter();
		abstractClass.format("abstract class I%s {\n", simpleName);
		final Formatter implemementation = new Formatter();
		implemementation.format("class %1$s extends I%1$s {\n" //
				+ "  Map _jsonMap;\n"//
				+ "  %1$s._create(this._jsonMap);\n\n"//
				+ "  factory %1$s.empty() => new %1$s._create({});\n" //
				+ "  factory %1$s.fromJson(Map map) => map==null ? null : new %1$s._create(map); \n" //
				+ "  factory %1$s.fromJsonString(string) => new %1$s._create(JSON.decode(string));\n\n" //
				+ "  String toString() => JSON.encode(_jsonMap);\n"//
				+ "  Map toJson() => _jsonMap;\n", simpleName);
		for (final Method method : methods) {
			System.err.println("DartCodeGenerator.generateClass() Method:" + method.getName());
			if (method.isAnnotationPresent(JsonProperty.class)) {
				String name = method.getName();
				if (name.startsWith("get")) {
					name = Character.toLowerCase(name.charAt(3)) + name.substring(4, name.length());
				}
				if (name.startsWith("is")) {
					name = Character.toLowerCase(name.charAt(2)) + name.substring(3, name.length());
				}
				final Class<?> returnType = method.getReturnType();
				format(abstractClass, implemementation, name, returnType, method.getGenericReturnType());
			}
		}
		for (final Field f : clazz.getFields()) {
			System.err.println("DartCodeGenerator.generateClass() Field:" + f.getName());
			final Class<?> type = f.getType();
			if (f.isAnnotationPresent(JsonProperty.class)) {
				format(abstractClass, implemementation, f.getName(), type, f.getGenericType());
			}
			final int mod = f.getModifiers();
			if (Modifier.isPublic(mod) && Modifier.isFinal(mod) && Modifier.isStatic(mod)) {
				if (type.isPrimitive() || String.class.equals(type)) {
					final String simpleType = getSimpleType(type);
					try {
						final Object object = f.get(null);
						if (object instanceof String) {
							abstractClass.format("  static const %s %s='%s';\n", simpleType, f.getName(), object);
						} else {
							abstractClass.format("  static const %s %s=%s;\n", simpleType, f.getName(), object.toString());
						}
					} catch (final IllegalArgumentException e) {
						e.printStackTrace();
					} catch (final IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		abstractClass.format("  Map toJson();\n}\n");
		implemementation.format("\n}\n");
		final String f = abstractClass.toString() + implemementation.toString();
		abstractClass.close();
		implemementation.close();
		return f;
	}

	private static void format(final Formatter abstractClass, final Formatter implemementation, String name, final Class<?> returnType, Type generic) {
		final boolean isPSHDLClass = returnType.getName().contains("pshdl");
		String simpleType = getSimpleType(returnType);
		if (isPSHDLClass && !returnType.isEnum()) {
			simpleType = "I" + simpleType;
		}
		boolean isCollection = false;
		if (returnType.isArray()) {
			isCollection = true;
			simpleType = formatCollection(implemementation, name, returnType.getComponentType());
		} else if (Iterable.class.isAssignableFrom(returnType)) {
			final ParameterizedType genericReturnType = (ParameterizedType) generic;
			final Type genericType = genericReturnType.getActualTypeArguments()[0];
			simpleType = formatCollection(implemementation, name, genericType);
			isCollection = true;
		} else {
			if (isPSHDLClass && !returnType.isEnum()) {
				implemementation.format("\n  set %1$s(%2$s newVal) => _jsonMap[\"%1$s\"] = newVal==null?null:newVal.toJson();\n"//
						+ "  @reflectable\n" //
						+ "  %2$s get %1$s => new %2$s.fromJson(_jsonMap[\"%1$s\"]);\n"//
				, name, returnType.getSimpleName());
			} else {
				if (returnType.isEnum()) {
					implemementation.format("\n  set %1$s(%2$s newVal) => _jsonMap[\"%1$s\"] = newVal==null?null:newVal.name;\n"//
							+ "  @reflectable\n" //
							+ "  %2$s get %1$s => %2$s.fromString(_jsonMap[\"%1$s\"]);\n"//
					, name, simpleType);
				} else {
					implemementation.format("\n  set %1$s(%2$s newVal) => _jsonMap[\"%1$s\"]=newVal;\n"//
							+ "  @reflectable\n" //
							+ "  %2$s get %1$s => _jsonMap[\"%1$s\"];\n"//
					, name, simpleType);
				}
			}
		}
		abstractClass.format("  %s %s", simpleType, name);
		if (isCollection) {
			abstractClass.format("=[]");
		}
		abstractClass.format(";\n");
	}

	public static String formatCollection(final Formatter implemementation, String name, final Type genericType) {
		String simpleType;
		final String type = genericType.toString();
		String last = Iterators.getLast(Splitter.on('.').split(type).iterator());
		last = Iterators.getLast(Splitter.on('$').split(last).iterator());
		if (type.contains("pshdl")) {
			simpleType = "Iterable<I" + last + ">";
			implemementation.format("\n  set %3$s(%1$s newList) => _jsonMap[\"%3$s\"] = newList.map((I%2$s o)=>o.toJson()).toList();\n"//
					+ "  @reflectable\n"//
					+ "  %1$s get %3$s {\n"//
					+ "    List list=_jsonMap[\"%3$s\"];\n"//
					+ "    if (list==null) return [];\n"//
					+ "    return list.where((o) => o!=null).map( (o) => new %2$s.fromJson(o) );\n" //
					+ "  }\n\n",//
					simpleType, last, name);
		} else {
			if (genericType.equals(Integer.class)) {
				last = "int";
			}
			simpleType = "List<" + last + ">";
			implemementation.format("\n  set %1$s(%2$s newVal) => _jsonMap[\"%1$s\"] = newVal;\n" + "  %2$s get %1$s => _jsonMap[\"%1$s\"];\n", name, simpleType);
		}
		return simpleType;
	}

	public static String getSimpleType(final Class<?> returnType) {
		if (returnType.isPrimitive() || returnType.equals(Integer.class) || returnType.equals(Double.class) || returnType.equals(Long.class) || returnType.equals(Float.class))
			return "num";
		return returnType.getSimpleName();
	}
}
