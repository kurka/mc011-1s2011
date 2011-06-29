class A {
  public static void main(String[] args) {
    System.out.println(new B().func());
  }
}

class B {

  public int func() {
    
    int i;
	int j;
    i = 5;
	j = 6;

	i = i + 1;
    j = i + 1;
    j = 1 + i;
	System.out.println(j);
	System.out.println(i);

    return i;
  }
}
