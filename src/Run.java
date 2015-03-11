
public class Run {

  public static void main(String[] args) {
    int size = Integer.parseInt(args[0]);
    double p1 = Double.parseDouble(args[1]);
    double p2 = Double.parseDouble(args[2]);
    double p3 = Double.parseDouble(args[3]);
    Lattice lattice = new Lattice(size, p1, p2, p3);
    DrawSIRS draw = new DrawSIRS(size);
    int[][] box = lattice.getLattice();

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
          draw.paintPixels(box[i][j], i,j);
          draw.repaint();

      }
    }

    boolean run = true;
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
      /*try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.getCause();
      }*/
    }
  }
}
