package milkyway.threadedPP;

import java.lang.reflect.InvocationTargetException;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import static java.lang.Math.*;

/**
 * This thread is responsible for drawing the visualization of the simulator.
 * 
 * @author james
 */
public class DrawThread extends Thread{
    
    // The main app window
    private JFrame window;
    
    // The screen being used.
    private GraphicsDevice screen;
    
    // The screen dimensions [width, height]
    private int width, height;
    
    @Override
    public void run(){
        
        // Create the window and set up essential options.
        try {
            SwingUtilities.invokeAndWait(new Runnable(){
                @Override
                public void run(){
                    
                    // Get graphics setup
                    screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                    
                    //Set up window
                    window = new JFrame("Milky Way", screen.getDefaultConfiguration());
                    window.setExtendedState(window.getExtendedState()|JFrame.MAXIMIZED_BOTH);
                    window.setResizable(false);
                    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    
                    
                    // setup exiting - badly
                    window.addKeyListener(new KeyAdapter(){
                        @Override
                        public void keyPressed(KeyEvent e){
                            if(e.getKeyChar() == 'q'){
                                System.exit(0);
                            }
                        }
                    });
                }
            });
            
        } catch (InterruptedException ex) {
            Logger.getLogger(DrawThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(DrawThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Attempt to set to fullscreen
        if(screen.isFullScreenSupported()){
            window.setUndecorated(true);
            window.setIgnoreRepaint(true);
            screen.setFullScreenWindow(window);
            DisplayMode dm = screen.getDisplayModes()[0];
            screen.setDisplayMode(dm);
            width = dm.getWidth();
            height = dm.getHeight();
        }
        
        // Show GUI
        window.setVisible(true);
        
        //Set up buffering
        window.createBufferStrategy(5);
        BufferStrategy strategy = window.getBufferStrategy();
        
        // Render loop
        while(true){
            // Get Graphics object
            Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
            // Render
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);
            
            g.setColor(Color.YELLOW);
            
            for(MassiveBody body : Universe.get().bodies){
                    double[] pos = body.getPos();
                    double rad = body.getRadius();
                    g.fillOval((int) ceil(pos[0]-rad/2), (int) ceil(pos[1]-rad/2), (int) ceil(rad), (int) ceil(rad));
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
