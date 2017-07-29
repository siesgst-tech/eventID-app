package siesgst.edu.in.eventID.utils;

/**
 * Created by rohitramaswamy on 30/07/17.
 */

public class Constants
{
	// this class will be used to store all keys and important keys used throughout the application
	
	// Table names here
	
	public static final String EVENTS_TABLE_NAME = "events";
	public static final String ENTRIES_TABLE_NAME = "entries";
	public static final String MESSAGES_TABLE_NAME = "messages";
	
	
	// column names go here
	
	// entries
	public static final String ENTRIES_ID = "id";
	public static final String ENTRIES_USER_ID = "user_id";
	public static final String ENTRIES_EVENT_ID = "event_id";
	public static final String ENTRIES_COST = "cost";
	public static final String ENTRIES_STATUS = "status";
	
	
	// events
	public static final String EVENT_ID = "id";
	public static final String EVENT_NAME = "name";
	public static final String EVENT_DESCRIPTION = "description";
	public static final String EVENT_RULES = "rules";
	public static final String EVENT_GAMEPLAY = "gameplay";
	public static final String EVENT_HEAD = "event_head";
	public static final String EVENT_ABOUT = "about";
	public static final String EVENT_COST = "cost";
	
	// messages
	public static final String MESSAGE_TITLE = "title";
	public static final String MESSAGE_MESSAGE = "message";
	
	
	
	
}
