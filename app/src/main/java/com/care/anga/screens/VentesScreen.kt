package com.care.anga.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.care.anga.data.entity.Vente
import com.care.anga.viewModel.VenteViewModel
import java.util.Date
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VenteScreen() {
    val viewModel: VenteViewModel = hiltViewModel()
    val allVentes by viewModel.allVentes.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }  // État pour gérer l'affichage du champ de recherche

    // Filtrer les ventes en fonction de la recherche
    val filteredVentes = allVentes.filter {
        it.commandeId.toString().contains(searchQuery, ignoreCase = true) ||
                it.dateVente.contains(searchQuery, ignoreCase = true) ||
                it.montant.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Liste des Ventes") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onTertiary,
                    actionIconContentColor = MaterialTheme.colorScheme.onTertiary
                ),
                actions = {
                    // Icône de recherche qui ouvre le champ de recherche
                    IconButton(onClick = { isSearchActive = !isSearchActive }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Recherche")
                    }

                    // Champ de recherche conditionnel
                    if (isSearchActive) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            label = { Text("Recherche") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            placeholder = { Text("Rechercher...") },
                            singleLine = true,
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    isSearchActive = false  // Ferme le champ de recherche une fois que l'utilisateur a terminé
                                }
                            )
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Ajouter Vente")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredVentes) { vente ->
                    VenteItem(vente = vente, onDelete = {
                        viewModel.deleteVente(vente)
                    })
                }
            }

            // Dialog pour ajouter une nouvelle vente
            if (showAddDialog) {
                AddVenteDialog(
                    onDismiss = { showAddDialog = false },
                    onAdd = { newVente ->
                        viewModel.addVente(newVente)
                        showAddDialog = false
                    }
                )
            }
        }
    }
}

@Composable
fun VenteItem(vente: Vente, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Vente ID: ${vente.venteId}")
                Text("Commande ID: ${vente.commandeId}")
                Text("Date: ${vente.dateVente}")
                Text("Montant: ${vente.montant}")
            }
            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Supprimer")
            }
        }
    }
}

@Composable
fun AddVenteDialog(onDismiss: () -> Unit, onAdd: (Vente) -> Unit) {
    var commandeId by remember { mutableStateOf(0L) }
    var montant by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Ajouter une Vente") },
        text = {
            Column {
                TextField(
                    value = commandeId.toString(),
                    onValueChange = { commandeId = it.toLongOrNull() ?: 0L },
                    label = { Text("Commande ID") }
                )
                // Placeholder pour la sélection de la date (peut être remplacée par une meilleure implémentation)
                Text("Date Vente: ${Date().toString()}")
                TextField(
                    value = montant,
                    onValueChange = { montant = it },
                    label = { Text("Montant") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                // Créer et ajouter une nouvelle vente
                onAdd(Vente(commandeId = commandeId, dateVente = Date().toString(), montant = montant))
            }) {
                Text("Ajouter")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Annuler")
            }
        }
    )
}
