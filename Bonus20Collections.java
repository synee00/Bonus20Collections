import java.text.DecimalFormat;
import java.util.*;

/*Jasmine S. Allen
 * July 25, 2018
 * Program: Display inventory of at least 8 item names and prices.
 * 			Ask the user to enter an item name: 
 * 				-if that item exists, display that item and price and add that item 
 * 				and its price to the user's order.
 * 				-if that item doesn't exist, display an error and re-prompt the user.
 * 				 (Display the menu again if you'd like)
 * 			Ask if they want to add another. Repeat if they do. 
 * 			(User can enter an item more than once; we're not taking quantity at this point.) 
 * 			When they're done adding items, display a list of all items ordered with 
 * 			prices in columns.
 * 			Display the average price of items ordered.
 * 			
 * 			
 * Build Specs: 1. Use a hashtable to keep track of the menu of items. You can code it 
 * 					with literals.
 * 				2. Use parallel ArrayLists (one of strings, one of doubles) to store 
 * 					the items ordered and their prices.
 * 				3. Write 3 methods to find 1) the average of the items ordered and 2) the
 * 					indexes of the highest and 3) lowest cost of items.
 * 					 
 * 					
 * 	
 *  ***With extended challenges? No
 */
public class Bonus20Collections {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> shoppingCart = new ArrayList<String>();
		ArrayList<Double> cartPrices = new ArrayList<Double>();

		Scanner scan = new Scanner(System.in);
		HashMap<String, Double> inventory = new HashMap<>();
		//fill inventory
		inventory = fillInventory(inventory);
		
		System.out.println("Welcome to Rose Apothecary!");
		System.out.println();
		
		displayInventory(inventory);
		
		
		
		String choice;
		int count = 0;
		do {
			if(count > 0)
				displayInventory(inventory);
			//Start shopping
			System.out.println();
			String newItem = addItem(scan, inventory, "What would you like to order? ", shoppingCart, cartPrices);
			shoppingCart.add(newItem);
			cartPrices.add(inventory.get(newItem));
			
			
			//"Add another item? (y/n)"
			System.out.println("Add another item? (y/n) ");
			choice = scan.nextLine();
			choice = choice.toLowerCase();
			count++;
			//if yes, then repeat to the top
		} while (choice.startsWith("y"));
		
			//if no, display:
		
		System.out.println();
		System.out.println();
		System.out.println("Thank you for shopping with us! Now displaying your cart...");
		System.out.println();
		//System.out.println(shoppingCart);
		displayCart(shoppingCart, cartPrices, inventory);
	}
	
	//method to fill inventory
	public static HashMap<String, Double> fillInventory(HashMap<String, Double> inventory) {
		inventory.put("apple", 0.69);
		inventory.put("pear", 0.79);
		inventory.put("broccoli", 0.99);
		inventory.put("water bottle", 2.01);
		inventory.put("chamolmile tea", 1.69);
		inventory.put("fancy cheese", 3.99);
		inventory.put("your thoughts", 0.01);
		inventory.put("friends", 3.99);
		
		return inventory;
		
	}
	//method to display inventory
	public static void displayInventory(HashMap<String, Double> items) {
		String headerText = String.format("%-20s %s", "Items", "Price");
		String boarder = "=============================";
		
		System.out.println(headerText);
		System.out.println(boarder);
		
		int count = 1;
		for(String item : items.keySet())
		{
			System.out.printf("%-20s  %-20s ",item, items.get(item));
			System.out.println();
			count++;
		}
		
	}
	//method to display cart
	public static void displayCart(ArrayList<String> cartItem, ArrayList<Double> itemPrice, HashMap<String, Double> inventory) {
		
		System.out.println("Here's what you got: ");
		System.out.println();
		
		if(cartItem.isEmpty() && itemPrice == null)
		{
			System.out.println("You have not bought anything!!");
		}
		else
		{
			double total = 0;
	
			for (int i = 0; i < cartItem.size(); i++) { 		      
		          System.out.printf("%-15s  %s%s", cartItem.get(i), "$", itemPrice.get(i)); 
		          System.out.println();
		          total += itemPrice.get(i);
		      }   		
	
			//two decimal places only
			DecimalFormat myFormatter = new DecimalFormat("##.##");
		    String output = myFormatter.format(total);
			
		    System.out.println();
			System.out.println("Your total is $" + output);
			average(cartItem, inventory);
			highest(cartItem, inventory);
			lowest(cartItem, inventory);
		}

	}
	//method to add item to cart
	private static String addItem(Scanner scan, HashMap<String, Double> inventory, String prompt, 
			ArrayList<String> cartItem, ArrayList<Double> itemPrice) {
		System.out.println(prompt);
		
		String input = scan.nextLine();
		
		//if userInput is in our inventory, then return userInput
				if (inventory.containsKey(input) == true) {
					System.out.println("Thank you! " + input + " added to cart at $" + inventory.get(input) + "!");					
					
					//else, repeat the method via recursion
				} else {
					System.out.println("Sorry, we don't have " + input + "! :(");
					addItem(scan, inventory, prompt, cartItem, itemPrice);
				}
				
				
				return input;
	}
	
	//method to calculate average
	//method to get max and min prices
	private static void average(ArrayList<String> shoppingCart, HashMap<String, Double> inventory) {
		double sum = 0;//the total price, added together
		int count = 0; //the number of individual items
		double avg = 0.0;
		for ( String fruit : shoppingCart ) {
			
			//sum get from the inventory,
			//the "value" of the "key" in this case fruit!
			sum += inventory.get(fruit);
			count++;
			
		}
		avg = sum/count;
		//two decimal places only
		DecimalFormat myFormatter = new DecimalFormat("##.##");
	    String output = myFormatter.format(avg);
	    
		System.out.println();
		System.out.println("Average item price: $" + output);
	}


	private static void lowest(ArrayList<String> shoppingCart, HashMap<String, Double> inventory) {
		double lowest = 3.99;
		String cheap = "";
		
		for ( String item : shoppingCart) {
			
			if (lowest > inventory.get(item)   ) {
				lowest = inventory.get(item);
				cheap = item;
			}
		}
		//two decimal places only
		DecimalFormat myFormatter = new DecimalFormat("##.##");
	    String output = myFormatter.format(lowest);
	    
		System.out.println("The cheapest item was " + cheap + " for $" + output + ".");
		
	}


	private static void highest(ArrayList<String> shoppingCart, HashMap<String, Double> inventory) {
		double highest = 0.0;
		String expensive = "";
		for ( String item : shoppingCart) {
			
			if (highest < inventory.get(item)   ) {
				highest = inventory.get(item);
				expensive = item;
			}
		}
		//two decimal places only
		DecimalFormat myFormatter = new DecimalFormat("##.##");
	    String output = myFormatter.format(highest);
	    
		System.out.println("The most expensive item was " + expensive + " for $" + output + ".");
		
	}

}
