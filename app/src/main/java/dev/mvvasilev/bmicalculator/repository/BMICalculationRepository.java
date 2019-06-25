package dev.mvvasilev.bmicalculator.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import dev.mvvasilev.bmicalculator.entity.BMICalculation;

public class BMICalculationRepository extends SQLiteOpenHelper {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private static final String SAVE_CALCULATION = "" +
            "INSERT INTO calculations (timestamp, weight, height, result)" +
            "VALUES (?, ?, ?, ?);";

    private static final String UPDATE_TASK = "" +
            "UPDATE calculations SET" +
            "   timestamp = ?," +
            "   weight = ?," +
            "   height = ?," +
            "   result = ?" +
            "WHERE id = ?;";

    private static final String DELETE_CALCULATION = "" +
            "DELETE FROM calculations WHERE id = ?;";

    private static final String FIND_CALCULATION = "" +
            "SELECT * FROM calculations WHERE id = ?;";

    private static final String FIND_ALL_CALCULATIONS = "" +
            "SELECT * FROM calculations;";

    public BMICalculationRepository(Context context) {
        super(context, "CalculationDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("" +
                "CREATE TABLE IF NOT EXISTS calculations (" +
                "   id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "   timestamp TEXT," +
                "   weight DECIMAL(5, 5)," +
                "   height DECIMAL(5, 5)," +
                "   result DECIMAL(5, 5)" +
                ");"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public BMICalculation save(BMICalculation entity) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            try (SQLiteStatement statement = db.compileStatement(SAVE_CALCULATION)) {
                statement.bindString(1, entity.getTimestamp().format(DATE_TIME_FORMATTER));
                statement.bindDouble(2, entity.getWeight());
                statement.bindDouble(3, entity.getHeight());
                statement.bindDouble(4, entity.getResult());

                entity.setId(statement.executeInsert());
            }
        }

        return entity;
    }

    public void update(BMICalculation entity) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            try (SQLiteStatement statement = db.compileStatement(UPDATE_TASK)) {
                statement.bindString(1, entity.getTimestamp().format(DATE_TIME_FORMATTER));
                statement.bindDouble(2, entity.getWeight());
                statement.bindDouble(3, entity.getHeight());
                statement.bindDouble(4, entity.getResult());

                statement.executeUpdateDelete();
            }
        }
    }

    public void deleteById(Long id) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            try (SQLiteStatement statement = db.compileStatement(DELETE_CALCULATION)) {
                statement.bindLong(1, id);
                statement.executeUpdateDelete();
            }
        }
    }

    public BMICalculation findById(Long id) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            Cursor cursor = db.rawQuery(FIND_CALCULATION, new String[]{id.toString()});

            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToNext();

            BMICalculation task = createCalculationFromCursor(cursor);

            cursor.close();

            return task;
        }
    }

    public Set<BMICalculation> getAll() {
        try (SQLiteDatabase db = getWritableDatabase()) {
            Cursor cursor = db.rawQuery(FIND_ALL_CALCULATIONS, null);

            if (cursor.getCount() == 0) {
                return new HashSet<>();
            }

            Set<BMICalculation> results = new HashSet<>();

            while (cursor.moveToNext()) {
                results.add(createCalculationFromCursor(cursor));
            }

            cursor.close();

            return results;
        }
    }

    private BMICalculation createCalculationFromCursor(Cursor cursor) {
        BMICalculation task = new BMICalculation();

        task.setId(cursor.getLong(0));
        task.setTimestamp(LocalDateTime.from(DATE_TIME_FORMATTER.parse(cursor.getString(1))));
        task.setWeight(cursor.getDouble(2));
        task.setHeight(cursor.getDouble(3));
        task.setResult(cursor.getDouble(4));

        return task;
    }
}
