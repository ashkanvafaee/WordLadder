/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Replace <...> with your actual data.
 * Ashkan Vafaee
 * av28837
 * 16238
 * Kevin Chau
 * kc28535
 * 16238
 * Slip days used: <0>
 * Git URL:https://github.com/ashkanvafaee/EE422C
 * Spring 2017
 */

package assignment3;

import java.util.*;
import java.io.*;

public class Main {

	static Set<String> checker = new HashSet<String>();
	
	static ArrayList<String> initial = new ArrayList<String>();
	
	static boolean dfsFlag = false;													//used to signal no element found
	
	static boolean makeDict = false;
	
	static Set<String> dict = new HashSet <String>(); 

	// static variables and constants only here.

	public static void main(String[] args) throws Exception {

		Scanner kb; // input Scanner for commands
		PrintStream ps; // output file
		// If arguments are specified, read/write from/to files instead of Std
		// IO.
		if (args.length != 0) {
			kb = new Scanner(new File(args[0]));
			ps = new PrintStream(new File(args[1]));
			System.setOut(ps); // redirect output to ps
		} else {
			kb = new Scanner(System.in);// default from Stdin
			ps = System.out; // default to Stdout
		}
		initialize();

		ArrayList<String> inputs = parse(kb);
		while (inputs.size() > 0) {

			printLadder(getWordLadderDFS(inputs.get(0).toLowerCase(), inputs.get(1).toLowerCase()));

			inputs = parse(kb);

		}

		return;

		// TODO methods to read in words, output ladder
	}

	public static void initialize() {
		// initialize your static variables or constants here.
		// We will call this method before running our JUNIT tests. So call it
		// only once at the start of main.
	}

	/**
	 * @param keyboard
	 *            Scanner connected to System.in
	 * @return ArrayList of Strings containing start word, rungs, and end word.
	 *         If command is /quit, return empty ArrayList.
	 */
	public static ArrayList<String> parse(Scanner keyboard) {
		ArrayList<String> inputs = new ArrayList<String>();
		String s1 = keyboard.next().toLowerCase();

		if (s1.equals("/quit")) {
			return (inputs);
		}

		String s2 = keyboard.next().toLowerCase();

		if (s2.equals("/quit")) {
			return (inputs);
		}

		inputs.add(s1);
		inputs.add(s2);
		return (inputs);

	}

	
	
	
	/**
	 * @param start and end strings
	 * DFS implementation
	 * @return ArrayList of Strings containing start word, rungs, and end word.
	 *        
	 */
	public static ArrayList<String> getWordLadderDFS(String start, String end) {
		
		if(initial.size()==0){
			initial.add(start);
			initial.add(end);
		}
		
		
		if(makeDict==false){
			dict = makeDictionary();
			makeDict=true;
		}
		
		
		ArrayList<String> results = new ArrayList<String>();

		if (start.equals(end)) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(start);
			
			if(start.equals(initial.get(0))){
				checker.clear();
				initial.clear();
				dfsFlag=false;
				makeDict=false;
				dict.clear();
			}
			
			return (temp);
		}

		char[] word = new char[start.length()];
		char[] str = new char[start.length()];
		str = start.toCharArray();
		String currentWord = start;
		checker.add(start);
		
		int skip = 0;														//used to skip same initial letters

		for (int i = 0; i < start.length(); i++) {
			word = start.toCharArray();
			while((skip+i)<start.length() && word[(i+skip)%(start.length())] == end.toCharArray()[(i+skip)%(start.length())]){		//while start and end letters are the same, skip them
				str[(i+skip)%(start.length())]=0;			//mark skips
				skip++;
				}
			
			while((skip+i)>=start.length() && str[(i+skip)%(start.length())]!=0){
				skip++;
			}
			
			
			for (int j = 0; j < 26; j++) {
				//word[(i+skip)%start.length()] = (char) (j + 97);
				//word[i] = (char) (j+97);
				if((end.toCharArray()[(i+skip)%(start.length())] + j)>122){				// 122 = 'z'
					word[(i+skip)%start.length()] = (char) (end.toCharArray()[(i+skip)%(start.length())] + j -26);
				}
				else{
				word[(i+skip)%start.length()] = (char) (end.toCharArray()[(i+skip)%(start.length())] + j);
				}
				
				if (dict.contains(String.valueOf(word).toUpperCase()) && !checker.contains(String.valueOf(word))) {
					checker.add(String.valueOf(word));
					results = (getWordLadderDFS(String.valueOf(word), end));
					

					
					if(results.size()>0){
						
					if(dfsFlag==true){
						ArrayList<String> temp = new ArrayList<String>();
						temp.clear();
						temp.add(initial.get(0));
						temp.add(initial.get(1));
						
						if(start.equals(initial.get(0))){
							checker.clear();
							initial.clear();
							dfsFlag=false;
							makeDict=false;
							dict.clear();
						}
						
						return(temp);
					}
					
					if(results.size()>=2 && dfsFlag==false && results.get(results.size()-2).equals(initial.get(0))){
						ArrayList<String> temp = new ArrayList<String>();
						temp.add(initial.get(0));
						temp.add(initial.get(1));
						
						if(start.equals(initial.get(0))){
							checker.clear();
							initial.clear();
							dfsFlag=false;
							makeDict=false;
							dict.clear();
						}
						
						return(temp);
					}
					
					if(dfsFlag==false && results.get(results.size()-1).equals(initial.get(1))){
						results.add(0,currentWord);
						if(start.equals(initial.get(0))){
							checker.clear();
							initial.clear();
							dfsFlag=false;
							makeDict=false;
							dict.clear();
						}
						
						return(results);
					}
				}
					
					
					// catches found case
				}
				
				if(i==start.length()-1 && j==25 && start.equals(initial.get(0))){								//if we've reached the end
					dfsFlag=true;
				}
			}
			
		}
		
	

		
		ArrayList<String> temp = new ArrayList<String>();	
		//if(start.equals(initial.get(0))){
		//temp.add(initial.get(0));
		//temp.add(initial.get(1));
		//}

