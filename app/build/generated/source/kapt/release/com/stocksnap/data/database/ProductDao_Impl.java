package com.stocksnap.data.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.stocksnap.data.model.ActivityLog;
import com.stocksnap.data.model.Arrival;
import com.stocksnap.data.model.ScanSession;
import com.stocksnap.domain.model.ProductStatus;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ProductDao_Impl implements ProductDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Product> __insertionAdapterOfProduct;

  private final RoomConverters __roomConverters = new RoomConverters();

  private final EntityInsertionAdapter<Arrival> __insertionAdapterOfArrival;

  private final EntityInsertionAdapter<ScanSession> __insertionAdapterOfScanSession;

  private final EntityInsertionAdapter<ActivityLog> __insertionAdapterOfActivityLog;

  private final EntityDeletionOrUpdateAdapter<Product> __updateAdapterOfProduct;

  private final EntityDeletionOrUpdateAdapter<Arrival> __updateAdapterOfArrival;

  private final SharedSQLiteStatement __preparedStmtOfDeleteProductById;

  private final SharedSQLiteStatement __preparedStmtOfMarkProductSynced;

  private final SharedSQLiteStatement __preparedStmtOfMarkProductSyncedWithUrl;

  private final SharedSQLiteStatement __preparedStmtOfDeleteArrivalById;

  private final SharedSQLiteStatement __preparedStmtOfDeleteArrivalByArrivalId;

  private final SharedSQLiteStatement __preparedStmtOfMarkArrivalSynced;

  private final SharedSQLiteStatement __preparedStmtOfMarkScanSessionSynced;

  private final SharedSQLiteStatement __preparedStmtOfMarkActivityLogSynced;

  public ProductDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProduct = new EntityInsertionAdapter<Product>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `products` (`id`,`name`,`barcode`,`mrp`,`brand`,`quantity`,`frontImagePath`,`barcodeImagePath`,`mrpImagePath`,`thumbnailPath`,`status`,`createdAt`,`updatedAt`,`weight`,`frontImageUrl`,`catalogImagePath`,`isPendingSync`,`code`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Product entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getBarcode() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getBarcode());
        }
        statement.bindDouble(4, entity.getMrp());
        if (entity.getBrand() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getBrand());
        }
        if (entity.getQuantity() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getQuantity());
        }
        if (entity.getFrontImagePath() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getFrontImagePath());
        }
        if (entity.getBarcodeImagePath() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getBarcodeImagePath());
        }
        if (entity.getMrpImagePath() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getMrpImagePath());
        }
        if (entity.getThumbnailPath() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getThumbnailPath());
        }
        final String _tmp = __roomConverters.fromProductStatus(entity.getStatus());
        if (_tmp == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp);
        }
        statement.bindLong(12, entity.getCreatedAt());
        statement.bindLong(13, entity.getUpdatedAt());
        if (entity.getWeight() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getWeight());
        }
        if (entity.getFrontImageUrl() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getFrontImageUrl());
        }
        if (entity.getCatalogImagePath() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getCatalogImagePath());
        }
        statement.bindLong(17, entity.isPendingSync());
        if (entity.getCode() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getCode());
        }
      }
    };
    this.__insertionAdapterOfArrival = new EntityInsertionAdapter<Arrival>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `arrivals` (`id`,`arrivalId`,`barcode`,`productName`,`mrp`,`quantityReceived`,`status`,`createdByUid`,`createdByName`,`createdByEmail`,`updatedByUid`,`updatedByName`,`createdAt`,`updatedAt`,`optionalSupplierName`,`isPendingSync`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Arrival entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getArrivalId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getArrivalId());
        }
        if (entity.getBarcode() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getBarcode());
        }
        if (entity.getProductName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getProductName());
        }
        statement.bindDouble(5, entity.getMrp());
        statement.bindLong(6, entity.getQuantityReceived());
        final String _tmp = __roomConverters.fromProductStatus(entity.getStatus());
        if (_tmp == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp);
        }
        if (entity.getCreatedByUid() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getCreatedByUid());
        }
        if (entity.getCreatedByName() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getCreatedByName());
        }
        if (entity.getCreatedByEmail() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getCreatedByEmail());
        }
        if (entity.getUpdatedByUid() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getUpdatedByUid());
        }
        if (entity.getUpdatedByName() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getUpdatedByName());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
        if (entity.getOptionalSupplierName() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getOptionalSupplierName());
        }
        statement.bindLong(16, entity.isPendingSync());
      }
    };
    this.__insertionAdapterOfScanSession = new EntityInsertionAdapter<ScanSession>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `scan_sessions` (`id`,`employeeUid`,`employeeName`,`timestamp`,`barcode`,`productName`,`action`,`isPendingSync`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ScanSession entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getEmployeeUid() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getEmployeeUid());
        }
        if (entity.getEmployeeName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getEmployeeName());
        }
        statement.bindLong(4, entity.getTimestamp());
        if (entity.getBarcode() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getBarcode());
        }
        if (entity.getProductName() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getProductName());
        }
        if (entity.getAction() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getAction());
        }
        statement.bindLong(8, entity.isPendingSync());
      }
    };
    this.__insertionAdapterOfActivityLog = new EntityInsertionAdapter<ActivityLog>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `activity_logs` (`id`,`activityId`,`performedByUid`,`performedByName`,`performedByEmail`,`actionType`,`productName`,`barcode`,`timestamp`,`isPendingSync`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ActivityLog entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getActivityId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getActivityId());
        }
        if (entity.getPerformedByUid() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getPerformedByUid());
        }
        if (entity.getPerformedByName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPerformedByName());
        }
        if (entity.getPerformedByEmail() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPerformedByEmail());
        }
        if (entity.getActionType() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getActionType());
        }
        if (entity.getProductName() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getProductName());
        }
        if (entity.getBarcode() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getBarcode());
        }
        statement.bindLong(9, entity.getTimestamp());
        statement.bindLong(10, entity.isPendingSync());
      }
    };
    this.__updateAdapterOfProduct = new EntityDeletionOrUpdateAdapter<Product>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `products` SET `id` = ?,`name` = ?,`barcode` = ?,`mrp` = ?,`brand` = ?,`quantity` = ?,`frontImagePath` = ?,`barcodeImagePath` = ?,`mrpImagePath` = ?,`thumbnailPath` = ?,`status` = ?,`createdAt` = ?,`updatedAt` = ?,`weight` = ?,`frontImageUrl` = ?,`catalogImagePath` = ?,`isPendingSync` = ?,`code` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Product entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getBarcode() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getBarcode());
        }
        statement.bindDouble(4, entity.getMrp());
        if (entity.getBrand() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getBrand());
        }
        if (entity.getQuantity() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getQuantity());
        }
        if (entity.getFrontImagePath() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getFrontImagePath());
        }
        if (entity.getBarcodeImagePath() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getBarcodeImagePath());
        }
        if (entity.getMrpImagePath() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getMrpImagePath());
        }
        if (entity.getThumbnailPath() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getThumbnailPath());
        }
        final String _tmp = __roomConverters.fromProductStatus(entity.getStatus());
        if (_tmp == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp);
        }
        statement.bindLong(12, entity.getCreatedAt());
        statement.bindLong(13, entity.getUpdatedAt());
        if (entity.getWeight() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getWeight());
        }
        if (entity.getFrontImageUrl() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getFrontImageUrl());
        }
        if (entity.getCatalogImagePath() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getCatalogImagePath());
        }
        statement.bindLong(17, entity.isPendingSync());
        if (entity.getCode() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getCode());
        }
        statement.bindLong(19, entity.getId());
      }
    };
    this.__updateAdapterOfArrival = new EntityDeletionOrUpdateAdapter<Arrival>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `arrivals` SET `id` = ?,`arrivalId` = ?,`barcode` = ?,`productName` = ?,`mrp` = ?,`quantityReceived` = ?,`status` = ?,`createdByUid` = ?,`createdByName` = ?,`createdByEmail` = ?,`updatedByUid` = ?,`updatedByName` = ?,`createdAt` = ?,`updatedAt` = ?,`optionalSupplierName` = ?,`isPendingSync` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Arrival entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getArrivalId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getArrivalId());
        }
        if (entity.getBarcode() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getBarcode());
        }
        if (entity.getProductName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getProductName());
        }
        statement.bindDouble(5, entity.getMrp());
        statement.bindLong(6, entity.getQuantityReceived());
        final String _tmp = __roomConverters.fromProductStatus(entity.getStatus());
        if (_tmp == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp);
        }
        if (entity.getCreatedByUid() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getCreatedByUid());
        }
        if (entity.getCreatedByName() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getCreatedByName());
        }
        if (entity.getCreatedByEmail() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getCreatedByEmail());
        }
        if (entity.getUpdatedByUid() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getUpdatedByUid());
        }
        if (entity.getUpdatedByName() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getUpdatedByName());
        }
        statement.bindLong(13, entity.getCreatedAt());
        statement.bindLong(14, entity.getUpdatedAt());
        if (entity.getOptionalSupplierName() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getOptionalSupplierName());
        }
        statement.bindLong(16, entity.isPendingSync());
        statement.bindLong(17, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteProductById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM products WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkProductSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE products SET isPendingSync = 0 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkProductSyncedWithUrl = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE products SET isPendingSync = 0, frontImageUrl = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteArrivalById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM arrivals WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteArrivalByArrivalId = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM arrivals WHERE arrivalId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkArrivalSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE arrivals SET isPendingSync = 0 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkScanSessionSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE scan_sessions SET isPendingSync = 0 WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkActivityLogSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE activity_logs SET isPendingSync = 0 WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Product product, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfProduct.insertAndReturnId(product);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertArrival(final Arrival arrival, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfArrival.insertAndReturnId(arrival);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertScanSession(final ScanSession session,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfScanSession.insertAndReturnId(session);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertActivityLog(final ActivityLog log,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfActivityLog.insertAndReturnId(log);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Product product, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfProduct.handle(product);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateArrival(final Arrival arrival, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfArrival.handle(arrival);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteProductById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteProductById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteProductById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markProductSynced(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkProductSynced.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkProductSynced.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markProductSyncedWithUrl(final long id, final String imageUrl,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkProductSyncedWithUrl.acquire();
        int _argIndex = 1;
        if (imageUrl == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, imageUrl);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkProductSyncedWithUrl.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteArrivalById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteArrivalById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteArrivalById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteArrivalByArrivalId(final String arrivalId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteArrivalByArrivalId.acquire();
        int _argIndex = 1;
        if (arrivalId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, arrivalId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteArrivalByArrivalId.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markArrivalSynced(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkArrivalSynced.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkArrivalSynced.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markScanSessionSynced(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkScanSessionSynced.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkScanSessionSynced.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markActivityLogSynced(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkActivityLogSynced.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkActivityLogSynced.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getById(final long id, final Continuation<? super Product> $completion) {
    final String _sql = "SELECT * FROM products WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Product>() {
      @Override
      @Nullable
      public Product call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfMrp = CursorUtil.getColumnIndexOrThrow(_cursor, "mrp");
          final int _cursorIndexOfBrand = CursorUtil.getColumnIndexOrThrow(_cursor, "brand");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfFrontImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "frontImagePath");
          final int _cursorIndexOfBarcodeImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "barcodeImagePath");
          final int _cursorIndexOfMrpImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "mrpImagePath");
          final int _cursorIndexOfThumbnailPath = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnailPath");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfFrontImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "frontImageUrl");
          final int _cursorIndexOfCatalogImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "catalogImagePath");
          final int _cursorIndexOfIsPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "isPendingSync");
          final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
          final Product _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpBarcode;
            if (_cursor.isNull(_cursorIndexOfBarcode)) {
              _tmpBarcode = null;
            } else {
              _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            }
            final double _tmpMrp;
            _tmpMrp = _cursor.getDouble(_cursorIndexOfMrp);
            final String _tmpBrand;
            if (_cursor.isNull(_cursorIndexOfBrand)) {
              _tmpBrand = null;
            } else {
              _tmpBrand = _cursor.getString(_cursorIndexOfBrand);
            }
            final Integer _tmpQuantity;
            if (_cursor.isNull(_cursorIndexOfQuantity)) {
              _tmpQuantity = null;
            } else {
              _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            }
            final String _tmpFrontImagePath;
            if (_cursor.isNull(_cursorIndexOfFrontImagePath)) {
              _tmpFrontImagePath = null;
            } else {
              _tmpFrontImagePath = _cursor.getString(_cursorIndexOfFrontImagePath);
            }
            final String _tmpBarcodeImagePath;
            if (_cursor.isNull(_cursorIndexOfBarcodeImagePath)) {
              _tmpBarcodeImagePath = null;
            } else {
              _tmpBarcodeImagePath = _cursor.getString(_cursorIndexOfBarcodeImagePath);
            }
            final String _tmpMrpImagePath;
            if (_cursor.isNull(_cursorIndexOfMrpImagePath)) {
              _tmpMrpImagePath = null;
            } else {
              _tmpMrpImagePath = _cursor.getString(_cursorIndexOfMrpImagePath);
            }
            final String _tmpThumbnailPath;
            if (_cursor.isNull(_cursorIndexOfThumbnailPath)) {
              _tmpThumbnailPath = null;
            } else {
              _tmpThumbnailPath = _cursor.getString(_cursorIndexOfThumbnailPath);
            }
            final ProductStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __roomConverters.toProductStatus(_tmp);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpWeight;
            if (_cursor.isNull(_cursorIndexOfWeight)) {
              _tmpWeight = null;
            } else {
              _tmpWeight = _cursor.getString(_cursorIndexOfWeight);
            }
            final String _tmpFrontImageUrl;
            if (_cursor.isNull(_cursorIndexOfFrontImageUrl)) {
              _tmpFrontImageUrl = null;
            } else {
              _tmpFrontImageUrl = _cursor.getString(_cursorIndexOfFrontImageUrl);
            }
            final String _tmpCatalogImagePath;
            if (_cursor.isNull(_cursorIndexOfCatalogImagePath)) {
              _tmpCatalogImagePath = null;
            } else {
              _tmpCatalogImagePath = _cursor.getString(_cursorIndexOfCatalogImagePath);
            }
            final int _tmpIsPendingSync;
            _tmpIsPendingSync = _cursor.getInt(_cursorIndexOfIsPendingSync);
            final String _tmpCode;
            if (_cursor.isNull(_cursorIndexOfCode)) {
              _tmpCode = null;
            } else {
              _tmpCode = _cursor.getString(_cursorIndexOfCode);
            }
            _result = new Product(_tmpId,_tmpName,_tmpBarcode,_tmpMrp,_tmpBrand,_tmpQuantity,_tmpFrontImagePath,_tmpBarcodeImagePath,_tmpMrpImagePath,_tmpThumbnailPath,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpWeight,_tmpFrontImageUrl,_tmpCatalogImagePath,_tmpIsPendingSync,_tmpCode);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object findByBarcode(final String barcode,
      final Continuation<? super Product> $completion) {
    final String _sql = "SELECT * FROM products WHERE barcode = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (barcode == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, barcode);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Product>() {
      @Override
      @Nullable
      public Product call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfMrp = CursorUtil.getColumnIndexOrThrow(_cursor, "mrp");
          final int _cursorIndexOfBrand = CursorUtil.getColumnIndexOrThrow(_cursor, "brand");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfFrontImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "frontImagePath");
          final int _cursorIndexOfBarcodeImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "barcodeImagePath");
          final int _cursorIndexOfMrpImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "mrpImagePath");
          final int _cursorIndexOfThumbnailPath = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnailPath");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfFrontImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "frontImageUrl");
          final int _cursorIndexOfCatalogImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "catalogImagePath");
          final int _cursorIndexOfIsPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "isPendingSync");
          final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
          final Product _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpBarcode;
            if (_cursor.isNull(_cursorIndexOfBarcode)) {
              _tmpBarcode = null;
            } else {
              _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            }
            final double _tmpMrp;
            _tmpMrp = _cursor.getDouble(_cursorIndexOfMrp);
            final String _tmpBrand;
            if (_cursor.isNull(_cursorIndexOfBrand)) {
              _tmpBrand = null;
            } else {
              _tmpBrand = _cursor.getString(_cursorIndexOfBrand);
            }
            final Integer _tmpQuantity;
            if (_cursor.isNull(_cursorIndexOfQuantity)) {
              _tmpQuantity = null;
            } else {
              _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            }
            final String _tmpFrontImagePath;
            if (_cursor.isNull(_cursorIndexOfFrontImagePath)) {
              _tmpFrontImagePath = null;
            } else {
              _tmpFrontImagePath = _cursor.getString(_cursorIndexOfFrontImagePath);
            }
            final String _tmpBarcodeImagePath;
            if (_cursor.isNull(_cursorIndexOfBarcodeImagePath)) {
              _tmpBarcodeImagePath = null;
            } else {
              _tmpBarcodeImagePath = _cursor.getString(_cursorIndexOfBarcodeImagePath);
            }
            final String _tmpMrpImagePath;
            if (_cursor.isNull(_cursorIndexOfMrpImagePath)) {
              _tmpMrpImagePath = null;
            } else {
              _tmpMrpImagePath = _cursor.getString(_cursorIndexOfMrpImagePath);
            }
            final String _tmpThumbnailPath;
            if (_cursor.isNull(_cursorIndexOfThumbnailPath)) {
              _tmpThumbnailPath = null;
            } else {
              _tmpThumbnailPath = _cursor.getString(_cursorIndexOfThumbnailPath);
            }
            final ProductStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __roomConverters.toProductStatus(_tmp);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpWeight;
            if (_cursor.isNull(_cursorIndexOfWeight)) {
              _tmpWeight = null;
            } else {
              _tmpWeight = _cursor.getString(_cursorIndexOfWeight);
            }
            final String _tmpFrontImageUrl;
            if (_cursor.isNull(_cursorIndexOfFrontImageUrl)) {
              _tmpFrontImageUrl = null;
            } else {
              _tmpFrontImageUrl = _cursor.getString(_cursorIndexOfFrontImageUrl);
            }
            final String _tmpCatalogImagePath;
            if (_cursor.isNull(_cursorIndexOfCatalogImagePath)) {
              _tmpCatalogImagePath = null;
            } else {
              _tmpCatalogImagePath = _cursor.getString(_cursorIndexOfCatalogImagePath);
            }
            final int _tmpIsPendingSync;
            _tmpIsPendingSync = _cursor.getInt(_cursorIndexOfIsPendingSync);
            final String _tmpCode;
            if (_cursor.isNull(_cursorIndexOfCode)) {
              _tmpCode = null;
            } else {
              _tmpCode = _cursor.getString(_cursorIndexOfCode);
            }
            _result = new Product(_tmpId,_tmpName,_tmpBarcode,_tmpMrp,_tmpBrand,_tmpQuantity,_tmpFrontImagePath,_tmpBarcodeImagePath,_tmpMrpImagePath,_tmpThumbnailPath,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpWeight,_tmpFrontImageUrl,_tmpCatalogImagePath,_tmpIsPendingSync,_tmpCode);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllProducts(final Continuation<? super List<Product>> $completion) {
    final String _sql = "SELECT * FROM products ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Product>>() {
      @Override
      @NonNull
      public List<Product> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfMrp = CursorUtil.getColumnIndexOrThrow(_cursor, "mrp");
          final int _cursorIndexOfBrand = CursorUtil.getColumnIndexOrThrow(_cursor, "brand");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfFrontImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "frontImagePath");
          final int _cursorIndexOfBarcodeImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "barcodeImagePath");
          final int _cursorIndexOfMrpImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "mrpImagePath");
          final int _cursorIndexOfThumbnailPath = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnailPath");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfFrontImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "frontImageUrl");
          final int _cursorIndexOfCatalogImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "catalogImagePath");
          final int _cursorIndexOfIsPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "isPendingSync");
          final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
          final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Product _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpBarcode;
            if (_cursor.isNull(_cursorIndexOfBarcode)) {
              _tmpBarcode = null;
            } else {
              _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            }
            final double _tmpMrp;
            _tmpMrp = _cursor.getDouble(_cursorIndexOfMrp);
            final String _tmpBrand;
            if (_cursor.isNull(_cursorIndexOfBrand)) {
              _tmpBrand = null;
            } else {
              _tmpBrand = _cursor.getString(_cursorIndexOfBrand);
            }
            final Integer _tmpQuantity;
            if (_cursor.isNull(_cursorIndexOfQuantity)) {
              _tmpQuantity = null;
            } else {
              _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            }
            final String _tmpFrontImagePath;
            if (_cursor.isNull(_cursorIndexOfFrontImagePath)) {
              _tmpFrontImagePath = null;
            } else {
              _tmpFrontImagePath = _cursor.getString(_cursorIndexOfFrontImagePath);
            }
            final String _tmpBarcodeImagePath;
            if (_cursor.isNull(_cursorIndexOfBarcodeImagePath)) {
              _tmpBarcodeImagePath = null;
            } else {
              _tmpBarcodeImagePath = _cursor.getString(_cursorIndexOfBarcodeImagePath);
            }
            final String _tmpMrpImagePath;
            if (_cursor.isNull(_cursorIndexOfMrpImagePath)) {
              _tmpMrpImagePath = null;
            } else {
              _tmpMrpImagePath = _cursor.getString(_cursorIndexOfMrpImagePath);
            }
            final String _tmpThumbnailPath;
            if (_cursor.isNull(_cursorIndexOfThumbnailPath)) {
              _tmpThumbnailPath = null;
            } else {
              _tmpThumbnailPath = _cursor.getString(_cursorIndexOfThumbnailPath);
            }
            final ProductStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __roomConverters.toProductStatus(_tmp);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpWeight;
            if (_cursor.isNull(_cursorIndexOfWeight)) {
              _tmpWeight = null;
            } else {
              _tmpWeight = _cursor.getString(_cursorIndexOfWeight);
            }
            final String _tmpFrontImageUrl;
            if (_cursor.isNull(_cursorIndexOfFrontImageUrl)) {
              _tmpFrontImageUrl = null;
            } else {
              _tmpFrontImageUrl = _cursor.getString(_cursorIndexOfFrontImageUrl);
            }
            final String _tmpCatalogImagePath;
            if (_cursor.isNull(_cursorIndexOfCatalogImagePath)) {
              _tmpCatalogImagePath = null;
            } else {
              _tmpCatalogImagePath = _cursor.getString(_cursorIndexOfCatalogImagePath);
            }
            final int _tmpIsPendingSync;
            _tmpIsPendingSync = _cursor.getInt(_cursorIndexOfIsPendingSync);
            final String _tmpCode;
            if (_cursor.isNull(_cursorIndexOfCode)) {
              _tmpCode = null;
            } else {
              _tmpCode = _cursor.getString(_cursorIndexOfCode);
            }
            _item = new Product(_tmpId,_tmpName,_tmpBarcode,_tmpMrp,_tmpBrand,_tmpQuantity,_tmpFrontImagePath,_tmpBarcodeImagePath,_tmpMrpImagePath,_tmpThumbnailPath,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpWeight,_tmpFrontImageUrl,_tmpCatalogImagePath,_tmpIsPendingSync,_tmpCode);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnsyncedProducts(final Continuation<? super List<Product>> $completion) {
    final String _sql = "SELECT * FROM products WHERE isPendingSync = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Product>>() {
      @Override
      @NonNull
      public List<Product> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfMrp = CursorUtil.getColumnIndexOrThrow(_cursor, "mrp");
          final int _cursorIndexOfBrand = CursorUtil.getColumnIndexOrThrow(_cursor, "brand");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfFrontImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "frontImagePath");
          final int _cursorIndexOfBarcodeImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "barcodeImagePath");
          final int _cursorIndexOfMrpImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "mrpImagePath");
          final int _cursorIndexOfThumbnailPath = CursorUtil.getColumnIndexOrThrow(_cursor, "thumbnailPath");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfFrontImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "frontImageUrl");
          final int _cursorIndexOfCatalogImagePath = CursorUtil.getColumnIndexOrThrow(_cursor, "catalogImagePath");
          final int _cursorIndexOfIsPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "isPendingSync");
          final int _cursorIndexOfCode = CursorUtil.getColumnIndexOrThrow(_cursor, "code");
          final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Product _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpBarcode;
            if (_cursor.isNull(_cursorIndexOfBarcode)) {
              _tmpBarcode = null;
            } else {
              _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            }
            final double _tmpMrp;
            _tmpMrp = _cursor.getDouble(_cursorIndexOfMrp);
            final String _tmpBrand;
            if (_cursor.isNull(_cursorIndexOfBrand)) {
              _tmpBrand = null;
            } else {
              _tmpBrand = _cursor.getString(_cursorIndexOfBrand);
            }
            final Integer _tmpQuantity;
            if (_cursor.isNull(_cursorIndexOfQuantity)) {
              _tmpQuantity = null;
            } else {
              _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            }
            final String _tmpFrontImagePath;
            if (_cursor.isNull(_cursorIndexOfFrontImagePath)) {
              _tmpFrontImagePath = null;
            } else {
              _tmpFrontImagePath = _cursor.getString(_cursorIndexOfFrontImagePath);
            }
            final String _tmpBarcodeImagePath;
            if (_cursor.isNull(_cursorIndexOfBarcodeImagePath)) {
              _tmpBarcodeImagePath = null;
            } else {
              _tmpBarcodeImagePath = _cursor.getString(_cursorIndexOfBarcodeImagePath);
            }
            final String _tmpMrpImagePath;
            if (_cursor.isNull(_cursorIndexOfMrpImagePath)) {
              _tmpMrpImagePath = null;
            } else {
              _tmpMrpImagePath = _cursor.getString(_cursorIndexOfMrpImagePath);
            }
            final String _tmpThumbnailPath;
            if (_cursor.isNull(_cursorIndexOfThumbnailPath)) {
              _tmpThumbnailPath = null;
            } else {
              _tmpThumbnailPath = _cursor.getString(_cursorIndexOfThumbnailPath);
            }
            final ProductStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __roomConverters.toProductStatus(_tmp);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpWeight;
            if (_cursor.isNull(_cursorIndexOfWeight)) {
              _tmpWeight = null;
            } else {
              _tmpWeight = _cursor.getString(_cursorIndexOfWeight);
            }
            final String _tmpFrontImageUrl;
            if (_cursor.isNull(_cursorIndexOfFrontImageUrl)) {
              _tmpFrontImageUrl = null;
            } else {
              _tmpFrontImageUrl = _cursor.getString(_cursorIndexOfFrontImageUrl);
            }
            final String _tmpCatalogImagePath;
            if (_cursor.isNull(_cursorIndexOfCatalogImagePath)) {
              _tmpCatalogImagePath = null;
            } else {
              _tmpCatalogImagePath = _cursor.getString(_cursorIndexOfCatalogImagePath);
            }
            final int _tmpIsPendingSync;
            _tmpIsPendingSync = _cursor.getInt(_cursorIndexOfIsPendingSync);
            final String _tmpCode;
            if (_cursor.isNull(_cursorIndexOfCode)) {
              _tmpCode = null;
            } else {
              _tmpCode = _cursor.getString(_cursorIndexOfCode);
            }
            _item = new Product(_tmpId,_tmpName,_tmpBarcode,_tmpMrp,_tmpBrand,_tmpQuantity,_tmpFrontImagePath,_tmpBarcodeImagePath,_tmpMrpImagePath,_tmpThumbnailPath,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpWeight,_tmpFrontImageUrl,_tmpCatalogImagePath,_tmpIsPendingSync,_tmpCode);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getArrivalById(final long id, final Continuation<? super Arrival> $completion) {
    final String _sql = "SELECT * FROM arrivals WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Arrival>() {
      @Override
      @Nullable
      public Arrival call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfArrivalId = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalId");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfMrp = CursorUtil.getColumnIndexOrThrow(_cursor, "mrp");
          final int _cursorIndexOfQuantityReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "quantityReceived");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedByUid = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByUid");
          final int _cursorIndexOfCreatedByName = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByName");
          final int _cursorIndexOfCreatedByEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByEmail");
          final int _cursorIndexOfUpdatedByUid = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByUid");
          final int _cursorIndexOfUpdatedByName = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfOptionalSupplierName = CursorUtil.getColumnIndexOrThrow(_cursor, "optionalSupplierName");
          final int _cursorIndexOfIsPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "isPendingSync");
          final Arrival _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpArrivalId;
            if (_cursor.isNull(_cursorIndexOfArrivalId)) {
              _tmpArrivalId = null;
            } else {
              _tmpArrivalId = _cursor.getString(_cursorIndexOfArrivalId);
            }
            final String _tmpBarcode;
            if (_cursor.isNull(_cursorIndexOfBarcode)) {
              _tmpBarcode = null;
            } else {
              _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            }
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final double _tmpMrp;
            _tmpMrp = _cursor.getDouble(_cursorIndexOfMrp);
            final int _tmpQuantityReceived;
            _tmpQuantityReceived = _cursor.getInt(_cursorIndexOfQuantityReceived);
            final ProductStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __roomConverters.toProductStatus(_tmp);
            final String _tmpCreatedByUid;
            if (_cursor.isNull(_cursorIndexOfCreatedByUid)) {
              _tmpCreatedByUid = null;
            } else {
              _tmpCreatedByUid = _cursor.getString(_cursorIndexOfCreatedByUid);
            }
            final String _tmpCreatedByName;
            if (_cursor.isNull(_cursorIndexOfCreatedByName)) {
              _tmpCreatedByName = null;
            } else {
              _tmpCreatedByName = _cursor.getString(_cursorIndexOfCreatedByName);
            }
            final String _tmpCreatedByEmail;
            if (_cursor.isNull(_cursorIndexOfCreatedByEmail)) {
              _tmpCreatedByEmail = null;
            } else {
              _tmpCreatedByEmail = _cursor.getString(_cursorIndexOfCreatedByEmail);
            }
            final String _tmpUpdatedByUid;
            if (_cursor.isNull(_cursorIndexOfUpdatedByUid)) {
              _tmpUpdatedByUid = null;
            } else {
              _tmpUpdatedByUid = _cursor.getString(_cursorIndexOfUpdatedByUid);
            }
            final String _tmpUpdatedByName;
            if (_cursor.isNull(_cursorIndexOfUpdatedByName)) {
              _tmpUpdatedByName = null;
            } else {
              _tmpUpdatedByName = _cursor.getString(_cursorIndexOfUpdatedByName);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpOptionalSupplierName;
            if (_cursor.isNull(_cursorIndexOfOptionalSupplierName)) {
              _tmpOptionalSupplierName = null;
            } else {
              _tmpOptionalSupplierName = _cursor.getString(_cursorIndexOfOptionalSupplierName);
            }
            final int _tmpIsPendingSync;
            _tmpIsPendingSync = _cursor.getInt(_cursorIndexOfIsPendingSync);
            _result = new Arrival(_tmpId,_tmpArrivalId,_tmpBarcode,_tmpProductName,_tmpMrp,_tmpQuantityReceived,_tmpStatus,_tmpCreatedByUid,_tmpCreatedByName,_tmpCreatedByEmail,_tmpUpdatedByUid,_tmpUpdatedByName,_tmpCreatedAt,_tmpUpdatedAt,_tmpOptionalSupplierName,_tmpIsPendingSync);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getArrivalByArrivalId(final String arrivalId,
      final Continuation<? super Arrival> $completion) {
    final String _sql = "SELECT * FROM arrivals WHERE arrivalId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (arrivalId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, arrivalId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Arrival>() {
      @Override
      @Nullable
      public Arrival call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfArrivalId = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalId");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfMrp = CursorUtil.getColumnIndexOrThrow(_cursor, "mrp");
          final int _cursorIndexOfQuantityReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "quantityReceived");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedByUid = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByUid");
          final int _cursorIndexOfCreatedByName = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByName");
          final int _cursorIndexOfCreatedByEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByEmail");
          final int _cursorIndexOfUpdatedByUid = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByUid");
          final int _cursorIndexOfUpdatedByName = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfOptionalSupplierName = CursorUtil.getColumnIndexOrThrow(_cursor, "optionalSupplierName");
          final int _cursorIndexOfIsPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "isPendingSync");
          final Arrival _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpArrivalId;
            if (_cursor.isNull(_cursorIndexOfArrivalId)) {
              _tmpArrivalId = null;
            } else {
              _tmpArrivalId = _cursor.getString(_cursorIndexOfArrivalId);
            }
            final String _tmpBarcode;
            if (_cursor.isNull(_cursorIndexOfBarcode)) {
              _tmpBarcode = null;
            } else {
              _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            }
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final double _tmpMrp;
            _tmpMrp = _cursor.getDouble(_cursorIndexOfMrp);
            final int _tmpQuantityReceived;
            _tmpQuantityReceived = _cursor.getInt(_cursorIndexOfQuantityReceived);
            final ProductStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __roomConverters.toProductStatus(_tmp);
            final String _tmpCreatedByUid;
            if (_cursor.isNull(_cursorIndexOfCreatedByUid)) {
              _tmpCreatedByUid = null;
            } else {
              _tmpCreatedByUid = _cursor.getString(_cursorIndexOfCreatedByUid);
            }
            final String _tmpCreatedByName;
            if (_cursor.isNull(_cursorIndexOfCreatedByName)) {
              _tmpCreatedByName = null;
            } else {
              _tmpCreatedByName = _cursor.getString(_cursorIndexOfCreatedByName);
            }
            final String _tmpCreatedByEmail;
            if (_cursor.isNull(_cursorIndexOfCreatedByEmail)) {
              _tmpCreatedByEmail = null;
            } else {
              _tmpCreatedByEmail = _cursor.getString(_cursorIndexOfCreatedByEmail);
            }
            final String _tmpUpdatedByUid;
            if (_cursor.isNull(_cursorIndexOfUpdatedByUid)) {
              _tmpUpdatedByUid = null;
            } else {
              _tmpUpdatedByUid = _cursor.getString(_cursorIndexOfUpdatedByUid);
            }
            final String _tmpUpdatedByName;
            if (_cursor.isNull(_cursorIndexOfUpdatedByName)) {
              _tmpUpdatedByName = null;
            } else {
              _tmpUpdatedByName = _cursor.getString(_cursorIndexOfUpdatedByName);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpOptionalSupplierName;
            if (_cursor.isNull(_cursorIndexOfOptionalSupplierName)) {
              _tmpOptionalSupplierName = null;
            } else {
              _tmpOptionalSupplierName = _cursor.getString(_cursorIndexOfOptionalSupplierName);
            }
            final int _tmpIsPendingSync;
            _tmpIsPendingSync = _cursor.getInt(_cursorIndexOfIsPendingSync);
            _result = new Arrival(_tmpId,_tmpArrivalId,_tmpBarcode,_tmpProductName,_tmpMrp,_tmpQuantityReceived,_tmpStatus,_tmpCreatedByUid,_tmpCreatedByName,_tmpCreatedByEmail,_tmpUpdatedByUid,_tmpUpdatedByName,_tmpCreatedAt,_tmpUpdatedAt,_tmpOptionalSupplierName,_tmpIsPendingSync);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllArrivals(final Continuation<? super List<Arrival>> $completion) {
    final String _sql = "SELECT * FROM arrivals ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Arrival>>() {
      @Override
      @NonNull
      public List<Arrival> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfArrivalId = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalId");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfMrp = CursorUtil.getColumnIndexOrThrow(_cursor, "mrp");
          final int _cursorIndexOfQuantityReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "quantityReceived");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedByUid = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByUid");
          final int _cursorIndexOfCreatedByName = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByName");
          final int _cursorIndexOfCreatedByEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByEmail");
          final int _cursorIndexOfUpdatedByUid = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByUid");
          final int _cursorIndexOfUpdatedByName = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfOptionalSupplierName = CursorUtil.getColumnIndexOrThrow(_cursor, "optionalSupplierName");
          final int _cursorIndexOfIsPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "isPendingSync");
          final List<Arrival> _result = new ArrayList<Arrival>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Arrival _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpArrivalId;
            if (_cursor.isNull(_cursorIndexOfArrivalId)) {
              _tmpArrivalId = null;
            } else {
              _tmpArrivalId = _cursor.getString(_cursorIndexOfArrivalId);
            }
            final String _tmpBarcode;
            if (_cursor.isNull(_cursorIndexOfBarcode)) {
              _tmpBarcode = null;
            } else {
              _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            }
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final double _tmpMrp;
            _tmpMrp = _cursor.getDouble(_cursorIndexOfMrp);
            final int _tmpQuantityReceived;
            _tmpQuantityReceived = _cursor.getInt(_cursorIndexOfQuantityReceived);
            final ProductStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __roomConverters.toProductStatus(_tmp);
            final String _tmpCreatedByUid;
            if (_cursor.isNull(_cursorIndexOfCreatedByUid)) {
              _tmpCreatedByUid = null;
            } else {
              _tmpCreatedByUid = _cursor.getString(_cursorIndexOfCreatedByUid);
            }
            final String _tmpCreatedByName;
            if (_cursor.isNull(_cursorIndexOfCreatedByName)) {
              _tmpCreatedByName = null;
            } else {
              _tmpCreatedByName = _cursor.getString(_cursorIndexOfCreatedByName);
            }
            final String _tmpCreatedByEmail;
            if (_cursor.isNull(_cursorIndexOfCreatedByEmail)) {
              _tmpCreatedByEmail = null;
            } else {
              _tmpCreatedByEmail = _cursor.getString(_cursorIndexOfCreatedByEmail);
            }
            final String _tmpUpdatedByUid;
            if (_cursor.isNull(_cursorIndexOfUpdatedByUid)) {
              _tmpUpdatedByUid = null;
            } else {
              _tmpUpdatedByUid = _cursor.getString(_cursorIndexOfUpdatedByUid);
            }
            final String _tmpUpdatedByName;
            if (_cursor.isNull(_cursorIndexOfUpdatedByName)) {
              _tmpUpdatedByName = null;
            } else {
              _tmpUpdatedByName = _cursor.getString(_cursorIndexOfUpdatedByName);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpOptionalSupplierName;
            if (_cursor.isNull(_cursorIndexOfOptionalSupplierName)) {
              _tmpOptionalSupplierName = null;
            } else {
              _tmpOptionalSupplierName = _cursor.getString(_cursorIndexOfOptionalSupplierName);
            }
            final int _tmpIsPendingSync;
            _tmpIsPendingSync = _cursor.getInt(_cursorIndexOfIsPendingSync);
            _item = new Arrival(_tmpId,_tmpArrivalId,_tmpBarcode,_tmpProductName,_tmpMrp,_tmpQuantityReceived,_tmpStatus,_tmpCreatedByUid,_tmpCreatedByName,_tmpCreatedByEmail,_tmpUpdatedByUid,_tmpUpdatedByName,_tmpCreatedAt,_tmpUpdatedAt,_tmpOptionalSupplierName,_tmpIsPendingSync);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getArrivalsByDay(final long day,
      final Continuation<? super List<Arrival>> $completion) {
    final String _sql = "SELECT * FROM arrivals WHERE DATE(createdAt / 1000, 'unixepoch') = DATE(? / 1000, 'unixepoch') ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, day);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Arrival>>() {
      @Override
      @NonNull
      public List<Arrival> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfArrivalId = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalId");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfMrp = CursorUtil.getColumnIndexOrThrow(_cursor, "mrp");
          final int _cursorIndexOfQuantityReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "quantityReceived");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedByUid = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByUid");
          final int _cursorIndexOfCreatedByName = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByName");
          final int _cursorIndexOfCreatedByEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByEmail");
          final int _cursorIndexOfUpdatedByUid = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByUid");
          final int _cursorIndexOfUpdatedByName = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfOptionalSupplierName = CursorUtil.getColumnIndexOrThrow(_cursor, "optionalSupplierName");
          final int _cursorIndexOfIsPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "isPendingSync");
          final List<Arrival> _result = new ArrayList<Arrival>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Arrival _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpArrivalId;
            if (_cursor.isNull(_cursorIndexOfArrivalId)) {
              _tmpArrivalId = null;
            } else {
              _tmpArrivalId = _cursor.getString(_cursorIndexOfArrivalId);
            }
            final String _tmpBarcode;
            if (_cursor.isNull(_cursorIndexOfBarcode)) {
              _tmpBarcode = null;
            } else {
              _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            }
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final double _tmpMrp;
            _tmpMrp = _cursor.getDouble(_cursorIndexOfMrp);
            final int _tmpQuantityReceived;
            _tmpQuantityReceived = _cursor.getInt(_cursorIndexOfQuantityReceived);
            final ProductStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __roomConverters.toProductStatus(_tmp);
            final String _tmpCreatedByUid;
            if (_cursor.isNull(_cursorIndexOfCreatedByUid)) {
              _tmpCreatedByUid = null;
            } else {
              _tmpCreatedByUid = _cursor.getString(_cursorIndexOfCreatedByUid);
            }
            final String _tmpCreatedByName;
            if (_cursor.isNull(_cursorIndexOfCreatedByName)) {
              _tmpCreatedByName = null;
            } else {
              _tmpCreatedByName = _cursor.getString(_cursorIndexOfCreatedByName);
            }
            final String _tmpCreatedByEmail;
            if (_cursor.isNull(_cursorIndexOfCreatedByEmail)) {
              _tmpCreatedByEmail = null;
            } else {
              _tmpCreatedByEmail = _cursor.getString(_cursorIndexOfCreatedByEmail);
            }
            final String _tmpUpdatedByUid;
            if (_cursor.isNull(_cursorIndexOfUpdatedByUid)) {
              _tmpUpdatedByUid = null;
            } else {
              _tmpUpdatedByUid = _cursor.getString(_cursorIndexOfUpdatedByUid);
            }
            final String _tmpUpdatedByName;
            if (_cursor.isNull(_cursorIndexOfUpdatedByName)) {
              _tmpUpdatedByName = null;
            } else {
              _tmpUpdatedByName = _cursor.getString(_cursorIndexOfUpdatedByName);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpOptionalSupplierName;
            if (_cursor.isNull(_cursorIndexOfOptionalSupplierName)) {
              _tmpOptionalSupplierName = null;
            } else {
              _tmpOptionalSupplierName = _cursor.getString(_cursorIndexOfOptionalSupplierName);
            }
            final int _tmpIsPendingSync;
            _tmpIsPendingSync = _cursor.getInt(_cursorIndexOfIsPendingSync);
            _item = new Arrival(_tmpId,_tmpArrivalId,_tmpBarcode,_tmpProductName,_tmpMrp,_tmpQuantityReceived,_tmpStatus,_tmpCreatedByUid,_tmpCreatedByName,_tmpCreatedByEmail,_tmpUpdatedByUid,_tmpUpdatedByName,_tmpCreatedAt,_tmpUpdatedAt,_tmpOptionalSupplierName,_tmpIsPendingSync);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getArrivalsForToday(final String barcode, final double mrp, final long startOfDay,
      final long endOfDay, final Continuation<? super List<Arrival>> $completion) {
    final String _sql = "SELECT * FROM arrivals WHERE barcode = ? AND mrp = ? AND createdAt >= ? AND createdAt <= ? ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    if (barcode == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, barcode);
    }
    _argIndex = 2;
    _statement.bindDouble(_argIndex, mrp);
    _argIndex = 3;
    _statement.bindLong(_argIndex, startOfDay);
    _argIndex = 4;
    _statement.bindLong(_argIndex, endOfDay);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Arrival>>() {
      @Override
      @NonNull
      public List<Arrival> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfArrivalId = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalId");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfMrp = CursorUtil.getColumnIndexOrThrow(_cursor, "mrp");
          final int _cursorIndexOfQuantityReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "quantityReceived");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedByUid = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByUid");
          final int _cursorIndexOfCreatedByName = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByName");
          final int _cursorIndexOfCreatedByEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByEmail");
          final int _cursorIndexOfUpdatedByUid = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByUid");
          final int _cursorIndexOfUpdatedByName = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfOptionalSupplierName = CursorUtil.getColumnIndexOrThrow(_cursor, "optionalSupplierName");
          final int _cursorIndexOfIsPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "isPendingSync");
          final List<Arrival> _result = new ArrayList<Arrival>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Arrival _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpArrivalId;
            if (_cursor.isNull(_cursorIndexOfArrivalId)) {
              _tmpArrivalId = null;
            } else {
              _tmpArrivalId = _cursor.getString(_cursorIndexOfArrivalId);
            }
            final String _tmpBarcode;
            if (_cursor.isNull(_cursorIndexOfBarcode)) {
              _tmpBarcode = null;
            } else {
              _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            }
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final double _tmpMrp;
            _tmpMrp = _cursor.getDouble(_cursorIndexOfMrp);
            final int _tmpQuantityReceived;
            _tmpQuantityReceived = _cursor.getInt(_cursorIndexOfQuantityReceived);
            final ProductStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __roomConverters.toProductStatus(_tmp);
            final String _tmpCreatedByUid;
            if (_cursor.isNull(_cursorIndexOfCreatedByUid)) {
              _tmpCreatedByUid = null;
            } else {
              _tmpCreatedByUid = _cursor.getString(_cursorIndexOfCreatedByUid);
            }
            final String _tmpCreatedByName;
            if (_cursor.isNull(_cursorIndexOfCreatedByName)) {
              _tmpCreatedByName = null;
            } else {
              _tmpCreatedByName = _cursor.getString(_cursorIndexOfCreatedByName);
            }
            final String _tmpCreatedByEmail;
            if (_cursor.isNull(_cursorIndexOfCreatedByEmail)) {
              _tmpCreatedByEmail = null;
            } else {
              _tmpCreatedByEmail = _cursor.getString(_cursorIndexOfCreatedByEmail);
            }
            final String _tmpUpdatedByUid;
            if (_cursor.isNull(_cursorIndexOfUpdatedByUid)) {
              _tmpUpdatedByUid = null;
            } else {
              _tmpUpdatedByUid = _cursor.getString(_cursorIndexOfUpdatedByUid);
            }
            final String _tmpUpdatedByName;
            if (_cursor.isNull(_cursorIndexOfUpdatedByName)) {
              _tmpUpdatedByName = null;
            } else {
              _tmpUpdatedByName = _cursor.getString(_cursorIndexOfUpdatedByName);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpOptionalSupplierName;
            if (_cursor.isNull(_cursorIndexOfOptionalSupplierName)) {
              _tmpOptionalSupplierName = null;
            } else {
              _tmpOptionalSupplierName = _cursor.getString(_cursorIndexOfOptionalSupplierName);
            }
            final int _tmpIsPendingSync;
            _tmpIsPendingSync = _cursor.getInt(_cursorIndexOfIsPendingSync);
            _item = new Arrival(_tmpId,_tmpArrivalId,_tmpBarcode,_tmpProductName,_tmpMrp,_tmpQuantityReceived,_tmpStatus,_tmpCreatedByUid,_tmpCreatedByName,_tmpCreatedByEmail,_tmpUpdatedByUid,_tmpUpdatedByName,_tmpCreatedAt,_tmpUpdatedAt,_tmpOptionalSupplierName,_tmpIsPendingSync);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object countArrivalsByStatus(final ProductStatus status,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM arrivals WHERE status = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __roomConverters.fromProductStatus(status);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp_1;
            if (_cursor.isNull(0)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(0);
            }
            _result = _tmp_1;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnsyncedArrivals(final Continuation<? super List<Arrival>> $completion) {
    final String _sql = "SELECT * FROM arrivals WHERE isPendingSync = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Arrival>>() {
      @Override
      @NonNull
      public List<Arrival> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfArrivalId = CursorUtil.getColumnIndexOrThrow(_cursor, "arrivalId");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfMrp = CursorUtil.getColumnIndexOrThrow(_cursor, "mrp");
          final int _cursorIndexOfQuantityReceived = CursorUtil.getColumnIndexOrThrow(_cursor, "quantityReceived");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedByUid = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByUid");
          final int _cursorIndexOfCreatedByName = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByName");
          final int _cursorIndexOfCreatedByEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "createdByEmail");
          final int _cursorIndexOfUpdatedByUid = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByUid");
          final int _cursorIndexOfUpdatedByName = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedByName");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfOptionalSupplierName = CursorUtil.getColumnIndexOrThrow(_cursor, "optionalSupplierName");
          final int _cursorIndexOfIsPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "isPendingSync");
          final List<Arrival> _result = new ArrayList<Arrival>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Arrival _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpArrivalId;
            if (_cursor.isNull(_cursorIndexOfArrivalId)) {
              _tmpArrivalId = null;
            } else {
              _tmpArrivalId = _cursor.getString(_cursorIndexOfArrivalId);
            }
            final String _tmpBarcode;
            if (_cursor.isNull(_cursorIndexOfBarcode)) {
              _tmpBarcode = null;
            } else {
              _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            }
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final double _tmpMrp;
            _tmpMrp = _cursor.getDouble(_cursorIndexOfMrp);
            final int _tmpQuantityReceived;
            _tmpQuantityReceived = _cursor.getInt(_cursorIndexOfQuantityReceived);
            final ProductStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __roomConverters.toProductStatus(_tmp);
            final String _tmpCreatedByUid;
            if (_cursor.isNull(_cursorIndexOfCreatedByUid)) {
              _tmpCreatedByUid = null;
            } else {
              _tmpCreatedByUid = _cursor.getString(_cursorIndexOfCreatedByUid);
            }
            final String _tmpCreatedByName;
            if (_cursor.isNull(_cursorIndexOfCreatedByName)) {
              _tmpCreatedByName = null;
            } else {
              _tmpCreatedByName = _cursor.getString(_cursorIndexOfCreatedByName);
            }
            final String _tmpCreatedByEmail;
            if (_cursor.isNull(_cursorIndexOfCreatedByEmail)) {
              _tmpCreatedByEmail = null;
            } else {
              _tmpCreatedByEmail = _cursor.getString(_cursorIndexOfCreatedByEmail);
            }
            final String _tmpUpdatedByUid;
            if (_cursor.isNull(_cursorIndexOfUpdatedByUid)) {
              _tmpUpdatedByUid = null;
            } else {
              _tmpUpdatedByUid = _cursor.getString(_cursorIndexOfUpdatedByUid);
            }
            final String _tmpUpdatedByName;
            if (_cursor.isNull(_cursorIndexOfUpdatedByName)) {
              _tmpUpdatedByName = null;
            } else {
              _tmpUpdatedByName = _cursor.getString(_cursorIndexOfUpdatedByName);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpOptionalSupplierName;
            if (_cursor.isNull(_cursorIndexOfOptionalSupplierName)) {
              _tmpOptionalSupplierName = null;
            } else {
              _tmpOptionalSupplierName = _cursor.getString(_cursorIndexOfOptionalSupplierName);
            }
            final int _tmpIsPendingSync;
            _tmpIsPendingSync = _cursor.getInt(_cursorIndexOfIsPendingSync);
            _item = new Arrival(_tmpId,_tmpArrivalId,_tmpBarcode,_tmpProductName,_tmpMrp,_tmpQuantityReceived,_tmpStatus,_tmpCreatedByUid,_tmpCreatedByName,_tmpCreatedByEmail,_tmpUpdatedByUid,_tmpUpdatedByName,_tmpCreatedAt,_tmpUpdatedAt,_tmpOptionalSupplierName,_tmpIsPendingSync);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnsyncedScanSessions(final Continuation<? super List<ScanSession>> $completion) {
    final String _sql = "SELECT * FROM scan_sessions WHERE isPendingSync = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ScanSession>>() {
      @Override
      @NonNull
      public List<ScanSession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmployeeUid = CursorUtil.getColumnIndexOrThrow(_cursor, "employeeUid");
          final int _cursorIndexOfEmployeeName = CursorUtil.getColumnIndexOrThrow(_cursor, "employeeName");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfIsPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "isPendingSync");
          final List<ScanSession> _result = new ArrayList<ScanSession>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScanSession _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpEmployeeUid;
            if (_cursor.isNull(_cursorIndexOfEmployeeUid)) {
              _tmpEmployeeUid = null;
            } else {
              _tmpEmployeeUid = _cursor.getString(_cursorIndexOfEmployeeUid);
            }
            final String _tmpEmployeeName;
            if (_cursor.isNull(_cursorIndexOfEmployeeName)) {
              _tmpEmployeeName = null;
            } else {
              _tmpEmployeeName = _cursor.getString(_cursorIndexOfEmployeeName);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpBarcode;
            if (_cursor.isNull(_cursorIndexOfBarcode)) {
              _tmpBarcode = null;
            } else {
              _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            }
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final String _tmpAction;
            if (_cursor.isNull(_cursorIndexOfAction)) {
              _tmpAction = null;
            } else {
              _tmpAction = _cursor.getString(_cursorIndexOfAction);
            }
            final int _tmpIsPendingSync;
            _tmpIsPendingSync = _cursor.getInt(_cursorIndexOfIsPendingSync);
            _item = new ScanSession(_tmpId,_tmpEmployeeUid,_tmpEmployeeName,_tmpTimestamp,_tmpBarcode,_tmpProductName,_tmpAction,_tmpIsPendingSync);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllScanSessions(final Continuation<? super List<ScanSession>> $completion) {
    final String _sql = "SELECT * FROM scan_sessions ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ScanSession>>() {
      @Override
      @NonNull
      public List<ScanSession> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfEmployeeUid = CursorUtil.getColumnIndexOrThrow(_cursor, "employeeUid");
          final int _cursorIndexOfEmployeeName = CursorUtil.getColumnIndexOrThrow(_cursor, "employeeName");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfIsPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "isPendingSync");
          final List<ScanSession> _result = new ArrayList<ScanSession>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ScanSession _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpEmployeeUid;
            if (_cursor.isNull(_cursorIndexOfEmployeeUid)) {
              _tmpEmployeeUid = null;
            } else {
              _tmpEmployeeUid = _cursor.getString(_cursorIndexOfEmployeeUid);
            }
            final String _tmpEmployeeName;
            if (_cursor.isNull(_cursorIndexOfEmployeeName)) {
              _tmpEmployeeName = null;
            } else {
              _tmpEmployeeName = _cursor.getString(_cursorIndexOfEmployeeName);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpBarcode;
            if (_cursor.isNull(_cursorIndexOfBarcode)) {
              _tmpBarcode = null;
            } else {
              _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            }
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final String _tmpAction;
            if (_cursor.isNull(_cursorIndexOfAction)) {
              _tmpAction = null;
            } else {
              _tmpAction = _cursor.getString(_cursorIndexOfAction);
            }
            final int _tmpIsPendingSync;
            _tmpIsPendingSync = _cursor.getInt(_cursorIndexOfIsPendingSync);
            _item = new ScanSession(_tmpId,_tmpEmployeeUid,_tmpEmployeeName,_tmpTimestamp,_tmpBarcode,_tmpProductName,_tmpAction,_tmpIsPendingSync);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getUnsyncedActivityLogs(final Continuation<? super List<ActivityLog>> $completion) {
    final String _sql = "SELECT * FROM activity_logs WHERE isPendingSync = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ActivityLog>>() {
      @Override
      @NonNull
      public List<ActivityLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfActivityId = CursorUtil.getColumnIndexOrThrow(_cursor, "activityId");
          final int _cursorIndexOfPerformedByUid = CursorUtil.getColumnIndexOrThrow(_cursor, "performedByUid");
          final int _cursorIndexOfPerformedByName = CursorUtil.getColumnIndexOrThrow(_cursor, "performedByName");
          final int _cursorIndexOfPerformedByEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "performedByEmail");
          final int _cursorIndexOfActionType = CursorUtil.getColumnIndexOrThrow(_cursor, "actionType");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "isPendingSync");
          final List<ActivityLog> _result = new ArrayList<ActivityLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ActivityLog _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpActivityId;
            if (_cursor.isNull(_cursorIndexOfActivityId)) {
              _tmpActivityId = null;
            } else {
              _tmpActivityId = _cursor.getString(_cursorIndexOfActivityId);
            }
            final String _tmpPerformedByUid;
            if (_cursor.isNull(_cursorIndexOfPerformedByUid)) {
              _tmpPerformedByUid = null;
            } else {
              _tmpPerformedByUid = _cursor.getString(_cursorIndexOfPerformedByUid);
            }
            final String _tmpPerformedByName;
            if (_cursor.isNull(_cursorIndexOfPerformedByName)) {
              _tmpPerformedByName = null;
            } else {
              _tmpPerformedByName = _cursor.getString(_cursorIndexOfPerformedByName);
            }
            final String _tmpPerformedByEmail;
            if (_cursor.isNull(_cursorIndexOfPerformedByEmail)) {
              _tmpPerformedByEmail = null;
            } else {
              _tmpPerformedByEmail = _cursor.getString(_cursorIndexOfPerformedByEmail);
            }
            final String _tmpActionType;
            if (_cursor.isNull(_cursorIndexOfActionType)) {
              _tmpActionType = null;
            } else {
              _tmpActionType = _cursor.getString(_cursorIndexOfActionType);
            }
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final String _tmpBarcode;
            if (_cursor.isNull(_cursorIndexOfBarcode)) {
              _tmpBarcode = null;
            } else {
              _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final int _tmpIsPendingSync;
            _tmpIsPendingSync = _cursor.getInt(_cursorIndexOfIsPendingSync);
            _item = new ActivityLog(_tmpId,_tmpActivityId,_tmpPerformedByUid,_tmpPerformedByName,_tmpPerformedByEmail,_tmpActionType,_tmpProductName,_tmpBarcode,_tmpTimestamp,_tmpIsPendingSync);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getLatestActivityLogs(final int limit,
      final Continuation<? super List<ActivityLog>> $completion) {
    final String _sql = "SELECT * FROM activity_logs ORDER BY timestamp DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ActivityLog>>() {
      @Override
      @NonNull
      public List<ActivityLog> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfActivityId = CursorUtil.getColumnIndexOrThrow(_cursor, "activityId");
          final int _cursorIndexOfPerformedByUid = CursorUtil.getColumnIndexOrThrow(_cursor, "performedByUid");
          final int _cursorIndexOfPerformedByName = CursorUtil.getColumnIndexOrThrow(_cursor, "performedByName");
          final int _cursorIndexOfPerformedByEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "performedByEmail");
          final int _cursorIndexOfActionType = CursorUtil.getColumnIndexOrThrow(_cursor, "actionType");
          final int _cursorIndexOfProductName = CursorUtil.getColumnIndexOrThrow(_cursor, "productName");
          final int _cursorIndexOfBarcode = CursorUtil.getColumnIndexOrThrow(_cursor, "barcode");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsPendingSync = CursorUtil.getColumnIndexOrThrow(_cursor, "isPendingSync");
          final List<ActivityLog> _result = new ArrayList<ActivityLog>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ActivityLog _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpActivityId;
            if (_cursor.isNull(_cursorIndexOfActivityId)) {
              _tmpActivityId = null;
            } else {
              _tmpActivityId = _cursor.getString(_cursorIndexOfActivityId);
            }
            final String _tmpPerformedByUid;
            if (_cursor.isNull(_cursorIndexOfPerformedByUid)) {
              _tmpPerformedByUid = null;
            } else {
              _tmpPerformedByUid = _cursor.getString(_cursorIndexOfPerformedByUid);
            }
            final String _tmpPerformedByName;
            if (_cursor.isNull(_cursorIndexOfPerformedByName)) {
              _tmpPerformedByName = null;
            } else {
              _tmpPerformedByName = _cursor.getString(_cursorIndexOfPerformedByName);
            }
            final String _tmpPerformedByEmail;
            if (_cursor.isNull(_cursorIndexOfPerformedByEmail)) {
              _tmpPerformedByEmail = null;
            } else {
              _tmpPerformedByEmail = _cursor.getString(_cursorIndexOfPerformedByEmail);
            }
            final String _tmpActionType;
            if (_cursor.isNull(_cursorIndexOfActionType)) {
              _tmpActionType = null;
            } else {
              _tmpActionType = _cursor.getString(_cursorIndexOfActionType);
            }
            final String _tmpProductName;
            if (_cursor.isNull(_cursorIndexOfProductName)) {
              _tmpProductName = null;
            } else {
              _tmpProductName = _cursor.getString(_cursorIndexOfProductName);
            }
            final String _tmpBarcode;
            if (_cursor.isNull(_cursorIndexOfBarcode)) {
              _tmpBarcode = null;
            } else {
              _tmpBarcode = _cursor.getString(_cursorIndexOfBarcode);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final int _tmpIsPendingSync;
            _tmpIsPendingSync = _cursor.getInt(_cursorIndexOfIsPendingSync);
            _item = new ActivityLog(_tmpId,_tmpActivityId,_tmpPerformedByUid,_tmpPerformedByName,_tmpPerformedByEmail,_tmpActionType,_tmpProductName,_tmpBarcode,_tmpTimestamp,_tmpIsPendingSync);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
