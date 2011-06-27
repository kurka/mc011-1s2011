class A {
  public static void main(String[] args) {
    System.out.println(new B().func());
  }
}

class B {

  public int func() {
    int a;
    a = 234;
    return this.func2(a);
  }

  public int func2(int arg) {
    return arg * 2;
  }
}
