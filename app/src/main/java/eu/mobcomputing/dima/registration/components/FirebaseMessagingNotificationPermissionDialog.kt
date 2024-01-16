package eu.mobcomputing.dima.registration.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.utils.Constants

/**
 * Composable function to display a notification permission dialog.
 *
 * @param showNotificationDialog A mutable state variable to control the visibility of the dialog.
 * @param notificationPermissionState Represents the state of the notification permission.
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FirebaseMessagingNotificationPermissionDialog(
    showNotificationDialog: MutableState<Boolean>,
    notificationPermissionState: PermissionState
) {
    if (showNotificationDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showNotificationDialog.value = false
                notificationPermissionState.launchPermissionRequest()
            },
            title = { Text(text = stringResource(R.string.notification_permission)) },
            text = { Text(text = stringResource(R.string.please_allow_this_app_to_send_you_a_notification)) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.pot_svgrepo_com),
                    contentDescription = null
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showNotificationDialog.value = false
                    notificationPermissionState.launchPermissionRequest()
                    Firebase.messaging.subscribeToTopic(Constants.TOPIC)
                }) {
                    Text(text = stringResource(R.string.ok))
                }
            }
        )
    }
}