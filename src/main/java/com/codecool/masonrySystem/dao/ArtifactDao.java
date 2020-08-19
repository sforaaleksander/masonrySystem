package com.codecool.masonrySystem.dao;

import com.codecool.masonrySystem.Exception.ElementNotFoundException;
import com.codecool.masonrySystem.Models.Artifact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtifactDao extends PostgresDAO<Artifact> implements IDAO<Artifact> {

    public ArtifactDao() {
        super("artifacts");
    }

    @Override
    protected Artifact create(ResultSet resultSet) throws SQLException {
        Artifact artifact = new Artifact();
        artifact.setId(resultSet.getLong("id"));
        artifact.setName(resultSet.getString("name"));
        artifact.setPrice(resultSet.getInt("price"));
        artifact.setDescription(resultSet.getString("description"));
        artifact.setIsCollective(resultSet.getBoolean("is_collective"));
        artifact.setIsActive(resultSet.getBoolean("is_active"));
        artifact.setExpirationDate(resultSet.getDate("expiration_date"));
        return artifact;
    }

    public List<Artifact> getAll() throws ElementNotFoundException, ClassNotFoundException {
        return getAllElements();
    }

    @Override
    public Artifact getById(Long id) throws ClassNotFoundException, ElementNotFoundException {
        return getElementById(id);
    }

    @Override
    public boolean insert(Artifact artifact) throws ClassNotFoundException {
        Connection connection = this.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO artifacts" +
                    "(id, name, price, description, is_collective, is_active, expiration_date) VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setLong(1, artifact.getId());
            preparedStatement.setString(2, artifact.getName());
            preparedStatement.setInt(3, artifact.getPrice());
            preparedStatement.setString(4, artifact.getDescription());
            preparedStatement.setBoolean(5, artifact.getIsCollective());
            preparedStatement.setBoolean(6, artifact.getIsActive());
            preparedStatement.setDate(7, (Date) artifact.getExpirationDate());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Artifact artifact) throws ClassNotFoundException {
        Connection connection = this.getConnection();
        Long id = artifact.getId();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE artifacts SET " +
                    "name=?, price=?, description=?, is_collective=?, is_active=?, expiration_date=? WHERE id = ?");
            preparedStatement.setString(1, artifact.getName());
            preparedStatement.setInt(2, artifact.getPrice());
            preparedStatement.setString(3, artifact.getDescription());
            preparedStatement.setBoolean(4, artifact.getIsCollective());
            preparedStatement.setBoolean(5, artifact.getIsActive());
            preparedStatement.setDate(6, (Date) artifact.getExpirationDate());
            preparedStatement.setLong(7, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Long id) throws ClassNotFoundException {
        return deleteElement(id);
    }

    public List<Artifact> getAllUsedByUserId(Long id) throws ElementNotFoundException, ClassNotFoundException {
        List<Artifact> artifacts = new ArrayList<>();
        Connection connection = this.getConnection();
        try {
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM artifacts LEFT JOIN transactions t on artifacts.id = t.artifact_id WHERE user_id=?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                artifacts.add(create(rs));
            }
            rs.close();
            statement.close();
            connection.close();
            return artifacts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new ElementNotFoundException("No artifacts not found");
    }
}
