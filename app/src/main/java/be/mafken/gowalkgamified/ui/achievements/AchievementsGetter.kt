package be.mafken.gowalkgamified.ui.achievements

import be.mafken.gowalkgamified.model.Achievement

fun getAchievements(): MutableList<Achievement> {
    val achievements: MutableList<Achievement> = mutableListOf()
    achievements.add(Achievement("Mystery achievement", "??? this achievement is a mystery ???"))





    return achievements
}