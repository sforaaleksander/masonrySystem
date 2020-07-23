import com.codecool.masonrySystem.DAO.ArtifactsDao;
import com.codecool.masonrySystem.Models.Artifact;

import java.util.List;

public class Main
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ArtifactsDao artifactsDao = new ArtifactsDao();
        try {
            List<Artifact> artifacts = artifactsDao.getArtifacts();
            for (Artifact artifact : artifacts) {
                System.out.println(artifact.getDescription());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
