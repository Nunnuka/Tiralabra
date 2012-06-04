package Tira;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Annika
 */
public class BinomiSolmu {

    int arvo;
    int aste;
    BinomiSolmu sisar;
    BinomiSolmu vanhempi;
    BinomiSolmu lapsi;

    public BinomiSolmu() {
    }

    public BinomiSolmu(int arvo) {
        this.arvo = arvo;
    }

    public String tulostaPuu(int syvyys) {
        String tulostus = "";

        for (int i = 0; i < syvyys; i++) {
            tulostus = tulostus + "  ";
        }
        tulostus = tulostus + toString() + "\n";

        BinomiSolmu a = lapsi;
        while (a != null) {
            tulostus = tulostus + a.tulostaPuu(syvyys + 1);
            a = a.sisar;
        }

        return tulostus;
    }

    public String toString() {
        return ("Arvo: " + arvo + " aste: " + aste);
    }
}


