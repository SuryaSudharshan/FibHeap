# FibHeap
Fibonacci Heap to store N most popular queries of a search engine

##Implementation of Fibonacci Heap in JAVA

The goal of this project is to implement a system to find the n most popular hashtags appeared on social media such as Facebook or Twitter. For the scope of this project queries are taken as an input file. Basic idea for the implementation is to use a max priority structure to find out the most popular queries.

##The project uses the following data structure.

Max Fibonacci heap: Use to keep track of the frequencies of Hashtags.
Hash table(Hash Map in java) : Key for the hash table is query and value is pointer to the corresponding node in the Fibonacci heap.
The project is written in JAVA. I have implemented Fibonacci Heap in java and stored the address of all the nodes in a Hash Map (Built in Data Structure). I have written the project using JAVA without any external in build data structure. Max Fibonacci heap is required because it has better theoretical bounds for increase key operation.

##COMPILING & RUNNING INSTRUCTIONS

The project has been compiled and tested on thunder.cise.ufl.edu and java compiler on local machine. To execute the program, You can remotely access the server using ssh username@thunder.cise.ufl.edu For running the Hash Tag Counter

Extract the contents of the zip file
Type ‘make’ without the quotes.
Type ‘java hashtagcounter ‘file path/input_file_name.txt’ ’ without the quotes and add the file name and pathI’ve included the file sampleInput.txt .
