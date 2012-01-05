/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkyway.grid;

import milkyway.*;
/**
 *
 * @author james
 */
public class MilkyWay {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Universe universe = new Universe(1, 0.01);
        universe.addBody(new MassiveBody(10, 10, 200, 200, 0, 0, 0, 0, universe));
        //universe.addBody(new MassiveBody(10, 10, 200, 600, 0, -0.1, 0, 0, universe));
        
        DrawThread render = new DrawThread(universe);
        
        (new Thread(universe, "Universe")).start();
        (new Thread(render, "DrawThread")).start();
        
    }
}
