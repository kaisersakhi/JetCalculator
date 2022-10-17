package com.kaisersakhi.calculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaisersakhi.calculator.helper.Postfix
import com.kaisersakhi.calculator.ui.components.CalculatorButton
import com.kaisersakhi.calculator.ui.theme.CalDarkGrey
import com.kaisersakhi.calculator.ui.theme.CalLightGrey
import com.kaisersakhi.calculator.ui.theme.CalOrange
import com.kaisersakhi.calculator.ui.theme.OpenSans
import kotlin.math.exp

@Composable
fun CalculatorMainScreen(postfix: Postfix) {

    var inputStringState by remember {
        mutableStateOf("")
    }

    var history by remember {
        mutableStateOf("")
    }

    Box(contentAlignment = Alignment.BottomCenter) {
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxWidth(0.95f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val buttonSpacing = 8.dp
            HistoryView(text = history)
            ResultTextView(text = inputStringState)
            Divider(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 10.dp)
                    .align(Alignment.CenterHorizontally),
                color = CalLightGrey,
                thickness = 1.dp
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                modifier = Modifier.fillMaxWidth()
            ) {
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(2f)
                        .weight(2f)
                        .background(CalLightGrey),
                    symbol = "AC"
                ) {
                    inputStringState = ""
                    history = ""
                }
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(CalLightGrey),
                    symbol = "Del",
                ) {
                    inputStringState = inputStringState.dropLast(1)
                }
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(CalOrange),
                    symbol = "÷"
                ) {
                    if (!doesEndsWithOperator(inputStringState) && inputStringState.isNotEmpty()) {
                        inputStringState += "/"
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                modifier = Modifier.fillMaxWidth()
            ) {
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(CalDarkGrey),
                    symbol = "7"
                ) {
                    inputStringState += "7"
                }
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(CalDarkGrey),
                    symbol = "8",
                ) {
                    inputStringState += "8"
                }
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(CalDarkGrey),
                    symbol = "9"
                ) {
                    inputStringState += "9"
                }
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(CalOrange),
                    symbol = "✖"
                ) {
                    if (!doesEndsWithOperator(inputStringState) && inputStringState.isNotEmpty()) {
                        inputStringState += "x"
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                modifier = Modifier.fillMaxWidth()
            ) {
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(CalDarkGrey),
                    symbol = "4"
                ) {
                    inputStringState += "4"
                }
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(CalDarkGrey),
                    symbol = "5",
                ) {
                    inputStringState += "5"
                }
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(CalDarkGrey),
                    symbol = "6"
                ) {
                    inputStringState += "6"
                }
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(CalOrange),
                    symbol = "−"
                ) {
                    if (!doesEndsWithOperator(inputStringState) && inputStringState.isNotEmpty()) {
                        inputStringState += "-"
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                modifier = Modifier.fillMaxWidth()
            ) {
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(CalDarkGrey),
                    symbol = "1"
                ) {
                    inputStringState += "1"
                }
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(CalDarkGrey),
                    symbol = "2",
                ) {
                    inputStringState += "2"
                }
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(CalDarkGrey),
                    symbol = "3"
                ) {
                    inputStringState += "3"
                }
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(CalOrange),
                    symbol = "＋"
                ) {
                    if (!doesEndsWithOperator(inputStringState) && inputStringState.isNotEmpty()) {
                        inputStringState += "+"
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing),
                modifier = Modifier.fillMaxWidth()
            ) {
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(CalDarkGrey),
                    symbol = "0"
                ) {
                    inputStringState += "0"
                }
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .weight(1f)
                        .background(CalLightGrey),
                    symbol = ".",
                ) {

                    if (!postfix.doesLastNumberContainsDecimalPoint(inputStringState))
                        inputStringState += "."
                }
                CalculatorButton(
                    modifier = Modifier
                        .aspectRatio(2f)
                        .weight(2f)
                        .background(CalOrange),
                    symbol = "="
                ) {
                    //calculate expression
                    if (inputStringState.isNotEmpty())
                        postfix.expression = postfix.cleanExpression(inputStringState)
                    val result: String =
                        if (inputStringState.isNotEmpty() && postfix.isOperable()) {
                            postfix.cleanExpression(postfix.evaluate().toString())
                        } else {
                            ""
                        }
                    history += "$inputStringState = $result\n"
                    inputStringState = result
                }
            }
        }
    }
}

@Composable
fun ResultTextView(text: String) {
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth(0.95f),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = text,
            fontSize = 40.sp,
            style = MaterialTheme.typography.h2,
//            fontWeight = FontWeight.Bold,
            fontFamily = OpenSans,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun HistoryView(text: String) {
    val scrollState = rememberScrollState(0)

    LaunchedEffect(scrollState.maxValue) {
        scrollState.scrollTo(scrollState.maxValue)
    }
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth(0.95f)
            .height(200.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = text,
            fontSize = 40.sp,
            style = MaterialTheme.typography.h3,
//            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = CalLightGrey,
            modifier = Modifier
                .verticalScroll(scrollState),
            textAlign = TextAlign.End
        )
    }
}


fun doesEndsWithOperator(str: String): Boolean {
    var lastChar = ' '
    if (str.isNotEmpty()) {
        lastChar = str.trim()[str.length - 1].lowercaseChar()
    }
    if (lastChar == '/' || lastChar == 'x' || lastChar == '-' || lastChar == '+')
        return true
    return false
}


