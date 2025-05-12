package com.example.trial_junior.feature_junior.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PrivacyAndPolicyScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Privacy Policy for Joyride",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 8.dp),
            thickness = 1.dp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Introduction",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Your privacy is important to us. This Privacy Policy explains how Joyride, a mobile app that brings joy and celebration to young children in Ethiopia, collects, uses, and protects your information.",
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Information We Collect",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
            .padding(8.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "• ",
                    fontSize = 18.sp
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Personal Information: ")
                        }
                        append(" We may collect personal information such as your name, email address, and phone number when you create an account or provide it about your child.")
                    },
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "• ",
                    fontSize = 18.sp
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Child Information: ")
                        }
                        append(" You may provide information about your child, including their name, age, and photos for birthday events.")
                    },
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "• ",
                    fontSize = 18.sp
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Usage Data: ")
                        }
                        append(" We automatically collect information about how you use the app, including device type, operating system, and the features you access.")
                    },
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "How We Use Your Information",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We use the information we collect for the following purposes:\n" +
                    "• Account Management: To create and manage your user account.\n" +
                    "• Event Booking: To facilitate the booking of birthday celebrations and interviews for your child.\n" +
                    "• Notifications: To send reminders and notifications for upcoming events.\n" +
                    "• Customer Support: To respond to your inquiries and provide assistance.\n" +
                    "• Analytics: To analyze user engagement and improve our app.",
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Security of Your Information",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We take the security of your information seriously. We implement measures to protect your information from unauthorized access and disclosure.",
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Changes to This Privacy Policy",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We may update this Privacy Policy from time to time. We will notify you of any changes by posting the updated Privacy Policy on this page.",
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Contact",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "If you have any questions about this Privacy Policy, please contact us at:\n" +
                    "- Email: privacy@joyrideapp.com\n" +
                    "- Address: Bole",
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth()
        )
    }
}