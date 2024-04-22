package concurrency;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import concurrency.schedulers.Scheduler;

public class Executor {

	private ConcurrentProgram program;
	private Scheduler scheduler;

	public Executor(ConcurrentProgram program, Scheduler scheduler) {
		this.program = program;
		this.scheduler = scheduler;
	}

	/**
	 * Executes program with respect to scheduler
	 *
	 * @return the final state and history of execution
	 */
	public String execute() {
		List<Integer> history = new LinkedList<Integer>();
		boolean deadlockOccurred = false;
		int curThreadID;

		while (!(deadlockOccurred || program.isTerminated())) {
			try {
				curThreadID = scheduler.chooseThread(program);
				program.step(curThreadID);
				history.add(curThreadID);
			} catch(DeadlockException e) {
				deadlockOccurred = true;
			}

		}
		StringBuilder result = new StringBuilder();
		result.append("Final state: " + program + "\n");
		result.append("History: " + history + "\n");
		result.append("Termination status: "
				+ (deadlockOccurred ? "deadlock" : "graceful") + "\n");
		return result.toString();
	}

	// An incorrect attempt at overriding the equals method
	// of Object
	@Override
	public boolean equals(Object that) {

		if (!(that instanceof Executor)) return false;

		return program.toString().equals(that.toString());
	}

}
