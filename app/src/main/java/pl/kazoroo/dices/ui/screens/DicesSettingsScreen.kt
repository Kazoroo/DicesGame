package pl.kazoroo.dices.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.github.skydoves.colorpicker.compose.*
import pl.kazoroo.dices.data.PreferencesViewModel
import pl.kazoroo.dices.navigation.items
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    navController: NavController,
    preferencesViewModel: PreferencesViewModel = viewModel(factory = PreferencesViewModel.Factory)
) {

    val appColor = MaterialTheme.colorScheme.primary

    if(preferencesViewModel.showPopup) {
        Popup(
            alignment = Alignment.TopCenter,
            offset = IntOffset(0, 25)
        ) {
            Box(
                modifier = Modifier
                    .background(appColor, RoundedCornerShape(6.dp))
                    .height(70.dp)
                    .width(380.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "WHITE THEME NOT ALLOWED!",
                    fontSize = 24.sp, fontWeight = FontWeight.Medium
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .semantics { contentDescription = "SettingsScreen" },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { _, item ->
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            navController.navigateUp()
                        },
                        label = {
                            Text(text = item.title)
                        },
                        icon = {
                            Icon(
                                    imageVector = item.icon, contentDescription = item.title
                            )
                        }
                    )
                }
            }
        }
    ) {
        Column {
            SettingsSwitches()
            Spacer(modifier = Modifier.height(36.dp))
            ColorPicker()
        }
    }
}

data class SwitchData(
        val label: String,
        val state: MutableState<Boolean>
)

@Composable
fun SettingsSwitches() {
    val switchData = listOf(
            SwitchData("Dark mode", remember { mutableStateOf(false) }),
            SwitchData("Music", remember { mutableStateOf(true) }),
            SwitchData("Sound", remember { mutableStateOf(true) })
    )

    Column {
        for (item in switchData) {
            Row(
                    modifier = Modifier.padding(horizontal = 18.dp)
            ) {
                Text(
                        text = item.label, modifier = Modifier
                    .padding(top = 10.dp)
                    .width(100.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Switch(checked = item.state.value,
                        onCheckedChange = { item.state.value = !item.state.value })
            }
        }
    }
}

@Composable
fun ColorPicker(
    viewModel: PreferencesViewModel = viewModel(
        factory = PreferencesViewModel.Factory
    )
) {
    val controller = rememberColorPickerController()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                    onClick = { viewModel.saveLayoutColorRGB(
                        viewModel.lastColor.red.toString(),
                        viewModel.lastColor.green.toString(),
                        viewModel.lastColor.blue.toString()
                    ) },
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(42),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = viewModel.lastColor
                    ),
                    modifier = Modifier
                        .height(60.dp)
                        .weight(1f),
            ) {
                Text(
                    text = "Back to previous color",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(
                    modifier = Modifier.width(10.dp)
            )

            Box(
                modifier = Modifier
                    .clickable {
                        viewModel.saveLayoutColorRGB(
                            controller.selectedColor.value.red.toString(),
                            controller.selectedColor.value.green.toString(),
                            controller.selectedColor.value.blue.toString()
                        )
                    }
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                AlphaTile(
                    modifier = Modifier
                        .height(60.dp)
                        .clip(shape = RoundedCornerShape(42))
                        .border(1.dp, Color.Black, RoundedCornerShape(42)),
                    controller = controller
                )
                Text(
                    text = "Save the color",
                    color = Color.White,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.W700,
                    fontSize = 18.sp
                )
            }
        }

        HsvColorPicker(modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .padding(10.dp),
                controller = controller,
                onColorChanged = { }
        )

        BrightnessSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp),
            controller = controller,
        )
    }
}

