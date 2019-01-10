package be.mafken.gowalkgamified.data.service

import be.mafken.gowalkgamified.data.OnServiceDataCallback
import be.mafken.gowalkgamified.model.Tracker


interface TrackerService {

 fun loadTrackerOnceFromDatabase(callback: OnServiceDataCallback<Tracker>)

 fun saveTrackerToDatabase(tracker: Tracker)

}