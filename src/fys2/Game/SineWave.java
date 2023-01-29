/*
 * This class implements a sinewave. Able to return various information about it.
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
public class SineWave {
    // EventHandlers

    private ArrayList<IEventHandler> eventListeners = new ArrayList<IEventHandler>();

    public void addEventListener(IEventHandler listener) {
        eventListeners.add(listener);
    }

    public void removeEventListener(IEventHandler listener) {
        eventListeners.remove(listener);
    }

    // Class parameters
    private final static int XSPACING = 15;
    private final static float AMPLITUDE = -100f;
    public final static float PERIOD = 600;

    private float amplitudeMultiplier = 1f;

    public float getAmplitudeMultiplier() {
        return amplitudeMultiplier;
    }

    public void setAmplitudeMultiplier(float value) {
        if (value != amplitudeMultiplier) {
            amplitudeMultiplier = value;
            onChanged();
        }
    }

    private float periodMultiplier = 1f;

    public float getPeriodMultiplier() {
        return periodMultiplier;
    }

    public void setPeriodMultiplier(float value) {
        if (value != periodMultiplier) {
            periodMultiplier = value;
            onChanged();
        }
    }

    private float waveY[];
    private int cColor = 0;

    private IGameScene parent;
    private PApplet p;

    public SineWave(IGameScene parent, float amplitudeMultiplier, float periodMultiplier, int cColor) {
        this.parent = parent;
        p = parent.getPApplet();

        waveY = new float[p.width / XSPACING + 3];
        this.amplitudeMultiplier = amplitudeMultiplier;
        this.periodMultiplier = periodMultiplier;
        this.cColor = cColor;

        recalculateWave();
    }

    public void recalculateWave() {
        int waveLength = p.round((PERIOD / p.abs(periodMultiplier) / XSPACING));
        waveLength = (waveLength > 0 && waveLength < 1000) ? waveLength : 1000;
        waveY = new float[waveLength];

        float t = 0;

        for (int i = 0; i < waveLength; i++) {
            t = p.HALF_PI + (p.TWO_PI / PERIOD * periodMultiplier) * XSPACING * i;

            waveY[i] = p.sin(t) * AMPLITUDE * amplitudeMultiplier;
        }
    }

    public void draw() {
        p.fill(cColor);

        // Temporary hack this to show nice curve
        for (int i = 0; i <= waveY.length; i++) {
            int j = i % waveY.length;
            p.ellipse(i * XSPACING, waveY[j], 16, 16);
        }
    }

    public PVector getPointAtX(float x) {
        // Calculate time at that point of the wave.
        float t = p.HALF_PI + (p.TWO_PI / PERIOD * periodMultiplier) * x;

        // Calculate the position Y at given time with the current amplitude and multiplier.
        float pY = p.sin(t) * AMPLITUDE * amplitudeMultiplier;
        return new PVector(x, pY);
    }

    /*
  public PVector getHALF_PI()
  {
    // calculate X.
    float x = getLength()/4;
    // Calculate the t of the sin wave.
    float t = HALF_PI + (TWO_PI/PERIOD * periodMultiplier) * x;
    // Obtain the height.
    float y = sin(t) * AMPLITUDE * amplitudeMultiplier;
    return new PVector(x, y);
  }

  public PVector getONE_PI()
  {
    // calculate X.
    float x = getLength()/2;
    // Calculate the t of the sin wave.
    float t = HALF_PI + (TWO_PI/PERIOD * periodMultiplier) * x;
    // Obtain the height.
    float y = sin(t) * AMPLITUDE * amplitudeMultiplier;
    return new PVector(x, y);
  }

  public PVector getTWO_PI()
  {
    // calculate X.
    float x = getLength();
    // Calculate the t of the sin wave.
    float t = HALF_PI + (TWO_PI/PERIOD * periodMultiplier) * x;
    // Obtain the height.
    float y = sin(t) * AMPLITUDE * amplitudeMultiplier;
    return new PVector(x, y);
  }
     */
    public float getLength() {
        return waveY.length * XSPACING;
    }

    private void onChanged() {
        recalculateWave();
        for (IEventHandler listener : eventListeners) {
            listener.onChanged(this);
        }
    }
}
