package semant;

import symbol.ClassInfo;
import symbol.Table;
import errors.ErrorEchoer;

public class Env {
  // Attributes
  public ErrorEchoer err;
  public Table<ClassInfo> classes;

  // Constructor
  public Env(ErrorEchoer e) {
    super();
    err = e;
    classes = new Table<ClassInfo>();
  }
}
