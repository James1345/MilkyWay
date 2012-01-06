package milkyway;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.logging.*;
import javax.swing.*;

import static java.lang.Math.*;

/**
 * This thread is responsible for drawing the visualization of the simulator.
 * 
 * @author james
 */
public class DrawThread implements Runnable{
    
    // The main app window
    protected JFrame window;
    
    // The screen being used.
    protected GraphicsDevice screen;
    
    // The screen dimensions [width, height]
    protected int width, height;
    
    protected BaseUniverse universe;
    
    protected boolean done = false; // are we done yet?
    
    public DrawThread(BaseUniverse universe){
        this.universe = universe;
    }    
    
    /**
     * Runs this DrawThread.
     * This method calls the DrawThread's protected methods in the following order:
     * <ul>
     * <li>createGUI()</li>
     * <li>register()</li>
     * <li>showGUI()</li>
     * <li>render()</li>
     * </ul>
     */
    @Override
    public void run(){
        
        createGUI();
        register();
        showGUI();
        render();
        
    }
    
    /**
     * Create the window and set up essential options
     */
    protected void createGUI(){
        
        // Get graphics setup
        screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        //Set up window
        window = new JFrame("Milky Way", screen.getDefaultConfiguration());
        window.setExtendedState(window.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        window.setResizable(false);
        window.setIgnoreRepaint(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    /**
     * A method for setting up listeners.
     */
    protected void register(){
        // setup exiting
        window.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyChar() == 'q'){
                    done = true;
                    universe.done();
                }
            }
        });
    }
    
    /**
     * Attempt to set the window to fullscreen and show it; create window
     * BufferStrategy.
     * 
     */
    protected void showGUI(){
        // Attempt to set to fullscreen
        if(screen.isFullScreenSupported()){
            window.setUndecorated(true);
            screen.setFullScreenWindow(window);
            DisplayMode dm = screen.getDisplayModes()[0];
            screen.setDisplayMode(dm);
            width = dm.getWidth();
            height = dm.getHeight();
            
            
        } else {
            width = window.getWidth();
            height = window.getHeight();
        }
        
        // Show GUI
        window.setVisible(true);
        
        //Set up buffering
        window.createBufferStrategy(5);
        
    }
    
    /**
     * render loop
     * 
     * This method should contain the loop which draws to the screen.
     * within this method is a call to {@link #drawBodies(Graphics2D)}, which draws all of
     * the associated Universe's bodies.
     * 
     * The loop ends by sleeping for 16,666ns (60Hz)
     */
    protected void render(){
        
        BufferStrategy strategy = window.getBufferStrategy();
        
        // Render loop
        while(!done){
            // Get Graphics object
            Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Render
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, width, height);
            
            drawBodies(g);
            
            g.dispose();
            strategy.show();
            
            try{
                Thread.sleep(16,666); // sleep for approx. 1/60 seconds
            } catch (InterruptedException e){
                Logger.getLogger(DrawThread.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        
        // If done dispose window.
        window.dispose();
    }
    
    /**
     * Draw all of the bodies of the universe to the given Graphics object.
     * This method is called by render and should be overridden rather than
     * {@link #render()} if only the body drawing (i.e. not general setup) needs
     * to be changed
     * @param g The graphics2D to draw to.
     */
    protected void drawBodies(Graphics2D g){
        /*
         * Create a new list of elements so original is unnaffected
         * This resolves 2 problems, the new list can be sorted with no 
         * ill effects to the model list; and the model list may be modified
         * Without crashing the draw thread.
         */

        ArrayList<BaseBody> bodies = new ArrayList<>( universe.getBodies() ); 
        Collections.sort(bodies, new BodySorter());
        for(BaseBody body : bodies){
            double rad = body.getRadius();

            double x =  body.getX0();
            double y =  body.getX1();
            float mag = (float) (10000/body.getMass()); // temporary magic number until I work out proper values.
            Color color = Color.getHSBColor(0.1666f, mag > 1.0f ? 1.0f : mag, 1.0f);
            g.setColor(color);
            g.fillOval((int) ceil(x-(rad/2)), (int) ceil(y-(rad/2)), (int) ceil(rad), (int) ceil(rad));
        }
    
    }
    
    /**
     * Compares bodies based on their z value (to determine drawing order)
     * Used by {@link #render()}
     */
    protected class BodySorter implements Comparator<BaseBody> {
        
        @Override
        public int compare(BaseBody o1, BaseBody o2){
            return Double.compare(o1.getX2(), o2.getX2());
        }
    }
    
}
