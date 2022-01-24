package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapperImpl;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private static final String ADD_TAG_SQL = "INSERT INTO tag (name) VALUES (?)";
    private static final String FIND_TAG_BY_ID_SQL = "SELECT tag.id AS tag_id, tag.name AS tag_name FROM tag WHERE tag.id = ?";
    private static final String FIND_TAG_BY_NAME_SQL = "SELECT tag.id AS tag_id, tag.name AS tag_name FROM tag WHERE tag.name = ?";
    private static final String FIND_ALL_TAGS_SQL = "SELECT tag.id AS tag_id, tag.name AS tag_name FROM tag";
    private static final String REMOVE_TAG_BY_ID_SQL = "DELETE FROM tag WHERE id = ?";
    private static final String UPDATE_TAG_SQL = "UPDATE tag SET name = ? WHERE id = ?";
    private final TagMapperImpl tagMapper;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(TagMapperImpl tagMapper, JdbcTemplate jdbcTemplate) {
        this.tagMapper = tagMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean add(Tag tag) {
        return 1 == jdbcTemplate.update(ADD_TAG_SQL, tag.getName());
    }

    @Override
    public Optional<Tag> findById(long id) {
        List<Tag> tagList = jdbcTemplate.query(FIND_TAG_BY_NAME_SQL, new Object[]{id}, tagMapper);
        return Objects.requireNonNull(tagList).isEmpty() ? Optional.empty() : Optional.of(tagList.get(0));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        List<Tag> tagList = jdbcTemplate.query(FIND_TAG_BY_NAME_SQL, new Object[]{name}, tagMapper);
        return Objects.requireNonNull(tagList).isEmpty() ? Optional.empty() : Optional.of(tagList.get(0));
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL_TAGS_SQL, tagMapper);
    }

    @Override
    public boolean update(Tag tag, long id) {
        return 1 <= jdbcTemplate.update(UPDATE_TAG_SQL, tag.getName(), id);
    }

    @Override
    public boolean remove(long id) {
        return 1 == jdbcTemplate.update(REMOVE_TAG_BY_ID_SQL, id);
    }
}
