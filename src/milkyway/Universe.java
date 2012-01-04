package milkyway;

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
public class Universe {
    
    // Global Singleton
    private static Universe one = null;
    public static void init(int dimensions, double G, double step){
        one = new Universe(dimensions, G, step);
    }
    public static Universe get(){return one;}
    
    // Global instance vars
    /**
     * Globally accessible list of Massive Bodies.
     */
    public final List<MassiveBody> bodies = new ArrayList<MassiveBody>();
    
    // global value of G;
    public final double G; // 6.67e-11;
    
    // Global number of Dimensions of the universe
    public final int Dimensions;
    
    // Ammount of time to step per cycle
    public final double Step;
    
    // Instance methods
    CyclicBarrier barrier;
    private Universe(int dimensions, double G, double step){
        this.Dimensions = dimensions;
        this.Step = step;
        this.G = G;
    }

    
    public void add(MassiveBody body){
        bodies.add(body);
    }
    
    public void start(){
        barrier = new CyclicBarrier(bodies.size()); 
        for(MassiveBody body : bodies){
            (new MassiveBodyThread(body, barrier)).start();
        }
    }
    
    
    
}
