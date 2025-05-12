package com.example.trial_junior.feature_junior.presentation.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.trial_junior.feature_junior.presentation.components.AppBottomNavigation
import com.example.trial_junior.feature_junior.presentation.components.HalfScreenMenu
import com.example.trial_junior.R
import kotlinx.coroutines.launch

data class FeaturedShow(val title: String, val imageResourceId: Int, val schedule: String, val youtubeVideoId: String? = null)
data class EventItem(val title: String, val description: String, val imageResourceId: Int, val youtubeVideoId: String? = null)

@SuppressLint("RememberReturnType")
@Composable
fun LandingScreen(navController: NavHostController, modifier: Modifier) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val eventList = remember {
        listOf(
            EventItem(
                "TV Appearances",
                "Be a star on our show!",
                R.drawable.ethiopis_1,
                "YwIGTtTdHY4"
            ),
            EventItem(
                "Birthday Celebrations",
                "Celebrate with Etopis!",
                R.drawable.tv_shows,
                "90w2RegGf9w"
            ),
            EventItem(
                "Talent Showcase",
                "Show us your amazing talents!",
                R.drawable.talent,
                "cgpOqSIffZ8"
            )
        )
    }
    val featuredShows = remember {
        listOf(
            FeaturedShow("Wonder Kids Special", R.drawable.img_1, "Today, 3:00 PM", "513a1r65OZY"),
            FeaturedShow("Birthday Bash", R.drawable.img_2, "Today, 3:00 PM", "513a1r65OZY"),
            FeaturedShow("Adventure Time", R.drawable.img_3, "Tomorrow, 11:00 AM", null),
            FeaturedShow("Story Land", R.drawable.img_2, "Saturday, 2:00 PM", "513a1r65OZY"),
            FeaturedShow("Fun with Colors", R.drawable.img_1, "Sunday, 9:00 AM", null),
            FeaturedShow("More Fun Show", R.drawable.img_3, "Monday, 1:00 PM", "513a1r65OZY")
        )
    }
    val mainListState = rememberLazyListState()

    var featuredShowsIndex by remember { mutableStateOf(0) }
    var upcomingEventsIndex by remember { mutableStateOf(0) }
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.widthIn(max = 300.dp)
            ) {
                HalfScreenMenu(
                    navController = navController,
                    isMenuOpen = remember { mutableStateOf(drawerState.isOpen) }
                ) {
                    scope.launch { drawerState.close() }
                }
            }
        },
        content = {
            Scaffold(
                bottomBar = {
                    AppBottomNavigation(navController = navController)
                }
            ) { innerPadding ->
                LazyColumn(
                    state = mainListState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(Color.White),
                    verticalArrangement = Arrangement.Top,
                    contentPadding = PaddingValues(bottom = 86.dp)
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(Color(0xFFFFFACD), Color(0xFFFFFFE0))
                                    )
                                )
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val width = size.width
                                val height = size.height
                                drawCircle(
                                    color = Color(0xFFADD8E6).copy(alpha = 0.6f),
                                    radius = 60.dp.toPx(),
                                    center = Offset(width * 0.4f, height * 0.4f)
                                )
                                drawCircle(
                                    color = Color(0xFFFFA07A).copy(alpha = 0.6f),
                                    radius = 55.dp.toPx(),
                                    center = Offset(width * 0.5f, height * 0.45f)
                                )
                                drawCircle(
                                    color = Color(0xFF98FB98).copy(alpha = 0.6f),
                                    radius = 65.dp.toPx(),
                                    center = Offset(width * 0.45f, height * 0.7f)
                                )
                            }
                            Text(
                                text = "Welcome\nto Junior\nJoyride",
                                style = TextStyle(
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                ),
                                modifier = Modifier
                                    .padding(start = 20.dp, top = 66.dp)
                                    .align(Alignment.TopStart)
                            )
                            Image(
                                painter = painterResource(id = R.drawable.img),
                                contentDescription = "Junior Joyride Character",
                                modifier = Modifier
                                    .padding(top = 68.dp, end = 5.dp)
                                    .width(200.dp)
                                    .height(300.dp)
                                    .align(Alignment.TopEnd)
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 8.dp,
                                            topEnd = 8.dp,
                                            bottomStart = 8.dp,
                                            bottomEnd = 8.dp
                                        )
                                    ),
                                contentScale = ContentScale.Fit
                            )
                            IconButton(
                                onClick = { scope.launch { drawerState.open() } },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 26.dp, end = 16.dp)
                            ) {
                                Icon(
                                    Icons.Filled.Menu,
                                    contentDescription = "Menu",
                                    tint = Color.Black
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .shadow(
                                    elevation = 4.dp,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "1.2k",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                )
                                Text(
                                    text = "Happy Kids",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                )
                            }
                            Divider(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(24.dp), color = Color.LightGray
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "250+",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                )
                                Text(
                                    text = "Shows",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                )
                            }
                            Divider(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(24.dp), color = Color.LightGray
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "4.9",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                )
                                Text(
                                    text = "Rating",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                selected = selectedTabIndex == 2,
                                onClick = {
                                    selectedTabIndex = 2
                                    scope.launch {
                                        mainListState.animateScrollToItem(
                                            upcomingEventsIndex + 1
                                        )
                                    }
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Star,
                                        contentDescription = "Star"
                                    )
                                },
                                label = { Text("All Shows") }
                            )

                            FilterChip(
                                selected = selectedTabIndex == 3,
                                onClick = {
                                    selectedTabIndex = 3
                                    scope.launch {
                                        mainListState.animateScrollToItem(
                                            upcomingEventsIndex + 4
                                        )
                                    }
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Star,
                                        contentDescription = "Star"
                                    )
                                },
                                label = { Text("Special") }
                            )
                            FilterChip(
                                selected = selectedTabIndex == 4,
                                onClick = {
                                    selectedTabIndex = 4
                                    scope.launch {
                                        mainListState.animateScrollToItem(upcomingEventsIndex + 7)
                                    }
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Star,
                                        contentDescription = "Star"
                                    )
                                },
                                label = { Text("Birthday") }
                            )
                        }
                    }
                    item {
                        featuredShowsIndex = 1
                        Column(modifier = Modifier.fillMaxWidth().padding(3.dp)) {
                            Text(
                                text = "Featured Shows",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                ),
                                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                            )
                            val itemsPerRow = 2
                            val pageCount = (featuredShows.size + itemsPerRow - 1) / itemsPerRow
                            var currentPage by remember { mutableStateOf(0) }
                            val listState = rememberLazyListState()
                            LaunchedEffect(currentPage) {
                                listState.animateScrollToItem(currentPage * itemsPerRow)
                            }
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .padding(bottom = 2.dp),
                                contentPadding = PaddingValues(start = 16.dp),
                                state = listState,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(featuredShows.size) { index ->
                                    FeaturedShowItem(show = featuredShows[index])
                                }
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 0.dp, start = 16.dp),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                for (i in 0 until pageCount) {
                                    val color = if (i == currentPage) Color.Gray else Color.LightGray
                                    Canvas(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clickable { currentPage = i }
                                    ) {
                                        drawCircle(color = color)
                                    }
                                    if (i < pageCount - 1) {
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                }
                            }
                        }
                    }
                    item {
                        upcomingEventsIndex = 2
                        Text(
                            text = "Upcoming Events",
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                        )
                    }
                    items(eventList.size) { index ->
                        EventItemView(eventList[index])

                    }
                }
            }
        }
    )
}

