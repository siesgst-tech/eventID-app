package siesgst.edu.in.eventID.model;

/**
 * Created by rohitramaswamy on 23/07/17.
 */

public class EntriesModel
{
	private String name, uid, id, email, contact;
	
	private String receipt_number, paid;
	
	private String cost;
	
	private int status;
	private String prn;
	
	public EntriesModel(String name, String prn, int status)
	{
		this.name = name;
		this.status = status;
		this.prn = prn;
	}
	
	public EntriesModel(String name, String uid, String id, String receipt_number, String email, String contact, String paid)
	{
		this.name = name;
		this.uid = uid;
		this.id = id;
		this.email = email;
		this.contact = contact;
		this.receipt_number = receipt_number;
		this.paid = paid;
	}
	
	public EntriesModel(String name, String uid, String id, String email, String contact)
	{
		this.name = name;
		this.uid = uid;
		this.id = id;
		this.email = email;
		this.contact = contact;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getUid()
	{
		return uid;
	}
	
	public void setUid(String uid)
	{
		this.uid = uid;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getContact()
	{
		return contact;
	}
	
	public void setContact(String contact)
	{
		this.contact = contact;
	}
	
	public String getReceipt_number()
	{
		return receipt_number;
	}
	
	public void setReceipt_number(String receipt_number)
	{
		this.receipt_number = receipt_number;
	}
	
	public String getPaid()
	{
		return paid;
	}
	
	public void setPaid(String paid)
	{
		this.paid = paid;
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
	
	public String getCost()
	{
		return cost;
	}
	
	public void setCost(String cost)
	{
		this.cost = cost;
	}
}