package main;

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


public class Binaarikekotest {
    Binaarikeko binaarikeko;
    
    public Binaarikekotest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        binaarikeko = new Binaarikeko();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void kekoTesti(){
        long alku = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++){
            binaarikeko.insert(i);
        }
        for (int i = 0; i < 10000000; i++){
            binaarikeko.delete();
            
        }
        long loppu = System.currentTimeMillis();
        long kokonaisaika = loppu - alku;
        System.out.println("Aikaa kului: " + kokonaisaika + "ms = " + (kokonaisaika*1.0)/1000 + "s");      
    }
}
