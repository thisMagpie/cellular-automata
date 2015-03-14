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

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class DrawSIRS extends JPanel implements ChangeListener {
  int size, dims,pixelDims;
  Graphics graphics;
  Image image;
  Timer timer; //TODO implement actionListener
  /**
  * DrawSIRS:
  *           Class constructor
  * @param size - The size of the lattice
  */
  DrawSIRS (int size) {
    this.size = size;
    Frame frame = new Frame("SIRS Simulation");

    //Create labels
    JLabel p1SliderLabel = new JLabel("p1", JLabel.CENTER);
    p1SliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    JLabel p2SliderLabel = new JLabel("p2", JLabel.CENTER);
    p2SliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    JLabel p3SliderLabel = new JLabel("p3", JLabel.CENTER);
    p3SliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


    //Create the sliders.
    JSlider p1Slider = new JSlider(JSlider.HORIZONTAL, 0, 1, 0);
    JSlider p2Slider = new JSlider(JSlider.HORIZONTAL, 0, 1, 0);
    JSlider p3Slider = new JSlider(JSlider.HORIZONTAL, 0, 1, 0);

    p1Slider.addChangeListener(this);
    p2Slider.addChangeListener(this);
    p3Slider.addChangeListener(this);

    p1Slider.setMajorTickSpacing(10);
    p1Slider.setMinorTickSpacing(1);
    p1Slider.setPaintTicks(true);
    p1Slider.setPaintLabels(true);
    p1Slider.setBorder(BorderFactory.createEmptyBorder(0,0,1,0));

    p2Slider.setMajorTickSpacing(10);
    p2Slider.setMinorTickSpacing(1);
    p2Slider.setPaintTicks(true);
    p2Slider.setPaintLabels(true);
    p2Slider.setBorder(BorderFactory.createEmptyBorder(0,0,1,0));

    p3Slider.setMajorTickSpacing(10);
    p3Slider.setMinorTickSpacing(1);
    p3Slider.setPaintTicks(true);
    p3Slider.setPaintLabels(true);
    p3Slider.setBorder(BorderFactory.createEmptyBorder(0,0,5,0));
    
    Font font = new Font("Serif", Font.BOLD, 20);
    p1Slider.setFont(font);
    p2Slider.setFont(font);
    p3Slider.setFont(font);

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
    setPreferredSize(new Dimension(dims,dims));
    JPanel controlPanel = new JPanel();
    frame.add(controlPanel,BorderLayout.SOUTH);
    controlPanel.add(p1SliderLabel);
    controlPanel.add(p1Slider);
    controlPanel.add(p2SliderLabel);
    controlPanel.add(p2Slider);
    controlPanel.add(p3SliderLabel);
    controlPanel.add(p3Slider);

    //timer = new Timer(10, this); TODO implement actionListener
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

  public void start() {
      timer.start();
  }

  public void stop() {
      //Stop the animating thread.
      timer.stop();
  }
  /**
   * stateChanged: 
   *              Slider Listener
   * @param e event as a ChangeEvent instance
   */
  public void stateChanged(ChangeEvent e) {
    JSlider source = (JSlider)e.getSource();
    if (!source.getValueIsAdjusting()) {
      start();
    }
  }

  public void run(boolean run, int[][] box, double p1, double p2, double p3){
    while (run) {
      int[] spins = SIR.picks(2, size);
    box[spins[0]][spins[1]] = SIR.update(box,
                              p1,
                              p2,
                              p3,
                              spins[0],
                              spins[1]);
    paintPixels(box[spins[0]][spins[1]],
             spins[0],
             spins[1]);
    repaint();
    }
  }
}

