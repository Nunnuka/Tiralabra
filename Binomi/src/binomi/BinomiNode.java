/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package binomi;

/**
 *
 * @author Annika
 */
public class BinomiNode {

    int arvo;
    int aste;
    BinomiNode sisar;
    BinomiNode vanhempi;
    BinomiNode lapsi;

    public BinomiNode() {
    }

    public BinomiNode(int arvo) {
        this.arvo = arvo;
    }

    public String tulostaPuu(int syvyys) {
        String tulostus = "";

        for (int i = 0; i < syvyys; i++) {
            tulostus = tulostus + "  ";
        }
        tulostus = tulostus + toString() + "\n";

        BinomiNode a = lapsi;
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

