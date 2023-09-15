package pl.kazoroo.dices.ui.theme.dices

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
)

val items = listOf(
        BottomNavigationItem(
                title = "Back",
                icon = Icons.Default.ArrowBack,
        ),
)



@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Settings() {
    var switchState by remember { mutableStateOf(false) }
    var switchState1 by remember { mutableStateOf(false) }
    var switchState2 by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { _, item ->
                    NavigationBarItem(
                        selected = false,
                        onClick = {
                              //navController.navigate(item.title)
                        },
                        label = {
                            Text(text = item.title)
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        }
                    )
                }
            }
        }
    ) {

    }

    Column {
        Row(
                modifier = Modifier.padding(horizontal = 18.dp)
        ) {
            Text(text = "Dark mode", modifier = Modifier
                .padding(top = 10.dp)
                .width(100.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Switch(checked = switchState, onCheckedChange = { switchState = !switchState })
        }

        Row(
                modifier = Modifier.padding(horizontal = 18.dp)
        ) {
            Text(text = "Color", modifier = Modifier
                .padding(top = 10.dp)
                .width(100.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Switch(checked = switchState1, onCheckedChange = { switchState1 = !switchState1 })
        }

        Row(
                modifier = Modifier.padding(horizontal = 18.dp)
        ) {
            Text(text = "Dark mode", modifier = Modifier
                .padding(top = 10.dp)
                .width(100.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Switch(checked = switchState2, onCheckedChange = { switchState2 = !switchState2 })
        }
    }

}