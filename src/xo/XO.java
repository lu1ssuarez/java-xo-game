package xo;

import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Eysemberth Abarca
 */
public class XO implements Runnable {

    /* Build GUI */
    private Painter $painter;
    private JFrame $frame;

    /* Socket Server */
    private ServerSocket $serverSocket;
    private ObjectOutputStream $dataOutputStreamServer;
    private ObjectInputStream $dataInputStreamServer;

    /* Socket Player */
    private Socket $socketPlayer;
    private ObjectOutputStream $dataOutputStreamPlayer;
    private ObjectInputStream $dataInputStreamPlayer;

    /* Input setting server */
    private String $ip = "";
    private int $port = 3000;
    private String $ipPort;

    private String $name;
    private String $namePlayer;

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
    }

    @Override
    public void run() {

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
                break;
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
        if (this.$ip.isEmpty() || (this.$ip.equals("127.0.0.1") || this.$ip.equals("127.0.1.1"))) {
            try {
                this.$ip = JOptionPane.showInputDialog("Ingrese la ip del servidor:", this.$ip);

                if (this.$ip.isEmpty() || (this.$ip.equals("127.0.0.1") || this.$ip.equals("127.0.1.1"))) {
                    this.setIpServer();
                }
            } catch (Exception $exception) {
                this.setIpServer();
            }
        }
    }

    private void setPortServer() {
        try {
            this.$port = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el puerto a conectar:", this.$port));

            if (this.$port == 0) {
                this.setPortServer();
            }
        } catch (NumberFormatException $exception) {
            this.setPortServer();
        }
    }

    /* Socket Player */
    private boolean connectSocket() {
        try {
            this.$socketPlayer = new Socket(this.$ip, this.$port);

            // JOptionPane.showMessageDialog(null, "Se ha conectado correctamente al servidor " + this.$ipPort);

            this.$dataOutputStreamPlayer = new ObjectOutputStream(this.$socketPlayer.getOutputStream());
            this.$dataOutputStreamPlayer.writeObject(this.$name);

            JOptionPane.showMessageDialog(null, "Espere mientras se acepta unirse al juego");

            this.$dataInputStreamServer = new ObjectInputStream(this.$socketPlayer.getInputStream());
            String $accepted = (String) this.$dataInputStreamServer.readObject();

            if ($accepted.equalsIgnoreCase("NO")) {
                JOptionPane.showMessageDialog(null, "Se ha denegado tu solicitud de unirte al juego", null, JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        } catch (IOException $io) {
            JOptionPane.showMessageDialog(null, "No se puede conectar a la dirección: " + this.$ipPort, null, JOptionPane.ERROR_MESSAGE);
            this.setConfig();
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(XO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    /* Socket Server */
    private void initServerSocket() {
        try {
            this.$serverSocket = new ServerSocket(this.$port, 8, InetAddress.getByName(this.$ip));
            JOptionPane.showMessageDialog(null, "Se ha creado el servidor [" + this.$ipPort + "] correctamente \n"
                    + "Esperando conexion...");

            Socket $socketPlayer = this.$serverSocket.accept();

            this.$dataInputStreamPlayer = new ObjectInputStream($socketPlayer.getInputStream());
            this.$namePlayer = (String) this.$dataInputStreamPlayer.readObject();

            Object[] $optionButtons = {"Si", "No"};
            int $optionConnect = JOptionPane.showOptionDialog(null, this.$namePlayer + " desea jugar contigo, estas de acuerdo?", "Conexion entrante", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, $optionButtons, "No");

            switch ($optionConnect) {
                case JOptionPane.YES_OPTION:
                    this.$dataOutputStreamServer = new ObjectOutputStream($socketPlayer.getOutputStream());
                    this.$dataOutputStreamServer.writeObject("YES");
                    break;
                case JOptionPane.NO_OPTION:
                    this.$dataOutputStreamServer = new ObjectOutputStream($socketPlayer.getOutputStream());
                    this.$dataOutputStreamServer.writeObject("NO");
                    break;
                default:
                    this.$dataOutputStreamServer = new ObjectOutputStream($socketPlayer.getOutputStream());
                    this.$dataOutputStreamServer.writeObject("NO");
                    break;
            }
        } catch (IOException $exception) {
            JOptionPane.showMessageDialog(null, "No se puede crear el servidor", null, JOptionPane.ERROR_MESSAGE);
            this.setConfig();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(XO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
