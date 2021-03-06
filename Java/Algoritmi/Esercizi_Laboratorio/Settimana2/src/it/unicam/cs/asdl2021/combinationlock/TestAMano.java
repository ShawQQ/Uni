/**
 * 
 */
package it.unicam.cs.asdl2021.combinationlock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Testa le funzionalità delle classi relative alla cassaforte.
 * 
 * @author Luca Tesei
 *
 */
public class TestAMano {

    public static void main(String[] args) {
        BufferedReader inputChannel = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("Dammi la combinazione della nuova cassaforte");
        CombinationLock c = null;
        String input = null;
        try {
            input = inputChannel.readLine();
            c = new CombinationLock(input.trim());
        } catch (IOException e) {
            System.err.println("Errore di input/output!");
            System.exit(1);
        }
        catch (NullPointerException e) {
            System.err.println("ERRORE: " + e.getMessage());
            System.exit(2);
        } catch (IllegalArgumentException e) {
            System.err.println("ERRORE: " + e.getMessage());
            System.exit(2);
        }
        c.lock();
        System.out
                .println("Ho creato correttamente la cassaforte e l'ho chiusa");
        if (c.isOpen())
            System.out.println("ERRORE: La cassaforte non si � chiusa correttamente!");
        else
            System.out.println("La cassaforte si � chiusa correttamente.");
        System.out.println("Imposto la combinazione errata...");
        c.setPosition(input.trim().charAt(0));
        c.setPosition(input.trim().charAt(1));
        c.setPosition((char) (input.trim().trim().charAt(2) + 1));
        c.open();
        if (c.isOpen())
            System.out.println(
                    "ERRORE: La cassaforte si � aperta con la combinazione errata!");
        else
            System.out.println("La cassaforte � rimasta chiusa correttamente.");
        System.out.println("Imposto la combinazione giusta...");
        c.setPosition(input.trim().charAt(0));
        c.setPosition(input.trim().charAt(1));
        c.setPosition(input.trim().trim().charAt(2));
        c.open();
        if (c.isOpen())
            System.out.println("La cassaforte si � aperta correttamente!");
        else
            System.out.println(
                    "ERRORE: La cassaforte � rimasta chiusa con la combinazione giusta!");
        System.out.println("Creo uno scassinatore...");
        Burglar f = new Burglar(c);
        System.out.println("Lo scassinatore dice che la combinazione è "
                + f.findCombination());
        System.out.println("Lo scassinatore ha effettuato " + f.getAttempts()
                + " tentativi prima di trovare la combinazione.");
        System.exit(0);
    }

}
