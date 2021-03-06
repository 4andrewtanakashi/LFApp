package com.ufla.lfapp.core.grammar;

import com.ufla.lfapp.utils.ResourcesContext;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class CloneTest {

    private Grammar g;

    @BeforeClass
    public static void setTest() {
        ResourcesContext.isTest = true;
    }

    @Before
    public void setUp() {
        String variables[] = new String[]{"S", "S1", "A", "B", "V"};
        String terminals[] = new String[]{"a", "b"};
        String initialSymbol = "S";
        String rules[] = new String[]{
                "S -> AB | BS1 | BV",
                "S1 -> AB",
                "A -> BA | a",
                "B -> aa | VS1 | S1V | S1S1 | b",
                "V -> a"};
        this.g = new Grammar(variables, terminals, initialSymbol, rules);
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        Grammar gc = (Grammar) this.g.clone();

        assertEquals(5, gc.getVariables().size());
        assertEquals(2, gc.getTerminals().size());
        assertEquals("S", gc.getInitialSymbol());
        assertEquals(12, gc.getRules().size());

        this.g.getVariables().remove("S1");
        assertEquals(5, this.g.getVariables().size());
        assertEquals(5, gc.getVariables().size());

        this.g.getTerminals().remove("a");
        assertEquals(2, this.g.getTerminals().size());
        assertEquals(2, gc.getTerminals().size());


        Rule rx = new Rule("V", "a");
        assertTrue(this.g.getRules().contains(rx));
        assertTrue(gc.getRules().contains(rx));

        for (Rule r : this.g.getRules()) {
            r.setLeftSide("X" + r.getLeftSide());
            r.setRightSide(r.getRightSide() + "Y");
        }

        assertFalse(this.g.getRules().contains(rx));
        assertTrue(gc.getRules().contains(rx));
    }

}
