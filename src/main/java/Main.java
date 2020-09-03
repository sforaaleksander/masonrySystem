import com.codecool.masonrysystem.controller.RootController;
import com.codecool.masonrysystem.dao.UserDao;
import com.codecool.masonrysystem.model.Apprentice;
import com.codecool.masonrysystem.model.Journeyman;
import com.codecool.masonrysystem.model.MasterMason;
import com.codecool.masonrysystem.model.User;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        RootController rootController = new RootController();
        rootController.run();
    }
}
