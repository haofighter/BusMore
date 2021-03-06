package com.xb.busmore.dao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.xb.busmore.entity.AllLineInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ALL_LINE_INFO".
*/
public class AllLineInfoDao extends AbstractDao<AllLineInfo, Long> {

    public static final String TABLENAME = "ALL_LINE_INFO";

    /**
     * Properties of entity AllLineInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Acnt = new Property(1, String.class, "acnt", false, "ACNT");
        public final static Property Routeno = new Property(2, String.class, "routeno", false, "ROUTENO");
        public final static Property Routename = new Property(3, String.class, "routename", false, "ROUTENAME");
        public final static Property Routeversion = new Property(4, String.class, "routeversion", false, "ROUTEVERSION");
        public final static Property Uptatetime = new Property(5, long.class, "uptatetime", false, "UPTATETIME");
    }


    public AllLineInfoDao(DaoConfig config) {
        super(config);
    }
    
    public AllLineInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ALL_LINE_INFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"ACNT\" TEXT," + // 1: acnt
                "\"ROUTENO\" TEXT," + // 2: routeno
                "\"ROUTENAME\" TEXT," + // 3: routename
                "\"ROUTEVERSION\" TEXT," + // 4: routeversion
                "\"UPTATETIME\" INTEGER NOT NULL );"); // 5: uptatetime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ALL_LINE_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AllLineInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String acnt = entity.getAcnt();
        if (acnt != null) {
            stmt.bindString(2, acnt);
        }
 
        String routeno = entity.getRouteno();
        if (routeno != null) {
            stmt.bindString(3, routeno);
        }
 
        String routename = entity.getRoutename();
        if (routename != null) {
            stmt.bindString(4, routename);
        }
 
        String routeversion = entity.getRouteversion();
        if (routeversion != null) {
            stmt.bindString(5, routeversion);
        }
        stmt.bindLong(6, entity.getUptatetime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AllLineInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String acnt = entity.getAcnt();
        if (acnt != null) {
            stmt.bindString(2, acnt);
        }
 
        String routeno = entity.getRouteno();
        if (routeno != null) {
            stmt.bindString(3, routeno);
        }
 
        String routename = entity.getRoutename();
        if (routename != null) {
            stmt.bindString(4, routename);
        }
 
        String routeversion = entity.getRouteversion();
        if (routeversion != null) {
            stmt.bindString(5, routeversion);
        }
        stmt.bindLong(6, entity.getUptatetime());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AllLineInfo readEntity(Cursor cursor, int offset) {
        AllLineInfo entity = new AllLineInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // acnt
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // routeno
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // routename
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // routeversion
            cursor.getLong(offset + 5) // uptatetime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AllLineInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setAcnt(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setRouteno(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRoutename(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRouteversion(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUptatetime(cursor.getLong(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AllLineInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AllLineInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AllLineInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
