package sjsu.kaur.cs146.project3;

import java.io.FileReader;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;

//import RedBlackTree.Node;
//
//import RedBlackTree.Visitor;

public class RedBlackTree<Key extends Comparable<Key>> {	
	private static RedBlackTree.Node<String> root = null;
	private String dict;
	public static class Node<Key extends Comparable<Key>> { //changed to static 
		
		  Key key;  		  
		  Node<String> parent;
		  Node<String> leftChild;
		  Node<String> rightChild;
		  boolean isRed;
		  int color;
		  
		  public Node(Key data){
			  this.key = data;
			  leftChild = null;
			  rightChild = null;
		  }	
		  
		  public int compareTo(Node<Key> n){ 	//this < that  <0
		 		return key.compareTo(n.key);  	//this > that  >0
		  }
		  
		  public boolean isLeaf(){
			  if (this.equals(root) && this.leftChild == null && this.rightChild == null) return true;
			  if (this.equals(root)) return false;
			  if (this.leftChild == null && this.rightChild == null){
				  return true;
			  }
			  return false;
		  }
	}
	
	public RedBlackTree() {
		
	}

	public RedBlackTree(String dictionary) {
		dict = dictionary;
	}
		
	 public boolean isLeaf(RedBlackTree.Node<String> n){
		  if (n.equals(root) && n.leftChild == null && n.rightChild == null) return true;
		  if (n.equals(root)) return false;
		  if (n.leftChild == null && n.rightChild == null){
			  return true;
		  }
		  return false;
	  }
	
	public interface Visitor<Key extends Comparable<Key>> {
		/**
		This method is called at each node.
		@param n the visited node
		*/
		void visit(Node<Key> n);  
	}
	
	public void visit(Node<Key> n){
		System.out.println(n.key);
	}
	
	public void printTree(){  //preorder: visit, go left, go right
		RedBlackTree.Node<String> currentNode = root;	
		printTree(currentNode);
	}
	
	public void printTree(RedBlackTree.Node<String> node){
		System.out.print(node.key);
		if (node.isLeaf()){
			return;
		}
		printTree(node.leftChild);
		printTree(node.rightChild);
	}
	
	// place a new node in the RB tree with data the parameter and color it red. 
	public void addNode(String data){  	//this < that  <0.  this > that  >0
	 //	fill
		
		RedBlackTree.Node<String> curr = new RedBlackTree.Node<String>(data);
		curr.color = 0;
		if (root == null) {
			root = curr;
			root.parent = null;
		}
		else {
			RedBlackTree.Node<String> check = root;
			RedBlackTree.Node<String> update = null;
			
			while (check != null) {
				update = check;
				if (curr.key.compareTo(check.key) > 0) {
					check = check.rightChild;
					
				}
				else if(curr.key.compareTo(check.key) < 0) {
					check = check.leftChild;
				}	
			}
			curr.parent = update;
			if (curr.key.compareTo(update.key) < 0){
				update.leftChild = curr;
			}
			else {
				update.rightChild = curr;
			}
			
			fixTree(curr);
			
		}
		
		
	}	

	public void insert(String data){
		addNode(data);	
	}
	
	public RedBlackTree.Node<String> lookup(String k){ 
		//searches for a key
		RedBlackTree.Node<String> n = new RedBlackTree.Node<String>(k);
		RedBlackTree.Node<String> check = root;
		if (n.equals(root)) {
			return root;
		}
		while (check != null) {
			if (check.compareTo(n) < 0) {
				check = check.rightChild;
			}
			else if (check.compareTo(n) > 0) {
				check = check.leftChild;
			}
			else if (check.compareTo(n) == 0) {
				return check;
			}
		}
		return null;
	}
 	
	
	public RedBlackTree.Node<String> getSibling(RedBlackTree.Node<String> n){  
		// returns the sibling node of the parameter If the sibling does not exist, then
		//return null.
		if (n.parent.leftChild != null && n.compareTo(n.parent) > 0) {
			return n.parent.leftChild;
		}
		else if (n.parent.rightChild != null) {
			return n.parent.rightChild;
		}
		return null;
	}
	
	
	public RedBlackTree.Node<String> getAunt(RedBlackTree.Node<String> n){
		//returns the aunt of the parameter or the sibling of the parent node. If the aunt
		 //node does not exist, then return null.

		if (n.parent.compareTo(n.parent.parent) > 0 && n.parent.parent.leftChild != null ) {
			return n.parent.parent.leftChild;
		}
		if (n.parent.compareTo(n.parent.parent) < 0 && n.parent.parent.rightChild != null) {
			return n.parent.parent.rightChild;
		}
		return null;
	}
	
	public RedBlackTree.Node<String> getGrandparent(RedBlackTree.Node<String> n){
		return n.parent.parent;
	}
	
