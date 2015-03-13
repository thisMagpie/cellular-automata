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
import java.io.*;

public class Run {

  public static void main(String[] args) {
    int size = Integer.parseInt(args[0]);
    double p1 = Double.parseDouble(args[1]);
    double p2 = Double.parseDouble(args[2]);
    double p3 = Double.parseDouble(args[3]);
    String choice = args[4];

    SIR sir = new SIR();
    sir.setLattice(size, p1, p2, p3);
    int[][] box = sir.getLattice();
 
    if(choice.equals("plot")) {
      System.out.println("\nWriting to file... ");

      int noSweeps = 20; // number of times to sweep through
      int eq = 1000; // number of equilibration cycles
      int n = 1000; // number of data points to measure
      double iValue = 0.0;
      double[] I = new double[noSweeps];
      double[] t = new double[noSweeps];
      double[] sigma = new double[noSweeps];
      double tCount = 0.0;

      for (int i = 0; i < noSweeps; i++) {
        tCount += 1.0;
        box = SIR.sweep(box, p1, p2, p3);
        for (int j = 0; j < eq; j ++)
          box = SIR.sweep(box, p1, p2, p3);

        // Start taking measurements
        for (int j = 0; j <= n; j++) {
          SIR.sweep(box, p1, p2, p3);
          if(j%10==0) {
            iValue += SIR.I(box);
            n++;
          }
        }
        iValue /= n;
        I[i] = iValue;
        t[i] = tCount;
        sigma[i] =  (iValue - (iValue * iValue)) / (box.length);
      }
      try {
        PrintWriter ibyn = IO.writeTo("I.dat");
        PrintWriter iSigma = IO.writeTo("sigma.dat");
        ArrayIO.writeDoubles(ibyn, t, I);
        ArrayIO.writeDoubles(iSigma, t, sigma);
        System.out.println("\nFiles written.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if(choice.equals("gui")) {
      // Create and Show GUI
      DrawSIRS draw = new DrawSIRS(size);
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
            draw.paintPixels(box[i][j], i,j);
            draw.repaint();
        }
      }
      boolean run = true;
      run(draw, run, size, box, p1, p2,p3);
    }
  }

  public static void run(DrawSIRS draw,
                         boolean run,
                         int size,
                         int[][] box,
                         double p1,
                         double p2,
                         double p3){
    while (run) {
        int[] spins = SIR.picks(2, size);
        box[spins[0]][spins[1]] = SIR.update(box,
                                             p1,
                                             p2,
                                             p3,
                                             spins[0],
                                             spins[1]);
        draw.paintPixels(box[spins[0]][spins[1]],
                            spins[0],
                            spins[1]);
        draw.repaint();
      }
  }
}
