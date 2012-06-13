/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fibonacci;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Annika
 */
public class FibonacciKekoTest {
    
    FibonacciKeko keko;
    
    @Before
    public void setUp() {
        keko = new FibonacciKeko();
    }

    @Test
    public void lisaysTesti(){
        long alku = System.currentTimeMillis();
        for (int i = 100000; i >= 0; i--){
            keko.insert(i);
        }
        long loppu = System.currentTimeMillis();
        System.out.println("Aikaa kului 100k alkion lisäykseen: " + (loppu-alku)*1.0 + "ms.");
        
        long poistoAlku = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++){
            keko.extract_min();
        }
        long poistoLoppu = System.currentTimeMillis();
        System.out.println("Aikaa 100000 alkion poistamiseen kului: " + (poistoLoppu-poistoAlku)*1.0 + "ms.");
    }
    
    @Test
    public void pieniLisaysTesti(){
        FibonacciKeko Fikeko = new FibonacciKeko();
        for (int i = 4; i != 0; i--) {
            Fikeko.insert(i);
        }
        FibonacciKeko Fikeko2 = new FibonacciKeko();
        for (int j = 4; j != 0; j--) {
            Fikeko2.insert(j);
        }
        
        FibonacciKeko unioni = Fikeko.union(Fikeko, Fikeko2);
        assertEquals(unioni.extract_min().arvo, 1);
        assertEquals(unioni.extract_min().arvo, 1);
        assertEquals(unioni.extract_min().arvo, 2);
        assertEquals(unioni.extract_min().arvo, 2);

    }
    //Lisätään kekoon 10000 alkiota ja poistetaan ne.
    @Test
    public void lisaysJaPoistoTesti(){
        long alku = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++){
            keko.insert(i);
        }
        for (int i = 0; i < 10000; i++){
            assertEquals(keko.extract_min().arvo, i);
        }
        long loppu = System.currentTimeMillis();
        System.out.println("Aikaa kului: " + (loppu-alku*1.0) + "ms");
    }
    
    //Lisätään kekoon 10000 alkiota käänteisessä järjestyksessä ja poistetaan ne.
    @Test
    public void kaanteinenLisaysJaPoistoTesti(){
        long alku = System.currentTimeMillis();
        for (int i = 10000; i >= 0; i--){
            keko.insert(i);
        }
        for (int i = 0; i < 10000; i++){
            assertEquals(keko.extract_min().arvo, i);
        }
        long loppu = System.currentTimeMillis();
        System.out.println("Käänteiseen aikaa kului: " + (loppu-alku*1.0) + "ms");
    }
    
    
}
