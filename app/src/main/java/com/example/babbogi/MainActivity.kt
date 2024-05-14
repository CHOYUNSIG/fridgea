package com.example.babbogi

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.babbogi.ui.CameraScreen
import com.example.babbogi.ui.MainScreen
import com.example.babbogi.ui.NutritionOverviewScreen
import com.example.babbogi.ui.model.BabbogiViewModel
import com.example.babbogi.ui.model.BabbogiViewModelFactory
import com.example.babbogi.ui.theme.BabbogiTheme

enum class BabbogiScreen {
    Home,
    NutritionOverview,
    Camera,
}

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(
            owner = this,
            factory = BabbogiViewModelFactory()
        )[BabbogiViewModel::class.java]

        setContent {
            BabbogiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainApp(viewModel = viewModel)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp(
    navController: NavHostController = rememberNavController(),
    viewModel: BabbogiViewModel
) {
    NavHost(
        navController = navController,
        startDestination = BabbogiScreen.Home.name
    ) {
        composable(route = BabbogiScreen.Home.name) {
            MainScreen(navController = navController)
        }
        composable(route = BabbogiScreen.NutritionOverview.name) {
            NutritionOverviewScreen()
        }
        composable(route = BabbogiScreen.Camera.name) {
            CameraScreen(viewModel = viewModel)
        }
    }
}