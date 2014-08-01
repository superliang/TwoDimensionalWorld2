package com.zjlsdm.twodimensionalworld.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "dimensional.db";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS TBL_URL_LIST (URL text not null, REMARK text , UPDATE_TIME text not null);");
		db.execSQL("CREATE TABLE IF NOT EXISTS TBL_BANGUMI_ONE (ORDER_INDEX text not null, NAME text not null , TIME_DAY text not null , TIME_WEEK text not null);");
		db.execSQL("CREATE TABLE IF NOT EXISTS TBL_BANGUMI_FOUR (ORDER_INDEX text not null, NAME text not null , TIME_DAY text not null , TIME_WEEK text not null);");
		db.execSQL("CREATE TABLE IF NOT EXISTS TBL_BANGUMI_SEVEN (ORDER_INDEX text not null, NAME text not null , TIME_DAY text not null , TIME_WEEK text not null);");
		db.execSQL("CREATE TABLE IF NOT EXISTS TBL_BANGUMI_TEN (ORDER_INDEX text not null, NAME text not null , TIME_DAY text not null , TIME_WEEK text not null);");
//		db.execSQL("CREATE TABLE IF NOT EXISTS TBL_RP_LIST (REPORT_ID text not null, ORG_CODE text, ORG_NAME text, "
//				+ "CLINIC_SIGN INTEGER, MEDICAL_CARD text, BED_NUMBER test, PATIENT_NUMBER text, RESULT text, "
//				+ "TEST_REASON text, SAMPLE_NAME text, SENDER text, TESTER text, REVIEWER text, COLLECT_TIME text,"
//				+ "TEST_TIME text, COMMENT text, READ_LVL INTEGER);");		
//		db.execSQL("CREATE TABLE IF NOT EXISTS TBL_JKBK (KNOWLEDGE_ID integer primary key not null, " +
//				"TITLE text not null, CONTENT text not null, TIME text not null, " +
//				"FILENAME text not null, READSTATE text not null ,COLLECT text not null);");
//		db.execSQL("CREATE TABLE IF NOT EXISTS TBL_DOCTOR_COLLECTION (DOCTOR_CODE TEXT NOT NULL, "
//				+ "HOSPITAL_ID TEXT NOT NULL, HOSPITAL_NAME TEXT NOT NULL, DOCTOR_NAME TEXT, "
//				+ "DOCTOR_TITLE TEXT, DOCTOR_SPECIALTY TEXT, DOCTOR_PHOTO BLOB);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}