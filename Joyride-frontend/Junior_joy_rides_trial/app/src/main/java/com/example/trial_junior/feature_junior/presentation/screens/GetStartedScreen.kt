package com.example.trial_junior.feature_junior.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch
import com.google.accompanist.pager.*
import com.example.trial_junior.R
import com.example.trial_junior.feature_junior.presentation.util.Screen


//
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@OptIn(ExperimentalPagerApi::class)
//@Composable
//fun GetStartedScreen(navController: NavHostController) {
//    val brandColor = Color(0xFFC5AE3D)
//    val scope = rememberCoroutineScope()
//    val pagerState = rememberPagerState()
//
//    val backgroundImages = listOf(
//        R.drawable.img_99,
//        R.drawable.img_9,
//        R.drawable.img_10
//    )
//    val titles = listOf(
//        "Book Interviews",
//        "Celebrate Birthdays",
//        "Fun & Surprises"
//    )
//    val descriptions = listOf(
//        "Easily schedule interviews for your little ones with just a few taps.",
//        "Make your child's birthday unforgettable with personalized celebrations.",
//        "Discover fun activities, engaging games, and delightful surprises."
//    )
//
//    Scaffold {
//        Column {
//            HorizontalPager(
//                count = backgroundImages.size,
//                state = pagerState,
//                contentPadding = PaddingValues(horizontal = 16.dp),
//                modifier = Modifier.weight(0.6f)
//            ) { page ->
//                Box(modifier = Modifier.fillMaxSize()) {
//                    Image(
//                        painter = painterResource(id = backgroundImages[page]),
//                        contentDescription = titles[page],
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier.fillMaxSize().alpha(0.8f)
//                    )
//                    Column(
//                        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 48.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Bottom
//                    ) {
//                        Text(
//                            text = titles[page],
//                            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White),
//                            modifier = Modifier.padding(bottom = 8.dp)
//                        )
//                        Text(
//                            text = descriptions[page],
//                            style = TextStyle(fontSize = 16.sp, color = Color.White.copy(alpha = 0.8f)),
//                            modifier = Modifier.padding(bottom = 32.dp)
//                        )
//                        Button(
//                onClick = { navController.navigate(Screen.SignupScreen.route) },
//                colors = ButtonDefaults.buttonColors(containerColor = brandColor),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(56.dp)
//                    .clip(RoundedCornerShape(16.dp)),
//                elevation = ButtonDefaults.buttonElevation(
//                    defaultElevation = 4.dp,
//                    pressedElevation = 8.dp
//                )
//            ) {
//                Text(
//                    text = "Get Started",
//                    fontSize = 18.sp,
//                    color = Color.White,
//                    fontWeight = FontWeight.SemiBold
//                )
//            }
//
//                    }
//                }
//            }
//
//            Row(
//                horizontalArrangement = Arrangement.Center,
//                modifier = Modifier.fillMaxWidth().padding(16.dp)
//            ) {
//                repeat(backgroundImages.size) { index ->
//                    Box(
//                        modifier = Modifier
//                            .width(if (index == pagerState.currentPage) 20.dp else 10.dp)
//                            .padding(end = if (index == backgroundImages.size - 1) 0.dp else 5.dp)
//                            .height(10.dp)
//                            .clip(RoundedCornerShape(10.dp))
//                            .background(
//                                if (index == pagerState.currentPage) Color.Yellow else Color.Yellow.copy(alpha = 0.5f)
//                            )
//                    )
//                }
//            }
//
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier.fillMaxWidth().padding(16.dp)
//            ) {
//                TextButton(
//                    onClick = { scope.launch { pagerState.animateScrollToPage(backgroundImages.size - 1) } },
//                    modifier = Modifier.height(56.dp)
//                ) {
//                    Text(text = "Skip")
//                }
//
//                Button(
//                    onClick = { scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) } },
//                    modifier = Modifier.height(56.dp)
//                ) {
//                    Text(text = "Next")
//                }
//            }
//        }
//    }
//}






@Composable
fun GetStartedScreen(navController: NavHostController) {
    val brandColor = Color(0xFFC5AE3D)
    var currentPage by remember { mutableIntStateOf(0) }
    val numberOfPages = 3
    val backgroundImages = listOf(
        R.drawable.img_99,
        R.drawable.img_9,
        R.drawable.img_10
    )
    val contentDescriptions = listOf(
        "Book interviews for children",
        "Order special birthday celebrations",
        "Enjoy fun activities and surprises"
    )
    val titles = listOf(
        "Book Interviews",
        "Celebrate Birthdays",
        "Fun & Surprises"
    )
    val descriptions = listOf(
        "Easily schedule interviews for your little ones with just a few taps. Connect with the right people to nurture their growth.",
        "Make your child's birthday unforgettable! Order personalized celebrations delivered right to your doorstep.",
        "Discover a world of fun activities, engaging games, and delightful surprises to make every moment special."
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF3D3D3D), Color(0xFF252525))))
    ) {
        Image(
            painter = painterResource(id = backgroundImages[currentPage]),
            contentDescription = contentDescriptions[currentPage],
            modifier = Modifier.fillMaxSize()
                .alpha(0.8f),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.2f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = titles[currentPage],
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = descriptions[currentPage],
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                ),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Button(
                onClick = { navController.navigate(Screen.SignupScreen.route) },
                colors = ButtonDefaults.buttonColors(containerColor = brandColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(16.dp)),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = { navController.navigate("learnMore") }) {
                Text(
                    "Learn More",
                    color = brandColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    textDecoration = TextDecoration.Underline
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(numberOfPages) { index ->
                    val isSelected = index == currentPage
                    Canvas(
                        modifier = Modifier
                            .size(12.dp)
                            .clickable { currentPage = index }
                    ) {
                        drawCircle(
                            color = if (isSelected) brandColor else brandColor.copy(alpha = 0.5f) // Highlight current page
                        )
                    }
                    if (index < numberOfPages - 1) {
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Junior Joyride",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }
    }
}