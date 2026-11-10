
object VideogameDAO {

    fun showVideogames(): List<Videogame> {
        val list = mutableListOf<Videogame>()
        val sql = "SELECT * FROM videogames"
        DBConnection.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    list.add(Videogame(
                        id = rs.getInt("id"),
                        title = rs.getString("title"),
                        genre = rs.getString("genre"),
                        releaseDate = rs.getInt("release_date"),
                        rating = rs.getDouble("rating")
                    ))
                }
            }
        }
        return list
    }

    fun searchById(id: Int): Videogame? {
        var videogame: Videogame? = null
        val sql = "SELECT * FROM videogames WHERE id = ?"
        DBConnection.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                val rs = stmt.executeQuery()
                if (rs.next()) {
                    videogame = Videogame(
                        id = rs.getInt("id"),
                        title = rs.getString("title"),
                        genre = rs.getString("genre"),
                        releaseDate = rs.getInt("release_date"),
                        rating = rs.getDouble("rating")
                    )
                }
            }
        }
        return videogame
    }

    fun createVideogame(videogame: Videogame) {
        val sql = "INSERT INTO videogames(title, genre, release_date, rating) VALUES (?, ?, ?, ?)"
        DBConnection.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, videogame.title)
                stmt.setString(2, videogame.genre)
                stmt.setInt(3, videogame.releaseDate)
                stmt.setDouble(4, videogame.rating)
                if (stmt.executeUpdate() > 0) {
                    println("${videogame.title} succesfully added.")
                }
            }
        }
    }

    fun updateVideogame(videogame : Videogame) {
        if (videogame.id == null) {
            println("Error: The videogame needs an ID in order to be updated.")
            return
        }
        val sql = "UPDATE videogames SET title = ?, genre = ?, release_date = ?, rating = ? WHERE id = ?"
        DBConnection.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, videogame.title)
                stmt.setString(2, videogame.genre)
                stmt.setInt(3, videogame.releaseDate)
                stmt.setDouble(4, videogame.rating)
                stmt.setInt(5, videogame.id)
                if (stmt.executeUpdate() > 0) {
                    println("Videogame with ID ${videogame.id} updated.")
                } else {
                    println("No videogame found with ID ${videogame.id}")
                }
            }
        }
    }

    fun deleteVideogame(id: Int) {
        val sql = "DELETE FROM videogames WHERE id = ?"
        DBConnection.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                if (stmt.executeUpdate() > 0) {
                    println("Videogame with ID: $id deleted successfully. ")
                } else {
                    println("No videogame found with the ID: $id.")
                }
            }
        }
    }
}