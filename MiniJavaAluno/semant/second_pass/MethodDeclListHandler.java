package semant.second_pass;

import semant.Env;
import symbol.ClassInfo;
import syntaxtree.MethodDecl;
import util.List;

public class MethodDeclListHandler {

	// Chama o secondPass() para cada metodo
	public static void secondPass(Env e, ClassInfo c, List<MethodDecl> mList) {
		for (List<MethodDecl> m = mList; m != null; m = m.tail) {
			MethodDeclHandler.secondPass(e, c, m.head);
		}		
	}

}
