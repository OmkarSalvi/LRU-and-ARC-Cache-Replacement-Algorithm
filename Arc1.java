import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Node01 --- To represent the pages in the cache as nodes of the Doubly Linked List
 * @author   OMKAR SALVI
 *
 */
class Node01 // to represent the pages
{
 public int data;
 public Node01 next;
 public Node01 previous;
 
 /**
  * constructor which initializes the node with argument passed
  * @param x page number to be assigned
  */
 public Node01(int x)
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
 * DoublyLinkList01 --- Implements the queue using Doubly Linked list
 * node is inserted at the front
 * node is removed from the tail
 * @author OMKAR
 *
 */
class DoublyLinkList01 // to track the pages in cache
{
 public Node01 first;
 public Node01 last;
 public int ListSize;
 
 /**
  * constructor to initialize an empty queue
  */
 public DoublyLinkList01()
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
 public boolean isEmpty()
 {
  return(first==null);
 }
 
 /**
  * Returns LRU node in the cache .i.e queue
  *   
  * @return   node at the tail of queue
  */
public Node01 getLast() 
{
	return last;
}

/**
 * Assigns the parameter last as the last node in the queue i.e. cache. 
 * The last argument must specify a page which is at tail of the queue i.e LRU page in cache. 
 * 
 * @param  last  node which is to be set as last node in the queue i.e. cache  
 * @return  No return value
 */
public void setLast(Node01 last) 
{
	this.last = last;
}
 
/**
 * Inserts the new node to the front of the queue i.e. Enqueue operation
 * @param map Hash table for the queue
 * @param x integer which is the page number which is being accessed
 * @return The new node which has been added to the queue
 */
 public Node01 insertFirst( HashMap <Integer , Node01 > map, int x)
 {
  Node01 newNode=new Node01(x);
  newNode.next=null;
  newNode.previous=null;
  if(isEmpty())
   setLast(newNode);
  else{
   first.previous=newNode;
   map.put(first.data, first);
  }
  newNode.next=first;
  first=newNode;
  ListSize++;
  map.put(x, first);
  return newNode;
 }
 
/**
 * Removes any given node from the queue i.e. Dequeue operation
 * @param map hash table for the queue
 * @param key integer which is the page number needed to be removed from queue
 * @return No Return Value
 */
 public void deleteAny( HashMap <Integer , Node01 > map, int key)
 {
	 if (map.get(key).previous != null) {
			map.get(key).previous.next = map.get(key).next;
		} else {
			first = map.get(key).next;
		}
		if (map.get(key).next != null) {
			map.get(key).next.previous = map.get(key).previous;
		} else {
			last = map.get(key).previous;
		}
		map.remove(key);
		ListSize--;
 }

}

/**
 * Arc1 ---  Reads the trace file and implements the ARC replacement Algorithm
 * @author OMKAR
 *
 */
class Arc1
{
	 public static DoublyLinkList01 t1=new DoublyLinkList01();//cache queue T1
	 public static DoublyLinkList01 t2=new DoublyLinkList01();//cache queue T2
	 public static DoublyLinkList01 b1=new DoublyLinkList01();//queue B1
	 public static DoublyLinkList01 b2=new DoublyLinkList01();//queue B2
	 
	 public static HashMap <Integer , Node01 > myMap_t1 = new HashMap <Integer , Node01>();//hash table for T1
	 public static HashMap <Integer , Node01 > myMap_t2 = new HashMap <Integer , Node01>();//hash table for T2
	 public static HashMap <Integer , Node01 > myMap_b1 = new HashMap <Integer , Node01>();//hash table for B1
	 public static HashMap <Integer , Node01 > myMap_b2 = new HashMap <Integer , Node01>();//hash table for B2
	
	 public static ArrayList<Integer> stratingblk = new ArrayList<Integer>(5);// arraylist to store starting block or page number
	 public static ArrayList<Integer> numofblks = new ArrayList<Integer>(5); // arraylist to store number of blocks or pages accessed
	 public static int c;
	  
	/**
	* Reads the trace file i.e. argument filename and separates
	* starting block number and number of blocks accessed
	* @param filename a String which is name of the trace file
	* @return No Return value
	*/
	public static void ReadData(String filename){
		try{
		      Scanner reader = new Scanner(System.in);  // Reading from System.in
		   	  FileInputStream fstream = new FileInputStream(filename);
	          DataInputStream in = new DataInputStream(fstream);
	          BufferedReader br = new BufferedReader(new InputStreamReader(in));
	          String strLine;
	          
	          while ((strLine = br.readLine()) != null)   {
	        	  String[] tokens = strLine.split(" ");
	        	  stratingblk.add(Integer.parseInt(tokens[0]));
	        	  numofblks.add(Integer.parseInt(tokens[1]));
	          }
	          in.close();
	          reader.close();
		   	}catch (Exception e){
		     System.err.println("Error: " + e.getMessage());
		     System.out.println("file not read");
		   }
		
	}
	
