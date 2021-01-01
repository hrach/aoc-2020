#!/usr/bin/env kotlin

@file:Suppress("EXPERIMENTAL_API_USAGE")

import java.io.File

val input: List<String> = File("./inputs/14.txt").readLines()

sealed class Input {
	data class SetValueMask(
		val orMask: Long,
		val andMask: Long,
	) : Input()

	data class SetAddressMask(
		val mask: Long,
		val varBits: List<Int>,
	) : Input()

	data class Write(
		val offset: Long,
		val value: Long,
	) : Input()
}

fun String.parse(): Input {
	return when (this[1]) {
		'a' -> {
			val mask = substring(7)
			val len = mask.length - 1
			Input.SetValueMask(
				orMask = mask.foldIndexed(0L) { index, acc, c ->
					if (c != '1') acc
					else acc or (1L shl (len - index))
				},
				andMask = mask.foldIndexed(0.inv()) { index, acc, c ->
					if (c != '0') acc
					else acc xor (1L shl (len - index))
				},
			)
		}
		'e' -> {
			val closing = indexOf(']')
			val index = substring(4, closing).toLong()
			val value = substring(closing + 4).toLong()
			Input.Write(index, value)
		}
		else -> error(this)
	}
}

run {
	val memory = mutableMapOf<Long, Long>()
	var mask = Input.SetValueMask(
		orMask = 0.inv(),
		andMask = 0,
	)

	input.forEach { line ->
		when (val parsed = line.parse()) {
			is Input.SetValueMask -> {
				mask = parsed
			}
			is Input.Write -> {
				val toWrite = (parsed.value and mask.andMask) or mask.orMask
//				println(parsed.value.toULong().toString(radix = 2).padStart(36, '0'))
//				println(mask.orMask.toULong().toString(radix = 2).padStart(36, '0'))
//				println(mask.andMask.toULong().toString(radix = 2).padStart(36, '0'))
//				println(toWrite.toULong().toString(radix = 2).padStart(36, '0'))
				memory[parsed.offset] = toWrite
			}
		}
	}

	val result = memory.values.sum()
	println("A result: $result")
}

fun String.parseAsV2Decoder(): Input {
	return when (this[1]) {
		'a' -> {
			val mask = substring(7)
			val len = mask.length - 1
			Input.SetAddressMask(
				mask = mask.foldIndexed(0L) { index, acc, c ->
					if (c != '1') acc
					else acc or (1L shl (len - index))
				},
				varBits = mask.mapIndexedNotNull { index, c ->
					if (c == 'X') len - index
					else null
				},
			)
		}
		'e' -> {
			val closing = indexOf(']')
			val index = substring(4, closing).toLong()
			val value = substring(closing + 4).toLong()
			Input.Write(index, value)
		}
		else -> error(this)
	}
}

fun Input.SetAddressMask.getPermutations(address: Long): List<Long> {
	fun getPermutations(bits: List<Int>): List<Long> {
		if (bits.isEmpty()) return mutableListOf(address or mask)

		val bit = bits[0]
		return getPermutations(bits.drop(1)).map { a ->
			listOf(
				a or (1L shl bit),
				a and (1L shl bit).inv(),
			)
		}.flatten()
	}
	return getPermutations(bits = this.varBits)
}

run {
	val memory = mutableMapOf<Long, Long>()
	var mask = Input.SetAddressMask(0, emptyList())

	input.forEach { line ->
		when (val parsed = line.parseAsV2Decoder()) {
			is Input.SetAddressMask -> {
				mask = parsed
			}
			is Input.Write -> {
				mask.getPermutations(parsed.offset).forEach { address ->
					memory[address] = parsed.value
				}
			}
		}
	}

	val result = memory.values.sum()
	println("B result: $result")
}
