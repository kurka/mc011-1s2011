class A {
  public static void main(String[] args) {
    System.out.println(new B().func());
  }
}

class B {
  int attribute;

  public int func() {
    attribute = 1234;
    return attribute;
  }
}
