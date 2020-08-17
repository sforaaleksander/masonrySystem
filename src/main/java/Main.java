import com.codecool.masonrySystem.Controllers.RootController;
import com.codecool.masonrySystem.DAO.ArtifactDao;
import com.codecool.masonrySystem.Models.Artifact;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        new RootController().run();
    }
}
