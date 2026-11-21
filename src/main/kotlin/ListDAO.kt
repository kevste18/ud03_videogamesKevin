import java.sql.Connection
import java.sql.SQLException

object ListDAO {

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
            println("The list was added succesfully.")


        } catch (e: SQLException) {
            println("Transaction error, the changes will be reverted: ${e.message}")
            conn?.rollback() // 3. Revertir cambios
        } finally {
            conn?.autoCommit = true
            conn?.close()
        }
    }
}