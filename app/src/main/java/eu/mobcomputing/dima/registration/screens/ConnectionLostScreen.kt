package eu.mobcomputing.dima.registration.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eu.mobcomputing.dima.registration.R
import eu.mobcomputing.dima.registration.components.HeaderTextComponent
import eu.mobcomputing.dima.registration.components.MyImageComponent
import eu.mobcomputing.dima.registration.components.NormalTextComponent
import eu.mobcomputing.dima.registration.utils.checkNetworkConnectivity

/**
 * Composable function representing the connection lost screen of the application.
 *
 * @param content which is the content of the application
 */
@Composable
fun ConnectionLostScreen(content: @Composable () -> Unit) {

    val context = LocalContext.current
    val isNetworkAvailable = remember { checkNetworkConnectivity(context) }

    if (isNetworkAvailable) {
        content()
    } else {

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.pink_50)),
            contentAlignment = Alignment.Center
        ) {
            val isSmallScreen = maxWidth < 600.dp

            if (isSmallScreen) {
                SmallNoInternetConnectionScreenView()
            }
            // If the screen is wider, if it is a tablet
            else {
                WideNoInternetConnectionScreenView()
            }
        }
    }
}

/**
 * Composable function representing the NoInternet screen of the application for small screens (mobile).
 */
@Composable
fun SmallNoInternetConnectionScreenView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .wrapContentSize()
        ) {
            Icon(
                imageVector = Icons.Filled.WifiOff,
                contentDescription = "no connection icon",
                modifier = Modifier.fillMaxSize()
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center)
            ) {
                HeaderTextComponent(
                    value = stringResource(R.string.connection_not_available),
                    shouldBeRed = true,
                    testTag = "no connection header"
                )
                NormalTextComponent(
                    value = stringResource(R.string.i_need_conection_txt),
                    testTag = "no connection text"
                )
            }

        }
    }
}

/**
 * Composable function representing the NoInternet screen of the application for wider screens (tablet).
 */
@Composable
fun WideNoInternetConnectionScreenView(
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
        ) {
            MyImageComponent(
                R.drawable.sous_chef,
                modifier = Modifier.fillMaxSize(),
                imageDesc = "app logo"
            )
        }

        SmallNoInternetConnectionScreenView()
    }
}


@Preview(device = Devices.PIXEL_TABLET)
@Preview(device = Devices.PIXEL_6)
@Composable
fun ConnectionLostScreenPreview() {
    ConnectionLostScreen {}
}