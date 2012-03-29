
JC = javac
FLAGS = -g -d bin
SRC = src/Matrix.java src/Strassen.java

default: all

all:
	$(JC) $(FLAGS) $(SRC)

clean:
	$(RM) bin/*.class