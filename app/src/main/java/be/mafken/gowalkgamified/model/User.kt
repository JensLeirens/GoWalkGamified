package be.mafken.gowalkgamified.model

data class User(
 val uid: String = "",
 var email: String = "",
 var name: String = "",
 var imageUrl: String = "No Image",
 var totalDistance: Int = -1,
 var totalTime: String = "",
 var totalScore: Int = -1,
 var lastLoggedDay: Int = -1,
 var dayStreak: Int = 0,
 var completedAchievements: MutableList<Int> = mutableListOf()
)
