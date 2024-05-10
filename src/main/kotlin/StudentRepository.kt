import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class StudentRepository: IRepo {


    override fun cargarDb(): Result<List<String>> {
        var connectionDb: Connection? = null
        var stmt: Statement? = null

        try {
            val students = mutableListOf<String>()
            connectionDb = Database.getConnection()
            stmt = connectionDb.createStatement()

            val query = "SELECT name FROM students"
            val resultSet = stmt.executeQuery(query)

            while (resultSet.next()) {
                students.add(resultSet.getString("name"))
            }

            stmt.close()
            connectionDb.close()

            return Result.success(students)

        } catch (e: Exception) {
            stmt?.close()
            connectionDb?.close()

            return Result.failure(e)
        }
    }

    override fun guardarDb(students: List<String>): Result<Unit> {
        var connectionDb : Connection? = null
        var stmt: Statement? = null
        var error: Exception? = null

        try {
            connectionDb = Database.getConnection()
            connectionDb.autoCommit = false
            stmt = connectionDb.createStatement()
            val query = "DELETE FROM students"
            stmt.execute(query)

            stmt = connectionDb.prepareStatement("INSERT INTO students (name) VALUES (?)")
            for (student in students) {
                stmt.setString(1, student)
                stmt.executeUpdate()
            }

            connectionDb.commit()

        } catch (e: Exception) {
            error = e
            try {
                connectionDb?.rollback()
            } catch (e: SQLException) {
                error = e
            }
        } finally {
            connectionDb?.autoCommit = true
            stmt?.close()
            connectionDb?.close()
        }

        if (error != null) {
            return Result.failure(error)
        }
        return Result.success(Unit)
    }
}