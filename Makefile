all:
	javac FileFormatter.java
	javac Markov.java

clean:	
	rm -rf FileFormatter.class Markov.class

