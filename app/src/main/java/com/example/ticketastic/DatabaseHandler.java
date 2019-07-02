package com.example.ticketastic;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ticketastic.db";

    //Users
    private final String TABLE_NAME_USER = "user_data";
    private final String COLUMN_USERNAME = "username";
    private final String COLUMN_PASSWORD = "password";

    private String USER_QUERY = "CREATE TABLE " + TABLE_NAME_USER + " (" +
                                        COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                                        COLUMN_PASSWORD + " TEXT)";

    //Events
    private final String TABLE_NAME_EVENT = "event_data";
    private final String COLUMN_ID = "id";
    private final String COLUMN_NAME = "name";
    private final String COLUMN_DESCRIPTION = "description";
    private final String COLUMN_TYPE = "type";
    private final String COLUMN_IMAGE = "image";
    private final String COLUMN_TOTAL_TICKETS = "totalTickets";
    private final String COLUMN_TICKETS_LEFT = "ticketsLeft";
    private final String COLUMN_DATE = "date";
    private final String COLUMN_SCHEDULE = "schedule";
    private final String COLUMN_PRICE = "price";
    private final String COLUMN_PROMOTION = "promotion";

    private String EVENT_QUERY = "CREATE TABLE " + TABLE_NAME_EVENT + " (" +
                                        COLUMN_ID + " INTEGER PRIMARY KEY, " +
                                        COLUMN_NAME + " TEXT, " +
                                        COLUMN_DESCRIPTION + " TEXT, " +
                                        COLUMN_TYPE + " TEXT, " +
                                        COLUMN_IMAGE + " TEXT, " +
                                        COLUMN_TOTAL_TICKETS + " INTEGER, " +
                                        COLUMN_TICKETS_LEFT + " INTEGER, " +
                                        COLUMN_DATE + " TEXT, " +
                                        COLUMN_SCHEDULE + " TEXT, " +
                                        COLUMN_PRICE + " TEXT, " +
                                        COLUMN_PROMOTION + " TEXT)";

    //Tickets
    private final String TABLE_NAME_TICKET  = "ticket_data";
    private final String COLUMN_TICKET_CODE = "code";
    private final String COLUMN_TICKET_NAME = "eventName";
    private final String COLUMN_TICKET_IMAGE = "image";
    private final String COLUMN_TICKET_DATE = "eventDate";
    private final String COLUMN_TICKET_SCHEDULE = "schedule";
    private final String COLUMN_TICKET_USERNAME = "username";
    private final String COLUMN_TICKET_PRICE = "price";
    private final String COLUMN_TICKET_QUANTITY = "quantity";
    private final String COLUMN_TICKET_PROMOTED = "promoted";

    private String TICKET_QUERY = "CREATE TABLE " + TABLE_NAME_TICKET + "( " +
                                        COLUMN_TICKET_CODE + " TEXT PRIMARY KEY, " +
                                        COLUMN_TICKET_NAME + " TEXT, " +
                                        COLUMN_TICKET_IMAGE + " TEXT, " +
                                        COLUMN_TICKET_DATE + " TEXT, " +
                                        COLUMN_TICKET_USERNAME + " TEXT, " +
                                        COLUMN_TICKET_SCHEDULE + " TEXT, " +
                                        COLUMN_TICKET_PRICE + " INTEGER, " +
                                        COLUMN_TICKET_QUANTITY + " INTEGER, " +
                                        COLUMN_TICKET_PROMOTED + " INTEGER)";

    //final Context context;
    static String res = null;
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
       // this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_QUERY);
        db.execSQL(EVENT_QUERY);
        db.execSQL(TICKET_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        onCreate(db);
    }

    void addEvent(int id, String name, String description, String type, String image, String date, String schedule, int price, int promotion){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, id);
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DESCRIPTION, description);
        contentValues.put(COLUMN_TYPE, type);
        contentValues.put(COLUMN_IMAGE, image);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_SCHEDULE, schedule);
        contentValues.put(COLUMN_PRICE, price);
        contentValues.put(COLUMN_PROMOTION, promotion);

        try{
            db.insertOrThrow(TABLE_NAME_EVENT, null, contentValues);
        } catch(SQLiteException e){}
    }

    void addTicket(Ticket t){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TICKET_CODE, t.getCode());
        contentValues.put(COLUMN_TICKET_NAME, t.getEventName());
        contentValues.put(COLUMN_TICKET_IMAGE, t.getImage());
        contentValues.put(COLUMN_TICKET_DATE, t.getEventDate());
        contentValues.put(COLUMN_TICKET_USERNAME, t.getUsername());
        contentValues.put(COLUMN_TICKET_SCHEDULE, t.getSchedule());
        contentValues.put(COLUMN_TICKET_PRICE, t.getPrice());
        contentValues.put(COLUMN_TICKET_QUANTITY, t.getQuantity());
        contentValues.put(COLUMN_TICKET_PROMOTED, t.getPromoted());

        long result = db.insert(TABLE_NAME_TICKET, null, contentValues);
    }

    boolean addUser(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_NAME_USER, null, contentValues);
        return (result != -1);
    }

    boolean deleteUser(String username){
        SQLiteDatabase db = getWritableDatabase();
        long result = db.delete(TABLE_NAME_USER, COLUMN_USERNAME + " = ?", new String[]{username});
        return (result != -1);
    }

    ArrayList<HashMap<String, String>> getUsers() {
        ArrayList<HashMap<String, String>> toReturn = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME_USER;
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){
            HashMap<String,String> oneUser = new HashMap<>();
            oneUser.put("username", cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
            oneUser.put("password", cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
            toReturn.add(oneUser);
        }

        cursor.close();
        return toReturn;
    }

    HashMap<String, String> getUserByUsername(String username) {
        HashMap<String, String> toReturn = new HashMap<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME_USER + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        if(cursor.moveToNext()){
            toReturn.put("username", cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
            toReturn.put("password", cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
        }

        else{
            toReturn = null;
        }

        cursor.close();
        return toReturn;
    }

    boolean checkAvailableUsername(String username) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_data where username=?", new String[]{username});
        boolean b = cursor.getCount() == 0;
        cursor.close();
        return b;
    }

    boolean checkUser(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from user_data where username = ? and password = ?", new String[]{username, password});
        if (cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        else{
            cursor.close();
            return false;
        }
    }

    public ArrayList<Event> getEvents(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_EVENT;

        ArrayList<Event> toReturn = new ArrayList<>();
        ArrayList<Integer> idArray = parseID(doInBackground());

        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            //Log.i("ID", String.format("%d",id));
            if(idArray.contains(id)){
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
                String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                String schedule = cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE));
                int price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE));
                int promotion = cursor.getInt(cursor.getColumnIndex(COLUMN_PROMOTION));

                Event e = new Event(id, name, description, type, image, date, schedule, price, promotion);
                toReturn.add(e);
            }
        }

        cursor.close();
        return toReturn;
    }

    private ArrayList<Integer> parseID(String str){
        ArrayList<Integer> toReturn = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(str);
            JSONArray data = obj.getJSONArray("user");
            for(int i=0; i<data.length(); i++){
                toReturn.add(Integer.valueOf(data.getJSONObject(i).getString("id")));
                Log.i("ELEM",data.getJSONObject(i).getString("id"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return toReturn;
    }

    public ArrayList<Event> getEvents(String type){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_EVENT + " WHERE " + COLUMN_TYPE + " = ?";
        ArrayList<Event> toReturn = new ArrayList<>();
        ArrayList<Integer> idArray = parseID(doInBackground());
        Cursor cursor = db.rawQuery(query, new String[]{type});
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            Log.i("ID", String.format("%d",id));
            if(idArray.contains(id)){
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                String schedule = cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE));
                int price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE));
                int promotion = cursor.getInt(cursor.getColumnIndex(COLUMN_PROMOTION));

                Event e = new Event(id, name, description, type, image, date, schedule, price, promotion);
                toReturn.add(e);
            }
        }

        cursor.close();
        return toReturn;
    }

    ArrayList<Ticket> getTicketsByUser(String username){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_TICKET + " WHERE " + COLUMN_TICKET_USERNAME + " = ?";
        ArrayList<Ticket> toReturn = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, new String[]{username});
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_TICKET_NAME));
            String image = cursor.getString(cursor.getColumnIndex(COLUMN_TICKET_IMAGE));
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_TICKET_DATE));
            String schedule = cursor.getString(cursor.getColumnIndex(COLUMN_TICKET_SCHEDULE));
            int price = cursor.getInt(cursor.getColumnIndex(COLUMN_TICKET_PRICE));
            int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_TICKET_QUANTITY));
            String code = cursor.getString(cursor.getColumnIndex(COLUMN_TICKET_CODE));
            int promotion = cursor.getInt(cursor.getColumnIndex(COLUMN_TICKET_PROMOTED));

            Ticket t = new Ticket(code, name, image, date, schedule, username, price, quantity, promotion);
            toReturn.add(t);
        }

        cursor.close();
        return toReturn;
    }

    private String doInBackground() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://script.google.com/macros/s/AKfycbzdscQP9WOrnnGuQidJf3zm2PDffGbKl7kO-689Ty2lI3vghs02/exec");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
            }
            Log.i("RES", buffer.toString());
            return buffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
