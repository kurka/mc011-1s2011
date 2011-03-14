sablecc:
	java -jar sablecc-3.2/lib/sablecc.jar minijava.grammar

compiler:
	javac minijava/Compiler.java

all: sablecc compiler


clean-sablecc:
	rm -rf minijava/*.class minijava/analysis minijava/lexer minijava/node minijava/parser

clean-compiler:
	rm --force minijava/*.class

clean: clean-compiler clean-sablecc
