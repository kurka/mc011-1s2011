class A {
  public static void main(String[] args) {
    System.out.println(new B().func(1, 2));
  }

}

class B {
  public int func(int arg1, int arg2) {
    return arg1 + arg2;
  }
}
