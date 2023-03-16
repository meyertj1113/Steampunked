package edu.msu.cse476.steampunked;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This is a test class that demonstrates how the PlayingArea and Pipe classes work.
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class PlayingAreaTest {
    /**
     * Simple constructor test
     */
    @Test
    public void testConstruct() {
        PlayingArea play = new PlayingArea(5, 7);

        assertEquals(7, play.getHeight());
        assertEquals(5, play.getWidth());
    }
    /**
     * Test for PlayingArea.search()
     * Builds up a playing area adding pipes one at a time until
     * there is a complete connection to the end. Leaks will continue
     * to be reported until then.
     */
    @Test
    public void testSearch() {
        PlayingArea play = new PlayingArea(5, 5);

        Pipe start = new Pipe(false, true, false, false);
        Pipe end = new Pipe(false, false, false, true);
        Pipe end2 = new Pipe(false, false, false, true);

        play.add(start, 0, 1);
        play.add(end, 4, 3);
        play.add(end2, 4, 1);

        /*
         * Just a start and end, so we certainly leak
         */
        assertFalse(play.search(start));

        play.add(new Pipe(false, false, true, true), 1, 1);
        assertFalse(play.search(start));

        play.add(new Pipe(true, true, true, false), 1, 2);
        assertFalse(play.search(start));

        play.add(new Pipe(true, true, false, false), 1, 3);
        assertFalse(play.search(start));

        play.add(new Pipe(true, false, true, true), 2, 2);
        assertFalse(play.search(start));

        play.add(new Pipe(true, false, true, true), 2, 3);
        assertFalse(play.search(start));

        play.add(new Pipe(true, true, false, false), 2, 4);
        assertFalse(play.search(start));

        play.add(new Pipe(true, false, false, true), 3, 4);
        assertFalse(play.search(start));

        play.add(new Pipe(false, true, true, false), 3, 3);
        assertFalse(play.search(start));

        // This caps the last leak, so we should get a true
        // return value
        play.add(new Pipe(false, false, true, false), 2, 1);
        assertTrue(play.search(start));

        // There is a path to the end, so it should have been visited
        assertTrue(end.beenVisited());

        // There is not a path to this other end, so it should not
        // have been visited
        assertFalse(end2.beenVisited());
    }
}