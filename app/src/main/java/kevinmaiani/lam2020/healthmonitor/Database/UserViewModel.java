package kevinmaiani.lam2020.healthmonitor.Database;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import kevinmaiani.lam2020.healthmonitor.Models.User;


public class UserViewModel extends AndroidViewModel {

    private UserDao userDao;

    public UserViewModel(@NonNull Application application) {
        super(application);

        userDao = ApplicationDatabase.getDatabase(application).userDao();
    }

    public User getUser(String mail, String password) {
        return userDao.getUser(mail, password);
    }

    public void insert(User user) { userDao.insert(user);}
    public void update(User user) { userDao.update(user);}
    public void delete(User user) { userDao.delete(user);}
}
