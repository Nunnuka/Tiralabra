/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Annika
 */

/*
 * Solmuun tallenetaan kolme linkki�: vanhempi, vasen ja oikea lapsi
 */
public class Binaarinode {

    int value;
    Binaarinode parent;
    Binaarinode left;
    Binaarinode right;

    public Binaarinode(int value) {
        this.value = value;
    }

    public Binaarinode() {
    }
}