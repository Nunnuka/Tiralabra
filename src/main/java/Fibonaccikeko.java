/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TiraAnnika;

/**
 *
 * @author Annika
 */
public class Fibonaccikeko {

    private static final double logPhi = 1.0 / Math.log((1.0 + Math.sqrt(5.0)) / 2.0);
    Fibonaccinode min;
    int koko;

    public Fibonaccikeko() {
        min = null;
        koko = 0;
    }

    public Fibonaccikeko(Fibonaccinode solmu) {
        min = solmu;
        koko = 1;
    }


    //lisätään uusi solmu kekoon. Lisäksi tarkistetaan, onko uusi lisätty solmu minimi. Keossa on kahteen suuntaan linkitys, jotta toiminnot olisivat nopeita
    public void insert(int arvo) {
        Fibonaccinode x = new Fibonaccinode(arvo);
        x.aste = 0;
        x.vanhempi = null;
        x.lapsi = null;
        x.vasen = x;
        x.oikea = x;
        if (min != null) {
            //solmut linkitetään toisiinsa
            x.vasen = min;
            x.oikea = min.oikea;
            min.oikea = x;
            x.oikea.vasen = x;

            if (x.arvo < min.arvo) {
                min = x;
            }
        } else {
            min = x;
        }
        koko++;
    }

  

    /*  poistetaan y keon juurilistasta (this)
     *  tehdään y:stä x:n lapsi
     *  kasvatetaan x:n astetta
     */
    public void link(Fibonaccinode y, Fibonaccinode x) {
        y.vasen.oikea = y.oikea;
        y.oikea.vasen = y.vasen;

        y.vanhempi = x;

        if (x.lapsi == null) {
            x.lapsi = y;
            y.oikea = y;
            y.vasen = y;
        } else {
            y.vasen = x.lapsi;
            y.oikea = x.lapsi.oikea;
            x.lapsi.oikea = y;
            y.oikea.vasen = y;
        }
        x.aste++;
        y.mark = false;
    }

    //yhdistetään kaksi kekoa
    public Fibonaccikeko union(Fibonaccikeko K1, Fibonaccikeko K2) {
        Fibonaccikeko K = new Fibonaccikeko();
        //tarkistetaan, ettei kummankaan keon minimi ole null
        if ((K1 != null) && (K2 != null)) {
            //tehdään alustavasti K1:n minimistä K:n minimi
            K.min = K1.min;
            
            if (K.min != null) {
                if (K2.min != null) {
                    //lisätään K2 uuteen kekoon laittamalla sen alkiot oikeille paikoille
                    K.min.oikea.vasen = K2.min.vasen;
                    K2.min.vasen.oikea = K.min.oikea;
                    K.min.oikea = K2.min;
                    K2.min.vasen = K.min;

                    //tarkistetaan, onko K2 minimi pienempi kuin K1:n minimi
                    if (K2.min.arvo < K1.min.arvo) {
                        K.min = K2.min;
                    }
                }
            } else {
                K.min = K2.min;
            }
            //kasvatetaan uuden keon kokoa
            K.koko = K1.koko + K2.koko;
        }
        return K;
    }

 
}
