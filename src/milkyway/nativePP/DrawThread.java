/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkyway.nativePP;

import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author james
 */
public class DrawThread extends milkyway.DrawThread{
    
    public DrawThread(Universe u){
        super(u);
    }
    
    @Override
    protected void register(){
        window.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent k){
                if(k.getKeyChar() == 'q'){
                    ((Universe)universe).stop0(); // Must free up C resources.
                    done = true; // exit this thread.
                }
            }
        });
    }
    
    /**
     * Override the drawBodies method to call the native code.
     * @param g The graphics context to draw to.
     */
    @Override
    protected void drawBodies(Graphics2D g){
        Universe u = (Universe)universe;
        g.setColor(Color.YELLOW);
        for(double[] x : u.getPositions()){
            g.fillOval((int)Math.ceil(x[0]), (int)Math.ceil(x[1]), 1, 1);
        }
    }
    
    
    
}
