#!/usr/bin/env kotlin

import java.io.File

val input: List<String> = File("./inputs/08.txt").readText().lines()

val lines = input.dropLast(1)

run {
	var accumulator: Int = 0
	var instructionOffset: Int = 0

	val readInstructions = mutableSetOf<Int>()

	while (!readInstructions.contains(instructionOffset)) {
		readInstructions.add(instructionOffset)

		val instruction = lines[instructionOffset]
		when (instruction.substring(0, 3)) {
			"nop" -> {
				instructionOffset += 1
			}
			"acc" -> {
				accumulator += instruction.substring(4).toInt()
				instructionOffset += 1
			}
			"jmp" -> {
				instructionOffset += instruction.substring(4).toInt()
			}
			else -> {
				error("uknown $instruction")
			}
		}
	}

	println("A result: $accumulator")
}

run {
	fun calc(changeNth: Int): Int? {
		var instructionCounter: Int = 0
		var accumulator: Int = 0
		var instructionOffset: Int = 0

		val readInstructions = mutableSetOf<Int>()

		while (!readInstructions.contains(instructionOffset)) {
			readInstructions.add(instructionOffset)

			val instruction = lines.getOrNull(instructionOffset) ?: return accumulator
			var command = instruction.substring(0, 3)
			if (changeNth == instructionCounter) {
				command = when (command) {
					"nop" -> "jmp"
					"jmp" -> "nop"
					else -> command
				}
			}
			when (command) {
				"nop" -> {
					instructionOffset += 1
					instructionCounter += 1
				}
				"acc" -> {
					accumulator += instruction.substring(4).toInt()
					instructionOffset += 1
				}
				"jmp" -> {
					instructionOffset += instruction.substring(4).toInt()
					instructionCounter += 1
				}
				else -> {
					error("uknown $instruction")
				}
			}
		}

		return null
	}

	var result: Int?
	var counter: Int = 0
	do {
		result = calc(counter++)
	} while (result == null)

	println("B result: $result")
}
