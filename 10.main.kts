#!/usr/bin/env kotlin

import java.io.File

val input: List<String> = File("./inputs/10.txt").readLines()
val parsed = input.map { it.toInt() }.sorted()
val numbers = listOf(0) + parsed + listOf(parsed.last() + 3)

run {
	val diffs = numbers.zipWithNext { a, b -> b - a }
	val diff1 = diffs.count { it == 1 }
	val diff3 = diffs.count { it == 3 }
	println("A result: ${diff1 * diff3}")
}

run {
	// THIS B solution is by Elizarov:
	// https://github.com/elizarov/AdventOfCode2020/blob/main/Day10.kt
	val variantsFor = LongArray(numbers.size)
	variantsFor[0] = 1

	for (i in 1 until numbers.size) {
		for (j in (i - 3).coerceAtLeast(0) until i) {
			if (numbers[i] - numbers[j] <= 3) {
				variantsFor[i] += variantsFor[j]
			}
		}
	}

	val variants = variantsFor.last()
	println("B result: $variants")
}
