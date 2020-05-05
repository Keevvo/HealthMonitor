package kevinmaiani.lam2020.healthmonitor.Database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import kevinmaiani.lam2020.healthmonitor.Converters.Converters;
import kevinmaiani.lam2020.healthmonitor.Models.Report;
import kevinmaiani.lam2020.healthmonitor.Models.User;

@Database(entities = {User.class, Report.class}, version = 1)
@TypeConverters({Converters.class})
    public abstract class ApplicationDatabase extends RoomDatabase {
        private static ApplicationDatabase INSTANCE;
        private static final String DB_NAME = "mi-database.db";

        public static ApplicationDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (ApplicationDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                ApplicationDatabase.class, DB_NAME)
                                .allowMainThreadQueries() // SHOULD NOT BE USED IN PRODUCTION !!!
                                .addCallback(new RoomDatabase.Callback() {
                                    @Override
                                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                        super.onCreate(db);
                                        Log.d("MoviesDatabase", "populating with data...");
                                        new PopulateDbAsync(INSTANCE).execute();
                                    }
                                })
                                .build();
                    }
                }
            }
            return INSTANCE;
        }
        public void clearDb() {
            if (INSTANCE != null) {
                new PopulateDbAsync(INSTANCE).execute();
            }
        }
        public abstract UserDao userDao();
        public abstract ReportDao reportDao();
        private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
            private final UserDao userDao;
            private final ReportDao reportDao;
            public PopulateDbAsync(ApplicationDatabase instance) {
                userDao = instance.userDao();
                reportDao = instance.reportDao();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }
        }
    }

