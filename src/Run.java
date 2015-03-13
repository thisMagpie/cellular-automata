
public class Run {

  public static void main(String[] args) {
    int size = Integer.parseInt(args[0]);
    double p1 = Double.parseDouble(args[1]);
    double p2 = Double.parseDouble(args[2]);
    double p3 = Double.parseDouble(args[3]);

    SIR sir = new SIR();
    sir.setLattice(size, p1, p2, p3);
    DrawSIRS draw = new DrawSIRS(size);
    int[][] box = sir.getLattice();

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
          draw.paintPixels(box[i][j], i,j);
          draw.repaint();
      }
    }

    boolean run = true;
    run(draw, run, size, box, p1, p2,p3);

    int noSweeps = 10; // number of times to sweep through
    int eq = 100; // number of equilibration cycles
    int n = 100; // number of data points to measure

    for (int i = 0; i < noSweeps; i++) {
      box = sweep(box, p1, p2, p3);
      for (int j = 0; j < eq; j ++)
        box = sweep(box, p1, p2, p3);
      // Start taking measurements
      for (int j = 0; j <= n; j++) {
        sweep(box, p1, p2, p3);
        if(j%10==0)
        n++;
        // calculate mean
      }
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

  public static int[][] sweep(int[][] box,
                           double p1,
                           double p2,
                           double p3) {

  for (int i = 0; i < box.length; i++) {
    for (int j = 0; j < box.length; j++) {
       int[] spins = SIR.picks(2, box.length);
       box[spins[0]][spins[1]] = SIR.update(box,
                                            p1,
                                            p2,
                                            p3,
                                            spins[0],
                                            spins[1]);
      }
    }
    return box;
  }
}
