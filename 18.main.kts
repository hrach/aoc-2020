#!/usr/bin/env kotlin

@file:CompilerOptions("-Xopt-in=kotlin.RequiresOptIn")
@file:OptIn(ExperimentalStdlibApi::class)

import java.io.File

val input = File("./inputs/18.txt").readLines()

sealed class Op {
	class Number(val value: Long) : Op() {
		override fun toString(): String = value.toString()
	}

	object OB : Op() {
		override fun toString(): String = "("
	}

	object CB : Op() {
		override fun toString(): String = ")"
	}

	object Plus : Op() {
		override fun toString(): String = " + "
	}

	object Multiply : Op() {
		override fun toString(): String = " * "
	}
}

fun parse(s: String): List<Op> =
	buildList {
		var p = 0
		while (p < s.length) {
			when (s[p]) {
				'(' -> {
					add(Op.OB)
					p += 1
				}
				')' -> {
					add(Op.CB)
					p += 1
				}
				'*' -> {
					add(Op.Multiply)
					p += 2
				}
				'+' -> {
					add(Op.Plus)
					p += 2
				}
				' ' -> {
					p += 1
				}
				else -> {
					val ws = s.indexOfAny(charArrayOf(' ', ')'), p)
					val ns = s.substring(p, if (ws == -1) s.length else ws)
					val n = ns.toLong()
					add(Op.Number(n))
					p += ns.length
				}
			}
		}
	}

run {
	fun calc(ops: MutableList<Op>): Long {
		var number = 0L
		while (ops.isNotEmpty()) {
			when (val op = ops.removeLast()) {
				is Op.Number -> number = op.value
				Op.Plus -> number += calc(ops)
				Op.Multiply -> number *= calc(ops)
				Op.CB -> {
					number = calc(ops)
					ops.removeLast()
				}
				Op.OB -> {
					ops.add(op)
					return number
				}
			}
		}

		return number
	}

	val result = input.map { calc(parse(it).toMutableList()) }.sum()
	println("A result: $result")
}

run {
	fun calc(ops: MutableList<Op>, requireOperand: Boolean = false): Long {
		var number = 0L
		while (ops.isNotEmpty()) {
			when (val op = ops.first()) {
				is Op.Number -> {
					ops.removeFirst()
					number = op.value
					if (requireOperand) {
						return number
					}
				}
				Op.Plus -> {
					ops.removeFirst()
					number += calc(ops, requireOperand = true)
				}
				Op.Multiply -> {
					ops.removeFirst()
					number *= calc(ops)
				}
				Op.OB -> {
					ops.removeFirst()
					number = calc(ops)
					ops.removeFirst()
					if (requireOperand) {
						return number
					}
				}
				Op.CB -> {
					return number
				}
			}
		}

		return number
	}

	val result = input.map { calc(parse(it).toMutableList()) }.sum()
	println("B result: $result")
}
