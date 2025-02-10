package com.ttbzrs.geezcalculator

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ttbzrs.geezcalculator.ui.theme.GeezCalculatorTheme
// ... (other imports)
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {
    private var textToSpeechEngine: TextToSpeechEngine? = null
    private lateinit var dataStoreManager: DataStoreManager // Add DataStoreManager

    @RequiresApi(35)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textToSpeechEngine = TextToSpeechEngine(this)
        dataStoreManager = DataStoreManager(this) // Initialize

        setContent {
            // Collect the flow as state
            val isDarkMode by dataStoreManager.isDarkMode.collectAsState(initial = false) // Initial value
            val coroutineScope = rememberCoroutineScope() // Use rememberCoroutineScope

            GeezCalculatorTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { checked ->
                                // Use a coroutine to update DataStore
                                coroutineScope.launch {
                                    dataStoreManager.setDarkMode(checked)
                                }
                            },
                            modifier = Modifier.padding(16.dp)
                        )
                        CalculatorApp(textToSpeechEngine)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        textToSpeechEngine?.shutdown()
        super.onDestroy()
    }
}

@RequiresApi(35)
@Composable
fun CalculatorApp(textToSpeechEngine: TextToSpeechEngine?) {
    var currentInput by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf<String?>(null) }
    var result by remember { mutableStateOf("") }
    var isResultDisplayed by remember { mutableStateOf(false) }

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppHeader()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    val intent = Intent(context, InfoActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Info")
            }
            Button(
                onClick = {
                    val intent = Intent(context, FeedbackActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Feedback")
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ResultDisplay(input = currentInput, result = result)
            Spacer(modifier = Modifier.height(16.dp))
            ButtonGrid(
                currentInput = currentInput,
                operator = operator,
                onNumberClick = { number ->
                    if (isResultDisplayed) {
                        currentInput = number
                        isResultDisplayed = false
                    } else {
                        currentInput += number
                    }
                },
                onOperatorClick = { newOperator ->
                    if (currentInput.isNotEmpty()) {
                        if (operator != null) {
                            try {
                                val newResult = GeezCalculator.calculateWithBODMAS(currentInput, operator!!, newOperator)
                                currentInput = newResult
                                result = ""
                            } catch (e: IllegalArgumentException) {
                                result = "Error"
                            }
                        }
                        operator = newOperator
                        currentInput += newOperator
                    }
                },
                onEqualsClick = {
                    if (currentInput.isNotEmpty() && operator != null) {
                        try {
                            val expression = currentInput
                            val newOperator = ""
                            result = GeezCalculator.calculateWithBODMAS(
                                expression,
                                operator!!,
                                newOperator
                            )
                            currentInput = ""
                            operator = null
                            isResultDisplayed = true
                        } catch (e: IllegalArgumentException) {
                            result = "Error"
                        } catch (e: ArithmeticException) {
                            result = "Error"
                        }
                    }
                },

                onClearClick = {
                    currentInput = ""
                    operator = null
                    result = ""
                    isResultDisplayed = false
                },
                onDecimalClick = {
                    // TODO: Handle decimal point if needed
                    result = "Can't Handle Decimal Numbers"
                },
                modifier = Modifier.padding(8.dp),
                textToSpeechEngine = textToSpeechEngine
            )
        }
    }
}

@Composable
fun ResultDisplay(input: String, result: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.End
    ) {
        if (input.isNotEmpty()) {
            AnimatedVisibility(
                visible = input.isNotEmpty(),
                enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                exit = fadeOut(animationSpec = tween(durationMillis = 300))
            ) {
                Text(
                    text = input,
                    style = TextStyle(fontSize = 24.sp, color = Color.Gray)
                )
            }
        }
        AnimatedVisibility(
            visible = result.isNotEmpty(),
            enter = scaleIn() + expandVertically(expandFrom = Alignment.Top),
            exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            Text(
                text = result,
                style = TextStyle(fontSize = 48.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}

@Composable
fun CalculatorButton(
    symbol: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface
) {
    val isOperator = symbol in listOf("+", "-", "*", "/")
    val isClear = symbol == "AC"
    val isEquals = symbol == "="

    OutlinedButton(
        onClick = onClick,
        shape = CircleShape,
        modifier = modifier
            .padding(8.dp)
            .size(72.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = when {
                isOperator -> MaterialTheme.colorScheme.surfaceVariant
                isClear -> MaterialTheme.colorScheme.tertiary
                isEquals -> MaterialTheme.colorScheme.error
                else -> backgroundColor
            },
            contentColor = when {
                isOperator -> MaterialTheme.colorScheme.onSurfaceVariant
                isClear -> MaterialTheme.colorScheme.onTertiary
                isEquals -> MaterialTheme.colorScheme.onError
                else -> contentColor
            }
        )
    ) {
        Text(
            text = symbol,
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
fun ButtonGrid(
    currentInput: String,
    operator: String?,
    onNumberClick: (String) -> Unit,
    onOperatorClick: (String) -> Unit,
    onEqualsClick: () -> Unit,
    onClearClick: () -> Unit,
    onDecimalClick: () -> Unit,
    modifier: Modifier = Modifier,
    textToSpeechEngine: TextToSpeechEngine?
) {
    val buttons = listOf(
        "፯", "፰", "፱", "/",
        "፬", "፭", "፮", "*",
        "፩", "፪", "፫", "-",
        "AC", "0", "=", "+"
    )

    Column(modifier = modifier) {
        for (i in 0 until buttons.size step 4) {
            Row {
                for (j in i until i + 4) {
                    val button = buttons[j]
                    CalculatorButton(
                        symbol = button,
                        onClick = {
                            when (button) {
                                in listOf("፩", "፪", "፫", "፬", "፭", "፮", "፯", "፰", "፱", "0") -> {
                                    onNumberClick(button)
                                    textToSpeechEngine?.speak(GeezToAmharicMapping.geezToAmharic(button))
                                }
                                in listOf("+", "-", "*", "/") -> {
                                    onOperatorClick(button)
                                    textToSpeechEngine?.speak(GeezToAmharicMapping.geezToAmharic(button))
                                }
                                "=" -> {
                                    onEqualsClick()
                                    textToSpeechEngine?.speak(GeezToAmharicMapping.geezToAmharic(button))
                                }
                                "AC" -> {
                                    onClearClick()
                                    textToSpeechEngine?.speak("አጽዳ")
                                }
                                "." -> onDecimalClick()
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun AppHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
       Spacer(modifier = Modifier.width(16.dp))
        Text(
            "Learn Ge'ez Numerals",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier.width(16.dp))

    }
}