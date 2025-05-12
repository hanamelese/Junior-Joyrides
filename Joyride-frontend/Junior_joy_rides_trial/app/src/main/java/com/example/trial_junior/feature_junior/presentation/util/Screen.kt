package com.example.trial_junior.feature_junior.presentation.util

sealed class Screen(val route: String) {
    object InvitationItemListScreen: Screen("invitationItemList_screen")
    object InvitationNewUpdateScreen: Screen("invitationNewUpdate_screen")

    object ExampleScreen: Screen("example_screen")
    object WishListUpdateScreen: Screen("wishList_update_screen")



    object LandingScreen: Screen("landing_screen")
    object GetStartedScreen: Screen("get_started_screen")

    object SignupScreen: Screen("signup_screen")
    object LoginScreen: Screen("login_screen")
    object ProfileScreen: Screen("profile_screen")
    object EditProfileScreen: Screen("edit_profile_screen")
    object AdminLoginScreen: Screen("admin_login_screen")
    object AdminDashboardScreen: Screen("admin_dashboard_screen")

    object BasicInterviewScreen: Screen("basic_interview_screen")
    object SpecialInterviewScreen: Screen("special_interview_screen")
    object WishListScreen: Screen("wish_list_screen")
    object InvitationScreen: Screen("invitation_screen")

    object PrivacyAndPolicyScreen: Screen("privacy_and_policy_screen")



}