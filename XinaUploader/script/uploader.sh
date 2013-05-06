#!/bin/sh

export CP=""
for jar in `ls $PWD/lib/*.jar`
do
	export CP="$CP:$jar"
done

java -cp $CP -Dfile.encoding=utf-8 CUILoader $1