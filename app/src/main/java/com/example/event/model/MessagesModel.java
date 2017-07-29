package com.example.event.model;

/**
 * Created by rohitramaswamy on 28/07/17.
 */

public class MessagesModel
{
	String event_name,title,description;
	
	public MessagesModel(String event_name, String title, String description)
	{
		this.event_name = event_name;
		this.title = title;
		this.description = description;
	}
	
	public String getEvent_name()
	{
		return event_name;
	}
	
	public void setEvent_name(String event_name)
	{
		this.event_name = event_name;
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
