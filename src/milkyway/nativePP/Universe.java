/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkyway.nativePP;

import milkyway.*;

/**
 *
 * @author james
 */
public class Universe extends BaseUniverse {
    
    /**
     * Constructor override.
     * 
     * Universe values irrelevant, processing done in native code.
     */
    public Universe(){
        super(0,0);
    }
    
    /**
     * Retrieve the array of position arrays from the native code.
     * @return 
     */
    public native double[][] getPositions();
    
    /**
     * the native code's run method.
     */
    public native void run0();
    
    /**
     * The call to stop the native code.
     */
    public native void stop0();
    
    /**
     * Load the C library.
     */
    static {
        System.loadLibrary("nativepp");
    }
    
    public void run(){
        run0();
    }
    
    public void done(){
        stop0();
    }
    
}
