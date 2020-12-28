#!/usr/bin/env kotlin

import java.io.File

val input: List<String> = File("./inputs/11.txt").readLines()
val seats: List<List<Char>> = input.map { it.toList() }
val rowLength = seats.first().size

fun getNewState(data: List<List<Char>>, x: Int, y: Int): Char {
	val occupied = with(data) {
		listOfNotNull(
			getOrNull(y - 1)?.getOrNull(x - 1),
			getOrNull(y - 1)?.getOrNull(x),
			getOrNull(y - 1)?.getOrNull(x + 1),
			getOrNull(y)?.getOrNull(x - 1),
			getOrNull(y)?.getOrNull(x + 1),
			getOrNull(y + 1)?.getOrNull(x - 1),
			getOrNull(y + 1)?.getOrNull(x),
			getOrNull(y + 1)?.getOrNull(x + 1),
		)
	}
		.filter { it == '#' }
		.count()

	val state = data[y][x]
	return when {
		state == 'L' && occupied == 0 -> '#'
		state == '#' && occupied >= 4 -> 'L'
		else -> state
	}
}

fun getNewState2(data: List<List<Char>>, x: Int, y: Int): Char {
	fun isOccupied(mx: Int, my: Int): Boolean {
		var i = x
		var j = y
		var field: Char
		do {
			i += mx
			j += my
			field = data.getOrNull(j)?.getOrNull(i) ?: return false
		} while (field == '.')
		return field == '#'
	}

	val occupied = listOf(
		isOccupied(-1, -1),
		isOccupied(+0, -1),
		isOccupied(+1, -1),
		isOccupied(-1, +0),
		isOccupied(+1, +0),
		isOccupied(-1, +1),
		isOccupied(+0, +1),
		isOccupied(+1, +1),
	).filter { it }.count()

	val state = data[y][x]
	return when {
		state == 'L' && occupied == 0 -> '#'
		state == '#' && occupied >= 5 -> 'L'
		else -> state
	}
}


fun List<List<Char>>.evolve(fn: (List<List<Char>>, Int, Int) -> Char): List<List<Char>> {
	return mapIndexed { y, row ->
		row.mapIndexed { x, state ->
			when (state) {
				'.' -> '.'
				else -> fn(this@evolve, x, y)
			}
		}
	}
}

fun List<List<Char>>.print() {
	forEach {
		println(it.joinToString(""))
	}
}

run {
	var state = seats
	var lastState: List<List<Char>>

	do {
		lastState = state
		state = state.evolve(::getNewState)
	} while (lastState != state)

//	state.print()
	val occupied = state.map { row -> row.count { it == '#' } }.sum()
	println("A result: $occupied")
}

run {
	var state = seats
	var lastState: List<List<Char>>

	do {
		lastState = state
		state = state.evolve(::getNewState2)
	} while (lastState != state)

//	state.print()
	val occupied = state.map { row -> row.count { it == '#' } }.sum()
	println("B result: $occupied")
}
