#!/bin/bash

PASS="first_pass"

HANDLERS="MainClass ClassDeclSimple ClassDeclExtends VarDecl MethodDecl Formal IntArrayType BooleanType IntegerType IdentifierType Block If While Print Assign ArrayAssign And LessThan Equal Plus Minus Times ArrayLookup ArrayLength Call IntegerLiteral True False This NewArray NewObject Not IdentifierExp Identifier"

for i in $HANDLERS;
do
  CLASS="${i}Handler"
  FILE="${CLASS}.java"
  echo "Creating $FILE"

  touch $FILE

  # Headers
  echo "package semant.${PASS};" > $FILE
  echo "" >> $FILE
  echo "import semant.Env;" >> $FILE
  echo "import syntaxtree.VisitorAdapter;" >> $FILE
  echo "import syntaxtree.${i};" >> $FILE
  echo "" >> $FILE

  # Class
  echo "class ${CLASS} extends VisitorAdapter {" >> $FILE
  echo "" >> $FILE

  # Env attribute
  echo "  private Env env;" >> $FILE
  echo "" >> $FILE

  # Constructor
  echo "  private ${CLASS}(Env e) {" >> $FILE
  echo "    super();" >> $FILE
  echo "    env = e;" >> $FILE
  echo "  }" >> $FILE
  echo "" >> $FILE
  echo "" >> $FILE

  # firstPass()
  echo "  static void firstPass(Env e, ${i} node) {" >> $FILE
  echo "    // Do firstPass() here..." >> $FILE
  echo "  }" >> $FILE
  echo "" >> $FILE

  # visit()
  echo "  public void visit(${i} node) {" >> $FILE
  echo "    // Do visit() here..." >> $FILE
  echo "  }" >> $FILE
  echo "" >> $FILE

  echo "}" >> $FILE
  echo "" >> $FILE

done
