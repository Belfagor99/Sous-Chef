package eu.mobcomputing.dima.registration.models

import eu.mobcomputing.dima.registration.navigation.Screen


/**
 * Data class representing an item in the bottom navigation bar.
 *
 * @property title The title or label of the navigation item.
 * @property selectedIcon The resource ID of the icon when the item is selected.
 * @property unselectedIcon The resource ID of the icon when the item is not selected.
 * @property selected Indicates whether the item is currently selected in the navigation bar.
 * @property screen The associated screen destination for navigation.
 */
data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val selected: Boolean = false,
    val screen: Screen
)

