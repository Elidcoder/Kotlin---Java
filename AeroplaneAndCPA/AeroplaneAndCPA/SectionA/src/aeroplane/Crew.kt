package aeroplane

class Crew(firstName: String, surName: String) : Passenger(firstName, surName) {

    override fun isAdult(): Boolean = true

    override fun toString(): String {
        return "Crew: " + super.toString()
    }
}
