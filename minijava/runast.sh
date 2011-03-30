#!/bin/bash

if [ $# -ne 1 ]
then
  echo "usage: ./runast testes/small/1/01"
  exit
fi

java minijava.TestAst < $1