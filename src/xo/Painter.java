package xo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Eysemberth Abarca
 */
public class Painter extends JPanel implements MouseListener {

    private static final long serialVersionUID = 1L;

    private BufferedImage $board;

    public Painter() {
        setFocusable(true);
        requestFocus();
        setBackground(Color.WHITE);
        addMouseListener(this);
    }

    public void loadImages() {
        try {
            this.$board = ImageIO.read(getClass().getResourceAsStream("/board.png"));
        } catch (IOException $io) {
            $io.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics $graphics) {
        super.paintComponent($graphics);
        this.render($graphics);
    }

    private void render(Graphics $graphics) {
        $graphics.drawImage(this.$board, 0, 0, null);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        System.out.println(me);
    }

    @Override
    public void mousePressed(MouseEvent me) {

    }

    @Override
    public void mouseReleased(MouseEvent me) {

    }

    @Override
    public void mouseEntered(MouseEvent me) {

    }

    @Override
    public void mouseExited(MouseEvent me) {

    }

}
