package semant.second_pass;

import semant.Env;
import symbol.ClassInfo;
import symbol.Symbol;
import syntaxtree.Type;
import syntaxtree.IntegerType;
import syntaxtree.IntArrayType;
import syntaxtree.BooleanType;
import syntaxtree.IdentifierType;


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

  //checa compatibilidade entre tipos de duas variaveis
  public static final boolean compatible(Env e, Type target, Type source){
	  //checa se objetos ja sao de algum tipo final
	  if(target instanceof IntegerType)
		  return source instanceof IntegerType;
	  else if(target instanceof IntArrayType)
		  return source instanceof IntArrayType;
	  else if(target instanceof BooleanType)
		  return source instanceof BooleanType;

	  if(!(source instanceof IdentifierType))
		  return false;

	  Symbol nc1 = Symbol.symbol( ((IdentifierType)target).name );
	  Symbol nc2 = Symbol.symbol( ((IdentifierType)source).name );

	  ClassInfo c1 = e.classes.get(nc1);
	  ClassInfo c2 = e.classes.get(nc2);

	  return compatible(e, c1, c2);
  }

  //checa compatibilidade entre classes com nomes diferentes (mas possivelmente mesmo tipo)
  private static final boolean compatible(Env e, ClassInfo s, ClassInfo d){
	if(s == null)
		return false;

    //procura nome igual entre os pais
	while(d != null){
		if(s.name == d.name)
			return true;
		else
			d = d.base;
	}
	return false;
  }

}
