class A {
  public static void main(String[] args) {
    System.out.println(new B().func());
  }
}

class B {

  public int func() {
    int[] arr;
    int ret;


    arr = new int[5];

    arr[0] = 0;
    arr[1] = 1;
    arr[2] = 2;
    arr[3] = 3;
    arr[4] = 4;

    ret = arr[1] + arr[4];
    return ret;
  }

}
