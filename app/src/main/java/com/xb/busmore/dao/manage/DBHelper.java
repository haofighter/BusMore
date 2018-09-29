package com.xb.busmore.dao.manage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.xb.busmore.dao.db.AllLineInfoDao;
import com.xb.busmore.dao.db.BlackListEntityDao;
import com.xb.busmore.dao.db.CarConfigDao;
import com.xb.busmore.dao.db.DaoMaster;
import com.xb.busmore.dao.db.LineInfoUpDao;
import com.xb.busmore.dao.db.LinePriceInfoDao;
import com.xb.busmore.dao.db.LineStationDao;
import com.xb.busmore.dao.db.MacKeyEntityDao;
import com.xb.busmore.dao.db.PublicKeyEntityDao;
import com.xb.busmore.dao.db.ScanInfoEntityDao;
import com.xb.busmore.entity.BlackListEntity;
import com.xb.busmore.entity.PosRecord;


/**
 * 作者：Tangren_ on 2017/3/23 0023.
 * 邮箱：wu_tangren@163.com
 * TODO：更新数据库
 */


public class DBHelper extends DaoMaster.OpenHelper {
    DBHelper(Context context, String name) {
        super(context, name);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.migrate(db,
                CarConfigDao.class,
                AllLineInfoDao.class,
                LineInfoUpDao.class,
                LinePriceInfoDao.class,
                LineStationDao.class,
                BlackListEntityDao.class,
                ScanInfoEntityDao.class,
                PublicKeyEntityDao.class,
                MacKeyEntityDao.class

        );
    }
}
