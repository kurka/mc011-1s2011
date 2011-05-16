package semant.second_pass;

import semant.Env;
import syntaxtree.ClassDecl;
import util.List;

public class ClassDeclListHandler {

	static void secondPass(Env e, List<ClassDecl> classList) {
	    // Chama o secondPass do ClassDeclHandler para cada classe da lista.
	    for (List<ClassDecl> c = classList; c != null; c = c.tail) {
	      ClassDeclHandler.secondPass(e, c.head);
	    }		
	}

}
