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
  *           @param size - The size of the lattice
  */
  DrawSIRS (int size) {
    this.size = size;
    lattice = new Lattice();
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
    box = lattice.getLattice();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
          paintPixels(i,j);
      }
    }
    frame.setVisible(true);
  }

 /**
  * run:
  *     Method to call for running initialised simulation
  *     CTRL+C to exit.
  */
  public void run() {
    timerOn = true;
    while (timerOn) {
      lattice.picks(2);
          // check if energy meets threshold
          // if picked is S then S -> I with p1
          // if picked is I then I -> R with p2
          // if picked is R then R -> S with p3
          // call paintPixels;
    }
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
  * @param i The ith dims of the box array as an int
  * @param j The jth dims of the box array as an int
  */
  public void paintPixels(int i, int j) {
    if (box[i][j] == 0) graphics.setColor(new Color(240,240,255));
    else if (box[i][j] == 1) graphics.setColor(new Color(30,124,25));
    else graphics.setColor(new Color(30,0,25));
      graphics.fillRect(i * pixelDims,
                        j * pixelDims,
                        pixelDims,
                        pixelDims);
  }
}

