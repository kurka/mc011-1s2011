package semant.first_pass;

import semant.Env;
import semant.first_pass.ProgramHandler;
import syntaxtree.Program;
import errors.ErrorEchoer;

/**
 * Classe que implementa o metodo que inicia a primeira passagem.
 * Faz isso chamando o handler do noh Program. Os outros handlers
 * sao chamados recursivamente.
 */
public final class FirstPass {
  private FirstPass(){
    super();
  }

  public static Env firstPass(ErrorEchoer err, Program p) {
    return ProgramHandler.firstPass(err, p);
  }
}
