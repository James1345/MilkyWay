package milkyway.threadedPP;

import milkyway.*;

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
        
        Universe u = Examples.binaryStar();
        u.run();
        (new Thread(new DrawThread(u))).start();
    }
    
}
