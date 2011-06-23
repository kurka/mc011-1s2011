all:
	javac syntaxtree/TypeVisitorAdapter.java
	javac main/Main.java
	javac main/PrettyPrint.java
	nasm -felf minijava.asm
	gcc -march=i386 -m32 -c runtime/runtime.c
	gcc -march=i386 -m32 minijava.o runtime.o -o mc011.o

clean:
	rm --force assem/*.class
	rm --force canon/*.class
	rm --force errors/*.class
	rm --force flow_graph/*.class
	rm --force frame/*.class
	rm --force graph/*.class
	rm --force main/*.class
	rm --force reg_alloc/*.class
	rm --force symbol/*.class
	rm --force syntaxtree/*.class
	rm --force temp/*.class
	rm --force translate/*.class
	rm --force tree/*.class
	rm --force util/*.class
	rm --force util/conversor/*.class
	rm --force visitor/*.class
	rm --force x86/*.class
	rm runtime.o minijava.o mc011.o
