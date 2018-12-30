package be.mafken.gowalkgamified.data.service

import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.model.Walk


interface WalkService {

    fun loadWalksFromDatabase(callback: OnServiceDataCallback<List<Walk>>)

    fun loadWalksOnceFromDatabase(callback: OnServiceDataCallback<List<Walk>>)

    fun loadWalk(walkId: Int, callback: OnServiceDataCallback<Walk>)

    fun saveWalkToDatabase(walk: Walk)

    fun deleteWalk(walkId: Int)

}