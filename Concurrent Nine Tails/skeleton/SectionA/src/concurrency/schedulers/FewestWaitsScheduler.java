package concurrency.schedulers;

import concurrency.ConcurrentProgram;
import concurrency.DeadlockException;

import java.util.Set;

public class FewestWaitsScheduler implements Scheduler {
    @Override
    public int chooseThread(ConcurrentProgram program) throws DeadlockException {
        if (program.getNumThreads() == 1){
            throw new DeadlockException();
        }
        else{
            Set<Integer> threads = program.getEnabledThreadIds();
            Integer chosenThread = null;
            for (Integer threadId : threads){
                if (compareThreads(chosenThread, threadId, program)){
                    chosenThread = threadId;
                }
            }
            return chosenThread;
        }
    }
    private Boolean compareThreads(Integer thread1, Integer thread2, ConcurrentProgram program){
        if (thread1 == null){
            return true;
        }
        if (program.remainingStatements(thread1).size() > program.remainingStatements(thread2).size()){
            return true;
        }
        if (program.remainingStatements(thread1).size() == program.remainingStatements(thread2).size()){
            return thread1 > thread2;
        }
        return false;
    }
}
