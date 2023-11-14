package pl.kazoroo.dices.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.*
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
) {
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .semantics { contentDescription = "SettingsScreen" },
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { _, item ->
                        NavigationBarItem(selected = false, onClick = {
                            navController.navigateUp()
                        }, label = {
                            Text(text = item.title)
                        }, icon = {
                            Icon(
                                    imageVector = item.icon, contentDescription = item.title
                            )
                        })
                    }
                }
            }) {
        Column {
            SettingsSwitches()
            Spacer(modifier = Modifier.height(36.dp))
            ColorPicker()
        }
    }
}

data class SwitchData(val label: String, val state: MutableState<Boolean>)

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
fun ColorPicker(viewModel: PreferencesViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = PreferencesViewModel.Factory
)) {
    val controller = rememberColorPickerController()

    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 15.dp)
    ) {
        Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.saveLayoutColorRGB(
                                controller.selectedColor.value.red.toString(),
                                controller.selectedColor.value.green.toString(),
                                controller.selectedColor.value.blue.toString()
                        )
                    }, contentAlignment = Alignment.Center
        ) {
            AlphaTile(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    controller = controller
            )
            Text(text = "Click here to save the color", color = Color.White)
        }
        HsvColorPicker(modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .padding(10.dp),
                controller = controller,
                onColorChanged = { })
        BrightnessSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(35.dp),
                controller = controller,
        )
    }
}

