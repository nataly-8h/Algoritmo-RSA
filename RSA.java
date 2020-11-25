
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
    public String name;
    public long kpub;
    private long kpr;

    public RSA(String name) {
        this.name = name;
        int p = 97;
        int q = 89;
        int n = p * q;
        int phi = (p - 1) * (q - 1);

        int e = 0;
        for (int i = 2; i < phi / 2; i++) {
            if (phi % i != 0) {
                e = i;
                break;
            }
        }
        int d = modInverse(e, phi);
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

    public static void encriptacion(int n, int e, String doc) {
        try {
            PrintWriter writer = new PrintWriter("encryp", "UTF-8");
            BufferedReader br = new BufferedReader(new FileReader(doc));
            String linea = br.readLine();
            String[] message;
            while (linea != null) {
                message = linea.split("");
                if (message.length > 0) {
                    String encryp = "";
                    for (int i = 0; i < message.length; i++) {
                        int x = (int) message[i].charAt(0);
                        int y = (int) (Math.pow(x, e) % n);
                        encryp = encryp + y + " ";
                    }
                    writer.println(encryp);
                    linea = br.readLine();
                    System.out.println();
                }
            }
            br.close();
            writer.close();

            //////// editar documento
            Writer a = new FileWriter(doc, false);
            BufferedReader br2 = new BufferedReader(new FileReader("encryp"));
            String linea2 = br2.readLine();
            while (linea2 != null) {
                a.append(linea2);
                linea2 = br2.readLine();

            }
            br2.close();
            a.close();
            System.out.println("Encriptado");

            File delTemp = new File("encryp");
            delTemp.delete();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void desencriptacion(int d, int n, String doc) {
        try {
            PrintWriter writer = new PrintWriter("descifraTemp.txt", "UTF-8");

            BufferedReader br = new BufferedReader(new FileReader(doc));
            String linea = br.readLine();
            String[] message;
            while (linea != null) {
                message = linea.split(" ");
                String descifraTemp = "";
                for (int i = 0; i < message.length; i++) {

                    BigInteger y = new BigInteger(message[i]);
                    y = y.modPow(new BigInteger(String.valueOf(d)), new BigInteger(String.valueOf(n)));
                    descifraTemp = descifraTemp + (char) (Integer.parseInt(y.toString()));
                }
                writer.println(descifraTemp);
                linea = br.readLine();

            }
            // System.out.println(libreria.toString());
            br.close();
            writer.close();

            //////// editar documento
            Writer a = new FileWriter(doc, false);
            BufferedReader br2 = new BufferedReader(new FileReader("descifraTemp.txt"));
            String linea2 = br2.readLine();
            while (linea2 != null) {
                a.append(linea2);
                // System.out.println(linea2);
                linea2 = br2.readLine();

            }
            br2.close();
            a.close();
            System.out.println("Desencriptado");

            File delTemp = new File("descifraTemp.txt");
            delTemp.delete();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static BigInteger exponen(BigInteger x, int d, int n) {
        BigInteger di = new BigInteger(String.valueOf(d));
        String[] binary = di.toString(2).split("");
        int[] binExpo = new int[binary.length];

        for (int i = 0; i < binExpo.length; i++) {
            binExpo[i] = Integer.parseInt(binary[i]);
        }

        BigInteger r = x;
        System.out.println(binExpo.length);
        for (int i = 0; i < binExpo.length; i++) {
            // System.out.println(i);
            r = r.pow(2);
            r = r.mod(new BigInteger(String.valueOf(n)));
            if (binExpo[i] == 1) {
                // r = r.multiply(x.mod(new BigInteger(String.valueOf(n))));
                r = r.mod(new BigInteger(String.valueOf(n)));
                r = r.modPow(r.multiply(x), new BigInteger(String.valueOf(n)));
            }
            // System.out.println("siu " + r.toString());
        }

        return r;
    }

    public static void main(String[] args) {
        encriptacion(8633, 5, "cat.jpg");

        // desencriptacion(5069, 8633,"prueba.txt");

    }
}