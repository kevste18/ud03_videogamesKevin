package dao

import db.DBConnection
import model.Videogame
import java.sql.Connection
import java.sql.SQLException
import kotlin.use

object GameListEntryDAO {

    fun addToList(idList: Int, idVideogame: Int, date: String) {

        var conn: Connection? = null

        try {
            conn = DBConnection.getConnection()
            conn?.autoCommit = false // 1. Iniciar transacción

            conn?.prepareStatement("SELECT * FROM videogames WHERE id = ?")?.use { stmt ->
                stmt.setInt(1, idVideogame)
                val rs = stmt.executeQuery()

                if (!rs.next()) {
                    throw SQLException("Videogame with ID: $idVideogame doesn't exist")
                }
            }

            conn?.prepareStatement("INSERT INTO videogames_lists (id_list, id_videogame, date_added) VALUES (?, ?, ?)")?.use { stmt ->
                stmt.setInt(1, idList)
                stmt.setInt(2, idVideogame)
                stmt.setString(3, date)
                stmt.executeUpdate()

            }

            conn?.commit()
            println("Videogame added to the list.")


        } catch (e: SQLException) {
            println("Transaction error, the changes will be reverted: ${e.message}")
            conn?.rollback() // 3. Revertir cambios
        } finally {
            conn?.autoCommit = true
            conn?.close()
        }
    }

    fun getVideogamesFromList(idList: Int): List<Videogame> {

        val sql = "SELECT v.* FROM videogames v JOIN videogames_lists vl ON v.id = vl.id_videogame WHERE vl.id_list = ?"

        val videogamesList = mutableListOf<Videogame>()

        DBConnection.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->

                stmt.setInt(1, idList)

                val rs = stmt.executeQuery()

                while (rs.next()) {
                    val id = rs.getInt("id")
                    val title = rs.getString("title")
                    val genre = rs.getString("genre")
                    val releaseDate = rs.getInt("release_date")
                    val rating = rs.getDouble("rating")

                    videogamesList.add(Videogame(id, title, genre, releaseDate, rating))
                }
            }
        }

        return videogamesList
    }
}