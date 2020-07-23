package com.codecool.masonrySystem.DAO;

import com.codecool.masonrySystem.Models.Artifact;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArtifactsDao {

    private Artifact create(ResultSet resultSet) throws SQLException {
        Artifact artifact = new Artifact();
        artifact.setDescription(resultSet.getString("description"));
        return artifact;
    }


    public List<Artifact> getArtifacts() throws Exception {
        List<Artifact> artifacts = new ArrayList<Artifact>();

        Connector connector = new Connector();
        Connection connection = connector.Connect();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM artifacts;");
            while (rs.next()) {
                artifacts.add(create(rs));
            }
            rs.close();
            statement.close();
            connection.close();
            return artifacts;
        }  catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        throw new Exception("Artifacts not found");
    }
}
