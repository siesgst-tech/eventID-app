package com.example.event.model;

/**
 * Created by rohitramaswamy on 23/07/17.
 */

public class EventsModel
{
	String eventName;
	String cost;
	
	public EventsModel(String eventName, String cost)
	{
		this.eventName = eventName;
		this.cost = cost;
	}
	
	public String getCost()
	{
		return cost;
	}
	
	public void setCost(String amount)
	{
		this.cost = amount;
	}
	
	public String getEventName()
	{
		return eventName;
	}
	
	public void setEventName(String eventName)
	{
		this.eventName = eventName;
	}
	
}