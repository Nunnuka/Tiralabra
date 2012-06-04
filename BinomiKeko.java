public class Binomikeko {

    BinomiNode root;
    int size;

    public Binomikeko() {
    }

    public Binomikeko(BinomiNode node, int size) {
        this.size = size;
        this.root = node;
    }

    public void insert(int value) {
        Binomikeko h = new Binomikeko();
        BinomiNode node = new BinomiNode(value);
        node.parent = null;
        node.child = null;
        node.sibling = null;
        node.degree = 0;
        h.root = node;
        Binomikeko uusi = union(h, this);
        this.root = uusi.root;
        size++;
    }

    public BinomiNode find_min() {
        int value = Integer.MAX_VALUE;
        BinomiNode apunode = root;
        BinomiNode min = null;
        while (apunode != null) {
            if (apunode.value < value) {
                value = apunode.value;
                min = apunode;
            }
            apunode = apunode.sibling;
        }
        return min;
    }

    public void extract_min() {
        Binomikeko apukeko = new Binomikeko();
        BinomiNode min = find_min();
        BinomiNode temp = min.child;
        BinomiNode current = root;
        BinomiNode apu = root;

        if (root.value != min.value) {
            while (apu.sibling.value != min.value) {
                apu = apu.sibling;
            }
            apu = apu.sibling.sibling;
        }
        if (root.value == min.value) {
            root = root.sibling;
        }

        if (min.child != null) {
            /*
             * Vaihdetaan alipuun järjestys
             */
            while (temp != null) {
                BinomiNode next = temp.sibling;
                temp.sibling = apukeko.root;
                apukeko.root = temp;
                temp = next;
            }

            Binomikeko uusi = union(this, apukeko);
            this.root = uusi.root;
        }
    }

    /*
     * Yhdistetään alipuu ja vanha puu, josta puutuu minimiarvon omaavan rootin alipuu.
     */
    public void decreaseKey(BinomiNode x, int k) {
        if (k > x.value) {
            System.out.println("Annoit suuremman arvon! BinomiNoden arvo on: " + x.value);
            return;
        }
        x.value = k;
        BinomiNode y = x;
        BinomiNode z = y.parent;

        while ((z != null) && y.value < x.value) {
            int apuvalue = y.value;
            y.value = x.value;
            x.value = apuvalue;
            y = z;
            z = y.parent;
        }
    }

    public void binomial_link(BinomiNode y, BinomiNode z) {
        y.parent = z;
        y.sibling = z.child;
        z.child = y;
        z.degree++;
    }

    public Binomikeko union(Binomikeko h1, Binomikeko h2) {
        Binomikeko h = new Binomikeko();
        h.root = merge(h1, h2);
        if (h.root == null) {
            return h;
        }
        BinomiNode prev_x = null;
        BinomiNode x = h.root;
        BinomiNode next_x = x.sibling;
        while (next_x != null) {
            if (x.degree != next_x.degree || (next_x.sibling != null) && (next_x.sibling.degree == x.degree)) {
                prev_x = x;
                x = next_x;
            } else {
                if (x.value <= next_x.value) {
                    x.sibling = next_x.sibling;
                    binomial_link(next_x, x);
                } else {
                    if (prev_x == null) {
                        h.root = next_x;
                    } else {
                        prev_x.sibling = next_x;
                    }
                    binomial_link(x, next_x);
                    x = next_x;
                }
            }
            next_x = x.sibling;
        }
        return h;
    }

    BinomiNode merge(Binomikeko h1, Binomikeko h2) {
        BinomiNode node1 = null;
        BinomiNode node2 = null;
        /*
         * Tarkastetaan, onko parametriksi saaduissa keoissa sisältöä.
         */
        if (h1 != null && h1.root != null) {
            node1 = h1.root;
        }
        if (h2 != null && h2.root != null) {
            node2 = h2.root;
        }
        /*
         * Jos toinen root oli null, ei mergeä suoriteta.
         */
        if (node1 == null) {
            return node2;
        } else if (node2 == null) {
            return node1;
        }
        BinomiNode h;
        if (node1.degree < node2.degree) {
            h = node1;
            node1 = node1.sibling;
        } else {
            h = node2;
            node2 = node2.sibling;
        }
        /*
         * Kuljetaan kahden listan läpi linkaten current seuraavaan pienempään nodeen.
         * Muutetaan tämä pienin uudeksi currentiksi ja jatketaan kunnes toinen alipuu tyhjä.
         */
        BinomiNode curr = h;
        while (node1 != null && node2 != null) {
            if (node1.degree < node2.degree) {
                curr.sibling = node1;
                curr = node1;
                node1 = node1.sibling;
            } else {
                curr.sibling = node2;
                curr = node2;
                node2 = node2.sibling;
            }
        }
        if (node1 == null) {
            curr.sibling = node2;
        } else {
            curr.sibling = node1;
        }
        return h;
    }

    public String toString() {
        String result = "";

        BinomiNode x = root;
        while (x != null) {
            result += x.printTree(0);
            x = x.sibling;
        }
        return result;
    }

    public static void main(String[] args) {

        Binomikeko binomikeko = new Binomikeko();
        for (int i = 10000; i != 0; i--) {
            binomikeko.insert(i);
        }
        binomikeko.extract_min();
        binomikeko.extract_min();
        binomikeko.extract_min();
        System.out.println(binomikeko.toString());
    }
}

