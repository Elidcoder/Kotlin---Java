package aeroplane

open class EconomyClass(firstName: String, lastName: String, private val age: Int) : Passenger(firstName, lastName) {

    init {
        require (age > 0) { "Age must be greater than 0" }
    }

    override fun isAdult(): Boolean = age >= 18

    override fun toString(): String {
        return "$age year old Economy Class Passenger: " + super.toString()
    }
}
