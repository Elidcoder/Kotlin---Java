package aeroplane

class BusinessClass(firstName: String, lastName: String, private val age: Int, private val luxury: Luxury) :
    EconomyClass(firstName, lastName, age) {

    override fun toString(): String {
        return ("$age year old Business Class Passenger: $firstName $lastName who likes $luxury" )
    }
}
