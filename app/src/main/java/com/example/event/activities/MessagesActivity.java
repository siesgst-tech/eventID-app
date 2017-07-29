package com.example.event.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.event.R;
import com.example.event.adapters.MessagesAdapter;
import com.example.event.model.MessagesModel;
import com.example.event.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity
{
	SessionManager sessionManager;
	AlertDialog alert;
	RecyclerView recyclerView;
	List<MessagesModel> messages = new ArrayList<MessagesModel>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messages);
		sessionManager = new SessionManager(MessagesActivity.this);
		if(getSupportActionBar()!=null)
		{
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSupportActionBar().setDisplayShowHomeEnabled(true);
			getSupportActionBar().setTitle("Messages");
		}
		recyclerView = (RecyclerView) findViewById(R.id.messages_recycler);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		init();
		recyclerView.setAdapter(new MessagesAdapter(messages));
	}
	
	public void init()
	{
		messages.add(new MessagesModel("Maze Bot","Title goes here","Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot","Title goes here","Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot","Title goes here","Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot","Title goes here","Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot","Title goes here","Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot","Title goes here","Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot","Title goes here","Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot","Title goes here","Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot","Title goes here","Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot","Title goes here","Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot","Title goes here","Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot","Title goes here","Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
		messages.add(new MessagesModel("Maze Bot","Title goes here","Description goes here. Description goes here. Description goes here. Description goes here. " +
				"Description goes here. Description goes here. Description goes here"));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// here the condition to check whether he is an event head or no
		/*if()
		{
			
		}*/
		getMenuInflater().inflate(R.menu.messages_menu,menu);
		return true;
//		else
//		{
//			return false;
//		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();
		if(id==android.R.id.home)
		{
			onBackPressed();
			finish();
			return true;
		}
		else if(id==R.id.new_message)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			LayoutInflater inflater = MessagesActivity.this.getLayoutInflater();
			final View view = inflater.inflate(R.layout.new_message_layout, null);
			builder.setView(view);
			final EditText titleInput = (EditText) view.findViewById(R.id.title_edit);
			final EditText contentInput = (EditText) view.findViewById(R.id.content_edit);
			builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i)
				{
					titleInput.setText("");
					contentInput.setText("");
					Toast.makeText(MessagesActivity.this,"Message sent",Toast.LENGTH_SHORT).show();
				}
			});
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i)
				{
					dialogInterface.cancel();
				}
			});
			builder.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
