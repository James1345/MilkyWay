package milkyway;

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
        
        Examples.circle(200);
        
        Universe.get().start();
        (new DrawThread()).start();
    }
    
}
