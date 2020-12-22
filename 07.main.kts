#!/usr/bin/env kotlin

@file:DependsOn("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
@file:CompilerOptions("-Xopt-in=kotlin.RequiresOptIn")
@file:OptIn(ExperimentalStdlibApi::class)

import java.io.File

val input: List<String> = File("./inputs/07.txt").readText().lines()

val lines = input.dropLast(1)

run {
	val regexp = """((?:\d+ )?\w+ \w+) bags?""".toRegex()

	val sizes: Map<String, Map<String, Int>> = lines.map { line ->
		val values = regexp.findAll(line).toList().map { it.groupValues[1] }
		values[0] to values.drop(1).filter { it != "no other" }.map { bag ->
			val indexOf = bag.indexOf(' ')
			bag.substring(indexOf + 1) to bag.substring(0, indexOf).toInt()
		}.toMap()
	}.toMap()

	val result = mutableSetOf<String>("shiny gold")

	do {
		val prevResult = result.toMutableSet()
		prevResult.forEach { wantedId ->
			sizes.forEach { (id, contains) ->
				if (contains.containsKey(wantedId)) {
					result.add(id)
				}
			}
		}
	} while (prevResult != result)

	println("A result: ${result.size - 1}")
}

run {
	val regexp = """((?:\d+ )?\w+ \w+) bags?""".toRegex()
	val sizes: Map<String, Map<String, Int>> = lines.map { line ->
		val values = regexp.findAll(line).toList().map { it.groupValues[1] }
		values[0] to values.drop(1).filter { it != "no other" }.map { bag ->
			val indexOf = bag.indexOf(' ')
			bag.substring(indexOf + 1) to bag.substring(0, indexOf).toInt()
		}.toMap()
	}.toMap()

	fun getBags(id: String): Int {
		return sizes[id]!!.map { (subId, count) ->
			getBags(subId) * count
		}.sum() + 1
	}

	val result = getBags("shiny gold") - 1
	println("B result: $result")
}

// alternative solution without stack overflow
run {
	val regexp = """((?:\d+ )?\w+ \w+) bags?""".toRegex()
	val sizes: Map<String, Map<String, Int>> = lines.map { line ->
		val values = regexp.findAll(line).toList().map { it.groupValues[1] }
		values[0] to values.drop(1).filter { it != "no other" }.map { bag ->
			val indexOf = bag.indexOf(' ')
			bag.substring(indexOf + 1) to bag.substring(0, indexOf).toInt()
		}.toMap()
	}.toMap()

	val getBags = DeepRecursiveFunction<String, Int> { id ->
		sizes[id]!!.map { (subId, count) ->
			callRecursive(subId) * count
		}.sum() + 1
	}

	val result = getBags("shiny gold") - 1
	println("B2 result: $result")
}
