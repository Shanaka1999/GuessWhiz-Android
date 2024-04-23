import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ScoreManager"
        private const val TABLE_SCORES = "scores"
        private const val KEY_ID = "id"
        private const val KEY_SCORE = "score"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_SCORES_TABLE = ("CREATE TABLE " + TABLE_SCORES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SCORE + " INTEGER" + ")")
        db?.execSQL(CREATE_SCORES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES)
        onCreate(db)
    }

    fun addScore(score: Int) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_SCORE, score)

        db.insert(TABLE_SCORES, null, values)
        db.close()
    }

    fun getHighestScore(): Int {
        val db = this.readableDatabase
        val selectQuery = "SELECT MAX($KEY_SCORE) FROM $TABLE_SCORES"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null && cursor.moveToFirst()) {
            val highestScore = cursor.getInt(0)
            cursor.close()
            return highestScore
        }
        cursor?.close()
        return 0
    }
}
