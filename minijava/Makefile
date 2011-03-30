all: sablecc
	javac minijava/TestParser.java

sablecc:
	java -jar sablecc-3.2/lib/sablecc.jar minijava.grammar


clean:
	rm -rf minijava/*.class minijava/analysis minijava/lexer minijava/node minijava/parser
	rm --force minijava/*.class

