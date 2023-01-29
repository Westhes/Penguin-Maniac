/*
 * This class can be seen as an indirect extension of 2 sinewaves. Able to return various information about it.
 */
package fys2.Game;

import fys2.Scenes.IGameScene;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author Westhes
 */
class SuperSineWave 
        implements IEventHandler, ITerrain {
    private final static int RED_COLOR = -65536;
    private final static int GREEN_COLOR = -16711936;
    private final static int BLUE_COLOR = -16776961;
    //private final static int EXTRA_OFFSET = 10;
    public final static int XSPACING = 10;
    private final static boolean DRAWSINES = false;

    private SineWave a;
    private SineWave b;
    
    private ArrayList<Float> waveY = new ArrayList<Float>();
    private int waveLength;
    int cColor = 0;

    public PVector startPosition = new PVector();

    // Property
    public PVector getWaveLength() {
        return new PVector(waveLength * XSPACING, getHeightAtX(0));
    }

    IGameScene parent;
    PApplet p;

    // Constructors
    public SuperSineWave(IGameScene parent) {
        this(parent, 1f, 1.35f, 1f, 1f, new PVector(0, 0));
    }

    public SuperSineWave(IGameScene parent, float redAmplitude, float redPeriod, float blueAmplitude, float bluePeriod, PVector startPosition) {
        /* 
        // Reason so much why java is a bad language. 
        // https://stackoverflow.com/questions/1168345/why-do-this-and-super-have-to-be-the-first-statement-in-a-constructor
        SineWave _a = new SineWave(1f, 1.8f, color(255,0,0));
        SineWave _b = new SineWave(1f, 1f, color(0,0,255));
        this(_a, _b, color(0,255,0));
         */

        // Java perfection. Bleh.
        this(parent,
                new SineWave(parent, redAmplitude, redPeriod, RED_COLOR),
                new SineWave(parent, blueAmplitude, bluePeriod, BLUE_COLOR),
                GREEN_COLOR);
        
        
        this.startPosition = startPosition;
        float startHeight = getHeightAtX(waveLength * XSPACING);
        this.startPosition.y = startPosition.y - startHeight;
    }

    public SuperSineWave(IGameScene parent, SineWave _a, SineWave _b, int _color) {
        // Assign variables
        this.parent = parent;
        p = parent.getPApplet();
        a = _a;
        b = _b;
        cColor = _color;

        // Add us to the listener
        a.addEventListener(this);
        b.addEventListener(this);

        // Force the onchanged method
        onChanged(null);
    }
    
    // Forces to recalculate the wave.
    void recalculateWave() {
        waveLength = p.round(calculateWaveLength());

        p.println("Total length = " + waveLength * XSPACING);
        p.println("end height = " + getHeightAtX(waveLength * XSPACING));
    }

    // Draws the waves
    void draw() {
        // Calculate screenbounds
        //int leftBound  = 0     - XSPACING - scroll;
        //int rightBound = width + XSPACING - scroll;

        for (int i = 0; i < waveLength; i++) {
            // TODO: Fix once you have time
            p.vertex(startPosition.x + i * XSPACING, startPosition.y + waveY.get(i));
        }

        if (DRAWSINES) {
            a.draw();
            b.draw();
        }
    }

    // Calculates the position of the wave at X.
    public PVector getRawPointAtX(float x) {
        PVector pY1 = a.getPointAtX(x);
        PVector pY2 = b.getPointAtX(x);

        return new PVector(x, (pY1.y + pY2.y) + startPosition.y);
    }

    // Checks if the given variable is within this wave segment
    public boolean ContainsX(float x) {
        return (x > startPosition.x && x < startPosition.x + getWaveLength().x);
    }

    // Returns the height at world location.
    private float getHeightAtX(float x) {
        return a.getPointAtX(x).y + b.getPointAtX(x).y;
    }

    public float calculateWaveLength() {
        waveY.clear();
        float startHeight = getHeightAtX(0);
        waveY.add(startHeight);

        // 1, otherwise the first equal point would be 0
        // 10000, because we don't want to create an almost infinite loop.
        for (int i = 1; i < 10000; i++) {
            float waveHeight = getHeightAtX(i * XSPACING);
            waveY.add(waveHeight);

            if (startHeight == waveHeight) {
                return i;
            }
        }
        return 0;
    }
    
    // Responds to the onchanged event.
    @Override
    public void onChanged(Object obj) {
        recalculateWave();
    }
}
