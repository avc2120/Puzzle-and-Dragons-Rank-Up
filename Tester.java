import java.util.Scanner;
public class Tester {
	public static void main(String[] args)
	{
		boolean again = true;
		while(again)
		{
			Scanner scan = new Scanner(System.in);
			Dungeons Game = new Dungeons();
			Game.addAll();
			System.out.println("Enter Exp:");
			int exp = scan.nextInt();
			System.out.println("Enter Stamina:");
			int stamina = scan.nextInt();
			Game.setStamina(stamina);
			Game.eliminate(stamina);
			Game.dynamic(stamina);
			System.out.println("\nTotal Exp Gained: " + Game.getTotalExp());
			System.out.println("Total Stamina Used: " + Game.getTotalStamina());
			System.out.println("Stamina Left: " + (stamina - Game.getTotalStamina()));
			System.out.println("\nPlay Again? y or n");
			boolean askAgain = true;
			while(askAgain)
			{
				String answer = scan.nextLine();
				if(answer.equals("n"))
				{
					again = false;
					askAgain = false;
				}
				else if(answer.equals("y"))
				{
					askAgain = false;
				}
				else
				{
					System.out.println("Invalid Input");
				}
					
			}
		}
	}
}
