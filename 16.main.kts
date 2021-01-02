#!/usr/bin/env kotlin

import java.io.File

val input = File("./inputs/16.txt").readText()
val parts = input.split("\n\n")

val conditionRegex = Regex("""([^:]+): (\d+)-(\d+) or (\d+)-(\d+)""")
val conditions: List<Pair<String, List<IntRange>>> = parts[0].lines().map { line ->
	val matches = conditionRegex.matchEntire(line)!!.groupValues
	val name = matches[1]
	val r1 = IntRange(matches[2].toInt(), matches[3].toInt())
	val r2 = IntRange(matches[4].toInt(), matches[5].toInt())
	name to listOf(r1, r2)
}

val myTicket: List<Int> = parts[1].lines().last().split(",").map { it.toInt() }

val tickets = parts[2].trim().lines().drop(1).map { line -> line.split(",").map { it.toInt() } }

run {
	val numbers = tickets.map { ticket ->
		ticket.filter { number ->
			!conditions.any { condition ->
				condition.second.any { range ->
					number in range
				}
			}
		}
	}.flatten()

	val result = numbers.sum()
	println("A result: $result")
}

run {
	val okTickets = tickets.filter { ticket ->
		ticket.all { number ->
			conditions.any { condition ->
				condition.second.any { range ->
					number in range
				}
			}
		}
	}

	val cs = conditions.size
	val map = mutableMapOf<Int, String>()

	val matches: List<MutableList<Boolean?>> = conditions.map { condition ->
		(0 until cs).map { numberIndex ->
			okTickets.fold(true) { acc, numbers ->
				val number = numbers[numberIndex]
				acc && condition.second.any { range ->
					number in range
				}
			}
		}.toMutableList()
	}

	val todo: MutableList<Pair<String, List<IntRange>>?> = conditions.toMutableList()

	loop@ do {
		for ((index, condition) in todo.withIndex()) {
			if (condition == null) continue

			if (matches[index].count { it == true } == 1) {
				val foundIndex = matches[index].indexOf(true)
				check(foundIndex != -1)
				todo[index] = null
				map[foundIndex] = condition.first
				matches.forEach { match ->
					match[foundIndex] = null
				}
				continue@loop
			}
		}
		error("there should be at least one match")
	} while (todo.any { it != null })

	val result = map
		.filter { it.value.startsWith("departure") }
		.keys
		.map { myTicket[it] }
		.fold(1L) { acc, n -> acc * n }

	println("B result: $result")
}
