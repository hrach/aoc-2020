#!/usr/bin/env kotlin

import java.io.File

val input = File("./inputs/06.txt").readText().trim()

val groups = input.split("\n\n")

run {
	val result = groups.map { group ->
		group.replace("""\s""".toRegex(), "").toSet().count()
	}.sum()

	println("A result: $result")
}

run {
	val result = groups.map { group ->
		group
			.trim()
			.lines()
			.map { it.trim().toSet() }
			.reduce { a, b -> a.intersect(b) }
			.count()
	}.sum()

	println("B result: $result")
}
