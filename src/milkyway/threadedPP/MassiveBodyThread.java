package milkyway.threadedPP;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;

/**
 * This is the thread in which a set of MassiveBodys runs their calculations. It is 
 * Synchronized with the other threads by use of a cyclic barrier.
 * 
 * @author james
 */
public class MassiveBodyThread extends Thread{
    
    private CyclicBarrier barrier;
    private final Universe u;
    private ArrayList<MassiveBody> bodies;
    boolean done = false;
    
    public MassiveBodyThread(String name, CyclicBarrier barrier, Universe u){
        super(name);
        this.barrier = barrier;
        this.bodies = new ArrayList<>();
        bodies.ensureCapacity(10); // ensure capacity is at least 10.
        this.u = u;
    }
    
   public void add(MassiveBody b){
       bodies.add(b);
   }
    
    @Override
    public void run(){
        while(!done){
            try {
                for(MassiveBody body : bodies){
                    body.update(); // Update acceleration wrt all other boddies
                }
                barrier.await(); //wait for end of updates
                
                // Reset barrier, synchronize on the universe object and check for broken to avoid multiple resets
                synchronized(u){
                    if(barrier.isBroken()) barrier = new CyclicBarrier(u.getBodies().size());
                }
                for(MassiveBody body : bodies){
                    body.step(); // Step correct amount
                }
                barrier.await(); //wait for all other steps.
                
                // Reset barrier, synchronize on the universe object to avoid multiple resets
                synchronized(u){
                    if(barrier.isBroken()) barrier = new CyclicBarrier(u.getBodies().size());
                }
                
            } catch ( InterruptedException | BrokenBarrierException ex) {
                /**
                 * On an exception allow the thread to exit. 
                 * The thread is interrupted when the program needs to exit.
                 */
                Logger.getLogger(MassiveBodyThread.class.getName()).log(Level.FINER, null, ex);
                done = true;
            }
            
            
        }
    }
    
    
}
