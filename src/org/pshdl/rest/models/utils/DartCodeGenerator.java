package org.pshdl.rest.models.utils;

import java.lang.reflect.*;
import java.util.*;

import org.pshdl.model.validation.Problem.ProblemSeverity;
import org.pshdl.rest.models.*;

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
	}

	private static String generateEnum(Class<?> clazz) {
		final Formatter f = new Formatter();
		final String simpleName = clazz.getSimpleName();
		f.format("class %1$s {\n", simpleName);
		final Enum<?>[] enumConstants = (Enum<?>[]) clazz.getEnumConstants();
		final StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (final Enum<?> e : enumConstants) {
			f.format("  static final %1$s %2$s=new %1$s._create(%3$d,\"%2$s\");\n", simpleName, e.name(), e.ordinal());
			if (!first) {
				sb.append(", ");
			}
			first = false;
			sb.append(e.name());
		}
		f.format("  static final List<%s> _values=[%s];\n", simpleName, sb);
		f.format("  int _id;\n"//
				+ "  String _name;\n"//
				+ "  int get ordinal=>_id;\n"//
				+ "  String get name=>_name;\n"//
				+ "  \n"//
				+ "  %1$s._create(this._id, this._name);\n"//
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

	private static String generateClass(Class<?> clazz) {
		final Method[] methods = clazz.getDeclaredMethods();
		final Formatter abstractClass = new Formatter();
		final String simpleName = clazz.getSimpleName();
		abstractClass.format("abstract class I%s {\n", simpleName);
		final Formatter implemementation = new Formatter();
		implemementation.format("class %1$s implements I%1$s {\n" + "  Map _jsonMap;\n" + "  %1$s._create(this._jsonMap);\n" + "  \n"
				+ "  factory %1$s.empty() => new %1$s._create(new HashMap());\n" + "  factory %1$s.fromJsonMap(Map map) => map==null ? null : new %1$s._create(map); \n"
				+ "  factory %1$s.fromJsonString(string) => new %1$s._create(JSON.decode(string));\n" + "\n" + "  String toString() => JSON.encode(_jsonMap);\n"
				+ "  Map toMap() => _jsonMap;\n", simpleName);
		for (final Method method : methods) {
			if (method.isAnnotationPresent(JsonProperty.class)) {
				String name = method.getName();
				if (name.startsWith("get")) {
					name = Character.toLowerCase(name.charAt(3)) + name.substring(4, name.length());
				}
				if (name.startsWith("is")) {
					name = Character.toLowerCase(name.charAt(2)) + name.substring(3, name.length());
				}
				final Class<?> returnType = method.getReturnType();
				String simpleType = returnType.getSimpleName();
				final boolean isPSHDLClass = returnType.getName().startsWith("org.pshdl");
				if (isPSHDLClass && !returnType.isEnum()) {
					simpleType = "I" + simpleType;
				}
				if (returnType.isPrimitive()) {
					simpleType = "num";
				}
				boolean isCollection = false;
				if (Iterable.class.isAssignableFrom(returnType)) {
					final ParameterizedType genericReturnType = (ParameterizedType) method.getGenericReturnType();
					final String type = genericReturnType.getActualTypeArguments()[0].toString();
					final String last = Iterators.getLast(Splitter.on('.').split(type).iterator());
					if (type.startsWith("class org.pshdl")) {
						simpleType = "List<I" + last + ">";
						implemementation.format("\n  set %3$s(%1$s newList) => _jsonMap[\"%3$s\"] = newList.map((I%2$s o)=>o.toMap());\n" + "  %1$s get %3$s {\n"
								+ "    List list=_jsonMap[\"%3$s\"];\n" + "    return list.where((o) => o!=null).map( (o) => new %2$s.fromJsonMap(o) ).toList();\n" + "  }\n\n",
								simpleType, last, name);
					} else {
						simpleType = "List<" + last + ">";
						implemementation.format("\n  set %1$s(%2$s newVal) => _jsonMap[\"%1$s\"] = newVal;\n" + "  %2$s get %1$s => _jsonMap[\"%1$s\"];\n", name, simpleType);
					}
					isCollection = true;
				} else {
					if (isPSHDLClass && !returnType.isEnum()) {
						implemementation.format(//
								"\n  set %1$s(%2$s newVal) => _jsonMap[\"%1$s\"] = newVal==null?null:newVal.toMap();\n"//
										+ "  %2$s get %1$s => new %2$s.fromJsonMap(_jsonMap[\"%1$s\"]);\n"//
								, name, returnType.getSimpleName());
					} else {
						if (returnType.isEnum()) {
							implemementation.format(//
									"\n  set %1$s(%2$s newVal) => _jsonMap[\"%1$s\"] = newVal==null?null:newVal.name;\n"//
											+ "  %2$s get %1$s => %2$s.fromString(_jsonMap[\"%1$s\"]);\n"//
									, name, simpleType);
						} else {
							implemementation.format(//
									"\n  set %1$s(%2$s newVal) => _jsonMap[\"%1$s\"]=newVal;\n"//
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
		}
		if ("FileInfo".equals(simpleName)) {
			implemementation.format("  String get name => record.relPath;\n");
			abstractClass.format("  String get name;\n");
		}
		abstractClass.format("  Map toMap();\n}\n");
		implemementation.format("\n}\n");
		final String f = abstractClass.toString() + implemementation.toString();
		abstractClass.close();
		implemementation.close();
		return f;
	}
}