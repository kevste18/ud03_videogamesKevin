import dao.GameListDAO
import dao.GameListEntryDAO
import dao.PlayerDAO
import dao.VideogameDAO
import model.GameList
import model.Player
import model.Videogame
import java.time.LocalDate

fun askInt(message: String): Int {
    var number: Int?
    do {
        println(message)
        number = readLine()?.trim()?.toIntOrNull()
        if (number == null) println("Please enter a valid integer.")
    } while (number == null)
    return number
}

fun askDouble(message: String): Double {
    var number: Double?
    do {
        println(message)
        number = readLine()?.trim()?.replace(",", ".")?.toDoubleOrNull()
        if (number == null) println("Please enter a valid number.")
    } while (number == null)
    return number
}

fun askText(message: String): String {
    var text: String
    do {
        println(message)
        text = readLine().orEmpty().trim()
    } while (text.isEmpty())
    return text
}

fun main() {
    var option: Int

    do {
        println(
            """
            ===== VIDEOGAME CATALOG =====

            --- VIDEOGAMES ---
            1. Show all videogames
            2. Search videogame by ID
            3. Add videogame
            4. Update videogame
            5. Delete videogame

            --- PLAYERS ---
            6. Create player
            7. Delete player

            --- LISTS ---
            8. Create list
            9. Delete list
            10. Add videogame to a list
            11. Show videogames of a list

            0. Exit
            =============================
        """.trimIndent()
        )

        option = askInt("Choose an option:")

        when (option) {
            1 -> {
                val list = VideogameDAO.showVideogames()

                for (i in list) {
                    println(i)
                }
            }

            2 -> {
                val id = askInt("> Insert the videogame ID")
                val game = VideogameDAO.searchById(id)

                if (game != null) {
                    println(game)
                } else {
                    println("Videogame with ID: $id not found")
                }
            }

            3 -> {
                VideogameDAO.createVideogame(addVideogame())
            }

            4 -> {
                updateVideogame()
            }

            5 -> {
                val id = askInt("> Insert the ID")
                VideogameDAO.deleteVideogame(id)
            }

            6 -> {
                val username = askText("> Create an username")
                PlayerDAO.createPlayer(Player(null, username))
            }

            7 -> {
                val playerId = askInt("> Insert the user ID")
                PlayerDAO.deletePlayer(playerId)
            }

            8 -> {
                val listName = askText("> Type in the list name")
                val userId = askInt("> Type in the user ID")
                GameListDAO.createList(GameList(null, listName, userId))
            }

            9 -> {
                val listId = askInt("> Type in the list ID")
                GameListDAO.deleteList(listId)
            }

            10 -> {
                val listID = askInt("> Type in the list ID")
                val videogameID = askInt("> Type in the videogame ID")
                val date = LocalDate.now().toString()
                GameListEntryDAO.addToList(listID, videogameID, date)
            }

            11 -> {
                val listID = askInt("> Type in the list ID")
                val gameList = GameListEntryDAO.getVideogamesFromList(listID)

                if (gameList.isEmpty()) {
                    println("This list is empty")
                } else {
                    for (i in gameList) {
                        println(i)
                    }
                }
            }

            0 -> println("Bye!")
            else -> println("Invalid option.")
        }
    } while (option != 0)
}

fun addVideogame(): Videogame {
    val id = null
    val name = askText("> Type in the title")
    val genre = askText("> Type in the genre")
    val releaseDate = askInt("> Type in the release date")
    val rating = askDouble("> Type in the rating")

    return Videogame(id, name, genre, releaseDate, rating)
}

fun updateVideogame() {

    val id = askInt("> Insert the ID")

    val update = VideogameDAO.searchById(id)

    if (update != null) {

        val id = update.id
        val name = askText("> Type in the title")
        val genre = askText("> Type in the genre")
        val releaseDate = askInt("> Type in the release date")
        val rating = askDouble("> Type in the rating")

        VideogameDAO.updateVideogame(Videogame(id, name, genre, releaseDate, rating))

    } else {
        println("No videogame found with ID: $id")
    }
}



