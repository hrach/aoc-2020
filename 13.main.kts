#!/usr/bin/env kotlin

import java.io.File

val input: List<String> = File("./inputs/13.txt").readLines()
val buses = input[1].split(",").map { it.toIntOrNull() }

run {
	val time = input[0].toInt()
	val arrivesAt = buses
		.filterNotNull()
		.map { number ->
			((time / number) + 1) * number to number
		}
		.minByOrNull { it.first }!!
	val result = (arrivesAt.first - time) * arrivesAt.second
	println("A result: $result")
}

run {
	// https://stackoverflow.com/questions/16044553/solving-a-modular-equation-python
	// https://algorithmist.com/wiki/Modular_inverse
	// https://rosettacode.org/wiki/Chinese_remainder_theorem#Kotlin

	fun multInv(a: Long, b: Long): Long {
		if (b == 1L) return 1
		var aa = a
		var bb = b
		var x0 = 0L
		var x1 = 1L
		while (aa > 1) {
			val q = aa / bb
			var t = bb
			bb = aa % bb
			aa = t
			t = x0
			x0 = x1 - q * x0
			x1 = t
		}
		if (x1 < 0) x1 += b
		return x1
	}

	fun chineseRemainder(n: LongArray, a: LongArray): Long {
		val prod = n.fold(1L) { acc, i -> acc * i }
		var sum = 0L
		for (i in 0 until n.size) {
			val p = prod / n[i]
			sum += a[i] * multInv(p, n[i]) * p
		}
		return sum % prod
	}

	// Looking for t
	// Positions = p
	// Remainders = a
	// Modulos = n

	val p = buses.mapIndexedNotNull { index, number ->
		if (number == null) null
		else index.toLong()
	}
	val pMax = p.maxOrNull()!!
	val a = p.map { (pMax - it) }.toLongArray()
	val n = buses.mapNotNull { it?.toLong() }.toLongArray()

	val result = chineseRemainder(n, a) - a.first()

	println("B result: $result")
}
