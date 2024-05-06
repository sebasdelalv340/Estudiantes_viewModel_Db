interface IRepo {
    fun cargarDb(): Result<List<String>>
    fun guardarDb(students: List<String>): Result<Unit>
}