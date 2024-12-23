package com.ttbzrs.geezcalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ttbzrs.geezcalculator.ui.theme.GeezCalculatorTheme
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.TextUnit

class FeedbackActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GeezCalculatorTheme {
                FeedbackScreen()
            }
        }
    }
}

@Composable
fun FeedbackScreen() {
    val context = LocalContext.current
    val buttonFontSize = 16.sp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            "Send Feedback",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        FeedbackButton(
            onClick = {
                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:zegambye@gmail.com")
                    putExtra(Intent.EXTRA_SUBJECT, "Feedback on Ge'ez Calculator")
                }
                if (isCallable(context, emailIntent)) {
                    context.startActivity(emailIntent)
                } else {
                    Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show()
                }
            },
            icon = R.drawable.email,
            text = "Send via Email",
            color = Color(0xFFdb4a39),
            fontSize = buttonFontSize
        )
    }
}

@Composable
fun FeedbackButton(onClick: () -> Unit, icon: Int, text: String, color: Color, fontSize: androidx.compose.ui.unit.TextUnit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        contentPadding = PaddingValues(16.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "Icon",
            modifier = Modifier.size(ButtonDefaults.IconSize),
            colorFilter = ColorFilter.tint(Color.White)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(text, color = Color.White, fontSize = fontSize)
    }
}

@SuppressLint("QueryPermissionsNeeded")
private fun isCallable(context: Context, intent: Intent): Boolean {
    val activities = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
    return activities.isNotEmpty()
}