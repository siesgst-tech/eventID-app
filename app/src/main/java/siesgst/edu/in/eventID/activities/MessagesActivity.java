package siesgst.edu.in.eventID.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import siesgst.edu.in.eventID.R;
import siesgst.edu.in.eventID.adapters.MessagesAdapter;
import siesgst.edu.in.eventID.model.MessagesModel;
import siesgst.edu.in.eventID.utils.SessionManager;

public class MessagesActivity extends AppCompatActivity
{
	SessionManager sessionManager;
	AlertDialog alert;
	RecyclerView recyclerView;
	List<MessagesModel> messages = new ArrayList<MessagesModel>();
	MessagesAdapter adapter;
	
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
		adapter = new MessagesAdapter(messages);
		recyclerView.setAdapter(adapter);
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
		else if(id== R.id.new_message)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			LayoutInflater inflater = MessagesActivity.this.getLayoutInflater();
			final View view = inflater.inflate(R.layout.new_message_layout, null);
			builder.setView(view);
			
			builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i)
				{
					EditText titleInput = (EditText) view.findViewById(R.id.title_edit);
					EditText contentInput = (EditText) view.findViewById(R.id.content_edit);
					
					String title = titleInput.getText().toString();
					String content = contentInput.getText().toString();
					Log.v("title",title);
					Log.v("content",content);
					messages.add(new MessagesModel(MessagesActivity.this.getSharedPreferences(SessionManager.PREF_NAME,SessionManager.PRIVATE_MODE).getString("EVENT_NAME","event_name"),title,content));
					adapter.notifyDataSetChanged();
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
