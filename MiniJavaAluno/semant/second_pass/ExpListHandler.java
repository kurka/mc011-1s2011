package semant.second_pass;

import semant.Env;
import symbol.ClassInfo;
import symbol.MethodInfo;
import syntaxtree.Formal;
import syntaxtree.Call;
import syntaxtree.Exp;
import util.List;

public class ExpListHandler {

	public static List<Type> secondPass(Env e, ClassInfo c, MethodInfo m, List<Exp> actuals) {
		List<Type> retorno = new List<Type>;
		for (List<Formal> a = actuals; f != null; f = f.tail) {
			retorno.add(ExpHandler.secondPass(e, c, m, a.head));
		}
		return retorno;
	}
}
