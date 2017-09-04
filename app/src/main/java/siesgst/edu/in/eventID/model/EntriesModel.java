package siesgst.edu.in.eventID.model;

/**
 * Created by rohitramaswamy on 23/07/17.
 */

public class EntriesModel
{
	private String name;
	private String event_status;
	private int status;
	private String prn;
	private int id, user_id, cost;
	
	public EntriesModel(String name, String prn, int status)
	{
		this.name = name;
		this.status = status;
		this.prn = prn;
	}
	
	public EntriesModel(String name, String prn)
	{
		this.name = name;
		this.prn = prn;
	}
	
	public EntriesModel(String name, String event_status, int id, int user_id, int cost)
	{
		
		this.name = name;
		this.event_status = event_status;
		this.id = id;
		this.user_id = user_id;
		this.cost = cost;
	}
	
	public int getStatus()
	{
		return status;
	}
	
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	public String getPrn()
	{
		
		return prn;
	}
	
	public void setPrn(String prn)
	{
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
	
	public String getEvent_status()
	{
		return event_status;
	}
	
	public void setEvent_status(String event_status)
	{
		this.event_status = event_status;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getUser_id()
	{
		return user_id;
	}
	
	public void setUser_id(int user_id)
	{
		this.user_id = user_id;
	}
	
	public int getCost()
	{
		return cost;
	}
	
	public void setCost(int cost)
	{
		this.cost = cost;
	}
}
