import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DBConnection {
    private val dbPath = "src/data/videogames.sqlite"
    private val dbFile = File(dbPath)
    private val url = "jdbc:sqlite:${dbFile.absolutePath}"

    fun getConnection(): Connection? {
        return try {
            DriverManager.getConnection(url)
        } catch (e: SQLException) {
            println("Error while trying to connect to the database ${e.message}")
            null
        }
    }

    // Función de prueba: verificar conexión
    fun testConnection(): Boolean {
        return getConnection()?.use { conn ->
            println("Connexion established successfully ${dbFile.absolutePath}")
            true
        } ?: false
    }

    // Cerrar conexión (para los casos en los que no se utiliza .use)
    fun closeConnection(conn: Connection?) {
        try {
            conn?.close()
            println("Connexion closed successfully.")
        } catch (e: SQLException) {
            println("Error trying to close the connexion ${e.message}")
        }
    }
}