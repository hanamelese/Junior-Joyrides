package com.example.trial_junior.feature_junior.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BarGraph(modifier: Modifier = Modifier, data: List<Int>) {
    val textMeasurer = rememberTextMeasurer()
    val dayLabels = listOf("Invitations", "Wishes", "Special interviews", "Basic interviews")
    val paddingFromAxes = 20.dp
    val graphHeight = 250.dp

    Card(
        modifier = modifier.padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(graphHeight)
            ) {
                val barWidth = (size.width - 2 * paddingFromAxes.toPx()) / (data.size * 2)
                val maxVal = 100 // Max percentage value (100%)
                val graphHeightPx = graphHeight.toPx() - 2 * paddingFromAxes.toPx()
                val scaleFactor = graphHeightPx / maxVal.toFloat()

                val originX = paddingFromAxes.toPx()
                val originY = size.height - paddingFromAxes.toPx()

                // Draw x-axis
                drawLine(
                    color = Color.Black,
                    start = Offset(originX, originY),
                    end = Offset(size.width - paddingFromAxes.toPx(), originY),
                    strokeWidth = 2f
                )

                // Draw y-axis
                drawLine(
                    color = Color.Black,
                    start = Offset(originX, originY),
                    end = Offset(originX, paddingFromAxes.toPx()),
                    strokeWidth = 2f
                )

                // Draw bars
                data.forEachIndexed { index, value ->
                    val barLeft = originX + (barWidth * 2 * index) + barWidth / 2
                    val barTop = originY - (value * scaleFactor)
                    val barRight = barLeft + barWidth

                    drawRect(
                        color = Color(0xFF344BFD),
                        topLeft = Offset(barLeft, barTop),
                        size = Size(barWidth, value * scaleFactor)
                    )
                }

                // Draw y-axis labels (percentages: 0% to 100%)
                val yAxisLabelCount = 5
                val yAxisStep = maxVal / yAxisLabelCount
                for (i in 0..yAxisLabelCount) {
                    val yValue = i * yAxisStep
                    val yPosition = originY - (yValue * scaleFactor)
                    val yLabel = "$yValue%"
                    val textLayoutResult = textMeasurer.measure(
                        text = yLabel,
                        style = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Right)
                    )
                    val yLabelX = originX - 10.dp.toPx() - textLayoutResult.size.width
                    val yLabelY = yPosition + textLayoutResult.size.height / 2

                    drawIntoCanvas {
                        it.nativeCanvas.drawText(
                            yLabel,
                            yLabelX,
                            yLabelY,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.BLACK
                                textSize = 12.sp.toPx()
                                textAlign = android.graphics.Paint.Align.LEFT
                            }
                        )
                    }
                }
            }

            // X-axis labels below the graph
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                dayLabels.forEachIndexed { index, label ->
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        softWrap = true,
                        modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BarGraphScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Bar Graph", fontSize = 24.sp)
        BarGraph(data = listOf(10, 30, 20, 60)) // Example data (percentages)
    }
}

@Preview(showBackground = true)
@Composable
fun BarGraphScreenPreview() {
    BarGraphScreen()
}