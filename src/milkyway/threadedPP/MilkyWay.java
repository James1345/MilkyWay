package milkyway.threadedPP;

/**
 * Main class.
 * 
 * @author james
 */
public class MilkyWay {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Universe.init(3, 1, 0.01);
        Universe.get().add(new MassiveBody(50, 10, new double[]{400,200, 200}, new double[]{0.1,0, 0}));
        Universe.get().add(new MassiveBody(50, 10, new double[]{400,600, -200}, new double[]{-0.1,0, 0}));
        
        Universe.get().start();
        (new DrawThread()).start();
    }
    
}
