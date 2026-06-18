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
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
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

  private final EntityDeletionOrUpdateAdapter<Product> __updateAdapterOfProduct;

  public ProductDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProduct = new EntityInsertionAdapter<Product>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `products` (`id`,`name`,`barcode`,`mrp`,`brand`,`quantity`,`frontImagePath`,`barcodeImagePath`,`mrpImagePath`,`thumbnailPath`,`status`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
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
      }
    };
    this.__updateAdapterOfProduct = new EntityDeletionOrUpdateAdapter<Product>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `products` SET `id` = ?,`name` = ?,`barcode` = ?,`mrp` = ?,`brand` = ?,`quantity` = ?,`frontImagePath` = ?,`barcodeImagePath` = ?,`mrpImagePath` = ?,`thumbnailPath` = ?,`status` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
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
        statement.bindLong(14, entity.getId());
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
            _result = new Product(_tmpId,_tmpName,_tmpBarcode,_tmpMrp,_tmpBrand,_tmpQuantity,_tmpFrontImagePath,_tmpBarcodeImagePath,_tmpMrpImagePath,_tmpThumbnailPath,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getAll(final Continuation<? super List<Product>> $completion) {
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
            _item = new Product(_tmpId,_tmpName,_tmpBarcode,_tmpMrp,_tmpBrand,_tmpQuantity,_tmpFrontImagePath,_tmpBarcodeImagePath,_tmpMrpImagePath,_tmpThumbnailPath,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getByDay(final long day, final Continuation<? super List<Product>> $completion) {
    final String _sql = "SELECT * FROM products WHERE DATE(createdAt / 1000, 'unixepoch') = DATE(? / 1000, 'unixepoch') ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, day);
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
            _item = new Product(_tmpId,_tmpName,_tmpBarcode,_tmpMrp,_tmpBrand,_tmpQuantity,_tmpFrontImagePath,_tmpBarcodeImagePath,_tmpMrpImagePath,_tmpThumbnailPath,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object countByStatus(final ProductStatus status,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM products WHERE status = ?";
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
            _result = new Product(_tmpId,_tmpName,_tmpBarcode,_tmpMrp,_tmpBrand,_tmpQuantity,_tmpFrontImagePath,_tmpBarcodeImagePath,_tmpMrpImagePath,_tmpThumbnailPath,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
