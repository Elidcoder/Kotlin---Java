package aeroplane

abstract class Passenger(protected var name: String, protected var surname: String) {
    abstract val isAdult: Boolean

    override fun toString(): String {
        return "$name $surname"
    }
}
