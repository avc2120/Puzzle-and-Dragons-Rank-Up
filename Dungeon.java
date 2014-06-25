
public class Dungeon implements Comparable<Dungeon>{
	private String name;
	private int stamina;
	private int exp;
	private String type;
	
	public Dungeon(String name, String stamina, String exp, String type)
	{
		this.name = name;
		this.stamina = Integer.parseInt(stamina);
		this.exp = Integer.parseInt(exp);
		this.type = type;
	}
	
	public int getStamina()
	{
		return stamina;
	}
	
	public int getExp()
	{
		return exp;
	}
	
	public double getExpPerStamina()
	{
		return ((double)exp)/stamina;
	}
	
	public String toString()
	{
			return type + ": " + name + "\t\t" + stamina + "\t" + exp;
	}
	
	public int compareTo(Dungeon other)
	{
		if(this.getExpPerStamina() < other.getExpPerStamina())
		{
			return 1;
		}
		else if (this.getExpPerStamina() > other.getExpPerStamina())
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
}