import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
public class Dungeons {
	private ArrayList<Dungeon> myDungeons;
	private Scanner scan;
	private String[] line;
	private String dungType;
	public Dungeons()
	{
		myDungeons = new ArrayList<Dungeon>();
	}

	public void addAll() //Adds all Dungeons
	{
		try
		{
			scan = new Scanner(new File("data.txt")); 
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		while (scan.hasNextLine())
		{
			line = scan.nextLine().split("\t");
			if(line.length == 1)
				dungType = line[0];
			else
				myDungeons.add(new Dungeon(line[0], line[1], line[4], dungType));

		}
	}
	public void printAll() //Prints all Dungeons
	{
		Collections.sort(myDungeons);
		for(int i = 0; i < myDungeons.size(); i++)
		{
			System.out.println(myDungeons.get(i).toString());
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
		for(int i = 0; i < stamina; i++)
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
		/*System.out.print("Available Dungeons: \n");
		this.printAll();
		System.out.println();*/
	}


	public void dynamic(int stamina) //Uses Dynamic Programming to calculate optimal Dungeons
	{
		ArrayList<Integer> exp = new ArrayList<Integer>();
		ArrayList<Integer> stam = new ArrayList<Integer>();
		for(int i = 0; i < stamina; i++)
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
		//System.out.println(r3);
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
		myDungeons = r2;
		this.printAll();
	}


	public int getTotalExp() //Returns total Exp in available Dungeons
	{
		int count = 0;
		for(Dungeon dung: myDungeons)
		{
			count += dung.getExp();
		}
		return count;
	}

	public int getTotalStamina() //Returns total Stamina in available Dungeons
	{
		int count = 0;
		for(Dungeon dung: myDungeons)
		{
			count += dung.getStamina();
		}
		return count;
	}
}

