#!/usr/bin/env kotlin

import java.io.File
import kotlin.math.pow

val input: List<String> = File("./inputs/05.txt").readText().lines()

val lines = input.dropLast(1)

fun getValue(input: String, lowChar: Char, topChar: Char): Int {
	val max = 2f.pow(input.length).toInt() - 1
	var a: Int = 0
	var b: Int = max
	input.forEach { c ->
		val diff = ((b - a) / 2) + 1
		if (c == lowChar) {
			b -= diff
		} else if (c == topChar) {
			a += diff
		} else {
			error("invalid char")
		}
	}
	return a
}

fun getSeat(input: String): Int {
	val y = getValue(input.substring(0, 7), 'F', 'B')
	val x = getValue(input.substring(7, 10), 'L', 'R')
	return y * 8 + x
}

run {
	val maxSeat = lines.map { line ->
		getSeat(line)
	}.maxOrNull() ?: -1

	println("A result: $maxSeat")
}

run {
	val seats = lines.map { line ->
		getSeat(line)
	}.sorted()

	seats.zipWithNext().forEach { (a, b) ->
		if (a + 1 != b) {
			println("B result: ${a + 1}")
		}
	}
}
