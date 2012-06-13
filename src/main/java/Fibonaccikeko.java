/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AnnikaTyökone
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
    
    //lisätään uusi solmu kekoon. Uudesta solmusta tehdään uusi 
    //yhden solmun kokoinen alipuu.
    //Aikavaativuudeltaan vakio
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
    
    /*
     * poistetaan y keon juurilistasta (this) 
     * tehdään y:stä x:n lapsi
     * kasvatetaan x:n astetta
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
    
    /*
     * Tehdään ensin uusi keko, johon yhdistetään kaksi vanhaa kekoa. Tehdään toisen
     * minimistä uuden keon minimi, päivitetään keon koko
     */
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
    
    /*
     * Poistetaan miminijuuri. Sen lapsista tulee uusien puiden juuria. Sen jälkeen
     * päivitetään pointteri osoittamaan minimiin. Jos kahdella juurella on sama aste,
     * tehdään toisesta lapsi siten että pienempi pysyy juurena, aste kasvaa yhdellä
     * Toistetaan kunnes kaikilla eri aste. 
     */
    public Fibonaccinode extract_min() {
        Fibonaccinode z = min;
        if (z != null) {
            int lapset = z.aste;
            Fibonaccinode x = z.lapsi;
            Fibonaccinode valiaikainen;
            while (lapset > 0) {
                valiaikainen = x.oikea;
                x.vasen.oikea = x.oikea;
                x.oikea.vasen = x.vasen;
                x.vasen = min;
                x.oikea = min.oikea;
                min.oikea = x;
                x.oikea.vasen = x;
                x.vanhempi = null;
                x = valiaikainen;
                lapset--;
            }
            z.vasen.oikea = z.oikea;
            z.oikea.vasen = z.vasen;
            if (z == z.oikea) {
                min = null;
            } else {
                min = z.oikea;
                consolidate();
            }
            koko--;
        }
        return z;
    }

    //etsitään uusi minimi. metodin läpikäynnin jälkeen keossa on maksimissaan
    //yksi jokaista kokoa olevia alipuita
    public void consolidate() {
        int taulukonKoko = ((int) Math.floor(Math.log(koko) * logPhi)) + 1;
        Fibonaccinode[] taulukko = new Fibonaccinode[taulukonKoko];
        for (int j = 0; j < taulukonKoko; j++) {
            taulukko[j] = null;
        }
        int juuret = 0;
        Fibonaccinode x = min;
        if (x != null) {
            juuret++;
            x = x.oikea;
            while (x != min) {
                juuret++;
                x = x.oikea;
            }
        }
        for (int i = 0; i < juuret; i++) {
            int d = x.aste;
            Fibonaccinode seuraava = x.oikea;            
            // Käydään taulukko läpi etsien onko saman kokoisia            
            for (Fibonaccinode n : taulukko) {
                Fibonaccinode y = taulukko[d];
                if (y == null) {
                    break;
                }
                if (x.arvo > y.arvo) {
                    Fibonaccinode valiaik = y;
                    y = x;
                    x = valiaik;
                }
                link(y, x);
                taulukko[d] = null;
                d++;
            }
            taulukko[d] = x;
            x = seuraava;
        }
        min = null;
        for (int i = 0; i < taulukonKoko; i++) {
            Fibonaccinode y = taulukko[i];
            if (y == null) {
                continue;
            }
            if (min != null) {
                y.vasen.oikea = y.oikea;
                y.oikea.vasen = y.vasen;
                y.vasen = min;
                y.oikea = min.oikea;
                min.oikea = y;
                y.oikea.vasen = y;
                if (y.arvo < min.arvo) {
                    min = y;
                }
            } else {
                min = y;
            }
        }
    }     
    
    
    /*
     * Otetaan solmu, pienennetään sen arvoa. Jos uusi arvo on pienempi kuin sen
     * vanhempi, leikataan pienempi solmu irti vanhemmastaan. Jos vanhempi ei ole
     * juuri, merkataan se. Jos se on jo merkattu, leikataan sekin irti ja sen vanhempi
     * merkataan. Tätä jatketaan kunnes löydetään juuri/merkkaamaton solmu
     */
    public void decreaseKey(Fibonaccinode x, int k) {
        if (k > x.arvo) {
            throw new IllegalArgumentException("Annoit suuremman arvon!");
        }
        x.arvo = k;
        Fibonaccinode y = x.vanhempi;
        if ((y != null) && (x.arvo < y.arvo)) {
            //täss kohtaa siis leikkaus
            cut(x, y);
            cascadingCut(y);
        }
        if (x.arvo < min.arvo) {
            min = x;
        }
    }

    protected void cascadingCut(Fibonaccinode y) {
        Fibonaccinode z = y.vanhempi;
        if (z != null) {
            if (!y.mark) {
                //merkataan solmu
                y.mark = true;
            } else {
                cut(y, z);
                cascadingCut(z);
            }
        }
    }

    /*
     * Leikataan solmu irti puusta.
     */
    protected void cut(Fibonaccinode x, Fibonaccinode y) {
        x.vasen.oikea = x.oikea;
        x.oikea.vasen = x.vasen;
        y.aste--;
        if (y.lapsi == x) {
            y.lapsi = x.oikea;
        }
        if (y.aste == 0) {
            y.lapsi = null;
        }
        x.vasen = min;
        x.oikea = min.oikea;
        min.oikea = x;
        x.oikea.vasen = x;
        x.vanhempi = null;
        x.mark = false;
    }    
    
    
    // poistetaan alkio kutsumalla metodeita decreaseKey ja extract_min  
    public void delete(Fibonaccinode x) {
        decreaseKey(x, Integer.MIN_VALUE);
        extract_min();
    }

    /*
     * Ohjelman main, jossa testataan eri metodeja
     */
    public static void main(String[] args) {
        Fibonaccikeko Fikeko = new Fibonaccikeko();
        for (int i = 4; i != 0; i--) {
            Fikeko.insert(i);
        }
        Fibonaccikeko Fikeko2 = new Fibonaccikeko();
        for (int j = 4; j != 0; j--) {
            Fikeko2.insert(j);
        }
        Fibonaccikeko unioni = Fikeko.union(Fikeko, Fikeko2);
        System.out.println(unioni.extract_min().arvo);
        System.out.println(unioni.extract_min().arvo);
        System.out.println(unioni.extract_min().arvo);
        System.out.println(unioni.extract_min().arvo);
    }
}

