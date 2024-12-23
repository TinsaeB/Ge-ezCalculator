package com.ttbzrs.geezcalculator

import androidx.annotation.RequiresApi

object GeezCalculator {

    // ... existing code for geezToInt, intToGeez, parseGeez, intToGeezNumeral ...
    private val geezToInt = mapOf(
        '0' to 0, '፩' to 1, '፪' to 2, '፫' to 3, '፬' to 4, '፭' to 5,
        '፮' to 6, '፯' to 7, '፰' to 8, '፱' to 9, '፲' to 10,
        '፳' to 20, '፴' to 30, '፵' to 40, '፶' to 50,
        '፷' to 60, '፸' to 70, '፹' to 80, '፺' to 90,
        '፻' to 100, '፼' to 10000, "፼፼" to 100000000
    )

    private val intToGeez = geezToInt.entries.associate { (k, v) -> v to k }

    fun parseGeez(numeral: String): Long {
        var total = 0L
        var currentValue = 0L
        var previousValue = 0L
        var myriadCounter = 0

        for (char in numeral) {
            val value = geezToInt[char] ?: throw IllegalArgumentException("Invalid Ge'ez numeral character: $char")

            when {
                value == 100 -> {
                    if (currentValue == 0L) {
                        currentValue = 1
                    }
                    total += currentValue * value
                    currentValue = 0
                }
                value == 10000 -> {
                    myriadCounter++
                    if (currentValue == 0L) {
                        currentValue = 1
                    }
                    if (previousValue == 100L || myriadCounter > 1) {
                        total = (total + currentValue) * value
                    } else {
                        total += currentValue * value
                    }
                    currentValue = 0
                }
                value < previousValue && previousValue != 100L && previousValue != 10000L -> {
                    throw IllegalArgumentException("Invalid Ge'ez numeral: decreasing order without a multiplier")
                }
                else -> {
                    currentValue += value
                }
            }
            previousValue = value.toLong()
        }
        total += currentValue
        return total
    }

    fun intToGeezNumeral(n: Long): String {
        if (n == 0L) {
            return "0"
        }

        if (n < 0) {
            return "-" + intToGeezNumeral(-n)
        }

        val result = StringBuilder()
        var num = n

        val myriadMyriads = num / 100_000_000
        if (myriadMyriads > 0) {
            if (myriadMyriads > 1) {
                result.append(intToGeezNumeral(myriadMyriads))
            }
            result.append('፼' to 10000, "፼፼" to 100000000)
            num %= 100_000_000
        }

        val myriads = num / 10000
        if (myriads > 0) {
            if (myriads > 1) {
                result.append(intToGeezNumeral(myriads))
            }
            result.append('፼')
            num %= 10000
        }

        val thousands = num / 1000
        if (thousands in 1..9) {
            result.append(intToGeezNumeral(thousands))
            num %= 1000
        }

        val hundreds = num / 100
        if (hundreds > 0) {
            result.append(intToGeez[hundreds.toInt()])
            result.append('፻')
            num %= 100
        }

        val tens = num / 10
        if (tens > 0) {
            result.append(intToGeez[tens.toInt() * 10])
            num %= 10
        }

        if (num > 0) {
            result.append(intToGeez[num.toInt()])
        }

        return result.toString()
    }

    @RequiresApi(35)
    fun calculateWithBODMAS(expression: String, operator: String, newOperator: String): String {
        // Tokenize the expression
        val tokens = tokenize(expression)

        // Evaluate the expression using the order of operations
        val result = evaluateExpression(tokens)

        // Convert the result to Ge'ez numeral and return
        return intToGeezNumeral(result)
    }

    private fun tokenize(expression: String): List<String> {
        val tokens = mutableListOf<String>()
        var currentToken = StringBuilder()

        for (char in expression) {
            when {
                char in listOf('+', '-', '*', '/') -> {
                    if (currentToken.isNotEmpty()) {
                        tokens.add(currentToken.toString())
                        currentToken = StringBuilder()
                    }
                    tokens.add(char.toString())
                }
                geezToInt.containsKey(char) -> currentToken.append(char)
                else -> throw IllegalArgumentException("Invalid character: $char")
            }
        }

        if (currentToken.isNotEmpty()) {
            tokens.add(currentToken.toString())
        }

        return tokens
    }

    @RequiresApi(35)
    private fun evaluateExpression(tokens: List<String>): Long {
        // Implement a stack-based algorithm for evaluating expressions with BODMAS
        val values = mutableListOf<Long>()
        val operators = mutableListOf<Char>()

        for (token in tokens) {
            when {
                geezToInt.containsKey(token[0]) -> values.add(parseGeez(token))
                token == "+" || token == "-" || token == "*" || token == "/" -> {
                    while (operators.isNotEmpty() && hasPrecedence(token[0], operators.last())) {
                        values.add(applyOperation(operators.removeLast(), values.removeLast(), values.removeLast()))
                    }
                    operators.add(token[0])
                }
                else -> throw IllegalArgumentException("Invalid token: $token")
            }
        }

        while (operators.isNotEmpty()) {
            values.add(applyOperation(operators.removeLast(), values.removeLast(), values.removeLast()))
        }

        return values.first()
    }

    private fun hasPrecedence(op1: Char, op2: Char): Boolean {
        return (op2 == '*' || op2 == '/') && (op1 == '+' || op1 == '-')
    }

    private fun applyOperation(operator: Char, b: Long, a: Long): Long {
        return when (operator) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> if (b == 0L) throw ArithmeticException("Division by zero") else a / b
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    }
}