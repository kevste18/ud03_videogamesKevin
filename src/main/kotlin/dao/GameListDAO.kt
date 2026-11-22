package dao

import db.DBConnection
import model.GameList
import java.sql.SQLException

object GameListDAO {

    fun createList (gameList: GameList) {

        val sql = "INSERT INTO lists (name, id_player) VALUES (?, ?)"

        DBConnection.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, gameList.name)
                stmt.setInt(2, gameList.idPlayer)

                try {
                    if (stmt.executeUpdate() > 0) {
                        println("List created successfully")
                    }
                } catch (e : SQLException) {
                    System.err.println("Unable to create the list: player with ID ${gameList.idPlayer} doesn't exist ")
                }
            }
        }
    }

    fun deleteList (id: Int) {
        val sql = "DELETE FROM lists WHERE id_list = ?"

        DBConnection.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->

                stmt.setInt(1, id)

                if (stmt.executeUpdate() > 0) {
                    println("List deleted successfuly")
                } else {
                    println("No list found with ID: $id")
                }
            }
        }
    }
}