package com.example.luka2.db.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



@DatabaseTable(tableName = IMDBmovie.TABLE_NAME_USERS)
public class IMDBmovie {

    public static final String TABLE_NAME_USERS = "movies2";
    public static final String FIELD_NAME_ID     = "id";
    public static final String FIELD_FILM_ID     = "id2";
    public static final String TABLE_MOVIE_NAME = "name";
    public static final String FIELD_NAME_USER  = "user";


    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = FIELD_FILM_ID)
    private String mId2;

    @DatabaseField(columnName = TABLE_MOVIE_NAME)
    private String mName;

    @DatabaseField(columnName = FIELD_NAME_USER, foreign = true, foreignAutoRefresh = true)
    private Actor mUser;

    public IMDBmovie() {
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmId2() {
        return mId2;
    }

    public void setmId2(String mId2) {
        this.mId2 = mId2;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Actor getmUser() {
        return mUser;
    }

    public void setmUser(Actor mUser) {
        this.mUser = mUser;
    }

    @Override
    public String toString() {
        return mName;
    }
}
