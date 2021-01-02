#!/usr/bin/env kotlin

fun calc(offset: Long): Long {
	val input = """1,20,11,6,12,0"""
	val numbers = input.split(",").map { it.toLong() }

	val map = numbers
		.mapIndexed { index, i -> i to index.toLong() + 1 }
		.dropLast(1)
		.toMap()
		.toMutableMap()

	var last = numbers.last()
	for (turn in (numbers.size + 1)..offset) {
		val used = map.getOrDefault(last, -1)
		map[last] = turn - 1
		val n = if (used == -1L) {
			0L
		} else {
			turn - 1 - used
		}
		last = n
	}

	return last
}

run {
	val result = calc(2020)
	println("A result: $result")
}

run {
	val result = calc(30000000)
	println("A result: $result")
}
