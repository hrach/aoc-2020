#!/usr/bin/env kotlin

import java.io.File

val input = File("./inputs/04.txt").readText().trim()
val passports = input.split("\n\n").map { it.trim() }

run {
	val regexp = """((?:byr|iyr|eyr|hgt|hcl|ecl|pid|cid)):[^\s]+""".toRegex()
	val result = passports.filter { passport ->
		val matched = regexp.findAll(passport)
		val params = matched.map { it.groupValues[1] }.toSet()
		params.size == 8 || (params.size == 7 && !params.contains("cid"))
	}.count()

	println("A result: $result")
}

run {
	val regexp = """((?:byr|iyr|eyr|hgt|hcl|ecl|pid|cid)):([^\s]+)""".toRegex()
	val hclRegexp = """#[a-f0-9]{6}""".toRegex()
	val result = passports.filter { p ->
		val matched = regexp.findAll(p)
		val params = matched.map { it.groupValues[1] to it.groupValues[2] }.toMap()
		(params.size == 8 || (params.size == 7 && !params.containsKey("cid"))) &&
			params["byr"]?.toIntOrNull().let { it != null && 1920 <= it && it <= 2002 } &&
			params["iyr"]?.toIntOrNull().let { it != null && 2010 <= it && it <= 2020 } &&
			params["eyr"]?.toIntOrNull().let { it != null && 2020 <= it && it <= 2030 } &&
			params["hgt"].let { hgt ->
				if (hgt?.endsWith("cm") == true) {
					hgt.removeSuffix("cm").toIntOrNull().let {
						it != null && 150 <= it && it <= 193
					}
				} else if (hgt?.endsWith("in") == true) {
					hgt.removeSuffix("in").toIntOrNull().let {
						it != null && 59 <= it && it <= 76
					}
				} else {
					false
				}
			} &&
			params["hcl"].let {
				hclRegexp.matches(it ?: "")
			} &&
			params["ecl"].let {
				it in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
			} &&
			params["pid"].let {
				it?.length == 9 && it.toIntOrNull() != null
			}
	}.count()

	println("B result: $result")
}
