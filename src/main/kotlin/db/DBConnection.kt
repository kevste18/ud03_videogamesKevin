package db

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DBConnection {
    private const val HOST = "localhost"
    private const val PORT = 5433
    private const val DATABASE = "videogames"
    private const val USER = "kevin"
    private const val PASSWORD = "wallRick22"

    private val url = "jdbc:postgresql://$HOST:$PORT/$DATABASE"


    fun getConnection(): Connection? {
        return try {
            DriverManager.getConnection(url, USER, PASSWORD)
        } catch (e: SQLException) {
            println("Error while trying to connect to the database ${e.message}")
            null
        }
    }

    // Función de prueba: verificar conexión
    fun testConnection(): Boolean {
        return getConnection()?.use { conn ->
            println("Connexion established successfully")
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