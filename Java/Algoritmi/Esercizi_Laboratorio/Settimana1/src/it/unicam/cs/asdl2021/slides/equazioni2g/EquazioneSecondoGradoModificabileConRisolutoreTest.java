/**
 * 
 */
package it.unicam.cs.asdl2021.slides.equazioni2g;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * @author Template: Luca Tesei, Implementation: Studente
 *
 */
class EquazioneSecondoGradoModificabileConRisolutoreTest {
	 /*
     * Costante piccola per il confronto di due numeri double
     */
    private static final double EPSILON = 1.0E-15;
	 
   
    @Test
    final void testEquazioneSecondoGradoModificabileConRisolutore() {
        assertThrows(IllegalArgumentException.class, () -> new EquazioneSecondoGradoModificabileConRisolutore(0, 1, 1));
        EquazioneSecondoGradoModificabileConRisolutore eq = new EquazioneSecondoGradoModificabileConRisolutore(1, 1, 1);
        assertTrue(Math.abs(eq.getA() - 1) < EPSILON);
        assertTrue(Math.abs(eq.getB() - 1) < EPSILON);
        assertTrue(Math.abs(eq.getC() - 1) < EPSILON);
        assertFalse(eq.isSolved());
        assertThrows(IllegalStateException.class, () -> eq.getSolution());
    }

    @Test
    final void testGetA() {
    	EquazioneSecondoGradoModificabileConRisolutore eq = new EquazioneSecondoGradoModificabileConRisolutore(1, 1, 1);
    	eq.setA(2);
    	assertTrue(Math.abs(eq.getA() - 2) < EPSILON);
    }

   
    @Test
    final void testSetA() {
        EquazioneSecondoGradoModificabileConRisolutore eq = new EquazioneSecondoGradoModificabileConRisolutore(1, 1, 1);
        assertThrows(IllegalArgumentException.class, () -> eq.setA(0));
        //set solved true
        eq.solve();
        eq.setA(2);
        assertTrue(Math.abs(eq.getA() - 2) < EPSILON);
        //test attributo solved settato a false dopo set 
        assertFalse(eq.isSolved());
    }

   
    @Test
    final void testGetB() {
    	EquazioneSecondoGradoModificabileConRisolutore eq = new EquazioneSecondoGradoModificabileConRisolutore(1, 1, 1);
    	eq.setB(2);
    	assertTrue(Math.abs(eq.getB() - 2) < EPSILON);
    }

    
    @Test
    final void testSetB() {
    	EquazioneSecondoGradoModificabileConRisolutore eq = new EquazioneSecondoGradoModificabileConRisolutore(1, 1, 1);
        eq.solve();
        eq.setB(2);
        assertTrue(Math.abs(eq.getB() - 2) < EPSILON);
        //test attributo solved settato a false dopo set 
        assertFalse(eq.isSolved());
    }

    
    @Test
    final void testGetC() {
    	EquazioneSecondoGradoModificabileConRisolutore eq = new EquazioneSecondoGradoModificabileConRisolutore(1, 1, 1);
    	eq.setC(2);
    	assertTrue(Math.abs(eq.getC() - 2) < EPSILON);
    }

   
    @Test
    final void testSetC() {
    	EquazioneSecondoGradoModificabileConRisolutore eq = new EquazioneSecondoGradoModificabileConRisolutore(1, 1, 1);
        eq.solve();
        eq.setC(2);
        assertTrue(Math.abs(eq.getC() - 2) < EPSILON);
        //test attributo solved settato a false dopo set 
        assertFalse(eq.isSolved());
    }

   
    @Test
    final void testIsSolved() {
    	EquazioneSecondoGradoModificabileConRisolutore eq = new EquazioneSecondoGradoModificabileConRisolutore(1, 1, 1);
    	//test solved pre risoluzione
    	assertFalse(eq.isSolved());
    	eq.solve();
    	//test solved post risoluzione
    	assertTrue(eq.isSolved());
    }

    
    @Test
    final void testSolve() {
    	//test equazione senza soluzioni 
    	EquazioneSecondoGradoModificabileConRisolutore eq1 = new EquazioneSecondoGradoModificabileConRisolutore(1, 1, 1);
    	eq1.solve();
    	SoluzioneEquazioneSecondoGrado no_solution = eq1.getSolution();
    	assertTrue(no_solution.isEmptySolution());
    	assertFalse(no_solution.isOneSolution());
    	//test equazione soluzioni coincidenti
    	EquazioneSecondoGradoModificabileConRisolutore eq2 = new EquazioneSecondoGradoModificabileConRisolutore(1, -2, 1);
    	eq2.solve();
    	SoluzioneEquazioneSecondoGrado single_solution = eq2.getSolution();
    	assertFalse(single_solution.isEmptySolution());
    	assertTrue(single_solution.isOneSolution());
    	assertTrue(Math.abs(single_solution.getS1() - 1) < EPSILON);
    	//test equazione soluzioni reali distinte
    	EquazioneSecondoGradoModificabileConRisolutore eq3 = new EquazioneSecondoGradoModificabileConRisolutore(1, -3, 2);
    	eq3.solve();
    	SoluzioneEquazioneSecondoGrado real_solutions = eq3.getSolution();
    	assertTrue(Math.abs(real_solutions.getS1() - 1) < EPSILON
                || Math.abs(real_solutions.getS1() - 2) < EPSILON);
        assertTrue(Math.abs(real_solutions.getS2() - 1) < EPSILON
                || Math.abs(real_solutions.getS2() - 2) < EPSILON);
        assertFalse(Math.abs(real_solutions.getS1() - real_solutions.getS2()) < EPSILON);
        //test attributo solved
        assertTrue(eq3.isSolved());
    }

    @Test
    final void testGetSolution() {
    	//test equazione non risolta
		assertThrows(IllegalStateException.class, () -> {
			EquazioneSecondoGradoModificabileConRisolutore eq = new EquazioneSecondoGradoModificabileConRisolutore(1, 1, 1);
			eq.getSolution();
		});
    }

}
