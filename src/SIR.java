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
public class SIR {

 /**
  * stateChanged
  * @param  p Probability, of state change, as a double.
  * @return Whether or not the state was changed.
  */
  public static boolean stateChanged(double p) {
    boolean stateChanged = false;
    if (Math.random() <= p)
      stateChanged = true;
    return stateChanged; 
  }

 /**
  * stateChanged
  * @param  box the lattice as a 2-D array of ints
  * @param p1 the probability, p1 as a double
  * @param p2 the probability, p2 as a double
  * @param p3 the probability, p3 as a double
  * 
  * @return Whether or not the state was changed.
  */
  public static int update(int[][] box,
                               double p1,
                               double p2,
                               double p3,
                               int m,
                               int n) {

    if (stateChanged(p1) & box[m][n] == 0) {
      box[m][n] = 1;
    } else if (stateChanged(p2) & box[m][n] == 1) {
        box[m][n] = 2;
    } else if (stateChanged(p3) & box[m][n] == 2) {
        box[m][n] = 0;
    }
    return box[m][n];
  }

  /**
   * @param length of array as an integer
   * @return The coordinates of the picked spin as a 1D
   *         array of ints
   */
   public static int[] picks(int length, int size) {
     int[] array = new int[length];
     for (int i = 0; i < length; i++)
       array[i] = (int) (Math.random() * size); 
     return array;
   }
}
