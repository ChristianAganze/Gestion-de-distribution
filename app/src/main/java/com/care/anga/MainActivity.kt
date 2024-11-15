package com.care.anga

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.care.anga.screens.AnalyticsScreen
import com.care.anga.screens.ClientScreen
import com.care.anga.screens.CommandeScreen
import com.care.anga.screens.LivraisonScreen
import com.care.anga.screens.VenteScreen
import com.care.anga.ui.theme.AngaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
               AngaTheme {
                   MainScreen()

               }

       }
    }
}


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current.applicationContext
    var selected = remember {
        mutableStateOf(Icons.Default.Person)
    }
    Scaffold(
        bottomBar = {
            BottomAppBar (){
                IconButton(
                    onClick = {
                        selected.value =Icons.Default.Person
                        navController.navigate(Screens.ClientsScreen.screen) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                ) {
                    Column {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = if (selected.value ==Icons.Default.Person ) Color.Green else Color.Gray
                        )
                        Text(
                             text =""
                        )
                    }

                }
                IconButton(
                    onClick = {
                        selected.value =Icons.Default.DateRange
                        navController.navigate(Screens.CommandesScreen.screen) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                ) {
                    Column {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = if (selected.value ==Icons.Default.ThumbUp ) Color.Green else Color.Gray
                        )
                        Text(
                            text =""
                        )
                    }

                }
                IconButton(
                    onClick = {
                        selected.value =Icons.Default.ShoppingCart
                        navController.navigate(Screens.LivraisonsScreen.screen) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                ) {
                    Column {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = if (selected.value ==Icons.Default.ShoppingCart ) Color.Green else Color.Gray
                        )
                        Text(
                            text =""
                        )
                    }

                }
                IconButton(
                    onClick = {
                        selected.value =Icons.Default.Check
                        navController.navigate(Screens.VentesScreen.screen) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                ) {
                    Column {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = if (selected.value ==Icons.Default.Check ) Color.Green else Color.Gray
                        )
                        Text(
                            text =""
                        )
                    }

                }
                IconButton(
                    onClick = {
                        selected.value =Icons.Default.Info
                        navController.navigate(Screens.AnalyticsScreen.screen) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                ) {
                    Column {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = if (selected.value ==Icons.Default.Check ) Color.Blue else Color.Gray
                        )
                        Text(
                            text ="Annalytics"
                        )
                    }

                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.ClientsScreen.screen,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screens.ClientsScreen.screen){
                ClientScreen()
            }
            composable(Screens.CommandesScreen.screen){
                CommandeScreen()
            }
            composable(Screens.LivraisonsScreen.screen){
                LivraisonScreen()
            }
            composable(Screens.VentesScreen.screen){
                VenteScreen()
            }
            composable(Screens.AnalyticsScreen.screen){
                AnalyticsScreen()
            }
        }
    }


}

sealed class Screens (val screen: String, ){
    data object ClientsScreen : Screens("ClientsScreen")
    data object CommandesScreen : Screens("CommandesScreen", )
    data object LivraisonsScreen : Screens("LivraisonsScreen", )
    data object VentesScreen : Screens("VentesScreen")
    data object  AnalyticsScreen:Screens("AnalyticsScreen")

}