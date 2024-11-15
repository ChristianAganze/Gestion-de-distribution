package com.care.anga.screens


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.care.anga.data.entity.Commande
import com.care.anga.viewModel.CommandeViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommandeScreen() {
    var viewModel:CommandeViewModel=hiltViewModel()
    val allCommandes by viewModel.allCommandes.collectAsStateWithLifecycle()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Liste des Commandes")

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onTertiary
                )
            )
        }
    ){it->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)){

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(allCommandes) { commande ->
                    CommandeItem(commande = commande, onDelete = {
                        viewModel.deleteCommande(commande)
                    })
                }
            }


            Button(
                onClick = { showAddDialog = true },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Ajouter Commande")
            }


            if (showAddDialog) {
                AddCommandeDialog(onDismiss = { showAddDialog = false }, onAdd = { newCommande ->
                    viewModel.addCommande(newCommande)
                    showAddDialog = false
                })
            }
        }
    }

}

@Composable
fun CommandeItem(commande: Commande, onDelete: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Commande ID: ${commande.commandeId}")
                Text("Client ID: ${commande.clientId}")
                Text("Date: ${commande.dateCommande}")
                Text("Quantité: ${commande.quantiteCommande}")
            }
            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Supprimer")
            }
        }
    }
}

@Composable
fun AddCommandeDialog(onDismiss: () -> Unit, onAdd: (Commande) -> Unit) {
    var clientId by remember { mutableStateOf(0) }
    var dateCommande by remember { mutableStateOf(Date()) }
    var quantiteCommande by remember { mutableStateOf(0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Ajouter une Commande") },
        text = {
            Column {
                TextField(value = clientId.toString(), onValueChange = { clientId =it.toIntOrNull() ?: 0 }, label = { Text("Client ID") })
                TextField(value = dateCommande.toString(), onValueChange = { dateCommande = Date() }, label = { Text("Date Commande") })
                TextField(value = quantiteCommande.toString(), onValueChange = { quantiteCommande = it.toIntOrNull() ?: 0 }, label = { Text("Quantité") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onAdd(Commande(clientId = clientId, dateCommande = dateCommande.toString(), quantiteCommande = quantiteCommande))
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
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun CommandeScreenPreview() {
    CommandeScreen()
}