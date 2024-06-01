package com.example.babbogi.ui

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.babbogi.ui.model.BabbogiViewModel
import com.example.babbogi.ui.view.NutritionCircularGraph
import com.example.babbogi.ui.view.TitleBar
import com.example.babbogi.util.IntakeState
import com.example.babbogi.util.Nutrition
import com.example.babbogi.util.NutritionState
import com.example.babbogi.util.testNutritionState
import kotlin.math.roundToInt

@Composable
fun NutritionOverviewScreen(viewModel: BabbogiViewModel, navController: NavController) {
    NutritionOverview(
        nutritionState = viewModel.nutritionState
    )
}

@Composable
fun CircularGraphCard(nutrition: Nutrition, intake: IntakeState) {
    val targetValue = remember { (intake.getRatio() * 100).roundToInt() }
    var animatedValue by remember { mutableIntStateOf(0) }

    // 애니메이션을 시작하는 LaunchedEffect
    LaunchedEffect(targetValue) {
        animate(
            initialValue = 0F,
            targetValue = targetValue.toFloat(),
            animationSpec = tween(durationMillis = 1000) // 1초 동안 애니메이션
        ) { value, _ ->
            animatedValue = value.toInt()
        }
    }

    ElevatedCard(
        modifier = Modifier.padding(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
        ) {
            Row(modifier = Modifier.height(100.dp)) {
                Row(modifier = Modifier.width(180.dp)) {
                    NutritionCircularGraph(nutrition = nutrition, intake = intake)
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 16.dp)
                    ) {
                        Text(text = stringResource(id = nutrition.res))
                        Text(text = "/${"%.1f".format(intake.recommended)}${nutrition.unit}")
                    }
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "오늘은 적정량의")
                    Text(text = "$animatedValue%", fontSize = 32.sp)
                    Text(text = "를 드셨습니다.")
                }
            }
        }
    }
}

@Preview
@Composable
fun NutritionOverview(nutritionState: NutritionState = testNutritionState) {
    Column {
        TitleBar("영양 정보")
        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ) {
            repeat(Nutrition.entries.size) { index ->
                CircularGraphCard(
                    nutrition = Nutrition.entries[index],
                    intake = nutritionState[index]
                )
            }
        }
    }
}