package com.care.anga.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.care.anga.data.entity.Vente
import com.care.anga.viewModel.VenteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen() {
    val viewModel: VenteViewModel = hiltViewModel()
    val allVentes by viewModel.allVentes.collectAsStateWithLifecycle()

    // Calculez les statistiques des ventes
    val totalVentes = allVentes.sumOf { it.montant.toDouble() }
    val ventesParDate = allVentes.groupBy { it.dateVente.take(7) } // Groupé par semaine par exemple

    // Vous pouvez également calculer d'autres métriques comme les ventes moyennes, etc.
    val ventesMoyennes = if (allVentes.isNotEmpty()) totalVentes / allVentes.size else 0.0
    Scaffold (
        topBar = {
        TopAppBar(title = {
            Text("Analytics des Ventes")
        },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onTertiary
            )
            )
    }){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Text("Analytics des Ventes", style = MaterialTheme.typography.titleSmall)

            Spacer(modifier = Modifier.height(16.dp))

            // Affichage des statistiques clés
            Text("Total des ventes : ${"%.2f".format(totalVentes)} FC")
            Text("Ventes Moyennes : ${"%.2f".format(ventesMoyennes)} FC")

            Spacer(modifier = Modifier.height(16.dp))

            // Affichage des ventes par date (groupées par semaine ici)
            Text("Ventes par Semaine", style = MaterialTheme.typography.titleSmall)
            LazyColumn {
                items(ventesParDate.entries.toList()) { (semaine, ventes) ->
                    Text("Semaine $semaine: ${ventes.sumOf { it.montant.toDouble() }} FC")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Graphiques - ici on pourrait intégrer un graphique simple (ex : bar chart)
            // Exemple avec une barre de progression pour visualiser le total des ventes
            SalesChart(ventesParDate)
        }
    }
}
    @Composable
    fun SalesChart(ventesParDate: Map<String, List<Vente>>) {
        val venteData = ventesParDate.map { it.value.sumOf { vente -> vente.montant.toDouble() } }

        // Utilisation d'un graphique simple
        // Ici,  intégration d'une bibliothèque de graphique comme 'MPAndroidChart' ou 'Compose Charts'
        // Pour l'instant,  j'afficher des barres avec un ProgressBar comme exemple.
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Graphique des Ventes",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Affichage sous forme de barres de progression (comme un graphique simple)
            venteData.forEachIndexed { index, venteTotal ->
                LinearProgressIndicator(
                    progress = { (((venteTotal / venteData.maxOrNull()!!) ?: 1.0)).toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }




