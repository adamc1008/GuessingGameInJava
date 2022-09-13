import java.util.*;
import java.io.*;

public class GussingGame extends BinaryTree<String>
{
	static String ans = "";
	static String guess = "";
	static String question = "";
	static boolean play = true;
	static BinaryTree<String> tree = new BinaryTree<String>(); //initialise the tree
	static Scanner scan = new Scanner(System.in);
	static String dir = System.getProperty("user.dir"); //finds user directory
	static String filename = dir + "//guessing.txt";
	
	public static void main(String[] args)
	{
		if(tree.isEmpty()) //creates initial tree if the tree is empty
		{
			createTree(tree);
		}
		
		while (play) //while the player wishes to keep playing play is set to true
		{
			System.out.println("If you would like to load a save game enter Yes.");
			ans = scan.nextLine();
			if(ans.toLowerCase().equals("yes"))
			{
				load(); //load saved binary tree
			}
			BinaryNodeInterface<String> currentNode = tree.getRootNode();
			while (! currentNode.isLeaf()) //keep asking questions until a leaf node is reached
			{
					System.out.println(currentNode.getData());
					System.out.println("Enter Yes or No");
					ans = scan.nextLine();
					if(ans.toLowerCase().equals("yes"))//currentNode = leftChild if ans is yes
					{
						currentNode = currentNode.getLeftChild();
					}
					else if(ans.toLowerCase().equals("no"))//currentNode = rightChild if ans is no
					{
						currentNode=currentNode.getRightChild();
					}
					else //ensures user enters valid answers
					{
						System.out.println("Invalid input. Please enter Yes or No!");
					}
			}
			
			System.out.printf("Are you thinking of %s\n", currentNode.getData());
			ans=scan.nextLine();
			if(ans.toLowerCase().equals("yes"))//if answer is correct
			{
				System.out.println("Yay :)");
			}
			else if(ans.toLowerCase().equals("no"))//if answer is incorrect
			{
				System.out.println("Oh no. Please enter the correct answer so that I can get it right next time :(");
				guess = scan.nextLine();
				System.out.println("Please enter a distinguishing question");
				question = scan.nextLine();
				String oldAns = currentNode.getData();
				currentNode.setData(question);
				currentNode.setRightChild(new BinaryNode<String>(oldAns));
				currentNode.setLeftChild(new BinaryNode<String>(guess));
			}

			System.out.println("Please choose an option"); //player options
			System.out.println("Press 1 to play again");
			System.out.println("Press 2 to save the game");
			System.out.println("Press 3 to load a saved game.");
			System.out.println("Press 4 to quit.");
			ans = scan.nextLine();
			if(ans.equals("1"))
			{
				play = true;
			}
			else if(ans.equals("2"))
			{
				save(tree);
			}
			else if(ans.equals("3"))
			{
				load();
			}
			else if(ans.equals("4"))
			{
				System.out.println("Ok. Have a nice day.");
				play = false;
			}
			else
			{
				System.out.println("Please enter a valid answer.");
			}
			}
	}
	
	public static void createTree(BinaryTree<String> tree) //initial tree is created
	{
		BinaryTree<String> cat = new BinaryTree<String>();
		cat.setTree("Cat");
		BinaryTree<String> dog = new BinaryTree<String>("dog");
		BinaryTree<String> france = new BinaryTree<String>("france");
		BinaryTree<String> kettle = new BinaryTree<String>("kettle");
		
		BinaryTree<String> bTree = new BinaryTree<String>("Does it meow?", cat, dog);
		BinaryTree<String> cTree = new BinaryTree<String>("Is it a country?", france, kettle);
		
		tree.setTree("Is it an animal?", bTree, cTree);
	}
	
	public static void save(BinaryTree<String> tree)
	{
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			BinaryNodeInterface<String> currNode = tree.getRootNode();
			Queue<BinaryNodeInterface<String>> queue = new LinkedList<BinaryNodeInterface<String>>();
			queue.add(currNode);
			int height = currNode.getHeight();
			while(height>0 && !queue.isEmpty())// level order traversal is used 
			{
				currNode = queue.remove();
				if(currNode!=null)
				{
				writer.write(currNode.getData()+"\n");
				height = currNode.getHeight();
				queue.add(currNode.getLeftChild());
				queue.add(currNode.getRightChild());
				}
				else if(currNode==null)
				{
					writer.write("X\n");
				}
				System.out.println(queue);
			}
			writer.close();
		}
		catch (IOException e) {}
	}
	
	public static void load() //upload tree saved from file
	{
		System.out.println();
		int num=0;
		int j=0;
		String line = "";
		ArrayList<BinaryNodeInterface<String>> nodes = new ArrayList<BinaryNodeInterface<String>>();
		try {
			System.out.println("Hello1");
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			line = reader.readLine();
			System.out.println(line);
			while(line != null)
			{
				nodes.add(new BinaryNode<String>(line));
				line = reader.readLine();
			}
			reader.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		tree.setRootNode(nodes.get(num));
		BinaryNodeInterface<String> currNode = nodes.get(num);
		currNode.setLeftChild(nodes.get(1));
		currNode.setRightChild(nodes.get(2));
		
		for(int i=1;i<nodes.size()/2;i++)
		{
			currNode = nodes.get(i);
			if(!nodes.get((i*2)+1).getData().equals("X"))
			{
				currNode.setLeftChild(nodes.get((i*2)+1));
			}
			
			if(!nodes.get((i*2)+2).getData().equals("X"))
			{
				currNode.setRightChild(nodes.get((i*2)+2));
			}
		}
	}
}
