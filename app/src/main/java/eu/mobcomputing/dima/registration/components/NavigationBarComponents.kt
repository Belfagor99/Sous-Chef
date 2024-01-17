package eu.mobcomputing.dima.registration.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.models.BottomNavigationItem
import eu.mobcomputing.dima.registration.navigation.Screen


/**
 * NavigationBarComponent: A composable function for creating a bottom navigation bar.
 *
 * This composable uses the Scaffold and NavigationBar components to create a bottom navigation bar
 * with items specified in the [items] parameter. Each item is represented by a NavigationBarItem, and
 * clicking on an item triggers navigation to the associated screen using the provided [navController].
 *
 * @param navController The NavController used for navigating between screens.
 * @param items A list of BottomNavigationItem objects representing navigation items.
 * @param selectedItemIndex The index of the currently selected navigation item.
 *
 */
@Composable
fun NavigationBarComponent(
    navController: NavController,
    items: List<BottomNavigationItem> = getBottomNavigationItems(),
    selectedItemIndex: Int
) {
//    Scaffold(
//        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, bottomNavigationItem ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            navController.navigate(bottomNavigationItem.screen.route) {
                                navController.graph.startDestinationRoute?.let { screenRoute ->
                                    popUpTo(screenRoute) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                            }
                        },
                        label = {
                            Text(bottomNavigationItem.title)
                        },
                        alwaysShowLabel = true,
                        icon = {
                            if (selectedItemIndex == index) {
                                Image(
                                    painter = painterResource(id = bottomNavigationItem.selectedIcon),
                                    contentDescription = bottomNavigationItem.title,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = bottomNavigationItem.unselectedIcon),
                                    contentDescription = bottomNavigationItem.title,
                                    modifier = Modifier.size(24.dp)
                                )

                            }
                        }
                    )
                }
            }
//        }
//    )
//    { it ->
//        ScreenContent(innerPadding = it)
//    }
}

/**
 * getBottomNavigationItems: A utility function to provide a list of default BottomNavigationItem objects.
 *
 * @return A list of BottomNavigationItem objects.
 */
@Composable
private fun getBottomNavigationItems(): List<BottomNavigationItem> {
    return listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = R.drawable.home_icon_selected,
            unselectedIcon = R.drawable.home_icon_not_selected,
            selected = true,
            screen = Screen.Home
        ),
        BottomNavigationItem(
            title = "Pantry",
            selectedIcon = R.drawable.fridge_icon_selected,
            unselectedIcon = R.drawable.fridge_icon_not_selected,
            screen = Screen.Pantry
        ),
        BottomNavigationItem(
            title = "Profile",
            selectedIcon = R.drawable.profile_icon_selected,
            unselectedIcon = R.drawable.profile_icon_not_selected,
            screen = Screen.Profile
        ),
        BottomNavigationItem(
            title = "Add Ingr",
            selectedIcon = R.drawable.add_ingredient_icon_selected,
            unselectedIcon = R.drawable.add_ingredients_icon_not_selected,
            screen = Screen.SearchIngredientScreen
        )
    )
}

/**
 * ScreenContent: A composable function for displaying the main content of the screen.
 *
 * This composable is meant to be used as the content parameter of the Scaffold in the NavigationBarComponent.
 * It can be replaced with the actual content components of the screen.
 *
 * @param innerPadding The PaddingValues applied to the inner content, useful for applying padding to content components.
 *
 */
/*@Composable
fun ScreenContent(innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        // there would be content of the screen with bottom navigation
    }
}*/

@Preview
@Composable
fun NavigationBarPreview() {
    MaterialTheme {
        NavigationBarComponent(navController = rememberNavController(),
            getBottomNavigationItems(),1)
    }
}


