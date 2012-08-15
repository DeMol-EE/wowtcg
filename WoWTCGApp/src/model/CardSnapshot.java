/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Warkst
 */
public class CardSnapshot implements Serializable {
    private final boolean exhausted;
    private final boolean flipped;
    private final int counters;

    public CardSnapshot(boolean exhausted, boolean flipped, int counters) {
	this.exhausted = exhausted;
	this.flipped = flipped;
	this.counters = counters;
    }

    public int getCounters() {
	return counters;
    }

    public boolean isExhausted() {
	return exhausted;
    }

    public boolean isFlipped() {
	return flipped;
    }
}
