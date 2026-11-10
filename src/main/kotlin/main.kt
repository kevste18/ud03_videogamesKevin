fun main() {

    println("=== Videogames List ===")
    VideogameDAO.showVideogames().forEach {
        println(" - [${it.id}], Title: ${it.title}, Genre: (${it.genre}), Release Date: ${it.releaseDate}, Rating: ${it.rating}€")
    }

    val videogame = VideogameDAO.searchById(3)
    if (videogame != null) {
        println("Videogame found: [${videogame.id}], Title: ${videogame.title}, Genre: (${videogame.genre}), Release Date: ${videogame.releaseDate}, Rating: ${videogame.rating}€")
    } else {
        println("No videogame found.")
    }

    VideogameDAO.createVideogame(
        Videogame(
            title = "GTA VI",
            genre = "Action",
            releaseDate = 2026,
            rating = 9.5
        )
    )

    VideogameDAO.updateVideogame(
        Videogame(
            id = 1,
            title = "COD: Black Ops II",
            genre = "Shooter",
            releaseDate = 2013,
            rating = 9.9
        )
    )

    VideogameDAO.deleteVideogame(6)
}