	/**
	 * Removes the LRU page from either T1 or T2 cache
	 * and adds it to either B1 or B2 queue
	 * @param pg integer which is page number to be replaced
	 * @param p integer which is adaptive parameter representing the size of T1 queue
	 * @return No Return Value
	 */
	public static void replace(int pg, int p)
	{
		if((t1.ListSize!=0)&&((t1.ListSize>p)||((myMap_b2.containsKey(pg))&&(t1.ListSize==p)))){
			Node01 temp=t1.getLast();
			t1.deleteAny(myMap_t1, t1.getLast().data);//new
			b1.insertFirst(myMap_b1,temp.data);
		}else{
			Node01 temp=t2.getLast();
			t2.deleteAny(myMap_t2, t2.getLast().data);//new
			b2.insertFirst(myMap_b2,temp.data);
			
		}
	}
	
	/**
	 * Round off the double value to nearest integer
	 * @param d value of type double which needs to be rounded off
	 * @return integer rounded value of argument d
	 */
	public static int round (double d)
	{
	    double dAbs = Math.abs(d);
	    int i = (int) dAbs;
	    double result = dAbs - (double) i;
	    if(result<=0.5){
	        return d<0 ? -i : i;            
	    }else{
	        return d<0 ? -(i+1) : i+1;          
	    }
	}

public static void main(String args[])throws IOException
 {
	 double ARC_miss =0;
	 double ARC_hit =0;
	 int p=0;
	 int delta1,delta2;
	 double d1,d2;
	 
  DataInputStream inp=new DataInputStream(System.in);
  
  String c1=System.getProperty("size"); // command Line argument cache size
  c=Integer.parseInt(c1);

  String filename = System.getProperty("file");// command Line argument trace file path
  
  ReadData(filename); // function to read data from the file
  
  
  for (int j=0; j < (stratingblk.size()) ;j++){
	  int startpage=stratingblk.get(j);
	  for (int i=0; i<numofblks.get(j);i++){
			 int page = (startpage + i);// page number which is being accessed
			/**
			 * ARC Replacement Algorithm implemented here
			 */
			 if(myMap_t1.containsKey(page)|| myMap_t2.containsKey(page)) //CASE 1
			 {
				 if(myMap_t1.containsKey(page))
				 {
					 t1.deleteAny(myMap_t1,page);
				 }else
				 {
					 t2.deleteAny(myMap_t2,page);
				 }
				 t2.insertFirst(myMap_t2,page);
				 ARC_hit++;
			 }else if(myMap_b1.containsKey(page)) //CASE 2
			 {
				ARC_miss++;
				if(b1.ListSize>=b2.ListSize)
				{
					delta1=1;
				}else
				{
					d1= (double)(b2.ListSize)/(b1.ListSize);
					delta1 = round(d1);
				}
				p=Math.min(p+delta1,c);
				replace(page,p);
				b1.deleteAny(myMap_b1,page);
				t2.insertFirst(myMap_t2,page);
			 }else if(myMap_b2.containsKey(page)) //CASE 3
			 {
				 ARC_miss++;
				 if(b2.ListSize>=b1.ListSize)
				 {
						delta2=1;
				 }else
					{
						d2= (double)(b1.ListSize)/(b2.ListSize);
						delta2 = round(d2);
					}
					p=Math.max(p-delta2,0);
					replace(page,p);
					b2.deleteAny(myMap_b2,page);
					t2.insertFirst(myMap_t2,page);
			 }else // CASE 4
			 {
				ARC_miss++;
				if((t1.ListSize+b1.ListSize)==c)  // CASE A
				{
					if(t1.ListSize<c)
					{
						b1.deleteAny(myMap_b1, b1.getLast().data);
						replace(page,p);
					}else
					{
						t1.deleteAny(myMap_t1, t1.getLast().data);
					}
				}else if((t1.ListSize+b1.ListSize)<c) // CASE B
				{
					int total_size =(t1.ListSize+b1.ListSize+t2.ListSize+b2.ListSize);
					if(total_size >= c){
						if(total_size == (2*c))
						{
							b2.deleteAny(myMap_b2, b2.getLast().data);
						}
						replace(page,p);
					}
				}
				t1.insertFirst(myMap_t1,page);
			 }
			 
	  }
  }
			
  double ratio = ARC_hit/(ARC_hit+ARC_miss); // caculate the hit ratio
  ratio *= 100;
  double ratio1 = Math.round(ratio * 100.0)/100.0; //rounding off the hit ratio to 2 decimal digits
  System.out.println("\ncache size: " + c + " trace file: " + filename);
  System.out.println("\nARC hit ratio:" + ratio + " is rounded to : " + ratio1);
 }
}

