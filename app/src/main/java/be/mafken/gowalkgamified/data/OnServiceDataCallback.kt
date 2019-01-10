package be.mafken.gowalkgamified.data

interface OnServiceDataCallback<T> {
 fun onDataLoaded(data: T)

 fun onError(error: Throwable)
}