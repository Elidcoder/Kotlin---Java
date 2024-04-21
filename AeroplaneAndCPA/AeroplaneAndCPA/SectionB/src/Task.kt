class Task(val name: String, val duration: Int) {
    companion object {
        val DUMMY_TASK: Task = Task("", 0)
    }
}
