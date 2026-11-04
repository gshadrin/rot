package task2;

import java.util.Hashtable;


public class Solver 
{
	public Hashtable<String, Double> solve(double a, double b, double c) throws Exception
	{
		Hashtable<String, Double> result = new Hashtable<String, Double>();
		if (a==0 && b == 0)
		{
			throw new Exception("Incorrect args");
		}
		if (a == 0)
		{
			result.put("val1", -c / b);
		}
		else
		{
			double d = b * b - 4 * a * c;
			if (d < 0)
			{
				throw new Exception("Discriminant < 0");
			}
			result.put("val1", (- b + Math.sqrt(d)) / (2 * a));
			result.put("val2", (- b - Math.sqrt(d)) / (2 * a));
		}
		return result;
	}
}
