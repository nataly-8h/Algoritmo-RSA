import java.math.BigInteger;

public class RSA {

    public BigInteger p, q, n, phi, e, d;
    // public int p, q, n, phi, e, d;

    public RSA() {

        // this.p = new BigInteger("170141183460469231731687303715884105727");
        // this.q = new BigInteger("20988936657440586486151264256610222593863921");

        this.p = new BigInteger("97");
        this.q = new BigInteger("89");

        this.n = new BigInteger(this.p.multiply(this.q).toString());

        this.phi = new BigInteger(
                ((this.p.subtract(BigInteger.ONE)).multiply(this.q.subtract(BigInteger.ONE))).toString());// (p - 1) *
                                                                                                          // (q - 1)

        // this.p = 97;
        // this.q = 89;
        // this.n = p * q;
        // this.phi = (p - 1) * (q - 1);

        this.e = new BigInteger("0");

        BigInteger phiMedio = phi.divide(BigInteger.TWO);
        System.out.println(phiMedio.toString());
        System.out.println();

        for(BigInteger i = BigInteger.TWO; i.compareTo(phiMedio) < 0; i = i.add(BigInteger.ONE)){
            if(this.phi.mod(i).compareTo(BigInteger.ZERO) != 0){
                this.e = i;
                break;
            }
        }

        this.d = modInverse(e, phi);

        System.out.println(
                "BIG - n: " + this.n.toString() + " | phi: " + this.phi.toString() + " | e: " + this.e.toString() + " | d: " + this.d.toString());
    }

    public static BigInteger modInverse(BigInteger a, BigInteger m) {
        BigInteger mx = m;
        BigInteger y = BigInteger.ZERO, x = BigInteger.ONE;

        if (m.equals(BigInteger.ONE)) {
            return BigInteger.ZERO;
        }

        while (a.compareTo(BigInteger.ONE) > 0) {
            BigInteger q = a.divide(m);
            BigInteger t = m;
            m = a.mod(m);
            a = t;
            t = y;
            y = x.subtract(q.multiply(y));
            x = t;
        }

        if (x.compareTo(BigInteger.ZERO) < 0) {
            x = x.add(mx);
        }
        return x;
    }

    public static void main(String[] args) {
        RSA a = new RSA();
        // System.out.println(("1").compareTo("2"));

        // for (BigInteger i = BigInteger.valueOf(0); i.compareTo(BigInteger.TEN) < 0; i.add(BigInteger.ONE)) {
        //     System.out.println(i.toString());
        // }

        // for (BigInteger bi = BigInteger.valueOf(5); bi.compareTo(BigInteger.ZERO) >
        // 0; bi = bi.subtract(BigInteger.ONE)) {

        // System.out.println(bi);
        // }

    }
}