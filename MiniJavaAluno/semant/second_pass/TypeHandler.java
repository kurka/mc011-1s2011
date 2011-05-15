package semant.second_pass;

import symbol.ClassInfo;

final class TypeHandler {

  public static final boolean canInheritFrom(ClassInfo derived, ClassInfo base) {

    if (base == null) {
      throw new NullPointerException("Classe base passada na checagem de heranca invalida.");
    }

    // Itera recursivamente sobre o class-info da classe base, verificando se em
    // algum ponto a classe derivada eh do mesmo tipo que algum ancestral seu.
    while (base != null) {
      if (derived.name == base.name) {
        return false;
      }
      else {
        base = base.base;
      }
    }

    // Se passar pelo while, entao a heranca eh valida
    return true;
  }


}