@Composable
fun EventItemView(event: EventItem) {
    var isImageClicked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.White, RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        if (!isImageClicked) {
            Image(
                painter = painterResource(id = event.imageResourceId),
                contentDescription = event.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { event.youtubeVideoId?.let { isImageClicked = true } },
                contentScale = ContentScale.Crop
            )
        } else if (event.youtubeVideoId != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                VideoPlayer(
                    youtubeVideoId = event.youtubeVideoId,
                    lifecycleOwner = LocalLifecycleOwner.current,
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = event.title,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = event.description,
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray
            )
        )
    }
}

@Composable
fun FeaturedShowItem(show: FeaturedShow) {
    var isImageClicked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .width(200.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            )
            .background(Color.White, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isImageClicked) {
            Image(
                painter = painterResource(id = show.imageResourceId),
                contentDescription = show.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp
                        )
                    )
                    .clickable { show.youtubeVideoId?.let { isImageClicked = true } },
                contentScale = ContentScale.Crop
            )
        } else if (show.youtubeVideoId != null) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                VideoPlayer(
                    youtubeVideoId = show.youtubeVideoId,
                    lifecycleOwner = LocalLifecycleOwner.current,
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = show.title,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Schedule Icon",
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = show.schedule,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewLandingScreenTopSection() {
    val navController = androidx.navigation.compose.rememberNavController()
    LandingScreen(navController = navController, modifier = Modifier)
}