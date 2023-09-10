package pl.kazoroo.dices.ui.theme.dices

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Settings() {
    var switchState by remember { mutableStateOf(false) }
    var switchState1 by remember { mutableStateOf(false) }
    var switchState2 by remember { mutableStateOf(false) }

    Column {
        Row(
                modifier = Modifier.padding(top = 5.dp, start = 18.dp)
        ) {
            Text(text = "Dark mode", modifier = Modifier.padding(top = 10.dp).width(100.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Switch(checked = switchState, onCheckedChange = { switchState = !switchState })
        }

        Row(
                modifier = Modifier.padding(horizontal = 18.dp)
        ) {
            Text(text = "Color", modifier = Modifier.padding(top = 10.dp).width(100.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Switch(checked = switchState1, onCheckedChange = { switchState1 = !switchState1 })
        }

        Row(
                modifier = Modifier.padding(horizontal = 18.dp)
        ) {
            Text(text = "Dark mode", modifier = Modifier.padding(top = 10.dp).width(100.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Switch(checked = switchState2, onCheckedChange = { switchState2 = !switchState2 })
        }
    }
}