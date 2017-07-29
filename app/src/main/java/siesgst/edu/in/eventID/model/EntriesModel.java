package siesgst.edu.in.eventID.model;

/**
 * Created by rohitramaswamy on 23/07/17.
 */

public class EntriesModel
{
	String name, prn;
	
	public EntriesModel(String name, String prn)
	{
		this.name = name;
		this.prn = prn;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getPrn()
	{
		return prn;
	}
	
	public void setPrn(String prn)
	{
		this.prn = prn;
	}
}
