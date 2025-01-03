package com.takanakonbu.hobbytime.ui.screens.hobby

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.takanakonbu.hobbytime.data.entity.HobbyEntity

@Composable
fun HobbiesScreen(
    viewModel: HobbyViewModel = hiltViewModel()
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var editingHobby by remember { mutableStateOf<HobbyEntity?>(null) }
    val hobbies by viewModel.hobbies.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(Icons.Default.Add, contentDescription = "趣味を追加")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(hobbies) { hobby ->
                HobbyCard(
                    hobby = hobby,
                    onEdit = { editingHobby = hobby },
                    onDelete = { viewModel.deleteHobby(hobby) }
                )
            }
        }

        if (showAddDialog) {
            HobbyDialog(
                onDismiss = { showAddDialog = false },
                onConfirm = { name, description ->
                    viewModel.addHobby(name, description)
                    showAddDialog = false
                }
            )
        }

        editingHobby?.let { hobby ->
            HobbyDialog(
                hobby = hobby,
                onDismiss = { editingHobby = null },
                onConfirm = { name, description ->
                    viewModel.updateHobby(hobby.copy(name = name, description = description))
                    editingHobby = null
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HobbyCard(
    hobby: HobbyEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onEdit
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = hobby.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "編集")
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "削除")
                    }
                }
            }
            if (hobby.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = hobby.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun HobbyDialog(
    hobby: HobbyEntity? = null,
    onDismiss: () -> Unit,
    onConfirm: (name: String, description: String) -> Unit
) {
    var name by remember { mutableStateOf(hobby?.name ?: "") }
    var description by remember { mutableStateOf(hobby?.description ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (hobby == null) "趣味を追加" else "趣味を編集") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("趣味の名前") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("活動内容") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm(name, description)
                    }
                }
            ) {
                Text(if (hobby == null) "追加" else "更新")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("キャンセル")
            }
        }
    )
}