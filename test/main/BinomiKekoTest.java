package Tira;

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
    public BinomiKekoTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
      keko = new BinomiKeko();
    }
    
    @After
    public void tearDown() {
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
