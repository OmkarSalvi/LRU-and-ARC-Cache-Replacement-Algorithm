#/bin/bash

# command Line argument1 : size-> cache size
# command Line argument2 : file-> path of the trace file

# LRU policy
javac Lru1.java

java -Dsize=1024 -Dfile="P4.lis" Lru1
java -Dsize=2048 -Dfile="P4.lis" Lru1
java -Dsize=4096 -Dfile="P4.lis" Lru1
java -Dsize=8192 -Dfile="P4.lis" Lru1
java -Dsize=16384 -Dfile="P4.lis" Lru1
java -Dsize=32768 -Dfile="P4.lis" Lru1
java -Dsize=65536 -Dfile="P4.lis" Lru1
java -Dsize=131072 -Dfile="P4.lis" Lru1
java -Dsize=262144 -Dfile="P4.lis" Lru1
java -Dsize=524288 -Dfile="P4.lis" Lru1

# ARC Policy 
javac Arc1.java

java -Dsize=1024 -Dfile="P4.lis" Arc1
java -Dsize=2048 -Dfile="P4.lis" Arc1
java -Dsize=4096 -Dfile="P4.lis" Arc1
java -Dsize=8192 -Dfile="P4.lis" Arc1
java -Dsize=16384 -Dfile="P4.lis" Arc1
java -Dsize=32768 -Dfile="P4.lis" Arc1
java -Dsize=65536 -Dfile="P4.lis" Arc1
java -Dsize=131072 -Dfile="P4.lis" Arc1
java -Dsize=262144 -Dfile="P4.lis" Arc1
java -Dsize=524288 -Dfile="P4.lis" Arc1
