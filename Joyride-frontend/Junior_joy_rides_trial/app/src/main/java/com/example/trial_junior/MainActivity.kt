package com.example.trial_junior

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.trial_junior.feature_junior.presentation.screens.AdminDashboardScreen
import com.example.trial_junior.feature_junior.presentation.screens.AdminLoginScreen
import com.example.trial_junior.feature_junior.presentation.screens.BasicInterviewScreen
import com.example.trial_junior.feature_junior.presentation.screens.EditProfileScreen
import com.example.trial_junior.feature_junior.presentation.screens.GetStartedScreen
import com.example.trial_junior.feature_junior.presentation.screens.InvitationScreen
import com.example.trial_junior.feature_junior.presentation.screens.LandingScreen
import com.example.trial_junior.feature_junior.presentation.screens.LoginScreen
import com.example.trial_junior.feature_junior.presentation.screens.PrivacyAndPolicyScreen
import com.example.trial_junior.feature_junior.presentation.screens.ProfileScreen
import com.example.trial_junior.feature_junior.presentation.screens.SignupScreen
import com.example.trial_junior.feature_junior.presentation.screens.SpecialInterviewScreen
import com.example.trial_junior.feature_junior.presentation.screens.WishListNewUpdateScreen
import com.example.trial_junior.feature_junior.presentation.screens.WishListScreen
import com.example.trial_junior.feature_junior.presentation.util.Screen
import com.example.trial_junior.feature_junior.presentation.viewModels.BasicInterviewListViewModel
import com.example.trial_junior.feature_junior.presentation.viewModels.InvitationListViewModel
import com.example.trial_junior.feature_junior.presentation.viewModels.SpecialInterviewListViewModel
import com.example.trial_junior.feature_junior.presentation.viewModels.UserViewModel
import com.example.trial_junior.feature_junior.presentation.viewModels.WishListViewModel
import com.example.trial_junior.ui.theme.TrialJuniorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrialJuniorTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val invitationViewModel: InvitationListViewModel = hiltViewModel()
                    val wishlistViewModel: WishListViewModel = hiltViewModel()
                    val basicInterviewViewModel: BasicInterviewListViewModel = hiltViewModel()
                    val specialInterviewViewModel: SpecialInterviewListViewModel = hiltViewModel()
                    val userViewModel: UserViewModel = hiltViewModel()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.GetStartedScreen.route
                    ) {
                        composable(route = Screen.GetStartedScreen.route) {
                            GetStartedScreen(
                                navController = navController
                            )
                        }
                        composable(route = Screen.SignupScreen.route) {
                            SignupScreen(
                                navController = navController,
                                viewModel = userViewModel
                            )
                        }
                        composable(route = Screen.LoginScreen.route) {
                            LoginScreen(
                                navController = navController,
                                viewModel = userViewModel
                            )
                        }
                        composable(route = Screen.AdminLoginScreen.route) {
                            AdminLoginScreen(
                                navController = navController,
                                viewModel = userViewModel
                            )
                        }
                        composable(route = Screen.AdminDashboardScreen.route) {
                            AdminDashboardScreen(
                                navController = navController,
                                modifier = Modifier,
                                invitationViewModel = invitationViewModel,
                                wishListViewModel = wishlistViewModel,
                                basicInterviewViewModel = basicInterviewViewModel,
                                specialInterviewViewModel = specialInterviewViewModel
                            )
                        }
                        composable(route = Screen.PrivacyAndPolicyScreen.route) {
                            PrivacyAndPolicyScreen()
                        }
                        composable(route = Screen.LandingScreen.route) {
                            LandingScreen(
                                navController = navController,
                                modifier = Modifier
                            )
                        }
                        composable(route = Screen.ProfileScreen.route) {
                            ProfileScreen(
                                navController = navController,
                                userViewModel = userViewModel,
                                invitationViewModel = invitationViewModel,
                                wishListViewModel = wishlistViewModel,
                                basicInterviewViewModel = basicInterviewViewModel,
                                specialInterviewViewModel = specialInterviewViewModel
                            )
                        }
                        composable(route = Screen.EditProfileScreen.route) {
                            EditProfileScreen(
                                navController = navController,
                                viewModel = userViewModel
                            )
                        }
                        composable(
                            route = Screen.WishListScreen.route + "?wishListId={wishListId}",
                            arguments = listOf(
                                navArgument(
                                    name = "wishListId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            WishListScreen(navController = navController,)
                        }
                        composable(
                            route = Screen.BasicInterviewScreen.route + "?basicInterviewId={basicInterviewId}",
                            arguments = listOf(
                                navArgument(
                                    name = "basicInterviewId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            BasicInterviewScreen(navController = navController,)
                        }
                        composable(
                            route = Screen.SpecialInterviewScreen.route + "?specialInterviewId={specialInterviewId}",
                            arguments = listOf(
                                navArgument(
                                    name = "specialInterviewId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            SpecialInterviewScreen(navController = navController,)
                        }
                        composable(
                            route = Screen.InvitationScreen.route + "?invitationId={invitationId}",
                            arguments = listOf(
                                navArgument(
                                    name = "invitationId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            InvitationScreen(navController = navController)
                        }




//                        composable(route = Screen.ExampleScreen.route) {
//                            ExampleScreen(
//                                navController = navController,
//                                invitationViewModel = invitationViewModel,
//                                wishlistViewModel = wishlistViewModel,
//                                basicInterviewViewModel = basicInterviewViewModel,
//                                specialInterviewViewModel = specialInterviewViewModel
//                            )
//                        }
//                        composable(
//                            route = Screen.WishListUpdateScreen.route + "?wishListId={wishListId}",
//                            arguments = listOf(
//                                navArgument(
//                                    name = "wishListId"
//                                ) {
//                                    type = NavType.IntType
//                                    defaultValue = -1
//                                }
//                            )
//                        ) {
//                            WishListNewUpdateScreen(
//                                navController = navController
//                            )
//                        }
                        // Placeholder for InvitationNewUpdateScreen route if needed
                        // composable(route = Screen.InvitationNewUpdateScreen.route) { ... }
                    }

                }
            }
        }
    }
}
