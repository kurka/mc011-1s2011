class A {
  public static void main(String[] args) {
    System.out.println(new B().func());
  }
}

class B {

  public int func() {
    int a;
    int b;
    int ret;

    a = 234;
    b = 1;

    if (b < a) {
      ret = a;
    }
    else {
      ret = b;
    }
    return ret;
  }

}
