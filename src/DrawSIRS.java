/**
 * Copyright (c) 2015 Magdalen Berns <m.berns@ed.ac.uk>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>
 *
 */
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class DrawSIRS extends Canvas {
	Lattice lattice; 
	int size, dims,pixelDims;
	int[][] box;
	boolean timerOn;
	Graphics graphics;
	Image image;

 /**
  * DrawSIRS:
  *           Class constructor
  * @param size - The size of the lattice
  */
  DrawSIRS (int size) {
    this.size = size;
    Frame frame = new Frame("SIRS Simulation");
    Button button = new Button("Stop Simulation");
    Panel panel = new Panel();

    pixelDims = 4;
    dims = size * pixelDims;

    frame.addWindowListener(new WindowAdapter() {
    public void windowClosing(WindowEvent e) {
    System.exit(0);
      }
    });

    frame.add(panel);
    panel.add(this);
    setSize(dims,dims);
    Panel controlPanel = new Panel();
    frame.add(controlPanel,BorderLayout.SOUTH);

    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
      }
    });

    controlPanel.add(button);
    frame.pack();
    image = createImage(dims,dims);
    graphics = image.getGraphics();

    // Initialise box
    frame.setVisible(true);
  }

 /**
  * paint
  * @param g method overides paint in Canvas
  *        for painting pixels 
  */
  public void paint(Graphics g) {
    g.drawImage(image, 0, 0, dims, dims, this);
  }

 /**
  *  @overide In order to prevent pixel painting lock ups
  *  		  this gets called in response to repaint.
  */
  public void update(Graphics g) {
    paint(g);
  }

 /**
  * paintPixels:
  *              Method to paint pixel spins onto the screen
  *
  * @param sir the value of the i, jth term of the 2D array
  *        as an int
  * @param i The ith dims of the box array as an int
  * @param j The jth dims of the box array as an int
  */
  public void paintPixels(int sir, int i, int j) {
    if (sir == 0) graphics.setColor(new Color(233,205,23));
    else if (sir == 1) graphics.setColor(new Color(233,23,58));
    else graphics.setColor(new Color(38,72,182));
      graphics.fillRect(i * pixelDims,
                        j * pixelDims,
                        pixelDims,
                        pixelDims);
  }
}

