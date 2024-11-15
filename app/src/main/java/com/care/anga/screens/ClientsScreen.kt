package com.care.anga.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.care.anga.data.entity.Client
import com.care.anga.viewModel.ClientViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientScreen() {
    val clientViewModel: ClientViewModel = hiltViewModel()
    val allClients by clientViewModel.allClients.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }
    var clientName by remember { mutableStateOf("") }
    var clientAddress by remember { mutableStateOf("") }
    var clientPhone by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") } // Variable pour la recherche
    var isSearching by remember { mutableStateOf(false) } // Variable pour savoir si on est en mode recherche

    // Filtrage des clients en fonction de la recherche
    val filteredClients = allClients.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearching) {
                        // Afficher un champ de recherche
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            label = { Text("Rechercher",
                                color = MaterialTheme.colorScheme.onTertiary) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    } else {
                        Text(text = "LISTE DES CLIENTS")
                    }
                },
                actions = {
                    if (!isSearching) {
                        IconButton(onClick = { isSearching = true }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Rechercher")
                        }
                    } else {
                        IconButton(onClick = { isSearching = false; searchQuery = "" }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Fermer la recherche")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onTertiary,
                    actionIconContentColor = MaterialTheme.colorScheme.onTertiary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Ajouter un client")
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            items(filteredClients) { client ->
                ClientItem(client, onDelete = {
                    clientViewModel.deleteClient(it)
                })
            }
        }
    }

    // Dialog pour ajouter un client
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Ajouter un client") },
            text = {
                Column {
                    TextField(
                        value = clientName,
                        onValueChange = { clientName = it },
                        label = { Text("Nom du client") }
                    )
                    TextField(
                        value = clientAddress,
                        onValueChange = { clientAddress = it },
                        label = { Text("Adresse du client") }
                    )
                    TextField(
                        value = clientPhone,
                        onValueChange = { clientPhone = it },
                        label = { Text("Téléphone du client") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (clientName.isNotEmpty() && clientAddress.isNotEmpty() && clientPhone.isNotEmpty()) {
                            clientViewModel.addClient(
                                Client(
                                    clientId = 0, // L'ID est auto-généré
                                    name = clientName,
                                    address = clientAddress,
                                    phone = clientPhone
                                )
                            )
                            clientName = ""
                            clientAddress = ""
                            clientPhone = ""
                            showDialog = false
                        }
                    }
                ) {
                    Text("Ajouter")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Annuler")
                }
            }
        )
    }
}

@Composable
fun ClientItem(client: Client, onDelete: (Client) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("identifiant du client:${client.clientId}",style = MaterialTheme.typography.bodyMedium)
                Text("Nom: ${client.name}", style = MaterialTheme.typography.bodyLarge)
                Text("Adresse: ${client.address}", style = MaterialTheme.typography.bodyMedium)
                Text("Téléphone: ${client.phone}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = { onDelete(client) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Supprimer le client"
                )
            }
        }
    }
}
