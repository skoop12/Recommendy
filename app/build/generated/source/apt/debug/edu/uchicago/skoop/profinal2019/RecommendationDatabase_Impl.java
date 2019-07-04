package edu.uchicago.skoop.profinal2019;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class RecommendationDatabase_Impl extends RecommendationDatabase {
  private volatile RecommendationDao _recommendationDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `recs_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `type` TEXT NOT NULL, `description` TEXT, `wikipedia` TEXT, `youtube` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"499b04d2562adab8e65c8c7f6023d3b5\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `recs_table`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsRecsTable = new HashMap<String, TableInfo.Column>(6);
        _columnsRecsTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsRecsTable.put("name", new TableInfo.Column("name", "TEXT", true, 0));
        _columnsRecsTable.put("type", new TableInfo.Column("type", "TEXT", true, 0));
        _columnsRecsTable.put("description", new TableInfo.Column("description", "TEXT", false, 0));
        _columnsRecsTable.put("wikipedia", new TableInfo.Column("wikipedia", "TEXT", false, 0));
        _columnsRecsTable.put("youtube", new TableInfo.Column("youtube", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRecsTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRecsTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRecsTable = new TableInfo("recs_table", _columnsRecsTable, _foreignKeysRecsTable, _indicesRecsTable);
        final TableInfo _existingRecsTable = TableInfo.read(_db, "recs_table");
        if (! _infoRecsTable.equals(_existingRecsTable)) {
          throw new IllegalStateException("Migration didn't properly handle recs_table(edu.uchicago.skoop.profinal2019.Recommendation).\n"
                  + " Expected:\n" + _infoRecsTable + "\n"
                  + " Found:\n" + _existingRecsTable);
        }
      }
    }, "499b04d2562adab8e65c8c7f6023d3b5", "2ab0be3a6a9f41c31785ee09c7fe9548");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "recs_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `recs_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public RecommendationDao recommendationDao() {
    if (_recommendationDao != null) {
      return _recommendationDao;
    } else {
      synchronized(this) {
        if(_recommendationDao == null) {
          _recommendationDao = new RecommendationDao_Impl(this);
        }
        return _recommendationDao;
      }
    }
  }
}
