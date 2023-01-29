/*
 * Starting point of our application.
 */
package fys2;

import processing.core.PApplet;
import fys2.Scenes.*;

//import fys2.Game;
/**
 *
 * @author Westhes
 */
public class Main extends PApplet 
{
    IScene manager;
    
    private static long prev;
    
    /**
     * Starting point of the application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        main("fys2.Main");
    }
    
    public void settings() 
    {
        // Requires investigating: https://processing.org/reference/size_.html
        // According to this there are multiple render engines we can use.
        size(1280, 720); //FX2D
        // Anti aliasing, options are 0, 2, 3, 4, 8.
        smooth(8);
    }
    
    @Override
    public void setup()
    {
        manager = new SceneManager(this);
        manager.initialize();
    }
    
    @Override
    public void draw()
    {
        manager.update();
        manager.draw();
    }
    
    @Override
    public void keyPressed()
    {
        manager.keyPressedEvent(key);
    }
    
    @Override
    public void keyReleased()
    {
        manager.keyReleasedEvent(key);
    }
    
    // Quick fix for the lack of processing functionality.
    // Returns the timestep between the current and last frame.
    public static float getDeltaTime()
    {
      // https://forum.processing.org/two/discussion/18478/deltatime
      // Modification: System.nanoTime() is inacessable in Processing.
      return (float)((-prev + (prev = System.nanoTime()))/(1e6d) / 1000.0);
    }
}
