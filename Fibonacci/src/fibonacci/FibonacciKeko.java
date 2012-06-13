package fibonacci;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Annika
 */
public class FibonacciKeko {

    //Tarvitaan solmun suurimman mahdollisen asteen laskemiseen. 
    private static final double logKultainenLeikkaus = 1.0 / Math.log((1.0 + Math.sqrt(5.0)) / 2.0);
    FibonacciNode min;
    int koko;

    public FibonacciKeko() {
        min = null;
        koko = 0;
    }

    public FibonacciKeko(FibonacciNode solmu) {
        min = solmu;
        koko = 1;
    }    
    
    //lisätään uusi solmu kekoon. Uudesta solmusta tehdÃ¤Ã¤n uusi 
    //yhden solmun kokoinen alipuu.
    //Aikavaativuudeltaan vakio
    public void insert(int arvo) {
        FibonacciNode x = new FibonacciNode(arvo);
        x.aste = 0;
        x.vanhempi = null;
        x.lapsi = null;
        x.vasen = x;
        x.oikea = x;
        if (min != null) {            
            //solmut linkitetÃ¤Ã¤n toisiinsa            
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
     * tehdään y_stä x:n lapsi
     * kasvatetaan x:n astetta
     */ 
    public void link(FibonacciNode y, FibonacciNode x) {
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
     * TehdÃ¤Ã¤n ensin uusi keko, johon yhdistetÃ¤Ã¤n kaksi vanhaa kekoa. TehdÃ¤Ã¤n toisen
     * minimistÃ¤ uuden keon minimi, pÃ¤ivitetÃ¤Ã¤n keon koko
     */
    public FibonacciKeko union(FibonacciKeko K1, FibonacciKeko K2) {
        FibonacciKeko K = new FibonacciKeko();        
        //tarkistetaan, ettei kummankaan keon minimi ole null        
        if ((K1 != null) && (K2 != null)) {            
            //tehdÃ¤Ã¤n alustavasti K1:n minimistÃ¤ K:n minimi            
            K.min = K1.min;
            if (K.min != null) {
                if (K2.min != null) {                    
                    //lisÃ¤tÃ¤Ã¤n K2 uuteen kekoon laittamalla sen alkiot oikeille paikoille                    
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
     * Poistetaan miminijuuri. Sen lapsista tulee uusien puiden juuria. Sen jÃ¤lkeen
     * pÃ¤ivitetÃ¤Ã¤n pointteri osoittamaan minimiin. Jos kahdella juurella on sama aste,
     * tehdÃ¤Ã¤n toisesta lapsi siten ettÃ¤ pienempi pysyy juurena, aste kasvaa yhdellÃ¤
     * Toistetaan kunnes kaikilla eri aste. 
     */
    public FibonacciNode extract_min() {
        FibonacciNode z = min;
        if (z != null) {
            int lapset = z.aste;
            FibonacciNode x = z.lapsi;
            FibonacciNode valiaikainen;
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

    //etsitÃ¤Ã¤n uusi minimi. metodin lÃ¤pikÃ¤ynnin jÃ¤lkeen keossa on maksimissaan
    //yksi jokaista kokoa olevia alipuita
    public void consolidate() {
        int taulukonKoko = ((int) Math.floor(Math.log(koko) * logKultainenLeikkaus)) + 1;
        FibonacciNode[] taulukko = new FibonacciNode[taulukonKoko];
        for (int j = 0; j < taulukonKoko; j++) {
            taulukko[j] = null;
        }
        int juuret = 0;
        FibonacciNode x = min;
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
            FibonacciNode seuraava = x.oikea;            
            // KÃ¤ydÃ¤Ã¤n taulukko lÃ¤pi etsien onko saman kokoisia            
            for (FibonacciNode n : taulukko) {
                FibonacciNode y = taulukko[d];
                if (y == null) {
                    break;
                }
                if (x.arvo > y.arvo) {
                    FibonacciNode valiaik = y;
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
            FibonacciNode y = taulukko[i];
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
     * Otetaan solmu, pienennetÃ¤Ã¤n sen arvoa. Jos uusi arvo on pienempi kuin sen
     * vanhempi, leikataan pienempi solmu irti vanhemmastaan. Jos vanhempi ei ole
     * juuri, merkataan se. Jos se on jo merkattu, leikataan sekin irti ja sen vanhempi
     * merkataan. TÃ¤tÃ¤ jatketaan kunnes lÃ¶ydetÃ¤Ã¤n juuri/merkkaamaton solmu
     */
    public void decreaseKey(FibonacciNode x, int k) {
        if (k > x.arvo) {
            throw new IllegalArgumentException("Annoit suuremman arvon!");
        }
        x.arvo = k;
        FibonacciNode y = x.vanhempi;
        if ((y != null) && (x.arvo < y.arvo)) {
            //tÃ¤ss kohtaa siis leikkaus
            cut(x, y);
            cascadingCut(y);
        }
        if (x.arvo < min.arvo) {
            min = x;
        }
    }

    protected void cascadingCut(FibonacciNode y) {
        FibonacciNode z = y.vanhempi;
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
    protected void cut(FibonacciNode x, FibonacciNode y) {
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
    public void delete(FibonacciNode x) {
        decreaseKey(x, Integer.MIN_VALUE);
        extract_min();
    }
}

