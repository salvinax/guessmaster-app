//Salvina Bouaboud - Assignment 3

package com.example.guessmaster;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.Random;

public class GuessMaster extends AppCompatActivity {
    private TextView entityName;
    private TextView ticketsum;
    private Button guessButton;
    private EditText userIn;
    private Button btnclearContent;
    private String user_input;
    private ImageView entityImage;
    String answer;

    private int numOfEntities;
    private Entity[] entities;
    private int[] tickets;
    private int numOfTickets;
    //Stores Entity Name
    String entName;
    int entityid = 0;
    int currentTicketWon = 0;

    Politician jTrudeau = new Politician("Justin Trudeau", new Date("December", 25, 1971), "Male", "Liberal", 0.25);////
    Singer cDion = new Singer("Celine Dion", new Date("March", 30, 1961), "Female", "La voix du bon Dieu", new Date("November", 6, 1981), 0.5);////
    Person myCreator = new Person("My Creator", new Date("September", 1, 2000), "Female", 1);////
    Country usa = new Country("United States", new Date("July", 4, 1776), "Washinton D.C.", 0.1);////

    public void addEntity (Entity entity) {
        entities[numOfEntities++] = entity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_activity);
        guessButton = (Button) findViewById(R.id.btnguess);
        userIn = (EditText) findViewById(R.id.guessinput);
        ticketsum = (TextView) findViewById(R.id.ticket);
        entityName = (TextView) findViewById(R.id.entityname);
        entityImage = (ImageView) findViewById(R.id.entityimage);
        btnclearContent = (Button) findViewById(R.id.btnclear);

       GuessMaster gm = new GuessMaster();

        addEntity(jTrudeau);
        addEntity(cDion);
        addEntity(myCreator);
        addEntity(usa);

        changeEntity();
        welcomeToGame(entities[entityid]);

        //OnClick Listener action for clear button
        btnclearContent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                changeEntity();
            }
        });

        //OnClick Listener action for clear button
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                playGame(entities[entityid]);
            }
        });

    }
    public void changeEntity() {
        userIn.getText().clear(); //clear user input
        entityid = genRandomEntityId(); //change entity
        Entity entity = entities[entityid];
        entName = entity.getName();
        ImageSetter(entity);
        entityName.setText(entName);
    }

    //display the right picture for each entity
    public void ImageSetter(Entity entity) {
        if (entity.toString().equals(jTrudeau.toString())){
            entityImage.setImageResource(R.drawable.justint);
        } else if (entity.toString().equals(usa.toString())){
            entityImage.setImageResource(R.drawable.usaflag);
        } else if (entity.toString().equals(myCreator.toString())){
            entityImage.setImageResource(R.drawable.creator);
        } else if (entity.toString().equals(cDion.toString())){
            entityImage.setImageResource(R.drawable.celidion);
        }
    }

    //generate a random number
    public int genRandomEntityId() {
        Random randomNumber = new Random();
        return randomNumber.nextInt(numOfEntities);
    }

    //constructor
    public GuessMaster() {
        numOfEntities = 0;
        entities = new Entity[10];
    }

    public void playGame(int entityId) {
        Entity entity = entities[entityId];
        playGame(entity);
    }

    public void playGame(Entity entity) {

            entityName.setText(entity.getName()); //change name displayed to new entity
            answer = userIn.getText().toString();
            answer = answer.replace("\n","").replace("\r", "");
            Date date = new Date(answer);

            if (date.precedes(entity.getBorn())) {
                AlertDialog.Builder laterDate = new AlertDialog.Builder(GuessMaster.this);
                laterDate.setTitle("Wrong Answer");
                laterDate.setMessage("Try a later date than " +date.toString());
                laterDate.setNegativeButton("Try Again", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                laterDate.show();

            } else if (entity.getBorn().precedes(date)) {
                AlertDialog.Builder earlierDate = new AlertDialog.Builder(GuessMaster.this);
                earlierDate.setTitle("Wrong Answer");
                earlierDate.setMessage("Try an earlier date than " +date.toString());
                earlierDate.setNegativeButton("Try Again", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                earlierDate.show();

            } else {
                currentTicketWon += entity.getAwardedTicketNumber();
                numOfTickets += currentTicketWon;
                AlertDialog.Builder rightAlert = new AlertDialog.Builder(GuessMaster.this);
                rightAlert.setTitle("You Won!");
                rightAlert.setMessage("BINGO! " + entity.closingMessage());
               rightAlert.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        continueGame();
                    }
                });
                rightAlert.show();
                ticketsum.setText("Total Tickets: " + numOfTickets);
            }
    }

    public void continueGame() {
        entityid = genRandomEntityId();
        Entity entity = entities[entityid];
        entName = entity.getName();
        ImageSetter(entity);
        entityName.setText(entName);
        userIn.getText().clear();
    }

    //welcome message
    public void welcomeToGame(Entity entity) {
        AlertDialog.Builder welcomealert = new AlertDialog.Builder(GuessMaster.this);
        welcomealert.setTitle("GuessMaster Game v3");
        welcomealert.setMessage(entity.welcomeMessage());
        welcomealert.setCancelable(false);
        welcomealert.setNegativeButton("START GAME", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Game is Starting...", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = welcomealert.create();
        dialog.show();
    }
}