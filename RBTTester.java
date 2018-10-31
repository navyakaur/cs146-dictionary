package sjsu.kaur.cs146.project3;

import java.util.ArrayList;


import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.junit.Test;




public class RBTTester {

	String dictFile = "/Users/navyakaur/Documents/cs146/potika/Project3/RBTrees/src/sjsu/kaur/cs146/project3/dictionary.txt";
	String poemFile = "/Users/navyakaur/Documents/cs146/potika/Project3/RBTrees/src/sjsu/kaur/cs146/project3/poem";
	ArrayList<String> dictWords = new ArrayList<String>();
	ArrayList<String> poemLines = new ArrayList<String>();
	ArrayList<String> poemWords = new ArrayList<String>();
	String[] misspelledWords = null;
	
	@Test
	public void setup(){
		try {
			RedBlackTree<String> tree = new RedBlackTree<String>(dictFile);
			dictWords = tree.getLines(dictFile);
		}
		catch(IOException e) {
			System.out.println("Not Reading File:" + e.getMessage());
		}
		
	}
	
	@Test
    //Test the Red Black Tree
	public void test() {
		RedBlackTree<String> rbt = new RedBlackTree<String>();
        rbt.insert("D");
        rbt.insert("B");
        rbt.insert("A");
        rbt.insert("C");
        rbt.insert("F");
        rbt.insert("E");
        rbt.insert("H");
        rbt.insert("G");
        rbt.insert("I");
        rbt.insert("J");
        assertEquals("DBACFEHGIJ", makeString(rbt));
        
        String str=     "Color: 1, Key:D Parent: \n"+
                        "Color: 1, Key:B Parent: D\n"+
                        "Color: 1, Key:A Parent: B\n"+
                        "Color: 1, Key:C Parent: B\n"+
                        "Color: 1, Key:F Parent: D\n"+
                        "Color: 1, Key:E Parent: F\n"+
                        "Color: 0, Key:H Parent: F\n"+
                        "Color: 1, Key:G Parent: H\n"+
                        "Color: 1, Key:I Parent: H\n"+
                        "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));
		
		boolean found = true;
		if (rbt.lookup("G") == null)
			found = false;
		assertEquals(true, found);
		
		if(rbt.lookup("L") == null)
			found = false;
		assertEquals(false, found);
		//System.out.println("what");
    }

    
    //add tester for spell checker
	@Test
	public void spellChecker() throws IOException{
		
		RedBlackTree<String> dictTree = new RedBlackTree<String>();
		for (int i = 0; i< dictWords.size(); i++){
			dictTree.insert(dictWords.get(i));
		}
		System.out.println("Time it took to create dictionary: " + System.currentTimeMillis());
		System.out.println();
		try {
			RedBlackTree<String> poemTree = new RedBlackTree<String>(poemFile);
			poemLines = poemTree.getLines(poemFile);
			poemWords = poemTree.getWords(poemLines);
		}
		
		catch(IOException e) {
			System.out.println("Not Reading File:" + e.getMessage());
		}
		
		for (int x = 0; x<poemLines.size();x++){
			System.out.println(poemLines.get(x));
		}

		
		File misspelledWords = new File("/Users/navyakaur/Documents/cs146/potika/Project3/RBTrees/src/sjsu/kaur/cs146/project3/misspelledWords");
		BufferedWriter writer = new BufferedWriter(new FileWriter(misspelledWords));
		
		for (int i = 0; i < poemWords.size(); i++) {
			if (dictTree.lookup(poemWords.get(i)) == null) {
				System.out.println("Time it took to lookup " + poemWords.get(i) + ": " + System.currentTimeMillis());
				writer.write(poemWords.get(i) + " is misspelled.");
				writer.newLine();
			}
		}	
		System.out.println();
		System.out.println("Total Lookup Time: " + System.currentTimeMillis());
		
		writer.close();
	}
    
    public static String makeString(RedBlackTree<String> t)
    {
       class MyVisitor implements RedBlackTree.Visitor {
          String result = "";
          public void visit(RedBlackTree.Node n)
          {
             result = result + n.key;
          }
       };
       MyVisitor v = new MyVisitor();
       t.preOrderVisit(v);
       return v.result;
    }

    public static String makeStringDetails(RedBlackTree<String> t) {
    	{
    	       class MyVisitor implements RedBlackTree.Visitor {
    	          String result = "";
    	          public void visit(RedBlackTree.Node n)
    	          {
    	        	  if(!(n.key).equals("") && n.parent != null)
    	        		  result = result +"Color: "+n.color+", Key:"+n.key+" Parent: "+n.parent.key+"\n";
    	        	  if(!(n.key.equals("")) && n.parent == null)
    	        		  result = result +"Color: "+n.color+", Key:"+n.key+" Parent: " +"\n";
    	             
    	          }
    	       };
    	       MyVisitor v = new MyVisitor();
    	       t.preOrderVisit(v);
    	       return v.result;
    	 }
    }
  // add this in your class  
  //  public static interface Visitor
  //  {
  //  	/**
  //     This method is called at each node.
  //     @param n the visited node
  //  	 */
  //  	void visit(Node n);
  //  }
 
  
  // public void preOrderVisit(Visitor v)
  //  {
  //  	preOrderVisit(root, v);
  //  }
 
 
  // private static void preOrderVisit(Node n, Visitor v)
  //  {
  //  	if (n == null) return;
  //  	v.visit(n);
  //  	preOrderVisit(n.left, v);
  //  	preOrderVisit(n.right, v);
  //  }
    
    
 }
  
