package com.care.anga.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.care.anga.data.entity.Livraison
import com.care.anga.viewModel.LivraisonViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LivraisonScreen() {
    val viewModel:LivraisonViewModel = hiltViewModel()
    val allLivraisons by viewModel.allLivraisons.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
          TopAppBar(
            title = {
                Text(text = "Liste des Livraisons")
            },
              colors = TopAppBarDefaults.topAppBarColors(
                  containerColor = MaterialTheme.colorScheme.primary,
                  titleContentColor = MaterialTheme.colorScheme.onTertiary
              )
          )
       }
    ){it->
        // Affichage de la liste des livraisons
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(allLivraisons) { livraison ->
                    LivraisonItem(livraison = livraison, onDelete = {
                        viewModel.deleteLivraison(livraison)
                    })
                }
            }

            // Bouton pour ajouter une livraison
            Button(
                onClick = { showAddDialog = true },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Ajouter Livraison")
            }

            // Dialog pour ajouter une nouvelle livraison
            if (showAddDialog) {
                AddLivraisonDialog(onDismiss = { showAddDialog = false }, onAdd = { newLivraison ->
                    viewModel.addLivraison(newLivraison)
                    showAddDialog = false
                })
            }
        }
    }

}

@Composable
fun LivraisonItem(livraison: Livraison, onDelete: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Livraison ID: ${livraison.livraisonId}")
                Text("Commande ID: ${livraison.commandeId}")
                Text("Date: ${livraison.dateLivraison}")
                Text("Quantité: ${livraison.quantiteLivree}")
                Text("Statut: ${livraison.status}")
            }
            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Supprimer")
            }
        }
    }
}

@Composable
fun AddLivraisonDialog(onDismiss: () -> Unit, onAdd: (Livraison) -> Unit) {
    var commandeId by remember { mutableLongStateOf(0L) }
    var dateLivraison by remember { mutableStateOf(Date()) }
    var quantiteLivree by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Ajouter une Livraison") },
        text = {
            Column {
                TextField(value = commandeId.toString(), onValueChange = { commandeId = it.toLongOrNull() ?: 0L }, label = { Text("Commande ID") })
                TextField(value = dateLivraison.toString(), onValueChange = { dateLivraison = Date() }, label = { Text("Date Livraison") })
                TextField(value = quantiteLivree, onValueChange = { quantiteLivree = it }, label = { Text("Quantité Livrée") })
                TextField(value = status, onValueChange = { status = it }, label = { Text("Statut") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onAdd(Livraison(commandeId = commandeId, dateLivraison = dateLivraison.toString(), quantiteLivree = quantiteLivree, status = status))
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
