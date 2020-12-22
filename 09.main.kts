#!/usr/bin/env kotlin

import java.io.File

val input: List<String> = File("./inputs/09.txt").readText().lines()
val lines = input.dropLast(1).map { it.toLong() }

run {
	val result = lines
		.asSequence()
		.windowed(size = 26, step = 1, partialWindows = false)
		.filter { nums ->
			val n = nums.last()
			val i = nums.dropLast(1)
			for (a in i) {
				for (b in i) {
					if (a != b && a + b == n) {
						return@filter false
					}
				}
			}
			return@filter true
		}

	check(result.count() == 1)
	println("A result: ${result.first().last()}")
}

run {
	val expectedSum = 105950735L
	val len = lines.size
	var i = 0
	var j = 1
	var sum: Long

	loop@while (i < len) {
		j = i + 1
		sum = lines[i]
		while (j < len){
			sum += lines[j]
			if (sum == expectedSum) {
				break@loop
			}
			j += 1
		}
		i += 1
	}

	val min = lines.subList(i, j).minOrNull()!!
	val max = lines.subList(i, j).maxOrNull()!!
	println("B result: ${min + max} (i: $i, j: $j)")
}
