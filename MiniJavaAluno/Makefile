all: sablecc mainjava

mainjava:
	javac main/Main.java
tests:
	javac minijava/TestLexer.java
	javac minijava/TestParser.java
	javac minijava/PrettyPrint.java
	javac minijava/UglyPrint.java
	javac minijava/TestAst.java

sablecc:
	java -jar sablecc-3.2/lib/sablecc.jar minijava.grammar


clean:
	rm -rf minijava/*.class minijava/analysis minijava/lexer minijava/node minijava/parser
	rm --force minijava/*.class

