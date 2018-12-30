package be.mafken.gowalkgamified.model

data class User(val uid: String = "",
                var email: String = "",
                var name: String = "",
                var imageUrl: String = "",
                var totalDistance: Int = -1,
                var totalTime: String = "")
