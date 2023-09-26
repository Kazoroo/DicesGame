package pl.kazoroo.dices.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import pl.kazoroo.dices.navigation.items


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(navController: NavController) {
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

    }
}
