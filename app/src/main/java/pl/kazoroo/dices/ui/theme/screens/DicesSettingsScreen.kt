package pl.kazoroo.dices.ui.theme.screens

import android.annotation.SuppressLint
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
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.github.skydoves.colorpicker.compose.*
import pl.kazoroo.dices.navigation.items
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
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

@Composable
fun SettingsSwitches() {
    var switchState by remember { mutableStateOf(false) }
    var switchState2 by remember { mutableStateOf(true) }
    var switchState3 by remember { mutableStateOf(true) }

    Column {
        Row(
                modifier = Modifier.padding(horizontal = 18.dp)
        ) {
            Text(
                    text = "Dark mode", modifier = Modifier
                .padding(top = 10.dp)
                .width(100.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Switch(checked = switchState, onCheckedChange = { switchState = !switchState })
        }

        Row(
                modifier = Modifier.padding(horizontal = 18.dp)
        ) {
            Text(
                    text = "Music", modifier = Modifier
                .padding(top = 10.dp)
                .width(100.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Switch(checked = switchState2, onCheckedChange = { switchState2 = !switchState2 })
        }

        Row(
                modifier = Modifier.padding(horizontal = 18.dp)
        ) {
            Text(
                    text = "Sound", modifier = Modifier
                .padding(top = 10.dp)
                .width(100.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Switch(checked = switchState3, onCheckedChange = { switchState3 = !switchState3 })
        }
    }
}

@Composable
fun ColorPicker() {
    val controller = rememberColorPickerController()

    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 15.dp)
    ) {
        Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
        ) {
            AlphaTile(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(6.dp)),
                    controller = controller
            )

            Text(text = "Text on your color", color = Color.White)
        }
        HsvColorPicker(modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .padding(10.dp),
                controller = controller,
                onColorChanged = {})
        BrightnessSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(35.dp),
                controller = controller,
        )
    }
}

