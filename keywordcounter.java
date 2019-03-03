import java.io.*;
import java.util.*;
import java.util.regex.*;
public class keywordcounter {
    public static void main(String[] args){
        //Initialize the data structures and files needed for the program.
        File file = new File("output_file.txt");
        BufferedWriter writer=null;
        String pathtofile = args[0];
        HashMap<String,Node> hash_table = new HashMap();  
        FibonacciHeap maxfib_heap = new FibonacciHeap();  
      try {
        //Compiling Patterns for the keyword and its search count.
        Pattern keyword_pattern = Pattern.compile("([$])([a-z_]+'?[a-z_]+)(\\s)(\\d+)");
        Pattern search_count_pattern = Pattern.compile("(\\d+)");
        BufferedReader br = new BufferedReader(new FileReader(pathtofile));
        String input_line = br.readLine();
        writer = new BufferedWriter( new FileWriter(file));
        while (input_line != null) {
            //Exit out of the program the moment the first 'stop' in input file is encountered.
            if( input_line.equals("stop") || input_line == "Stop" || input_line == "STOP"){
                System.out.println("Reached stop. Terminating Program.");
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                System.exit(0);
            }
            // System.out.println(input_line);

            //Creating matchers for the Patterns defined above.
            Matcher keyword_matcher = keyword_pattern.matcher(input_line);
            Matcher search_count_matcher = search_count_pattern.matcher(input_line);
            if (keyword_matcher.find()) {
                //Extract the keyword and its search count from matched groups.
                String keyword = keyword_matcher.group(2);
                int key = Integer.parseInt(keyword_matcher.group(4));
                //Check if Hash map has keyword, if not found create new node and insert in fibonacci heap and hash map
                if ( !hash_table.containsKey(keyword))
                {
                    Node node = new Node(keyword,key);
                    maxfib_heap.insert(node);
                    hash_table.put(keyword,node);
                }
                else
                {
                    //if already in hashmap then call increase key in fibonacci heap
                    int increaseKey = hash_table.get(keyword).key + key;
                    maxfib_heap.increaseKey(hash_table.get(keyword),increaseKey);
                }
            } else if (search_count_matcher.find()) {
                //Query which is the number of nodes to be removed is extracted and converted to Int.
                int removeNumber = Integer.parseInt(search_count_matcher.group(1));
                //Storage structure for removed nodes
                ArrayList<Node> removedNodes = new ArrayList<Node>(removeNumber);
                //Perform remove max as many number of times as the query in the input file and store removed nodes in an array
                for ( int i=0;i<removeNumber;i++)
                {
                    Node node = maxfib_heap.removeMax();
                    hash_table.remove(node.getKeyword());
                    Node newNode= new Node(node.getKeyword(),node.key);
                    //add the new node for insertion into removed nodes array
                    removedNodes.add(newNode);
                    //Comma separators for the output
                    if ( i <removeNumber-1) {
                        writer.write(node.getKeyword() + ",");
                    }
                    else {
                        writer.write(node.getKeyword());
                    }
                }
                //Insert the removed nodes back into the FHeap.
                for ( Node iterate : removedNodes)
                {
                    maxfib_heap.insert(iterate);
                    hash_table.put(iterate.getKeyword(),iterate);
                }
                writer.newLine();//Print new line in output_file.txt
            }
            input_line = br.readLine(); //Advance to next line in input file
        }
    }
    //Handle the different exceptions that may arise.
    catch(NullPointerException e){
        System.out.println("The query exceeds number of keywords available. Please check input and try again. ");
    }
    catch(FileNotFoundException e){
        System.out.println("No file found at path or incorrect path. Please try again.");
    }
    catch(IOException e){
        System.out.println("IO exception was caught:"+e.getMessage());
    }
    finally {
        if ( writer != null ) {
            try {
                writer.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    }
}