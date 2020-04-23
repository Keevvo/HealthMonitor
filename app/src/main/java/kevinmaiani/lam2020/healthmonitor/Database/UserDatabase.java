package kevinmaiani.lam2020.healthmonitor.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import kevinmaiani.lam2020.healthmonitor.Models.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao getUserDao();
}
