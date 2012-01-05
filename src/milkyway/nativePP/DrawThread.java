/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkyway.nativePP;

import java.awt.*;
import java.awt.image.*;
import java.util.logging.*;

/**
 *
 * @author james
 */
public class DrawThread extends milkyway.DrawThread{
    
    public DrawThread(Universe u){
        super(u);
    }
    
    /**
     * Override render to draw the objects retrieved from native code.
     */
    @Override
    public void render(){
        BufferStrategy strategy = window.getBufferStrategy();  
        Universe u = (Universe)universe;
        while(true){
            
            Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);
            g.setColor(Color.YELLOW);
            for(double[] x : u.getPositions()){
                g.fillOval((int)Math.ceil(x[0]), (int)Math.ceil(x[1]), 1, 1);
            }
            g.dispose();
            strategy.show();
            
            try{
                Thread.sleep(16,666); // sleep for approx. 1/60 seconds
            } catch (InterruptedException e){
                Logger.getLogger(DrawThread.class.getName()).log(Level.SEVERE, null, e);
            }
            
        }
    }
    
    
    
}
