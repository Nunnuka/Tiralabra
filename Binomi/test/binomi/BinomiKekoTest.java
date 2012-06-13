package binomi;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Annika
 */
public class BinomiKekoTest {
    BinomiKeko keko;
    
    @Before
    public void setUp() {
    keko = new BinomiKeko();
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
        System.out.println("100k alkion poistamiseen kului: " + (poistoLoppu-poistoAlku)*1.0 + "ms.");
    }

    @Test
    public void kekoTesti(){
        long alku = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++){
            keko.insert(i);
        }
        
     
        long loppu = System.currentTimeMillis();
        long kokonaisaika = loppu - alku;
        System.out.println("Aikaa kului: " + kokonaisaika + "ms = " + (kokonaisaika*1.0)/1000 + "s");      
    }
    
    
    @Test
    public void kekoTesti2(){
        long alku = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++){
            keko.insert(i);
        }
        for (int i = 0; i < 10; i++){
            keko.extract_min();
        }
     
        long loppu = System.currentTimeMillis();
        long kokonaisaika = loppu - alku;
        System.out.println("Aikaa kului: " + kokonaisaika + "ms = " + (kokonaisaika*1.0)/1000 + "s");      
    }
}
