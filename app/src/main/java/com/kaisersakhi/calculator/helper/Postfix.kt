package com.kaisersakhi.calculator.helper

/**
 * Author : Kaiser Sakhi
 * Date : 16-10-22
 */


import android.util.Log
import kotlin.math.pow
import java.util.Stack
import kotlin.math.exp

class Postfix() {
    var expression: String = ""
        set(exp: String) {
            field = exp.replace('x', '*', true)
        }

    /**
     * isComputable should be true if  an expression can be computed i.e, every operand is a number
     * when 'isComputable' is set to true , toPostfix() will insert spaces around a number,
     * that way it is easier to parse the whole expression based on spaces
     * */
    private var isComputable: Boolean = false

    constructor(expression: String = "", isComputable: Boolean = false) : this() {
        this.expression = expression
        this.isComputable = isComputable
    }

    /**
     * toPostfix() will return the postfix of Postfix.expression
     */
    fun toPostfix(): String {
        val stack = Stack<String>()
        var postfixExpression: String = ""
        if (this.expression.isNotBlank()) {
            var i = 0
            //this loop will run form 0 to expression.length
            while (i < this.expression.length) {

                if (expression[i] == ')') {
                    while (true) {
                        if (stack.peek()!![0] == '(')
                            break
                        postfixExpression += stack.pop()
                    }
                    stack.pop()
                    ++i
                    continue
                }
                //if the token is an operator
                if (this.isOperator(expression[i])) {
                    if (expression[i] == ')')
                        continue
                    //pop all operators until the stack is empty or expression[i] has greater precedence
                    //then that of the stack top
                    while (
                        stack.isNotEmpty() && stack.peek()!![0] != '('
                        && precedence(stack.peek()!![0]) >= precedence(expression[i])
                    ) {
                        postfixExpression += stack.pop()
                    }
                    stack.push(expression[i].toString())
                    i++
                } else { // if it is an operand, directly append it to the o.p string
                    /**
                     *if the expression is computable, this code will respect individual numbers
                     *and put spaces around an individual numbers
                     **/
                    if (isComputable) {
                        var number = ""
                        while (i < this.expression.length && !isOperator(this.expression[i])) {
                            number += this.expression[i++]
                        }
                        postfixExpression += " $number "
                        continue // to the outer loop
                    }
                    postfixExpression += expression[i].toString()
                    i++
                }
            }

            while (stack.isNotEmpty()) {
                postfixExpression += stack.pop()
            }
        }
        return postfixExpression
    }


    /**
     **evaluate() will compute the value of the expression only,
     **isComputable is set to true
     **/
    fun evaluate(): Double {
        val stack = Stack<Double>()

        this.cleanExpression()

        if(!this.isOperable())
            return 0.0

        if (isComputable && this.expression.length > 1) {
            var i = 0
            val postfix = this.toPostfix().trim()
            var number = ""
            while (i < postfix.length) {
                if (isOperator(postfix[i])) {
                    val o2 = stack.pop()
                    val o1 = stack.pop()
                    if (o1 != null && o2 != null) {
                        stack.push(performCalculation(o1.toDouble(), o2.toDouble(), postfix[i]))
                    }
                    ++i
                    continue
                }
                /**
                 **get the actual number
                 **/
                while (postfix[i] != ' ') {
                    number += postfix[i++]
                }
                if (number.isNotBlank())
                    number.toDouble().let {
                        stack.push(it)
                    }
                number = ""
                ++i
            }
        }
        return stack.pop() ?: 0.0
    }

    private fun precedence(char: Char) = when (char) {
        '-' -> 1
        '+' -> 1
        '*' -> 2
        '/' -> 2
        '^' -> 3
        '(' -> 4
        ')' -> 4
        else -> -1
    }

    private fun isOperator(char: Char) =
        (char == '-' || char == '+' || char == '*' || char == '/' || char == '^' || char == '(' || char == ')')

    private fun performCalculation(o1: Double, o2: Double, operator: Char): Double =
        when (operator) {
            '-' -> o1 - o2
            '+' -> o1 + o2
            '*' -> o1 * o2
            '/' -> o1 / o2
            '^' -> o1.pow(o2)
            else -> 0.0
        }

    /**
     * drops trailing zeros after decimal
     */
    fun cleanExpression(xps: String = this.expression): String {

        var expression = xps.trim().replace('x', '*', true)
        expression = if (isOperator(expression.last())) {
            expression.trim().subSequence(0, expression.trim().length - 1).toString()

        } else {
            expression
        }

        Log.d(this::class.simpleName, "cleanExpression: $expression")

        var result = ""
        var i = 0
        var valueAfterDecimal = ""
        var wasLastCharOperator = false
        while (i < expression.length) {

            if (wasLastCharOperator && expression[i] == '.'){
                result += "0"
            }
            wasLastCharOperator = false
            //start from the decimal point
            if (expression[i] == '.') {
                ++i
                //until the current token is an operator or has exponent E
                while (i < expression.length && !isOperator(expression[i]) && expression[i] != 'E') {
                    valueAfterDecimal += expression[i].toString()
                    i++
                }
                if (valueAfterDecimal.trim().toLong() != 0L) {
                    result += ".$valueAfterDecimal"
                }
                valueAfterDecimal = ""
                continue
            }
            if (isOperator(expression[i]))
                wasLastCharOperator = true
            result += expression[i]
            i++
        }
        return result
    }


    /**
     * Will return true if the last operand contains a decimal point in it
     */
    fun doesLastNumberContainsDecimalPoint(exps: String): Boolean {
        val expression = exps.trim().replace('x', '*', true)
        var i = expression.length - 1;
        while (i >= 0 && !isOperator(expression[i] )){
            if (expression[i] == '.')
                return true
            i--
        }
        return false
    }

    /**
     * returns true if an infix expression can be computed
     */
    fun isOperable(exp :String = this.expression) : Boolean{
        //([0-9]+\+[0-9]+|[0-9]+\*[0-9]+|[0-9]+\/[0-9]+|[0-9]+\-[0-9]+)+
        //([0-9]+\+|[0-9]+\*|[0-9]+\/|[0-9]+\-)+\d+
        //([0-9|.\0-9]+\+|[0-9|.\0-9]+\*|[0-9|.\0-9]+\/|[0-9|.\0-9]+\-)+(\d|[.\0-9])+
        val regex = "([0-9|.0-9]+\\+|[0-9|.0-9]+\\*|[0-9|.0-9]+\\/|[0-9|.0-9]+\\-)+(\\d|[.0-9])+".toRegex()
        var expression = ""
        for (x in exp){
            if (x == ' ')
                continue
            expression += x.toString()
        }
        return expression.matches(regex)
    }

}