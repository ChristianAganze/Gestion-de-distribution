//package com.care.anga.screens
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.material.icons.filled.Place
//import androidx.compose.material.icons.filled.ShoppingCart
//import androidx.compose.material.icons.filled.ThumbUp
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.CardElevation
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.FloatingActionButton
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.unit.dp
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DashboardScreen(
//    onNavigateToClients: () -> Unit,
//    onNavigateToCommandes: () -> Unit,
//    onNavigateToLivraisons: () -> Unit,
//    onNavigateToVentes: () -> Unit,
//    onAddItem: () -> Unit
//) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Tableau de bord") },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor =MaterialTheme.colorScheme.primary ),
//
//                actions = {
//                    IconButton(onClick = { /* Ouvrir le menu */ }) {
//                        Icon(Icons.Default.Menu, contentDescription = "Menu")
//                    }
//                }
//            )
//        },
//        floatingActionButton = {
//            FloatingActionButton(onClick = onAddItem) {
//                Icon(Icons.Default.Add, contentDescription = "Ajouter")
//            }
//        },
//        content = { padding ->
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(padding),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                DashboardCard("Clients", Icons.Default.Person, onNavigateToClients)
//                DashboardCard("Commandes", Icons.Default.ShoppingCart, onNavigateToCommandes)
//                DashboardCard("Livraisons", Icons.Default.Place, onNavigateToLivraisons)
//                DashboardCard("Ventes", Icons.Default.ThumbUp, onNavigateToVentes)
//            }
//        }
//    )
//}
//
//@Composable
//fun DashboardCard(title: String, icon: ImageVector, onClick: () -> Unit) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//            .clickable { onClick() },
//        elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)//defaultElevation(elevation = 4.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Icon(icon, contentDescription = title, modifier = Modifier.size(40.dp))
//            Spacer(modifier = Modifier.width(16.dp))
//            Text(text = title, style = MaterialTheme.typography.titleSmall)
//        }
//    }
//}
