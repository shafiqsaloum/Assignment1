import java.awt.*;
import java.awt.image.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
 
@SuppressWarnings("serial")

/** A small application that displays an image in black and white,
 *  and fills contiguous black areas red if you click on them. */ 
public class FloodFiller extends JPanel implements MouseListener {

  private BufferedImage img;
 
  public void turnBlacknWhite() {
    int x, y;
    int w = img.getWidth();
    int h = img.getHeight();
    // first compute the mean intensity
    int totintensity = 0;
    for (y = 0; y < h; y++) {
      for (x = 0; x < w; x++) {
        int rgb = img.getRGB(x, y);
        totintensity += (rgb >> 16) & 0xFF + (rgb >>  8) & 0xFF + rgb & 0xFF;
      }
    }
    int meanintensity = totintensity / (w * h);
    for (y = 0; y < h; y++) {
      for (x = 0; x < w; x++) {
        int rgb = img.getRGB(x, y);
        int intensity = (rgb >> 16) & 0xFF + (rgb >>  8) & 0xFF + rgb & 0xFF;
        if (intensity < meanintensity) {  // it's darker than mean intensity
          img.setRGB(x, y, Color.BLACK.getRGB());  // turn black
        } else {  // it's lighter
          img.setRGB(x, y, Color.WHITE.getRGB());  // turn white
        }
      }
    }
  }

  public void mousePressed(MouseEvent e) {
  }

  public void mouseReleased(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mouseClicked(MouseEvent e) {
    floodFill(e.getX(), e.getY(), Color.RED.getRGB());
    this.repaint();
  }

  /** Paint the black area including and around (x,y) in the specified color.
    * If (x,y) is not black, do nothing. 
    */
  public void floodFill(int x, int y, int color) {
     //TODO 
 
  }
 
  public FloodFiller(String fileName) {
    try {
      img = ImageIO.read(new File(fileName));
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    turnBlacknWhite();
    setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
    this.addMouseListener(this);
  }
 
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    setBackground(Color.WHITE);
    g.drawImage(img, 0, 0, null);
  }
 
  public static void main(String[] args) {
    final String fileName = args[0];
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        JFrame frame = new JFrame("Flood Filler");
        frame.setContentPane(new FloodFiller(fileName));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
      }
    });
  }
}
