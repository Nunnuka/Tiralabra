/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package binaari;

/**
 *
 * @author Annika
 */
public class BinaariNode {

    int arvo;
    BinaariNode vanhempi;
    BinaariNode vasen;
    BinaariNode oikea;

    public BinaariNode(int arvo) {
        this.arvo = arvo;
    }

    public BinaariNode() {
    }
}