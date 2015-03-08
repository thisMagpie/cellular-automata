
public class Run {

  public static void main(String[] args) {
    int size = Integer.parseInt(args[0]);
    double p1 = Double.parseDouble(args[1]);
    double p2 = Double.parseDouble(args[2]);
    double p3 = Double.parseDouble(args[3]);
    new Lattice(size, p1, p2, p3);
  }
}
