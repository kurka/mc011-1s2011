class A {
  public static void main(String[] args) {
    System.out.println(new B().func());
  }
}

class B {

  public int func() {
    
    int i;
    i = 1;

    i = i + 1;
    i = i + 1;
    return i;
  }
}
