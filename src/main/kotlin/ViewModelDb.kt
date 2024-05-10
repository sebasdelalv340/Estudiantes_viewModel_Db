import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class ViewModelDb(
    private val studentRepo: IRepo
): IViewModel{

    private val _nuevoEstudiante = mutableStateOf("")
    override var nuevoEstudiante: State<String> = _nuevoEstudiante

    private val _listaEstudiantes = mutableStateListOf<String>()
    override var listaEstudiante: MutableList<String> = _listaEstudiantes

    init {
        cargarEstudiantes()
    }
    override fun cargarEstudiantes() {
        val datosCargados = studentRepo.cargarDb()
        datosCargados.onSuccess {
            _listaEstudiantes.addAll(it)
        }.onFailure {
            throw Exception("*Error* fallo al cargar la base de datos")
        }
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
        studentRepo.guardarDb(_listaEstudiantes)
    }

    override fun newStudentChange(name: String) {
        _nuevoEstudiante.value = name
    }

}