package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private static final String REMOVE_TAG_FROM_CERTIFICATES_BY_ID_SQL = "DELETE FROM gift_tags WHERE tag_id = ?";
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Tag tag) {
        tag.setId(0);
        entityManager.persist(tag);
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public Optional<Tag> findByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> rootEntry = criteriaQuery.from(Tag.class);
        criteriaQuery.select(rootEntry).where(criteriaBuilder.equal(rootEntry.get("name"), name));
        TypedQuery<Tag> nameQuery = entityManager.createQuery(criteriaQuery);
        return nameQuery.getResultList().stream()
                .findFirst();
    }

    @Override
    public List<Tag> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> rootEntry = criteriaQuery.from(Tag.class);
        criteriaQuery.select(rootEntry);
        TypedQuery<Tag> allTagsQuery = entityManager.createQuery(criteriaQuery);
        return allTagsQuery.getResultList();
    }

    @Override
    @Transactional
    public Tag update(Tag tag) {
        return entityManager.merge(tag);
    }

    @Override
    public void remove(long id) {
        Tag removingTag = entityManager.find(Tag.class, id);
        entityManager.remove(removingTag);
    }

    @Override
    public void removeTagFromCertificates(long id) {
        Query nativeQuery = entityManager.createNativeQuery(REMOVE_TAG_FROM_CERTIFICATES_BY_ID_SQL);
        nativeQuery.setParameter(1, id);
        nativeQuery.executeUpdate();
    }
}
