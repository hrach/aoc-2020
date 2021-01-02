#!/usr/bin/env kotlin

import java.io.File

val input = File("./inputs/17.txt").readLines()

// Overall design inspired by https://github.com/elizarov/AdventOfCode2020/blob/main/Day17_1.kt

data class PositionA(val x: Int, val y: Int, val z: Int) {
	override fun toString(): String = "($z,$y,$x)"
}

data class PositionB(val x: Int, val y: Int, val z: Int, val w: Int) {
	override fun toString(): String = "($z,$y,$x,$w)"
}

run {
	var positions = input.flatMapIndexed { y, line ->
		line.mapIndexedNotNull { x, c ->
			if (c == '#') PositionA(x, y, 0)
			else null
		}
	}.toSet()

	repeat(6) {
		val adjacent = positions.flatMap { p ->
			val neighbors = mutableListOf<PositionA>()
			for (dz in -1..1) {
				for (dy in -1..1) {
					for (dx in -1..1) {
						if (dx == 0 && dy == 0 && dz == 0) continue
						neighbors.add(PositionA(p.x + dx, p.y + dy, p.z + dz))
					}
				}
			}
			neighbors
		}

		val adjacentCount: Map<PositionA, Int> = adjacent
			.groupBy { it }
			.mapValues { it.value.count() }

		positions = positions
			.filter { (adjacentCount[it] ?: 0) in 2..3 }
			.toMutableList()
			.plus(adjacent.filter { adjacentCount[it] == 3 })
			.toSet()
	}

	val result = positions.count()
	println("A result: $result")
}

run {
	var positions = input.flatMapIndexed { y, line ->
		line.mapIndexedNotNull { x, c ->
			if (c == '#') PositionB(x, y, z = 0, w = 0)
			else null
		}
	}.toSet()

	repeat(6) {
		val adjacent = positions.flatMap { p ->
			val neighbors = mutableListOf<PositionB>()
			for (dz in -1..1) {
				for (dy in -1..1) {
					for (dx in -1..1) {
						for (dw in -1..1) {
							if (dx == 0 && dy == 0 && dz == 0 && dw == 0) continue
							neighbors.add(PositionB(p.x + dx, p.y + dy, p.z + dz, p.w + dw))
						}
					}
				}
			}
			neighbors
		}

		val adjacentCount: Map<PositionB, Int> = adjacent
			.groupBy { it }
			.mapValues { it.value.count() }

		positions = positions
			.filter { (adjacentCount[it] ?: 0) in 2..3 }
			.toMutableList()
			.plus(adjacent.filter { adjacentCount[it] == 3 })
			.toSet()
	}

	val result = positions.count()
	println("B result: $result")
}
