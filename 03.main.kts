#!/usr/bin/env kotlin

import java.io.File

val input: List<String> = File("./inputs/03.txt").readText().lines()

val lines = input.dropLast(1)

run {
	val ll: List<Sequence<Char>> = lines.map {
		sequence {
			while (true) {
				it.forEach {
					yield(it)
				}
			}
		}
	}

	var counter = 0
	var x = 0
	ll.forEach { line ->
		if (line.elementAt(x) == '#') {
			counter += 1
		}
		x += 3
	}

	println("A result: $counter")
}

run {
	val ll: List<Sequence<Char>> = lines.map {
		sequence {
			while (true) {
				it.forEach {
					yield(it)
				}
			}
		}
	}

	val results = mutableListOf<Int>()
	for ((mx, my) in listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)) {
		var counter = 0
		var x = 0
		var y = 0

		do {
			if (ll[y].elementAt(x) == '#') {
				counter += 1
			}
			x += mx
			y += my
		} while (y < lines.size)
		results.add(counter)
	}

	val result = results.fold(1L) { a, b -> a * b }
	println("B results $result")
}
