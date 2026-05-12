package com.prathamchikitse.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.prathamchikitse.model.AppLanguage
import com.prathamchikitse.screens.DetailScreen
import com.prathamchikitse.screens.HomeScreen
import com.prathamchikitse.screens.HospitalScreen
import com.prathamchikitse.screens.SplashScreen

object Routes {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val DETAIL = "detail/{emergencyId}"
    const val HOSPITALS = "hospitals"

    fun detail(emergencyId: String) = "detail/$emergencyId"
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    currentLanguage: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                onSplashComplete = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                currentLanguage = currentLanguage,
                onLanguageChange = onLanguageChange,
                onEmergencyClick = { emergencyId ->
                    navController.navigate(Routes.detail(emergencyId))
                },
                onHospitalClick = {
                    navController.navigate(Routes.HOSPITALS)
                }
            )
        }

        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("emergencyId") { type = NavType.StringType })
        ) { backStackEntry ->
            val emergencyId = backStackEntry.arguments?.getString("emergencyId") ?: ""
            DetailScreen(
                emergencyId = emergencyId,
                currentLanguage = currentLanguage,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.HOSPITALS) {
            HospitalScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
