package siesgst.edu.in.eventID.model;

/**
 * Created by rohitramaswamy on 28/07/17.
 */

public class MessagesModel
{
	String title, description;
	
	public MessagesModel(String title, String description)
	{
		this.title = title;
		this.description = description;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
}
