package aeroplane

open class EconomyClass(name: String, surname: String, age: Int) : Passenger(name, surname) {
    protected var age: Int

    init {
        assert(age >= 0) { "Age >= 0" }
        this.age = age
    }

    override val isAdult: Boolean
        get() = age >= 18

    override fun toString(): String {
        return "Economy Class Passenger: " + super.toString() + " is " + age + " years old"
    }
}
