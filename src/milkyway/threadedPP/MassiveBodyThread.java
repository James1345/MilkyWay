package milkyway.threadedPP;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the thread in which a single MassiveBody runs its calculations. It is 
 * Synchronized with the other threads by use of a cyclic barrier.
 * 
 * @author james
 */
public class MassiveBodyThread extends Thread{
    
    private CyclicBarrier barrier;
    private final Universe u;
    private MassiveBody body;
    boolean done = false;
    
    public MassiveBodyThread(MassiveBody body, CyclicBarrier barrier, Universe u){
        this.barrier = barrier;
        this.body = body;
        this.u = u;
    }
    
    @Override
    public void run(){
        while(!done){
            try {
                body.update(); // Update acceleration wrt all other boddies
                barrier.await(); //wait for end of updates
                
                // Reset barrier, synchronize on the universe object and check for broken to avoid multiple resets
                synchronized(u){
                    if(barrier.isBroken()) barrier = new CyclicBarrier(u.getBodies().size());
                }
                body.step(); // Step correct amount
                barrier.await(); //wait for all other steps.
                
                // Reset barrier, synchronize on the universe object to avoid multiple resets
                synchronized(u){
                    if(barrier.isBroken()) barrier = new CyclicBarrier(u.getBodies().size());
                }
                
            } catch (    InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(MassiveBodyThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }
    
    /**
     * This method ends the thread gracefully by causing run to return.
     */
    public void done(){
        done = true;
    }
    
    
}
