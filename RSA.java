
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class RSA {

    private boolean encriptado;

    //public BigInteger p, q, n, phi, e, d;
    public int p, q, n, phi, e, d;

    public RSA(int p, int q) {

        // this.p = new BigInteger(String.valueOf(p));
        // this.q = new BigInteger(String.valueOf(q));

        // this.n = new BigInteger(this.p.multiply(this.q).toString());

        // BigInteger uno = new BigInteger("1");
        // this.phi = new BigInteger(((this.p.subtract(uno)).multiply(this.q.subtract(uno))).toString()); // (p - 1) * (q - 1)
        this.p = 97;
        this.q = 89;
        this.n = p * q;
        this.phi = (p - 1) * (q - 1);
        
        this.phi = (p - 1) * (q - 1);

        this.e = 0;
        for (int i = 2; i < phi / 2; i++) {
            if (phi % i != 0) {
                e = i;
                break;
            }
        }
        this.d = modInverse(e, phi);
    }

    public boolean isEncriptado(){
        return encriptado;
    }

    public static int modInverse(int a, int m) {
        int mx = m;
        int y = 0, x = 1;

        if (m == 1) {
            return 0;
        }

        while (a > 1) {
            int q = a / m;
            int t = m;
            m = a % m;
            a = t;
            t = y;
            y = x - q * y;
            x = t;
        }

        if (x < 0) {
            x += mx;
        }
        return x;
    }

    

    public static void main(String[] args) {
        //encriptacion(8633, 5, "cat.jpg");

        // desencriptacion(5069, 8633,"prueba.txt");

    }
}