		// Returned list should be ordered start to end. Include start and end.
		// If ladder is empty, return list with just start and end.
		
		if(start.equals(initial.get(0))){
			temp.add(initial.get(0));
			temp.add(initial.get(1));
			
			checker.clear();
			initial.clear();
			dfsFlag=false;
			makeDict=false;
			dict.clear();
		}

		return temp; // replace this line later with real return
	}

	
	/**
	 * @param start and end strings
	 * BFS implementation
	 * @return ArrayList of Strings containing start word, rungs, and end word.
	 *        
	 */
	public static ArrayList<String> getWordLadderBFS(String start, String end) {

		if (start.equals(end)) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(start);
			temp.add(end);
			return (temp);
		}

		Set<String> check = new HashSet<String>();
		Set<String> dict = makeDictionary();

		Queue<String> q = new LinkedList<String>();
		ArrayList<neighbors> results = new ArrayList<neighbors>(); 
		

		boolean flag = false;
		q.add(start);

		while (q.size() > 0) {
			String temp = q.remove();
			if (temp.equals(end)) {
				flag = true;
				break;
			}
			check.add(temp); // adds head to check set

			char[] word = new char[temp.length()];

			for (int i = 0; i < temp.length(); i++) {
				word = temp.toCharArray();
				for (int j = 0; j < 26; j++) {
					word[i] = (char) (j + 97);

					if (dict.contains(String.valueOf(word).toUpperCase()) && !check.contains(String.valueOf(word))) {
						neighbors next = new neighbors();
						next.parent = temp;
						next.value = String.valueOf(word);
						results.add(next);
						check.add(next.value);

						q.add(String.valueOf(word));
					}

				}
			}

		}

		ArrayList<String> data = new ArrayList<String>();

		if (flag == true) {
			int index = 0;

			for (; index < results.size(); index++) {
				if (results.get(index).value.equals(end)) {
					break;
				}
			}

			while (!results.get(index).value.equals(start)) {
				neighbors temp = new neighbors();
				temp = results.get(index);
				data.add(temp.value);

				if (temp.parent.equals(start)) {
					break;
				}

				for (index = 0; index < results.size(); index++) {
					if (results.get(index).value.equals(temp.parent)) {
						break;
					}
				}
			}

			data.add(start);

			ArrayList<String> dataReversed = new ArrayList<String>();

			for (int i = data.size() - 1; i >= 0; i--) {
				dataReversed.add(data.get(i));
			}

			return dataReversed; // replace this line later with real return
		}

		else {
			data.add(start);
			data.add(end);
			return (data);
		}
	}

	
	/**
	 * @param void
	 * @return Set containing all words in dictionary
	 *        
	 */
	public static Set<String> makeDictionary() {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner(new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}

	/**
	 * @param ArrayList containing the ladder elements including start and end word in order
	 * prints the ladder to console
	 * @return void
	 *        
	 */
	public static void printLadder(ArrayList<String> ladder) {
		if (ladder.size() > 2) {
			System.out.println("a " + (ladder.size() - 2) + "-rung word ladder exists between " + ladder.get(0)
					+ " and " + ladder.get(ladder.size() - 1) + ".");
			for (int i = 0; i < ladder.size(); i++) {
				System.out.println(ladder.get(i));
			}
		}

		else {
			System.out.println("no word ladder can be found between " + ladder.get(0) + " and " + ladder.get(1) + ".");
		}
	}

	// TODO
	// Other private static methods here
}
