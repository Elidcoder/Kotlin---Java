package aeroplane

class Crew(name: String, surname: String) : Passenger(name, surname) {
    override val isAdult: Boolean
        get() = true

    override fun toString(): String {
        return "Crew: " + super.toString()
    }
}
