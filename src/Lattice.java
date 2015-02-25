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
public class Lattice {
	int size;
	int[][] box;
	
	public Lattice(int size) {
		this.size = size;
		box = new int[size][size];
	
	    for (int i = 0; i < size; i++) {
	      for (int j = 0; j < size; j++) {
	        box[i][j] = 0;
	        if (SIR.stateChanged(0.5)) box[i][j] = 1;
	        else if  (SIR.stateChanged(0.5)) box[i][j] = 2;
	      }
	   }
	}

  /**
    * picks
    * @length  The desired length for the array
    * @return  The coordinates of the picked spin
    */
	public int[] picks(int length) {
	     int[] array = new int[length];
	     for (int i = 0; i < length; i++)
	       array[i] = (int) (Math.random() * size); 
	     return array;
	}
	
	public int[][] getLattice(){
		return box;
	}
}
