#!/usr/bin/env kotlin

import java.io.File

val input: List<String> = File("./inputs/02.txt").readText().lines()

val lines = input.dropLast(1)

run {
	val regexp = """(\d+)-(\d+)\s(\w):\s(\w+)""".toRegex()
	val result = lines.filter { line ->
		val vals = regexp.matchEntire(line)!!.groupValues
		val min = vals[1].toInt()
		val max = vals[2].toInt()
		val char = vals[3][0]
		val password = vals[4]
		val count = password.count { it == char }
		count in min..max
	}.count()
	println("A result: $result")
}


run {
	val regexp = """(\d+)-(\d+)\s(\w):\s(\w+)""".toRegex()
	val result = lines.filter { line ->
		val vals = regexp.matchEntire(line)!!.groupValues
		val min = vals[1].toInt() - 1
		val max = vals[2].toInt() - 1
		val char = vals[3][0]
		val password = vals[4]
		val isFirst = password.getOrNull(min) == char
		val isSecond = password.getOrNull(max) == char
		(isFirst || isSecond) && (isFirst != isSecond)
	}.count()
	println("B result: $result")
}
