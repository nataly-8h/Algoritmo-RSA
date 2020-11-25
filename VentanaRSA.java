import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.*;
 

public class VentanaRSA extends JPanel implements ActionListener {
    static private final String newline = "\n";
    JButton openButton, selecButton, encripButton, desencripButton;
    JTextArea log;
    JFileChooser fc;
    JLabel archSelec;
    File archivo;
    HashMap<File, Boolean> files;
    String password;
    RSA rsa;
    
    public VentanaRSA(){
        super(new BorderLayout());

        this.archivo = null;
        this.files = new HashMap<File, Boolean>();
        this.password = "1234";
        this.rsa = new RSA();

        log = new JTextArea(2,5);
        log.setMargin(new Insets(5,5,5,5));
        log.append("Archivo seleccionado: ");
        log.setEditable(false);

        fc = new JFileChooser();
        String direc = System.getProperty("user.dir") + "/archivos";
        File workingDirectory = new File(direc);
        fc.setCurrentDirectory(workingDirectory);

        openButton = new JButton("Abrir archivo");
        openButton.addActionListener(this);

        selecButton = new JButton("Selecciona archivo");
        selecButton.addActionListener(this);

        encripButton = new JButton("Encriptar archivo");
        encripButton.addActionListener(this);

        desencripButton = new JButton("Desencriptar archivo");
        desencripButton.addActionListener(this);
        
        JPanel buttonPanel = new JPanel(); 
        
        buttonPanel.add(selecButton);
        

        JPanel archPanel = new JPanel();
        archSelec = new JLabel("Archivo seleccionado: ", JLabel.CENTER);
        archPanel.add(archSelec);

        JPanel cipherPanel = new JPanel();
        cipherPanel.add(openButton);
        cipherPanel.add(encripButton);
        cipherPanel.add(desencripButton);

        

        add(buttonPanel, BorderLayout.PAGE_START);
        //add(archPanel, BorderLayout.CENTER);
        add(archPanel, BorderLayout.CENTER);
        add(cipherPanel, BorderLayout.PAGE_END);
    }
    
    public void actionPerformed(ActionEvent e) {
 
        //Handle open button action.
        if (e.getSource() == selecButton) {
            int returnVal = fc.showOpenDialog(VentanaRSA.this);
 
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                this.archivo = fc.getSelectedFile();
                
                //String seltext = this.archSelec.getText();
                this.archSelec.setText("Archivo seleccionado: " + this.archivo.getName());

                if(!this.files.containsKey(this.archivo)){
                    this.files.put(this.archivo, false);
                }
                
            } else {
                //log.append("Open command cancelled by user." + newline);
            }
        }else if(e.getSource() == openButton){
            if(this.archivo != null){
                try{
                    Desktop dt = Desktop.getDesktop(); 
                    dt.open(this.archivo);
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }else{
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un archivo",  null, JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == encripButton) {

            if(this.archivo == null){
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un archivo",  null, JOptionPane.ERROR_MESSAGE);
            }else{
                if(this.files.get(this.archivo)){
                    JOptionPane.showMessageDialog(this, "Archivo ya encriptado", null, JOptionPane.INFORMATION_MESSAGE);
                }else{
                    String input = JOptionPane.showInputDialog(this, "Ingrese la contraseña");
                    if(this.password.equals(input)){
                        encriptacion(this.rsa.n, this.rsa.e, this.archivo.getPath());
                        this.files.replace(this.archivo, true);
                        JOptionPane.showMessageDialog(this, "Encriptado", null, JOptionPane.PLAIN_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(this, "Contraseña incorrecta", null, JOptionPane.WARNING_MESSAGE);
                    }
                }
            }

            //log.setCaretPosition(log.getDocument().getLength());
        
        }else if(e.getSource() == desencripButton){
            if(this.archivo == null){
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un archivo",  null, JOptionPane.ERROR_MESSAGE);
            }else{
                if(!this.files.get(this.archivo)){
                    JOptionPane.showMessageDialog(this, "Archivo no encriptado", null, JOptionPane.INFORMATION_MESSAGE);
                }else{
                    String input = JOptionPane.showInputDialog(this, "Ingrese la contraseña");
                    if(this.password.equals(input)){
                        desencriptacion(this.rsa.d, this.rsa.n, this.archivo.getPath());
                        this.files.replace(this.archivo, false);
                        JOptionPane.showMessageDialog(this, "Desencriptado", null, JOptionPane.PLAIN_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(this, "Contraseña incorrecta", null, JOptionPane.WARNING_MESSAGE);
                    }
                }
            }

        }
    }

    public static void encriptacion(BigInteger n, BigInteger e, String doc) {
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

                        BigInteger y = BigInteger.valueOf((int) message[i].charAt(0)).modPow(e, n);
                        encryp = encryp + y.toString() + " ";
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

    public static void desencriptacion(BigInteger d, BigInteger n, String doc) {
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

        }

        return r;
    }
    

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("RSA");
        frame.setBounds(1,2,500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new VentanaRSA());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
    }

    
}