package it.unicam.cs.asdl2021.es6;

import static java.time.Duration.ofMillis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class ASDL2021SingleLinkedListTest {
    private ASDL2021SingleLinkedList<Integer> list;
    @BeforeEach
    void setUp() {
        list = new ASDL2021SingleLinkedList<Integer>();
    }

    @Test
    void size() {
        assertEquals(this.list.size(), 0);
        this.list.add(new Integer(1));
        this.list.add(new Integer(2));
        assertEquals(this.list.size(), 2);
    }

    @Test
    void isEmpty() {
        assertTrue(this.list.isEmpty());
        this.list.add(1);
        assertFalse(this.list.isEmpty());
    }

    @Test
    void contains() {
        assertThrows(NullPointerException.class, () -> list.add(null));
        this.list.add(new Integer(1));
        assertTrue(this.list.contains(new Integer(1)));
        assertFalse(this.list.contains(new Integer(2)));
    }

    @Test
    void add() {
        assertThrows(NullPointerException.class, () -> list.add(null));
        assertTrue(this.list.add(new Integer(1)));
        assertEquals(1, this.list.size());
        assertTrue(this.list.add(new Integer(2)));
        assertEquals(2, this.list.size());
        assertTrue(this.list.add(new Integer(3)));
        assertEquals(3, this.list.size());
    }

    @Test
    void remove() {
        assertThrows(NullPointerException.class, () -> list.add(null));
        assertFalse(this.list.remove(new Integer(3)));
        this.list.add(new Integer(1));
        this.list.add(new Integer(2));
        this.list.add(new Integer(3));
        assertTrue(this.list.remove(new Integer(3)));
        assertEquals(this.list.size(), 2);
    }

    @Test
    void clear() {
        for(int i = 0; i < 200; i++){
            this.list.add(new Integer((int)Math.random()));
        }
        assertTimeout(ofMillis(10), () -> {
            this.list.clear();
            assertTrue(this.list.isEmpty());
        });
    }

    @Test
    void get() {
        for(int i = 0; i < 200; i++){
            this.list.add(i);
        }
        assertEquals(3, this.list.get(new Integer(3)));
    }

    @Test
    void set() {
        for(int i = 0; i < 200; i++){
            this.list.add(i);
        }
        assertEquals(4, this.list.set(4, new Integer(4)));
    }

    @Test
    void testAdd() {
        for(int i = 0; i < 200; i++){
            this.list.add(i);
        }
        list.add(5, new Integer(201));
        assertTrue(this.list.contains(5));
        assertTrue(this.list.contains(201));
    }

    @Test
    void testRemove() {
        for(int i = 0; i < 200; i++){
            this.list.add(i);
        }
    }

    @Test
    void indexOf() {
        for(int i = 0; i < 200; i++){
            this.list.add(i);
        }
    }

    @Test
    void lastIndexOf() {
        for(int i = 0; i < 200; i++){
            this.list.add(i);
        }
    }

    @Test
    void toArray() {
        for(int i = 0; i < 200; i++){
            this.list.add(i);
        }
    }
}