package com.example.joyrides.feature_junior.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trial_junior.R
import com.example.trial_junior.feature_junior.presentation.screens.WishListItem
import com.example.trial_junior.feature_junior.presentation.viewModels.WishListViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.Image
import coil.compose.rememberAsyncImagePainter

@Composable
fun WeekNavigator(viewModel: WishListViewModel) {
    val state by viewModel.state

    var currentDayIndex by remember { mutableStateOf(0) }
    val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    fun getWeekdayNameFromDate(dateStr: String): String {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = sdf.parse(dateStr) ?: return ""
            val calendar = Calendar.getInstance()
            calendar.time = date
            when (calendar.get(Calendar.DAY_OF_WEEK)) {
                Calendar.MONDAY -> "Monday"
                Calendar.TUESDAY -> "Tuesday"
                Calendar.WEDNESDAY -> "Wednesday"
                Calendar.THURSDAY -> "Thursday"
                Calendar.FRIDAY -> "Friday"
                Calendar.SATURDAY -> "Saturday"
                Calendar.SUNDAY -> "Sunday"
                else -> ""
            }
        } catch (e: Exception) {
            ""
        }
    }

    val itemsGroupedByDay = remember(state.items) {
        state.items.groupBy { getWeekdayNameFromDate(it.date) }
    }

    val currentDay = days.getOrNull(currentDayIndex) ?: "Unknown Day"
    val currentItems = itemsGroupedByDay[currentDay]?.map { item ->
        WishListItem(
            childName = item.childName,
            age = item.age,
            date = item.date
        )
    } ?: emptyList()

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.fillMaxWidth(0.7f).padding(end = 8.dp)) {
                    Text(text = currentDay, style = MaterialTheme.typography.headlineMedium)
                    Text(text = "Birthday on $currentDay", fontSize = 17.sp)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        if (currentDayIndex > 0) {
                            Text(
                                text = "<",
                                fontSize = 24.sp, // Adjust text size
                                color = Color.Black,
                                modifier = Modifier.clickable { currentDayIndex-- } // Make text clickable
                            )
                        }
                        if (currentDayIndex < days.lastIndex) {
                            Text(
                                text = ">",
                                fontSize = 24.sp, // Adjust text size
                                color = Color.Black,
                                modifier = Modifier.clickable { currentDayIndex++ } // Make text clickable
                            )
                        }

                    }
                }
            }

            ImageCardNavigator(items = currentItems)
        }
    }
}

@Composable
fun ImageCardNavigator(items: List<WishListItem>) {
    var currentIndex by remember { mutableStateOf(0) }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth().height(250.dp).padding(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (currentIndex < items.size) {
                    WishListItemCard(item = items[currentIndex])
                }

                if (currentIndex + 1 < items.size) {
                    WishListItemCard(item = items[currentIndex + 1])
                }
            }

            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
                if (currentIndex > 0) {
                    Button(onClick = { currentIndex = maxOf(0, currentIndex - 2) }, modifier = Modifier.padding(bottom = 8.dp)) {
                        Text("<", fontSize = 30.sp, color = Color.Black)
                    }
                }
                if (currentIndex + 2 < items.size) {
                    Button(onClick = { currentIndex = minOf(items.size - 1, currentIndex + 2) }) {
                        Text(">", fontSize = 30.sp, color = Color.Black)
                    }
                }
            }
        }
    }
}
//
//@Composable
//fun WishListItemCard(item: WishListItem) {
//    Column(
//        modifier = Modifier.fillMaxHeight(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Image(
//            painter = painterResource(R.drawable.profile),
//            contentDescription = "Child Image",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.size(145.dp)
//        )
//        Text(text = item.childName, style = MaterialTheme.typography.bodyMedium)
//        Text(text = "Age: ${item.age}", fontSize = 14.sp)
//    }
//}



@Composable
fun WishListItemCard(item: WishListItem) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val painter = rememberAsyncImagePainter(model = item.imageUrl)
        Image(
            painter = painter,
            contentDescription = "Child Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(145.dp)
        )
        Text(text = item.childName, style = MaterialTheme.typography.bodyMedium)
        Text(text = "Age: ${item.age}", fontSize = 14.sp)
    }
}
