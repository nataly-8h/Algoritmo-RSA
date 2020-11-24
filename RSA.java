
import javax.swing.*;
import java.awt.event.*;  


public class RSA implements ActionListener {
    public String name;
    public long kpub;
    private long kpr;

    // Graficos
    JFrame f;
    JTextField tf1, tf2, tf3;
    JButton b1, b2;
    //

    public RSA(String name) {
        this.name = name;
        int p = 11;
        int q = 3;
        int n = p * q;
        int phi = (p - 1) * (q - 1);

        int e = 0;
        for (int i = 2; i < phi / 2; i++) {
            if (phi % i != 0) {
                e = i;
                break;
            }
        }
        // System.out.println(e%phi);
        int d = modInverse(e, phi);
        // System.out.println(d);

        // Graficos
        // JTextField tf1, tf2, tf3;
        // JButton b1, b2;

        this.f = new JFrame();
        tf1 = new JTextField();
        tf1.setBounds(50, 50, 150, 20);
        tf2 = new JTextField();
        tf2.setBounds(50, 100, 150, 20);
        tf3 = new JTextField();
        tf3.setBounds(50, 150, 150, 20);
        tf3.setEditable(false);
        b1 = new JButton("FC");
        b1.setBounds(50, 200, 50, 50);
        b2 = new JButton("-");
        b2.setBounds(150, 200, 50, 50);
        b1.addActionListener(this);
        b2.addActionListener(this);
        f.add(tf1);
        f.add(tf2);
        f.add(tf3);
        f.add(b1);
        f.add(b2);
        f.setSize(500, 500);
        f.setLayout(null);
        f.setVisible(true);
        //
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

    public int inversaPTF(int a) {
        int p = 5;
        // int inversa = ((Math.pow(a, (p-2))%p)%p);

        return p;
    }

    public static void programa() {
        JFrame f = new JFrame("RSA");// creating instance of JFrame

        JLabel titulo = new JLabel("RSA");
        // titulo.setSize(new Dimension(5));
        titulo.setBounds(50, 50, 100, 30);
        ;
        f.add(titulo);

        JTextField tx = new JTextField("  ");
        tx.setBounds(50, 100, 200, 30);

        JButton b = new JButton("click");
        b.setBounds(130, 100, 100, 50);

        f.add(b);// adding button in JFrame
        f.add(tx);

        f.setSize(600, 800);
        f.setLayout(null);// using no layout managers
        f.setVisible(true);// making the frame visible
    }

    public void actionPerformed(ActionEvent e) {
        String s1 = tf1.getText();
        String s2 = tf2.getText();
        int a = Integer.parseInt(s1);
        int b = Integer.parseInt(s2);
        int c = 0;
        if (e.getSource() == b1) {
            // JFileChooser fc = new JFileChooser();
            // int seleccion=fc.showOpenDialog(new JFrame());
            System.out.println("OA");
            
        }else if(e.getSource()==b2){  
            c=a-b;  
        }  
        String result=String.valueOf(c);  
        tf3.setText(result);  
    }  

    public static void main(String[] args) {
        //System.out.println("oa");
        RSA a = new RSA("hola");
        //System.out.println(fermat(8));
        // int portNumber = 4444;
        // serverSocket = null;

        // try{
        //     ServerSocket serverSocket = new ServerSocket(portNumber);
        // }catch (IOException e){
        //     System.err.println("No se pudo conectar al servidor: " + portNumber);
        //     System.exit(1);
        // }
        //programa();
        
    }
}