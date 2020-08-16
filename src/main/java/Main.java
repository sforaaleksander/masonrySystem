import com.codecool.masonrySystem.DAO.ArtifactDao;
import com.codecool.masonrySystem.Models.Artifact;

import java.util.List;

public class Main
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ArtifactDao artifactDao = new ArtifactDao();
        try {
            List<Artifact> artifacts = artifactDao.getArtifacts();
            for (Artifact artifact : artifacts) {
                System.out.println(artifact.getDescription());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
