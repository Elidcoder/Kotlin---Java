package proglang

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class ProgramExecutor(var threadBody: Stmt,
                      val lock: ReentrantLock,
                      val pauseValue: Long,
                      val store: MutableMap<String, Int>): Runnable {

    override fun run() {
        var currentStmt: Stmt? = threadBody
        while (threadBody != null) {
            Thread.sleep(pauseValue)
            lock.withLock {
                currentStmt = currentStmt!!.step(store)
            }
        }
    }
}
