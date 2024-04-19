package aeroplane

class BusinessClass(name: String, surname: String, age: Int, private val luxury: Luxury) :
    EconomyClass(name, surname, age) {
    override fun toString(): String {
        return ("Business Class Passenger: " + name + " " + surname + " is " + age + " years old" + " has luxury: "
                + luxury)
    }
}
