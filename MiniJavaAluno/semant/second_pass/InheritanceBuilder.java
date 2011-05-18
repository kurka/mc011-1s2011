package semant.second_pass;

import util.List;

import semant.Env;

import syntaxtree.VisitorAdapter;
import syntaxtree.ClassDecl;
import syntaxtree.ClassDeclExtends;
import syntaxtree.Program;

import symbol.Symbol;
import symbol.ClassInfo;

class InheritanceBuilder extends VisitorAdapter {

	private Env env;
	List<ClassDecl> classes;

	private InheritanceBuilder(Env e, List<ClassDecl> c) {
		super();
		env = e;
		classes = c;
	}

	static void secondPass(Env e, Program p) {
		InheritanceBuilder b = new InheritanceBuilder(e, p.classList);
		p.accept(b);//constroi arvore de herancas
    
		//// Checagem do restante do programa
    	//MainClassHandler.secondPass(env, p.mainClass);
    	//ClassDeclListHandler.secondPass(env, p.classList);

	}

	public void visit(Program p) {
		//constroi a hierarquia das classes, usando setBase
		for ( List<ClassDecl> aux = p.classList; aux != null; aux = aux.tail ){
			if(aux.head instanceof ClassDeclExtends){
				ClassDeclExtends node = (ClassDeclExtends) aux.head;
				//encontra o filho em env
				Symbol filho = Symbol.symbol(node.name.s);
				ClassInfo cfilho = env.classes.get(filho);
				//encontra o pai em env
				Symbol base = Symbol.symbol(node.superClass.s);
				ClassInfo cbase = env.classes.get(base);
				cfilho.setBase(cbase);
			}
		}
		// Checagem do restante do programa
    	MainClassHandler.secondPass(env, p.mainClass);
    	ClassDeclListHandler.secondPass(env, p.classList);
	}
}
