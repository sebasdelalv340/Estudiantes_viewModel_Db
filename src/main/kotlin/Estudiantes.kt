import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewStudent(
    newStudent: String,
    onNewStudentChange: (String) -> Unit,
    onNewStudentClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically)
    ) {
        OutlinedTextField(
            value = newStudent,
            onValueChange = onNewStudentChange,
            label = { Text("New student name") },
            singleLine = true,
            modifier = Modifier.onKeyEvent { event ->
                if (
                    event.key == Key.Enter
                    && event.type == KeyEventType.KeyDown
                    && newStudent.isNotBlank()
                    ) {
                    onNewStudentClick()
                    true
                } else false
            }
        )

        Button(
            onClick = onNewStudentClick
        ) {
            Text(text = "Add new student")
        }
    }
}


@Composable
fun ListaEstudiantes(
    listaEstudiantes: MutableList<String>,
    onIconBorrarEstudianteClick: (String) -> Unit,
    onClearClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = "Estudiantes: ${listaEstudiantes.count()}"
        )
        Surface(
            modifier = Modifier.height(400.dp).width(250.dp),
            color = MaterialTheme.colors.surface,
            border = BorderStroke(2.dp, Color.Black)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(20.dp)
            ) {
                items(listaEstudiantes) { estudiante ->
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = estudiante,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(10.dp),
                        )
                        IconButton(
                            onClick = { onIconBorrarEstudianteClick(estudiante) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
        Button(
            onClick = onClearClick
        ) {
            Text(text = "Clear all")
        }
    }
}


@Composable
fun ButtonSaveChanges(
    onSaveClick: () -> Unit
) {
    Button(
        onClick = onSaveClick
    ) {
        Text(text = "Save changes")
    }
}


@Composable
fun StudentScreen(
    viewModel: IViewModel
) {
    val nuevoEstudiante by viewModel.nuevoEstudiante
    val listaEstudiantes = viewModel.listaEstudiante

    LaunchedEffect(key1 = true) {
        viewModel.cargarFichero()
    }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.Gray),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                NewStudent(
                    nuevoEstudiante,
                    {
                        viewModel.newStudentChange(it)
                    },
                    {
                        viewModel.agregarStudent()
                    }
                )

                ListaEstudiantes(
                    listaEstudiantes,
                    {
                        viewModel.borrarStudent(it)
                    },
                    {
                        viewModel.borrarLista()
                    }
                )
            }

            ButtonSaveChanges {
                viewModel.saveChanges()
            }
        }
    }
}