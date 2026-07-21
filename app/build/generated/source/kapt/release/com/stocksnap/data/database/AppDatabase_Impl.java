package com.stocksnap.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ProductDao _productDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(6) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `products` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `barcode` TEXT NOT NULL, `mrp` REAL NOT NULL, `brand` TEXT, `quantity` INTEGER, `frontImagePath` TEXT NOT NULL, `barcodeImagePath` TEXT NOT NULL, `mrpImagePath` TEXT NOT NULL, `thumbnailPath` TEXT, `status` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `weight` TEXT NOT NULL, `frontImageUrl` TEXT, `catalogImagePath` TEXT, `isPendingSync` INTEGER NOT NULL, `code` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `arrivals` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `arrivalId` TEXT NOT NULL, `barcode` TEXT NOT NULL, `productName` TEXT NOT NULL, `mrp` REAL NOT NULL, `quantityReceived` INTEGER NOT NULL, `status` TEXT NOT NULL, `createdByUid` TEXT NOT NULL, `createdByName` TEXT NOT NULL, `createdByEmail` TEXT NOT NULL, `updatedByUid` TEXT, `updatedByName` TEXT, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `optionalSupplierName` TEXT, `isPendingSync` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `scan_sessions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `employeeUid` TEXT NOT NULL, `employeeName` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `barcode` TEXT NOT NULL, `productName` TEXT NOT NULL, `action` TEXT NOT NULL, `isPendingSync` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `activity_logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `activityId` TEXT NOT NULL, `performedByUid` TEXT NOT NULL, `performedByName` TEXT NOT NULL, `performedByEmail` TEXT NOT NULL, `actionType` TEXT NOT NULL, `productName` TEXT NOT NULL, `barcode` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `isPendingSync` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '3dc175a9444a5e773e3be7180dd6a1d9')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `products`");
        db.execSQL("DROP TABLE IF EXISTS `arrivals`");
        db.execSQL("DROP TABLE IF EXISTS `scan_sessions`");
        db.execSQL("DROP TABLE IF EXISTS `activity_logs`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsProducts = new HashMap<String, TableInfo.Column>(18);
        _columnsProducts.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("barcode", new TableInfo.Column("barcode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("mrp", new TableInfo.Column("mrp", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("brand", new TableInfo.Column("brand", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("quantity", new TableInfo.Column("quantity", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("frontImagePath", new TableInfo.Column("frontImagePath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("barcodeImagePath", new TableInfo.Column("barcodeImagePath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("mrpImagePath", new TableInfo.Column("mrpImagePath", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("thumbnailPath", new TableInfo.Column("thumbnailPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("weight", new TableInfo.Column("weight", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("frontImageUrl", new TableInfo.Column("frontImageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("catalogImagePath", new TableInfo.Column("catalogImagePath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("isPendingSync", new TableInfo.Column("isPendingSync", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("code", new TableInfo.Column("code", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProducts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProducts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProducts = new TableInfo("products", _columnsProducts, _foreignKeysProducts, _indicesProducts);
        final TableInfo _existingProducts = TableInfo.read(db, "products");
        if (!_infoProducts.equals(_existingProducts)) {
          return new RoomOpenHelper.ValidationResult(false, "products(com.stocksnap.data.database.Product).\n"
                  + " Expected:\n" + _infoProducts + "\n"
                  + " Found:\n" + _existingProducts);
        }
        final HashMap<String, TableInfo.Column> _columnsArrivals = new HashMap<String, TableInfo.Column>(16);
        _columnsArrivals.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivals.put("arrivalId", new TableInfo.Column("arrivalId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivals.put("barcode", new TableInfo.Column("barcode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivals.put("productName", new TableInfo.Column("productName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivals.put("mrp", new TableInfo.Column("mrp", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivals.put("quantityReceived", new TableInfo.Column("quantityReceived", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivals.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivals.put("createdByUid", new TableInfo.Column("createdByUid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivals.put("createdByName", new TableInfo.Column("createdByName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivals.put("createdByEmail", new TableInfo.Column("createdByEmail", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivals.put("updatedByUid", new TableInfo.Column("updatedByUid", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivals.put("updatedByName", new TableInfo.Column("updatedByName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivals.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivals.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivals.put("optionalSupplierName", new TableInfo.Column("optionalSupplierName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArrivals.put("isPendingSync", new TableInfo.Column("isPendingSync", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysArrivals = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesArrivals = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoArrivals = new TableInfo("arrivals", _columnsArrivals, _foreignKeysArrivals, _indicesArrivals);
        final TableInfo _existingArrivals = TableInfo.read(db, "arrivals");
        if (!_infoArrivals.equals(_existingArrivals)) {
          return new RoomOpenHelper.ValidationResult(false, "arrivals(com.stocksnap.data.model.Arrival).\n"
                  + " Expected:\n" + _infoArrivals + "\n"
                  + " Found:\n" + _existingArrivals);
        }
        final HashMap<String, TableInfo.Column> _columnsScanSessions = new HashMap<String, TableInfo.Column>(8);
        _columnsScanSessions.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScanSessions.put("employeeUid", new TableInfo.Column("employeeUid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScanSessions.put("employeeName", new TableInfo.Column("employeeName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScanSessions.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScanSessions.put("barcode", new TableInfo.Column("barcode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScanSessions.put("productName", new TableInfo.Column("productName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScanSessions.put("action", new TableInfo.Column("action", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsScanSessions.put("isPendingSync", new TableInfo.Column("isPendingSync", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysScanSessions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesScanSessions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoScanSessions = new TableInfo("scan_sessions", _columnsScanSessions, _foreignKeysScanSessions, _indicesScanSessions);
        final TableInfo _existingScanSessions = TableInfo.read(db, "scan_sessions");
        if (!_infoScanSessions.equals(_existingScanSessions)) {
          return new RoomOpenHelper.ValidationResult(false, "scan_sessions(com.stocksnap.data.model.ScanSession).\n"
                  + " Expected:\n" + _infoScanSessions + "\n"
                  + " Found:\n" + _existingScanSessions);
        }
        final HashMap<String, TableInfo.Column> _columnsActivityLogs = new HashMap<String, TableInfo.Column>(10);
        _columnsActivityLogs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityLogs.put("activityId", new TableInfo.Column("activityId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityLogs.put("performedByUid", new TableInfo.Column("performedByUid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityLogs.put("performedByName", new TableInfo.Column("performedByName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityLogs.put("performedByEmail", new TableInfo.Column("performedByEmail", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityLogs.put("actionType", new TableInfo.Column("actionType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityLogs.put("productName", new TableInfo.Column("productName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityLogs.put("barcode", new TableInfo.Column("barcode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityLogs.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsActivityLogs.put("isPendingSync", new TableInfo.Column("isPendingSync", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysActivityLogs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesActivityLogs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoActivityLogs = new TableInfo("activity_logs", _columnsActivityLogs, _foreignKeysActivityLogs, _indicesActivityLogs);
        final TableInfo _existingActivityLogs = TableInfo.read(db, "activity_logs");
        if (!_infoActivityLogs.equals(_existingActivityLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "activity_logs(com.stocksnap.data.model.ActivityLog).\n"
                  + " Expected:\n" + _infoActivityLogs + "\n"
                  + " Found:\n" + _existingActivityLogs);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "3dc175a9444a5e773e3be7180dd6a1d9", "c5b75106d0d0f2c033acae005309abe8");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "products","arrivals","scan_sessions","activity_logs");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `products`");
      _db.execSQL("DELETE FROM `arrivals`");
      _db.execSQL("DELETE FROM `scan_sessions`");
      _db.execSQL("DELETE FROM `activity_logs`");
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
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ProductDao.class, ProductDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ProductDao productDao() {
    if (_productDao != null) {
      return _productDao;
    } else {
      synchronized(this) {
        if(_productDao == null) {
          _productDao = new ProductDao_Impl(this);
        }
        return _productDao;
      }
    }
  }
}
