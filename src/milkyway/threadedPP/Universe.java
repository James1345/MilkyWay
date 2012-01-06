package milkyway.threadedPP;

import milkyway.*;

import java.util.*;
import java.util.concurrent.*;

/**
 * The universe that contains Massive Bodies.
 * 
 * This is a singleton so it may be accessed from anywhere. However, first it 
 * must be initialized with the number of dimensions that the universe will be 
 * constructed in, and the time step per cycle. This must happen before any drawing or addition of massive
 * bodies. When the universe is started, each massive body begins updating in its
 * own thread, while synchronized by a Cyclic barrier. At this point, no more 
 * bodies can be added, but drawing may begin.
 * 
 * @author james
 */
public class Universe extends BaseUniverse{

    //Threading Objects
    protected CyclicBarrier barrier;
    protected MassiveBodyThread[] threads;
    
    public Universe(double G, double step){
        super(G, step);
        this.bodies = new ArrayList<>();
    }

    
    public void add(MassiveBody body){
        bodies.add(body);
    }
    
    /**
     * A run once run method, does not necessarily need a thread for this
     * implementation of Universe.
     */
    public void run(){
        barrier = new CyclicBarrier(bodies.size());
        threads = new MassiveBodyThread[bodies.size()];
        for(int i = 0; i < bodies.size(); i++){
            threads[i] = new MassiveBodyThread((MassiveBody)(bodies.get(i)), barrier, this);
            threads[i].start();
        }
    }
    
    public void done(){
        for(MassiveBodyThread t : threads){
            t.done();
        }
    }
    
    
    
}
