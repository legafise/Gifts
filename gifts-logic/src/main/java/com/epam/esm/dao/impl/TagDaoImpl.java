package com.epam.esm.dao.impl;

import com.epam.esm.dao.DaoException;
import com.epam.esm.dao.DaoMapper;
import com.epam.esm.dao.DataSource;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TagDaoImpl implements TagDao {
    private static final String ADD_TAG_SQL = "INSERT INTO tag (name) VALUES (?)";
    private static final String FIND_TAG_BY_ID_SQL = "SELECT tag.id AS tag_id, tag.name AS tag_name FROM tag WHERE tag.id = ?";
    private static final String FIND_ALL_TAGS_SQL = "SELECT tag.id AS tag_id, tag.name AS tag_name FROM tag";
    private static final String REMOVE_TAG_BY_ID_SQL = "DELETE FROM tag WHERE id = ?";
    private static final String UPDATE_TAG_SQL = "UPDATE tag SET name = ? WHERE id = ?";
    private final DaoMapper mapper;

    public TagDaoImpl() {
        mapper = new DaoMapper();
    }

    @Override
    public boolean add(Tag tag) {
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(ADD_TAG_SQL)) {
            statement.setString(1, tag.getName());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Tag> findById(Long id) {
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_TAG_BY_ID_SQL)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Optional<Tag> tagOptional = Optional.empty();

            if (resultSet.next()) {
                tagOptional = Optional.of(mapper.mapTag(resultSet));
            }

            return tagOptional;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Tag> findAll() {
        try(Connection connection = DataSource.getConnection();
            Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_TAGS_SQL);
            List<Tag> tagList = new ArrayList<>();

            while (resultSet.next()) {
                tagList.add(mapper.mapTag(resultSet));
            }

            return tagList;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Tag tag) {
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_TAG_SQL)) {
            statement.setString(1, tag.getName());
            statement.setLong(2, tag.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean remove(Long id) {
        try(Connection connection = DataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(REMOVE_TAG_BY_ID_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
