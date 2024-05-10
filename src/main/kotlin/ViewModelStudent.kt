import androidx.compose.runtime.*
import java.io.File

class ViewModelStudent(
    private val gestorFichero: IFichero,
    private val fichero: File,
): IViewModel {

    private val _nuevoEstudiante = mutableStateOf("")
    override var nuevoEstudiante: State<String> = _nuevoEstudiante

    private val _listaEstudiantes = mutableStateListOf<String>()
    override var listaEstudiante: MutableList<String> = _listaEstudiantes

    override fun cargarEstudiantes() {
        val estudiantesCargados = gestorFichero.cargarFichero(fichero)
        _listaEstudiantes.addAll(estudiantesCargados)
    }

    override fun agregarStudent() {
        _listaEstudiantes.add(_nuevoEstudiante.value)
        _nuevoEstudiante.value = ""
    }

    override fun borrarStudent(estudiante: String) {
        _listaEstudiantes.remove(estudiante)
    }

    override fun borrarLista() {
        _listaEstudiantes.clear()
    }

    override fun saveChanges() {
        gestorFichero.guardarFichero(_listaEstudiantes, fichero)
    }

    override fun newStudentChange(name: String) {
        _nuevoEstudiante.value = name
    }
}