#!/bin/bash

for i in $(ls testes/small/*/*) 
do 
  echo $i
  java minijava.TestParser < $i
done

