package com.example.ticketastic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;

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
    private final String COLUMN_NAME = "name";
    private final String COLUMN_DESCRIPTION = "description";
    private final String COLUMN_TYPE = "type";
    private final String COLUMN_IMAGE = "image";
    private final String COLUMN_TOTAL_TICKETS = "totalTickets";
    private final String COLUMN_TICKETS_LEFT = "ticketsLeft";
    private final String COLUMN_DATE = "date";
    private final String COLUMN_SCHEDULE = "schedule";
    private final String COLUMN_PRICE = "price";

    private String EVENT_QUERY = "CREATE TABLE " + TABLE_NAME_EVENT + " (" +
                                        COLUMN_NAME + " TEXT PRIMARY KEY, " +
                                        COLUMN_DESCRIPTION + " TEXT, " +
                                        COLUMN_TYPE + " TEXT, " +
                                        COLUMN_IMAGE + " TEXT, " +
                                        COLUMN_TOTAL_TICKETS + " INTEGER, " +
                                        COLUMN_TICKETS_LEFT + " INTEGER, " +
                                        COLUMN_DATE + " TEXT, " +
                                        COLUMN_SCHEDULE + " TEXT, " +
                                        COLUMN_PRICE + " TEXT)";

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

    private String TICKET_QUERY = "CREATE TABLE " + TABLE_NAME_TICKET + "( " +
                                        COLUMN_TICKET_CODE + " TEXT PRIMARY KEY, " +
                                        COLUMN_TICKET_NAME + " TEXT, " +
                                        COLUMN_TICKET_IMAGE + " TEXT, " +
                                        COLUMN_TICKET_DATE + " TEXT, " +
                                        COLUMN_TICKET_USERNAME + " TEXT, " +
                                        COLUMN_TICKET_SCHEDULE + " TEXT, " +
                                        COLUMN_TICKET_PRICE + " INTEGER, " +
                                        COLUMN_TICKET_QUANTITY + " INTEGER)";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    void addEvent(String name, String description, String type, String image, String date, String schedule, int price){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DESCRIPTION, description);
        contentValues.put(COLUMN_TYPE, type);
        contentValues.put(COLUMN_IMAGE, image);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_SCHEDULE, schedule);
        contentValues.put(COLUMN_PRICE, price);

        long result = db.insert(TABLE_NAME_EVENT, null, contentValues);
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
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_EVENT;
        ArrayList<Event> toReturn = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
            String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
            String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
            String schedule = cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE));
            int price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE));

            Event e = new Event(name, description, type, image, date, schedule, price);
            toReturn.add(e);
        }

        cursor.close();
        return toReturn;
    }

    public ArrayList<Event> getEvents(String type){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME_EVENT + " WHERE " + COLUMN_TYPE + " = ?";
        ArrayList<Event> toReturn = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, new String[]{type});
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
            String image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
            String schedule = cursor.getString(cursor.getColumnIndex(COLUMN_SCHEDULE));
            int price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE));

            Event e = new Event(name, description, type, image, date, schedule, price);
            toReturn.add(e);
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
            String code = cursor.getString(cursor.getColumnIndex(COLUMN_TICKET_CODE));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_TICKET_NAME));
            String image = cursor.getString(cursor.getColumnIndex(COLUMN_TICKET_IMAGE));
            String date = cursor.getString(cursor.getColumnIndex(COLUMN_TICKET_DATE));
            String schedule = cursor.getString(cursor.getColumnIndex(COLUMN_TICKET_SCHEDULE));
            int price = cursor.getInt(cursor.getColumnIndex(COLUMN_TICKET_PRICE));
            int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_TICKET_QUANTITY));

            Ticket t = new Ticket(code, name, image, date, schedule, username, price, quantity);
            toReturn.add(t);
        }

        cursor.close();
        return toReturn;
    }
}