	public void rotateLeft(RedBlackTree.Node<String> n){
		//
		RedBlackTree.Node<String> y = n.rightChild;
		n.rightChild = y.leftChild;
		if (y.leftChild != null) {
			y.leftChild.parent = n;
		}
		y.parent = n.parent;
		if (n.parent == null) {
			root = y;
		}
		else if (n == n.parent.leftChild) {
			n.parent.leftChild = y;
		}
		else {
			n.parent.rightChild = y;
		}
		y.leftChild = n;
		n.parent = y;	
	}
	
	public void rotateRight(RedBlackTree.Node<String> n){
		//x keeps its left child
		//y keeps its right child
		//x’s right child becomes y’s left child
		//x’s and y’s parents change
		
		//x's right child //y's left child
//		n.parent = n.leftChild; //setting parent of y to x
//		n.leftChild = n.leftChild.rightChild; // changing x to B
//		n.parent.leftChild = n.leftChild.leftChild; //setting x's left child to A
//		n.leftChild.rightChild = null; //setting B children to null
//		n.leftChild.leftChild = null; //setting B children to null 
		
		RedBlackTree.Node<String> x = n.leftChild;
		n.leftChild = x.rightChild;
		if (x.rightChild != null) {
			x.rightChild.parent = n;
		}
		x.parent = n.parent;
		if (n.parent == null) {
			root = x;
			root.color = 1;
		}
		else if (n == n.parent.rightChild) {
			n.parent.leftChild = x;
		}
		else {
			n.parent.rightChild = x;
		}
		x.rightChild = n;
		n.parent = x;
	}
	
	public void fixTree(RedBlackTree.Node<String> current) {
		
		if (current.equals(root)) {
			current.color = 1; //1 means black
			return;
		}

		if(current.parent == null || current.parent.color == 1 || current.parent == root) {
			return;
		}
		
		if (current.color == 0 && current.parent.color == 0) {
					if (getAunt(current) == null || getAunt(current).color == 1) {
						if (isLeftChild(current.parent, current) && current == current.parent.rightChild) {
							rotateLeft(current.parent);
							current = current.parent;
							fixTree(current);
						}
						else if (isRightChild(current.parent, current) && current == current.parent.leftChild) {
							rotateRight(current.parent);
							current = current.parent;
							fixTree(current);
						}
						else if (isLeftChild(current.parent, current) && current == current.parent.leftChild) {
							current.parent.color = 1;
							getGrandparent(current).color = 0;
							rotateRight(getGrandparent(current));
							return;
						}
						else if (isRightChild(current.parent, current) && current == current.parent.rightChild) {
							current.parent.color = 1;
							getGrandparent(current).color = 0;
							rotateLeft(getGrandparent(current));
							return;
						}
					}
					else if (getAunt(current).color == 0) {
						current.parent.color = 1;
						getAunt(current).color = 1;
						getGrandparent(current).color = 0;
						current = getGrandparent(current);
						fixTree(current);
					}
					
				}
				
			}
		
	
	
	public boolean isEmpty(RedBlackTree.Node<String> n){
		if (n.key == null){
			return true;
		}
		return false;
	}
	 
	public boolean isLeftChild(RedBlackTree.Node<String> parent, RedBlackTree.Node<String> child)
	{
		if (child.compareTo(parent) < 0 ) {//child is less than parent
			return true;
		}
		return false;
	}
	
	public boolean isRightChild(RedBlackTree.Node<String> parent, RedBlackTree.Node<String> child)
	{
		if (child.compareTo(parent) > 0 ) {//child is less than parent
			return true;
		}
		return false;
	}
	
	public ArrayList<String> getLines(String x) throws IOException{
		FileReader fileReader = new FileReader(x);
        // Always wrap FileReader in BufferedReader.
		String frLine = null;
		ArrayList<String> lines = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while((frLine = bufferedReader.readLine()) != null) {
           lines.add(frLine);
        // Always close files.
        }
        bufferedReader.close();
        return lines;
	}
	
	public ArrayList<String> getWords(ArrayList<String> x) throws IOException {
		ArrayList<String> words = new ArrayList<String>();
		for (int i = 0; i < x.size(); i++) {
			String[] separate = x.get(i).split("\\s");
			for (int j = 0; j < separate.length; j++) {
				if (separate[j] != "\n") {
					words.add(separate[j]);
				}
			}
		}
		return words;
	}

	public void preOrderVisit(Visitor<String> v) {
	   	preOrderVisit(root, v);
	}
	 
	 
	private static void preOrderVisit(RedBlackTree.Node<String> n, Visitor<String> v) {
	  	if (n == null) {
	  		return;
	  	}
	  	v.visit(n);
	  	preOrderVisit(n.leftChild, v);
	  	preOrderVisit(n.rightChild, v);
	}

}

