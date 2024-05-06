import java.io.File

class Fichero: IFichero {

    override fun cargarFichero(file: File): MutableList<String> {
        val datos: MutableList<String> = mutableListOf()

        try {
            file.forEachLine { linea -> datos.add(linea) }
        } catch (e: Exception) {
            println("Error al cargar los datos del fichero: ${e.message}")
        }
        return datos
    }

    override fun guardarFichero(lista: MutableList<String>, file: File) {
        try {
            file.writeText("")
            for (linea in lista) {
                file.appendText("$linea\n")
            }
        } catch (e: Exception) {
            println("Error al sobreescribir el fichero: ${e.message}")
        }
    }
}