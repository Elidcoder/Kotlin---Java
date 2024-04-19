package aeroplane

abstract class Passenger(
    protected val firstName: String,
    protected val lastName: String
) {
    protected abstract fun isAdult():Boolean

    override fun toString(): String {
        return "$firstName $lastName"
    }
}
