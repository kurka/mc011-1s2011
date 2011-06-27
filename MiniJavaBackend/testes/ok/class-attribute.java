class A {
  public static void main(String[] args) {
    System.out.println(new B().func());
  }
}

class B {

  public int func() {
    
    int a;
    int b;
    boolean check;

    Data d;
    d = new Data();
    check = d.init(1, 2);

    a = d.getI();
    b = d.getJ();

    return a + b;
  }
}

class Data {
  int i;
  int j;

  public boolean init(int v_i, int v_j) {
    i = v_i;
    j = v_j;
    return true;
  }

  public int getI() {
    return i;
  }
  public int getJ() {
    return j;
  }
}
