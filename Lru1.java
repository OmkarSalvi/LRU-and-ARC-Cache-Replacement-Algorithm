import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Node2 --- To represent the pages in the cache as nodes of the Doubly Linked List
 * @author   OMKAR SALVI
 *
 */
class Node2 
{
 public int data; // page number
 public Node2 next;
 public Node2 previous;
 
 /**
  * constructor which initializes the node with argument passed
  * @param x page number to be assigned
  */
 public Node2(int x) 
 {
  data=x;
 }
 
 /**
  * method to display page number stored in the node
  * @return No return value
  */
 public void displayNode() 
 {
  System.out.print(data+"  ");
 }

/**
 * method to get the page number stored in the node
 * @return data i.e. page number stored by the node
 */
 public int GetData() 
 {
	 return data;
 }
}


/**
 * DoublyLinkList2 --- Implements the queue using Doubly Linked list
 * @author OMKAR
 *
 */
class DoublyLinkList2 
{
 public Node2 first;
 public Node2 last;
 public static int ListSize; // size of the queue

/**
 * constructor to initialize an empty queue
 */
 public DoublyLinkList2() 
 {
  first=null;
  setLast(null);
  ListSize=0;
 }
 
/**
 * method to get the size of the linked list
 * @return Size of the queue formed
 */
 public int GetSize() 
 {
	 return ListSize;
 }
 
 /**
  * Returns whether the cache .i.e linked list is empty
  *   
  * @return     true if cache is empty else false
  */
 public boolean isEmpty() // Checks whether linked list is empty
 {
  return(first==null);
 }

 /**
  * Returns LRU node in the cache .i.e queue
  *   
  * @return   node at the tail of queue
  */
public Node2 getLast() {
	return last;
}

/**
 * Assigns the parameter last as the last node in the queue i.e. cache. 
 * The last argument must specify a page which is at tail of the queue i.e LRU page in cache. 
 * 
 * @param  last  node which is to be set as last node in the queue i.e. cache  
 * @return  No return value
 */
public void setLast(Node2 last) {
	this.last = last;
}
 
 /**
  * Returns a node inserted at the head of the queue i.e. linked list.
  * The x argument must specify a page. 
  * This method always returns the new node inserted into the cache.
  * 
  * @param  x  page number which is needed to be inserted into cache  
  * @return    new node inserted into the cache.
  */
 public Node2 insertFirst(int x)// Add node to front of queue if first reference
 {
  Node2 newNode=new Node2(x);
  newNode.next=null;
  if(isEmpty())
   setLast(newNode);
  else
   first.previous=newNode;
  newNode.next=first;
  first=newNode;
  ListSize++;
  return newNode;
 }
 
 
 /**
  * Returns the LRU page number which is deleted from the cache .i.e linked list
  * This method delete LRU data i.e. last node in the queue
  *   
  * @return      page number deleted from cache
  */
 public int deleteLast()
 {
  int t=getLast().data;
  if(first.next==null)
   first=null;
  else
   getLast().previous.next=null;
  setLast(getLast().previous);
  ListSize--;
  return t;
 }
}


/**
 * ListTest1 --- Reads the trace file and implements the LRU replacement Algorithm
 * @author OMKAR
 *
 */
class Lru1
{
	public static ArrayList<Integer> stratingblk = new ArrayList<Integer>(5);
	public static ArrayList<Integer> numofblks = new ArrayList<Integer>(5);
	public static int c;
	  
	  /**
	   * Reads the trace file i.e. argument filename and separates
	   * starting block number and number of blocks accessed
	   * @param filename a String which is name of the trace file
	   * @return No Return value
	   */
	public static void ReadData(String filename)
	{
		try{
		      Scanner reader = new Scanner(System.in);  // Reading from System.in
		   	  FileInputStream fstream = new FileInputStream(filename);
	          DataInputStream in = new DataInputStream(fstream);
	          BufferedReader br = new BufferedReader(new InputStreamReader(in));
	          String strLine;
	          
	          while ((strLine = br.readLine()) != null)   
			{
	        	String[] tokens = strLine.split(" ");
	        	stratingblk.add(Integer.parseInt(tokens[0]));//storing starting block number in arraylist
	        	numofblks.add(Integer.parseInt(tokens[1])); //storing number of blocks accessed in arraylist
			}
	          in.close();
	          reader.close();
		   	}catch (Exception e)
			{
		     System.err.println("Error: " + e.getMessage()); // Exception if error in file path or any other error
		     System.out.println("file not read");
			 }
	}
	
public static void main(String args[])throws IOException
 {
	 int miss =0;
	 int hit =0;
  
  DataInputStream inp=new DataInputStream(System.in);
 
  DoublyLinkList2 l=new DoublyLinkList2();// Queue as Doubly linked list is formed
  
  String c1=System.getProperty("size"); // command Line argument cache size
  c=Integer.parseInt(c1);
  
  String filename = System.getProperty("file"); // command Line argument trace file path
  
  ReadData(filename); //Read data from the trace file
  
  /**
   * hash table to hold the nodes of the queue.
   * searching for a node using page number becomes fast
   * key = page number :: value = node of queue associated with that page number
   */
  HashMap <Integer , Node2 > myMap = new HashMap <Integer , Node2>(); 
  
  for (int j=0; j < (stratingblk.size()) ;j++){
	  int startpage=stratingblk.get(j);
	  for (int i=0; i<numofblks.get(j);i++){
			 int page = (startpage + i);
			 /**
			  * LRU replacement Algorithm is implemented here
			  */
			 Boolean dec = myMap.containsKey(page); // check if page is present in cache
			 if(dec) // if cache hit
			 {
				 Node2 test = myMap.get(page);
				if(test==l.last)
				{
					 l.setLast(l.getLast().previous);
					 l.getLast().next=null;
					 DoublyLinkList2.ListSize--;
					 myMap.remove(page);
					 l.insertFirst(page);
					 myMap.put(page, l.first);
				 }
				 else if(test!=null && test!=l.first)
				 {
					 myMap.get(page).previous.next=myMap.get(page).next;
					 myMap.get(page).next.previous=myMap.get(page).previous;
					 DoublyLinkList2.ListSize--;
					 myMap.remove(page);
					 l.insertFirst(page);
					 myMap.put(page, l.first);
				 }
				hit++;
			 }
			 else // cache miss
			 {
				 if(DoublyLinkList2.ListSize < c)
				 {
					 Node2 testNode = l.insertFirst(page);
					 myMap.put(page, testNode );
				 }
				 else 
				 {
					 myMap.remove(l.getLast().GetData());
					 l.deleteLast();
					 Node2 testNode = l.insertFirst(page);
					 myMap.put(page, testNode );
				 }
				 miss++;
			 }
		  }
  }
  
  double ratio = (double) hit/(hit+miss); // hit ratio calculation
  ratio *= 100;
  double ratio1 = Math.round(ratio * 100.0)/100.0; //rounding off the hit ratio to 2 decimal digits
  System.out.println("\ncache size: " + c + " trace file: " + filename);
  System.out.println("\nLRU hit ratio:" + ratio + " is rounded off to : " + ratio1);
 }
}

