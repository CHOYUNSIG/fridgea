package com.example.babbogi.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.babbogi.R
import com.example.babbogi.Screen
import com.example.babbogi.ui.model.BabbogiViewModel

@Composable
fun LoadingScreen(viewModel: BabbogiViewModel, navController: NavController) {
    if (!viewModel.isServerResponding)
        navController.navigate(Screen.Home.name) {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
        }
    Loading()
}

@Composable
fun Loading() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resId = R.raw.loading))
    Box(
        modifier = Modifier.fillMaxSize(), // 화면 크기만큼 채우기
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(150.dp) // 크기를 150x150 dp로 설정
        )
    }
}

@Preview
@Composable
fun PreviewLoading() {
    Loading()
}