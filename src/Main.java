import eduni.simjava.Sim_entity;


public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

}

class SimEntity extends Sim_entity {

  public SimEntity(String s) {
    super(s);
  }

  public SimEntity(String s, String s1, int i, int i1) {
    super(s, s1, i, i1);
  }
}
