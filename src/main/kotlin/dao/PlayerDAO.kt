package dao

import db.DBConnection
import model.Player

object PlayerDAO {

    fun createPlayer(player: Player) {

        val sql = "INSERT INTO players(username) VALUES (?)"

        DBConnection.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, player.username)

                if (stmt.executeUpdate() > 0) {
                    println("${player.username} added successfuly")
                }
            }
        }
    }

    fun deletePlayer(id: Int) {

        val sql = "DELETE FROM players WHERE id_player = ?"

        DBConnection.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->

                stmt.setInt(1, id)

                if (stmt.executeUpdate() > 0) {
                    println("Player removed successfuly")
                } else {
                    println("No player found with ID: $id")
                }
            }
        }

    }
}
