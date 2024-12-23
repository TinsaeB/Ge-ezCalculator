package com.ttbzrs.geezcalculator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ttbzrs.geezcalculator.ui.theme.GeezCalculatorTheme

class InfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeezCalculatorTheme {
                InfoScreen()
            }
        }
    }
}

@Composable
fun InfoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "About the Ge'ez Calculator",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "The Ge'ez Calculator is a unique tool designed to perform arithmetic calculations using the Ge'ez numeral system. " +
                    "This system, originating from ancient Ethiopia, is distinct from the commonly used Arabic numeral system. " +
                    "The calculator supports basic arithmetic operations such as addition, subtraction, multiplication, and division.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "How to Use",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Using the Ge'ez Calculator is straightforward. " +
                    "Input your numbers using the provided Ge'ez numerals, select the desired operation, and press '=' to get the result. " +
                    "The 'AC' button clears the current input. For a detailed guide on Ge'ez numerals, refer to the provided link.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Supported Ge'ez Numerals",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "The calculator supports the following Ge'ez numerals:\n" +
                    "፩ (1), ፪ (2), ፫ (3), ፬ (4), ፭ (5), ፮ (6), ፯ (7), ፰ (8), ፱ (9), ፲ (10), " +
                    "፳ (20), ፴ (30), ፵ (40), ፶ (50), ፷ (60), ፸ (70), ፹ (80), ፺ (90), " +
                    "፻ (100), ፼ (10,000)",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Calculation Range",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "• Minimum Calculation: 1 - 10,000\n" +
                    "• Maximum Calculation: 10,000 * 10,000 (100,000,000)",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Possible Use Cases",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "This calculator can be used for educational purposes, helping students learn about different numeral systems. " +
                    "It's also a great tool for those interested in Ethiopian culture and history. " +
                    "Additionally, it serves as a practical tool for anyone needing to perform calculations in Ge'ez numerals.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}