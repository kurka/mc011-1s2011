package semant.second_pass;

import semant.Env;
import syntaxtree.Program;

/**
 * Classe que implementa o metodo que inicia a segunda passagem.
 * Faz isso chamando o handler do noh Program. Os outros handlers
 * sao chamados recursivamente.
 */
public final class SecondPass {

  public static void secondPass(Env e, Program p) {
    // Constroi a hierarquia de classes, verificando herancas circulares.
    InheritanceBuilder.secondPass(e, p);

    // Checagem do programa
    MainClassHandler.secondPass(e, p.mainClass);
    ClassDeclListHandler.secondPass(e, p.classList);
  }
}
