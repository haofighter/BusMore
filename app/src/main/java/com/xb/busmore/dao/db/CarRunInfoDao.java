package com.xb.busmore.dao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.xb.busmore.entity.car.CarRunInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CAR_RUN_INFO".
*/
public class CarRunInfoDao extends AbstractDao<CarRunInfo, Long> {

    public static final String TABLENAME = "CAR_RUN_INFO";

    /**
     * Properties of entity CarRunInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Diraction = new Property(1, int.class, "diraction", false, "DIRACTION");
        public final static Property DeviceStatus = new Property(2, int.class, "deviceStatus", false, "DEVICE_STATUS");
        public final static Property BianStatu = new Property(3, int.class, "bianStatu", false, "BIAN_STATU");
        public final static Property Price = new Property(4, int.class, "price", false, "PRICE");
        public final static Property Sign = new Property(5, boolean.class, "sign", false, "SIGN");
        public final static Property Coefficient = new Property(6, String.class, "coefficient", false, "COEFFICIENT");
    }


    public CarRunInfoDao(DaoConfig config) {
        super(config);
    }
    
    public CarRunInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CAR_RUN_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"DIRACTION\" INTEGER NOT NULL ," + // 1: diraction
                "\"DEVICE_STATUS\" INTEGER NOT NULL ," + // 2: deviceStatus
                "\"BIAN_STATU\" INTEGER NOT NULL ," + // 3: bianStatu
                "\"PRICE\" INTEGER NOT NULL ," + // 4: price
                "\"SIGN\" INTEGER NOT NULL ," + // 5: sign
                "\"COEFFICIENT\" TEXT);"); // 6: coefficient
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CAR_RUN_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CarRunInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getDiraction());
        stmt.bindLong(3, entity.getDeviceStatus());
        stmt.bindLong(4, entity.getBianStatu());
        stmt.bindLong(5, entity.getPrice());
        stmt.bindLong(6, entity.getSign() ? 1L: 0L);
 
        String coefficient = entity.getCoefficient();
        if (coefficient != null) {
            stmt.bindString(7, coefficient);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CarRunInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getDiraction());
        stmt.bindLong(3, entity.getDeviceStatus());
        stmt.bindLong(4, entity.getBianStatu());
        stmt.bindLong(5, entity.getPrice());
        stmt.bindLong(6, entity.getSign() ? 1L: 0L);
 
        String coefficient = entity.getCoefficient();
        if (coefficient != null) {
            stmt.bindString(7, coefficient);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CarRunInfo readEntity(Cursor cursor, int offset) {
        CarRunInfo entity = new CarRunInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // diraction
            cursor.getInt(offset + 2), // deviceStatus
            cursor.getInt(offset + 3), // bianStatu
            cursor.getInt(offset + 4), // price
            cursor.getShort(offset + 5) != 0, // sign
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // coefficient
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CarRunInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDiraction(cursor.getInt(offset + 1));
        entity.setDeviceStatus(cursor.getInt(offset + 2));
        entity.setBianStatu(cursor.getInt(offset + 3));
        entity.setPrice(cursor.getInt(offset + 4));
        entity.setSign(cursor.getShort(offset + 5) != 0);
        entity.setCoefficient(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CarRunInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CarRunInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CarRunInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
