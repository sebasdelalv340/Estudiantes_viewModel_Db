
import androidx.compose.runtime.State

interface IViewModel {

    val nuevoEstudiante: State<String>
    val listaEstudiante: MutableList<String>

    fun cargarEstudiantes()
    fun agregarStudent()
    fun borrarStudent(estudiante: String)
    fun borrarLista()
    fun saveChanges()
    fun newStudentChange(name: String)
}