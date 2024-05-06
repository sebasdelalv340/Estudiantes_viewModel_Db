import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.io.File


fun main() = application {
    val windosState = rememberWindowState(size = DpSize(1200.dp, 800.dp))
    val icon = BitmapPainter(useResource("sample.png", ::loadImageBitmap))
    val gestorFichero: IFichero = Fichero()
    val ruta = "src/Estudiantes.txt"
    val file = File(ruta)
    val viewModel = ViewModelStudent(gestorFichero, file)

    Window(onCloseRequest = ::exitApplication,
        title = "My Students",
        state = windosState,
        icon = icon
    ) {
        StudentScreen(viewModel)
    }
}
