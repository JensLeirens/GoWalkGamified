package be.mafken.gowalkgamified.ui.achievements

import be.mafken.gowalkgamified.model.Achievement
object AchievementsGetter {

    fun getAchievements(): MutableList<Achievement> {
        val achievements: MutableList<Achievement> = mutableListOf()
        achievements.add(Achievement("Mystery achievement", 0,"??? this achievement is a mystery ???"))
        achievements.add(Achievement("First Walk", 1,"Congratulations on your first walk!"))
        achievements.add(Achievement("10 walks", 2,"Add 10 walks"))
        achievements.add(Achievement("20 Walks", 3,"Add 20 walks"))
        achievements.add(Achievement("50 Walks", 4,"Add 50 walks"))
        achievements.add(Achievement("50 Points", 5,"Get 50 points"))
        achievements.add(Achievement("150 Points", 6,"Get 150 points"))
        achievements.add(Achievement("500 Points", 7,"Get 500 points"))
        achievements.add(Achievement("1000 Points", 8,"Get 1000 points"))
        achievements.add(Achievement("2 Day streak", 9,"Get a 2 day streak"))
        achievements.add(Achievement("5 Day streak", 10,"Get a 5 day streak"))
        achievements.add(Achievement("7 Day streak", 11,"Get a 7 day streak"))
        achievements.add(Achievement("5 Km walked", 12,"Walk 5 Km"))
        achievements.add(Achievement("15 Km walked", 13,"Walk 15 Km"))
        achievements.add(Achievement("30 Km walked", 14,"Walk 30 Km"))
        achievements.add(Achievement("Walk with a friend", 15,"Walk with a friend"))

        return achievements
    }
}