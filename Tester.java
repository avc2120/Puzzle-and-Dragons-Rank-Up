import java.util.ArrayList;
import java.util.Scanner;
public class Tester {
	public static void main(String[] args)
	{
		boolean again = true;
		String answer;
		while(again)
		{
			boolean again1 = true;
			Scanner scan = new Scanner(System.in);
			Dungeons Game = new Dungeons();
			System.out.println("Use all dungeons? y or n");
			answer = scan.nextLine();
			while(again1)
			{
				if(answer.equals("n"))
				{
					System.out.println("Which Normal dungeon are you up to?");
					answer = scan.nextLine();
					if (Game.addAll(answer, "normal.txt") == false)
					{
						System.out.println("Dungeon Not Found");
						break;
					}
					System.out.println("Which Technical dungeon are you up to?");
					answer = scan.nextLine();
					if(Game.addAll(answer, "technical.txt") == false)
					{
						System.out.println("Dungeon Not Found");
						break;
					}
					Tester.runGame(Game);
					again1 = false;
				}
				else
				{
					Game.addAll("Tower Entrance", "normal.txt");
					Game.addAll("Tower Entrance", "technical.txt");
					Tester.runGame(Game);
					again1 = false;
				}
			}
			System.out.println("Play Again? y or n");
			boolean askAgain = true;
			while(askAgain)
			{
				answer = scan.nextLine();
				if(answer.equals("n"))
				{
					again = false;
					askAgain = false;
				}
				else if(answer.equals("y"))
				{
					askAgain = false;
				}
			}
		}
	}
	public static void runGame(Dungeons Game)
	{
		Scanner scan = new Scanner(System.in);
		ArrayList<Integer> used = new ArrayList<Integer>();
		System.out.println("Enter Stamina:");
		int stamina = scan.nextInt();
		System.out.println("Enter Exp:");
		int exp = scan.nextInt();
		Game.setStamina(stamina);
		Game.eliminate(stamina);
		System.out.println("--------------------------------------------------------------");
		for(int i = 2; i <=stamina; i++)
		{
			Game.dynamic(i);
			if(Game.getTotalExp() >= exp && !used.contains(Game.getTotalExp()))
			{
				Game.printAll();
				System.out.println("\nTotal Exp Gained: " + Game.getTotalExp());
				used.add(Game.getTotalExp());
				System.out.println("Total Stamina Used: " + Game.getTotalStamina());
				System.out.println("Stamina Left: " + (stamina - Game.getTotalStamina()) 
						+ "\n--------------------------------------------------------------");
			}
		}
	}
}
