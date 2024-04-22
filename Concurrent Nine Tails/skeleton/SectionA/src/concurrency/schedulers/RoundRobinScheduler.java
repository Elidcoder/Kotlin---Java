package concurrency.schedulers;

import concurrency.ConcurrentProgram;
import concurrency.DeadlockException;

import java.util.NoSuchElementException;
import java.util.Set;

public class RoundRobinScheduler implements Scheduler{
    private Integer previousThread;
    @Override
    public int chooseThread(ConcurrentProgram program) throws DeadlockException {
        if (program.getNumThreads() == 1){
            throw new DeadlockException();
        }
        else{
            Set<Integer> threads = program.getEnabledThreadIds();
            System.out.println(threads);
            if (previousThread == null){
                previousThread = threads.stream().min(Integer::compare).get();
            }
            else{
                try {
                    previousThread = threads.stream().filter(x -> x > previousThread).findFirst().get();
                } catch (java.util.NoSuchElementException e){
                    previousThread = threads.stream().min(Integer::compare).get();
                }
            }
            return previousThread;
        }
    }
}
