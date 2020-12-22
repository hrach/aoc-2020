#!/usr/bin/env kotlin

import java.io.File

val input = File("./inputs/01.txt").readText().lines()

val numbers = input.drop(1).dropLast(1).map { it.toInt() }

loop@for (i in numbers) {
	for (j in numbers) {
		if (i + j == 2020) {
			println("A result: ${i * j}")
			break@loop
		}
	}
}

loop@for (i in numbers) {
	for (j in numbers) {
		for (k in numbers) {
			if (i + j + k == 2020) {
				println("B result: ${i * j * k}")
				break@loop
			}
		}
	}
}
