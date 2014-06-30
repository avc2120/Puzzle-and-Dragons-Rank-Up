import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class Dungeons {
	private ArrayList<Dungeon> myDungeons;
	private ArrayList<Dungeon> finalDungeons;
	private ArrayList<String> possibleDungeons;
	private Scanner scan;
	private String[] line;
	private String dungType;
	private int count;
	public Dungeons()
	{
		myDungeons = new ArrayList<Dungeon>();
		finalDungeons = new ArrayList<Dungeon>();
	}

	public boolean addAll(String name, String filename) //Adds all Dungeons
	{
		count = 0;
		possibleDungeons = new ArrayList<String>();
		boolean add = false;
		boolean found = false;
		try
		{
			scan = new Scanner(new File(filename)); 
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		while (scan.hasNextLine())
		{
			line = scan.nextLine().split("\t");
			if(line[0].toLowerCase().contains(name.toLowerCase()) == true)
			{
				add = true;
				if(!line[0].toLowerCase().equals(name.toLowerCase()))
				{
					found = true;
					System.out.println("Did you mean: " + "[" + count + "] "+ line[0]);
					possibleDungeons.add(line[0]);
					count++;
				}				
			}
			if(line.length == 1)
				dungType = line[0];
			else if (line.length > 1 && add == true)
				myDungeons.add(new Dungeon(line[0], line[1], line[4], dungType));
		}
		if(possibleDungeons.size() > 0)
		{
			System.out.println("Select correct dungeon from above");
			Scanner scan = new Scanner(System.in);
			int answer = scan.nextInt();
			myDungeons.clear();
			addAll(line[answer], filename);
		}
		return found;
	}
	public void printAll() //Prints all Dungeons
	{
		Collections.sort(finalDungeons);
		for(int i = 0; i < finalDungeons.size(); i++)
		{
			System.out.println(finalDungeons.get(i).toString());
		}
	}

	public void setStamina(int stamina) //Eliminates all Dungeons that require more stamina
	{
		for(int i = myDungeons.size()-1; i >=0; i--)
		{
			if(myDungeons.get(i).getStamina()>stamina)
			{
				myDungeons.remove(myDungeons.get(i));
			}
		}
	}

	public void setExp(double exp) //Eliminates all Dungeons that have a lower ExpPerStamina
	{
		for(int i = myDungeons.size()-1; i >=0; i--)
		{
			if(myDungeons.get(i).getExpPerStamina()< exp)
			{
				myDungeons.remove(myDungeons.get(i));
			}
		}
	}

	public void eliminate(int stamina) //Eliminates all dungeons of same stamina with lower exp
	{
		ArrayList<Dungeon> finalList = new ArrayList<Dungeon>();
		for(int i = 1; i <=stamina; i++)
		{
			ArrayList<Dungeon> result = new ArrayList<Dungeon>();
			for(int j = 0; j < myDungeons.size(); j++)
			{
				if(myDungeons.get(j).getStamina() == i)
				{
					result.add(myDungeons.get(j));
				}
			}
			Collections.sort(result);
			if(result.size()>0)
			{
				finalList.add(result.get(0));
			}
		}
		for(int k = 0; k < finalList.size(); k++)
		{
			finalList.get(k).toString();
		}
		myDungeons = finalList;
	}


	public void dynamic(int stamina) //Uses Dynamic Programming to calculate optimal Dungeons
	{
		ArrayList<Integer> exp = new ArrayList<Integer>();
		ArrayList<Integer> stam = new ArrayList<Integer>();
		exp.add(0);
		stam.add(0);
		for(int i = 1; i <= stamina; i++)
		{
			boolean write = false;
			for(int j = 0; j < myDungeons.size(); j++)
			{
				if(myDungeons.get(j).getStamina()==i)
				{
					exp.add(myDungeons.get(j).getExp());
					stam.add(myDungeons.get(j).getStamina());
					write = true;
				}
			}
			if (write == false)
			{
				exp.add(0);
				stam.add(0);
			}
		}
		optimize(exp, stam);
	}
	public void optimize(ArrayList<Integer> exp, ArrayList<Integer> stamina)
	{
		ArrayList<Integer> r = new ArrayList<Integer>();
		String results = null;
		ArrayList<String[]> r1 = new ArrayList<String[]>();
		ArrayList<Dungeon> r2 = new ArrayList<Dungeon>();
		ArrayList<String> r3 = new ArrayList<String>();
		for(int i: exp)
		{
			r3.add("0");
			r.add(0);
		}
		for (int i = 0; i < exp.size(); i++)
		{
			int q = 0;
			String q1 = "0 0 0";
			for (int j = 0; j <= i; j++ )
			{
				if(q < (exp.get(j) + r.get(i-j)))
				{
					q = exp.get(j) + r.get(i-j);
					q1 = exp.get(j).toString() + " " + r3.get(i-j).toString();
					if(exp.get(j)!= 0 && r.get(i-j)!=0)
					{
						results = exp.get(j).toString() + " " + r.get(i-j).toString() + " " + ((Integer)q).toString() + "\n";
						r1.add(results.split(" "));
					}
				}
			}
			r.set(i,q);
			r3.set(i,q1);
		}
		String[] last = r3.get(r3.size()-1).split(" ");
		for(int k = 0; k < myDungeons.size(); k++)
		{
			for(int j = 0; j < last.length; j++)
			{
				if(Integer.parseInt(last[j]) == myDungeons.get(k).getExp())
				{
					r2.add(myDungeons.get(k));
				}
			}
		}
		finalDungeons = r2;
	}


	public int getTotalExp() //Returns total Exp in available Dungeons
	{
		int count = 0;
		for(Dungeon dung: finalDungeons)
		{
			count += dung.getExp();
		}
		return count;
	}

	public int getTotalStamina() //Returns total Stamina in available Dungeons
	{
		int count = 0;
		for(Dungeon dung: finalDungeons)
		{
			count += dung.getStamina();
		}
		return count;
	}
}

