all:
	java -jar sablecc-3.2/lib/sablecc.jar minijava.grammar
	javac main/Main.java

tests:
	javac minijava/TestLexer.java
	javac minijava/TestParser.java
	javac minijava/PrettyPrint.java
	javac minijava/UglyPrint.java
	javac minijava/TestAst.java

clean:
	rm -rf minijava/analysis minijava/lexer minijava/node minijava/parser
	rm --force */*/*.class
	rm --force */*.class

