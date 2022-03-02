package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.entity.Certificate;
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
public class CertificateDaoImpl implements CertificateDao {
    private static final String ADD_TAG_TO_CERTIFICATE_SQL = "INSERT INTO gift_tags (certificate_id, tag_id) VALUES (?, ?)";
    private static final String CLEAR_CERTIFICATE_TAGS_QUERY = "DELETE FROM gift_tags WHERE certificate_id = ?";
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(Certificate certificate) {
        entityManager.persist(certificate);
    }

    @Override
    public Optional<Certificate> findById(long id) {
        Session session = entityManager.unwrap(Session.class);
        Certificate findingCertificate = entityManager.find(Certificate.class, id);
        if (findingCertificate != null && session.contains(findingCertificate)) {
            entityManager.unwrap(Session.class).evict(findingCertificate);
        }

        return Optional.ofNullable(entityManager.find(Certificate.class, id));
    }

    @Override
    public List<Certificate> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> root = criteriaQuery.from(Certificate.class);
        criteriaQuery.select(root);
        TypedQuery<Certificate> allCertificatesQuery = entityManager.createQuery(criteriaQuery);
        return allCertificatesQuery.getResultList();
    }

    @Override
    @Transactional
    public Certificate update(Certificate certificate) {
        Certificate updatedCertificate = entityManager.merge(certificate);
        entityManager.unwrap(Session.class).flush();
        return updatedCertificate;
    }

    @Override
    public void remove(long id) {
        Certificate removingCertificate = entityManager.find(Certificate.class, id);
        entityManager.remove(removingCertificate);
    }

    @Override
    @Transactional
    public boolean addTagToCertificate(long certificateId, long tagId) {
        Query nativeQuery = entityManager.createNativeQuery(ADD_TAG_TO_CERTIFICATE_SQL);
        nativeQuery.setParameter(1, certificateId);
        nativeQuery.setParameter(2, tagId);
        return nativeQuery.executeUpdate() >= 1;
    }

    @Override
    public boolean clearCertificateTags(long certificateId) {
        Query query = entityManager.createNativeQuery(CLEAR_CERTIFICATE_TAGS_QUERY);
        query.setParameter(1, certificateId);
        return query.executeUpdate() >= 1;
    }

    @Override
    public Optional<Certificate> findByName(String name) {
        entityManager.unwrap(Session.class).flush();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> root = criteriaQuery.from(Certificate.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"), name));
        TypedQuery<Certificate> nameQuery = entityManager.createQuery(criteriaQuery);
        return nameQuery.getResultList().stream()
                .findFirst();
    }
}
