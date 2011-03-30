#!/bin/bash

for i in $(ls testes/small/*/* testes/large/*) 
do 
  echo $i
  java minijava.TestParser < $i
done

