#!/usr/bin/env kotlin

import java.io.File
import kotlin.math.abs
import kotlin.math.absoluteValue

val input: List<String> = File("./inputs/12.txt").readLines()
val commands = input.map {
	it[0] to it.substring(1).toInt()
}

run {
	var north = 0
	var east = 0
	var direction = 'E'

	val directions = listOf('E', 'N', 'W', 'S')

	commands.forEach { (command, amount) ->
		when (if (command == 'F') direction else command) {
			'N' -> north += amount
			'S' -> north -= amount
			'E' -> east += amount
			'W' -> east -= amount
			'L' -> {
				val angle = (directions.indexOf(direction) * 90 + amount).div(90)
				direction = directions[angle.rem(4)]
			}
			'R' -> {
				val angle = (directions.indexOf(direction) * 90 + 360 - amount).div(90)
				direction = directions[angle.rem(4)]
			}
		}
	}

	val result = abs(east) + abs(north)
	println("A result: $result")
}

run {
	var wX = 10
	var wY = 1
	var sX = 0
	var sY = 0

	commands.forEach { (command, amount) ->
		when (command) {
			'N' -> wY += amount
			'S' -> wY -= amount
			'E' -> wX += amount
			'W' -> wX -= amount
			'F' -> {
				sX += wX * amount
				sY += wY * amount
			}
			'L', 'R' -> {
				val angle = (if (command == 'R') 360 - amount else amount).rem(360)
				when (angle) {
					0 -> {
						// noop
					}
					90 -> {
						wX = -wY.also { wY = wX }
					}
					180 -> {
						wX = -wX
						wY = -wY
					}
					270 -> {
						wX = wY.also { wY = -wX }
					}
				}
			}
		}
	}

	val result = sX.absoluteValue + sY.absoluteValue
	println("B result: $result")
}
