package edu.uchicago.skoop.profinal2019;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings("unchecked")
public final class RecommendationDao_Impl implements RecommendationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfRecommendation;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfRecommendation;

  public RecommendationDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRecommendation = new EntityInsertionAdapter<Recommendation>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `recs_table`(`id`,`name`,`type`,`description`,`wikipedia`,`youtube`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Recommendation value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getType() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getType());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        if (value.getWikipedia() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getWikipedia());
        }
        if (value.getYoutube() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getYoutube());
        }
      }
    };
    this.__deletionAdapterOfRecommendation = new EntityDeletionOrUpdateAdapter<Recommendation>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `recs_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Recommendation value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public void insertRecommendation(final Recommendation recommendation) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfRecommendation.insert(recommendation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteRecommendation(final Recommendation recommendation) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfRecommendation.handle(recommendation);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Recommendation>> getAllRecs() {
    final String _sql = "SELECT * from recs_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"recs_table"}, new Callable<List<Recommendation>>() {
      @Override
      public List<Recommendation> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfWikipedia = CursorUtil.getColumnIndexOrThrow(_cursor, "wikipedia");
          final int _cursorIndexOfYoutube = CursorUtil.getColumnIndexOrThrow(_cursor, "youtube");
          final List<Recommendation> _result = new ArrayList<Recommendation>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Recommendation _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpWikipedia;
            _tmpWikipedia = _cursor.getString(_cursorIndexOfWikipedia);
            final String _tmpYoutube;
            _tmpYoutube = _cursor.getString(_cursorIndexOfYoutube);
            _item = new Recommendation(_tmpId,_tmpName,_tmpType,_tmpDescription,_tmpWikipedia,_tmpYoutube);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Recommendation>> getAllRecsTitleSort() {
    final String _sql = "SELECT * from recs_table ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"recs_table"}, new Callable<List<Recommendation>>() {
      @Override
      public List<Recommendation> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfWikipedia = CursorUtil.getColumnIndexOrThrow(_cursor, "wikipedia");
          final int _cursorIndexOfYoutube = CursorUtil.getColumnIndexOrThrow(_cursor, "youtube");
          final List<Recommendation> _result = new ArrayList<Recommendation>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Recommendation _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpWikipedia;
            _tmpWikipedia = _cursor.getString(_cursorIndexOfWikipedia);
            final String _tmpYoutube;
            _tmpYoutube = _cursor.getString(_cursorIndexOfYoutube);
            _item = new Recommendation(_tmpId,_tmpName,_tmpType,_tmpDescription,_tmpWikipedia,_tmpYoutube);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Recommendation>> getAllRecsTypeSort() {
    final String _sql = "SELECT * from recs_table ORDER BY type ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"recs_table"}, new Callable<List<Recommendation>>() {
      @Override
      public List<Recommendation> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfWikipedia = CursorUtil.getColumnIndexOrThrow(_cursor, "wikipedia");
          final int _cursorIndexOfYoutube = CursorUtil.getColumnIndexOrThrow(_cursor, "youtube");
          final List<Recommendation> _result = new ArrayList<Recommendation>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Recommendation _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpWikipedia;
            _tmpWikipedia = _cursor.getString(_cursorIndexOfWikipedia);
            final String _tmpYoutube;
            _tmpYoutube = _cursor.getString(_cursorIndexOfYoutube);
            _item = new Recommendation(_tmpId,_tmpName,_tmpType,_tmpDescription,_tmpWikipedia,_tmpYoutube);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Recommendation>> getAllRecsTitleTypeSort() {
    final String _sql = "SELECT * from recs_table ORDER BY name,type ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"recs_table"}, new Callable<List<Recommendation>>() {
      @Override
      public List<Recommendation> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfWikipedia = CursorUtil.getColumnIndexOrThrow(_cursor, "wikipedia");
          final int _cursorIndexOfYoutube = CursorUtil.getColumnIndexOrThrow(_cursor, "youtube");
          final List<Recommendation> _result = new ArrayList<Recommendation>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Recommendation _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpWikipedia;
            _tmpWikipedia = _cursor.getString(_cursorIndexOfWikipedia);
            final String _tmpYoutube;
            _tmpYoutube = _cursor.getString(_cursorIndexOfYoutube);
            _item = new Recommendation(_tmpId,_tmpName,_tmpType,_tmpDescription,_tmpWikipedia,_tmpYoutube);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
