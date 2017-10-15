package xo;

import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Eysemberth Abarca
 */
public class XO implements Runnable {

    private Painter $painter;
    private JFrame $frame;
    private Thread $thread;

    /* Socket Server */
    private ServerSocket $serverSocket;
    private Socket $socket;
    private DataOutputStream $dataOutputStream;
    private DataInputStream $dataInputStream;

    /* Input setting server */
    private String $ip = "";
    private int $port = 3000;
    private String $ipPort;

    private String $name;

    private boolean $accepted = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        XO $XO = new XO();
    }

    public XO() {
        System.out.println("Iniciando XO...");
        this.setNameUser();

        this.setConfig();

        /* 2- draw jPanel for event listener */
        this.$painter = new Painter();
        this.$painter.setPreferredSize(new Dimension(506, 527));

        /* 1- init $body with jFrame */
        this.$frame = new JFrame();
        this.$frame.setTitle("Hola " + this.$name + ", Bienvenido a XO Game");
        this.$frame.setContentPane(this.$painter);
        this.$frame.setSize(506, 527);
        this.$frame.setLocationRelativeTo(null);
        this.$frame.setResizable(false);
        this.$frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.$frame.setVisible(true);

        this.$thread = new Thread(this, "XO Game");
        this.$thread.start();
    }

    @Override
    public void run() {
        while (true) {
            this.$painter.repaint();

            if (!this.$accepted) {
                this.listenForServerRequest();
            }
        }
    }

    private void setNameUser() {
        try {
            this.$name = JOptionPane.showInputDialog("Ingrese su nombre de usuario:", this.$name);

            if (this.$name.isEmpty()) {
                this.setNameUser();
            }
        } catch (Exception exception) {
            this.setNameUser();
        }
    }

    private void setConfig() {
        Object[] $typeServerButtons = {"Conectar", "Crear servidor", "Cerrar"};
        int $typeServer = JOptionPane.showOptionDialog(null, "Deseas conectarte a un servidor o crear uno nuevo.", "Hola " + this.$name, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, $typeServerButtons, "Cerrar");

        switch ($typeServer) {
            case JOptionPane.CANCEL_OPTION:
                System.exit(0);
            case JOptionPane.YES_OPTION:
                this.$ip = "";
                this.setConfigServer();
                this.connectSocket();
                break;
            case JOptionPane.NO_OPTION:
                this.$ip = this.getIpLocal();
                this.setConfigServer();
                this.initServerSocket();
                break;
            default:
                System.exit(0);
                break;
        }
    }
    
    private String getIpLocal() {
        String $ip;
        
        try {
            InetAddress $address = InetAddress.getLocalHost();
            $ip = $address.getHostAddress();
        } catch (Exception $exception) {
            $ip = "127.0.0.1";
        }
        
        return $ip;
    }

    private void setConfigServer() {
        this.setIpServer();
        this.setPortServer();

        this.$ipPort = this.$ip + ":" + this.$port;
    }

    private void setIpServer() {
        try {
            this.$ip = JOptionPane.showInputDialog("Ingrese la ip del servidor:", this.$ip);

            if (this.$ip.isEmpty()) {
                this.setIpServer();
            }
        } catch (Exception exception) {
            this.setIpServer();
        }
    }

    private void setPortServer() {
        try {
            this.$port = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el puerto a conectar:", this.$port));

            if (this.$port == 0) {
                this.setPortServer();
            }
        } catch (Exception exception) {
            this.setPortServer();
        }
    }

    /* Socket */
    private boolean connectSocket() {
        try {
            this.$socket = new Socket(this.$ip, this.$port);
            this.$dataOutputStream = new DataOutputStream(this.$socket.getOutputStream());
            this.$dataInputStream = new DataInputStream(this.$socket.getInputStream());

            this.$dataOutputStream.writeUTF(this.$name);
            this.$dataOutputStream.writeUTF("prueba pruebita");
            
            this.$accepted = true;

            JOptionPane.showMessageDialog(null, "Se ha conectado correctamente al servidor " + this.$ipPort);
        } catch (IOException $io) {
            JOptionPane.showMessageDialog(null, "No se puede conectar a la dirección: " + this.$ipPort, null, JOptionPane.ERROR_MESSAGE);
            this.setConfig();
            return false;
        }

        return true;
    }

    private void initServerSocket() {
        try {
            this.$serverSocket = new ServerSocket(this.$port, 8, InetAddress.getByName(this.$ip));

            JOptionPane.showMessageDialog(null, "Se ha creado el servidor correctamente " + this.$ipPort);
        } catch (Exception $exception) {
            JOptionPane.showMessageDialog(null, "No se puede crear el servidor", null, JOptionPane.ERROR_MESSAGE);
            this.setConfig();
        }
    }

    private boolean listenForServerRequest() {
        Socket $socketClient = null;

        try {
            $socketClient = this.$serverSocket.accept();
            this.$dataOutputStream = new DataOutputStream($socketClient.getOutputStream());
            this.$dataInputStream = new DataInputStream($socketClient.getInputStream());

            this.$accepted = true;

            System.out.println($socketClient);
            System.out.println(this.$dataOutputStream);
            System.out.println(this.$dataInputStream);
        } catch (IOException $io) {
            System.err.println("error");
            $io.printStackTrace();
        }

        return true;
    }
}
