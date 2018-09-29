package com.xb.busmore.dao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.xb.busmore.entity.car.CarConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CAR_CONFIG".
*/
public class CarConfigDao extends AbstractDao<CarConfig, Long> {

    public static final String TABLENAME = "CAR_CONFIG";

    /**
     * Properties of entity CarConfig.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property GPS = new Property(1, int.class, "GPS", false, "GPS");
        public final static Property Time = new Property(2, Long.class, "time", false, "TIME");
        public final static Property BusNo = new Property(3, String.class, "busNo", false, "BUS_NO");
        public final static Property UnionTraNo = new Property(4, String.class, "unionTraNo", false, "UNION_TRA_NO");
        public final static Property UnionPos = new Property(5, String.class, "unionPos", false, "UNION_POS");
        public final static Property PosSn = new Property(6, String.class, "posSn", false, "POS_SN");
        public final static Property Mark = new Property(7, String.class, "mark", false, "MARK");
    }


    public CarConfigDao(DaoConfig config) {
        super(config);
    }
    
    public CarConfigDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CAR_CONFIG\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"GPS\" INTEGER NOT NULL ," + // 1: GPS
                "\"TIME\" INTEGER," + // 2: time
                "\"BUS_NO\" TEXT," + // 3: busNo
                "\"UNION_TRA_NO\" TEXT," + // 4: unionTraNo
                "\"UNION_POS\" TEXT," + // 5: unionPos
                "\"POS_SN\" TEXT," + // 6: posSn
                "\"MARK\" TEXT);"); // 7: mark
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CAR_CONFIG\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CarConfig entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getGPS());
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(3, time);
        }
 
        String busNo = entity.getBusNo();
        if (busNo != null) {
            stmt.bindString(4, busNo);
        }
 
        String unionTraNo = entity.getUnionTraNo();
        if (unionTraNo != null) {
            stmt.bindString(5, unionTraNo);
        }
 
        String unionPos = entity.getUnionPos();
        if (unionPos != null) {
            stmt.bindString(6, unionPos);
        }
 
        String posSn = entity.getPosSn();
        if (posSn != null) {
            stmt.bindString(7, posSn);
        }
 
        String mark = entity.getMark();
        if (mark != null) {
            stmt.bindString(8, mark);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CarConfig entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getGPS());
 
        Long time = entity.getTime();
        if (time != null) {
            stmt.bindLong(3, time);
        }
 
        String busNo = entity.getBusNo();
        if (busNo != null) {
            stmt.bindString(4, busNo);
        }
 
        String unionTraNo = entity.getUnionTraNo();
        if (unionTraNo != null) {
            stmt.bindString(5, unionTraNo);
        }
 
        String unionPos = entity.getUnionPos();
        if (unionPos != null) {
            stmt.bindString(6, unionPos);
        }
 
        String posSn = entity.getPosSn();
        if (posSn != null) {
            stmt.bindString(7, posSn);
        }
 
        String mark = entity.getMark();
        if (mark != null) {
            stmt.bindString(8, mark);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CarConfig readEntity(Cursor cursor, int offset) {
        CarConfig entity = new CarConfig( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // GPS
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // time
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // busNo
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // unionTraNo
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // unionPos
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // posSn
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // mark
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CarConfig entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setGPS(cursor.getInt(offset + 1));
        entity.setTime(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setBusNo(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setUnionTraNo(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUnionPos(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setPosSn(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setMark(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CarConfig entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CarConfig entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CarConfig entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
