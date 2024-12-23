package com.ttbzrs.geezcalculator

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

object EmailUtils {
    fun sendEmail(context: Context, subject: String, body: String, audioFilePath: String?) {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("zegambye@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
            audioFilePath?.let {
                val uri = Uri.parse("file://$it")
                putExtra(Intent.EXTRA_STREAM, uri)
            }
        }

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }
}

object WhatsAppUtils {
    fun sendWhatsApp(context: Context, subject: String, body: String) {
        val whatsAppIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            setPackage("com.whatsapp")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }

        try {
            context.startActivity(whatsAppIntent)
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(context, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show()
        }
    }
}

object TelegramUtils {
    fun sendTelegram(context: Context, subject: String, body: String) {
        val telegramIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            setPackage("org.telegram.messenger")
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }

        try {
            context.startActivity(telegramIntent)
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(context, "Telegram is not installed.", Toast.LENGTH_SHORT).show()
        }
    }
}