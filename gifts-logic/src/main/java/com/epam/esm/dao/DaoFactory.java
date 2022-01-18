package com.epam.esm.dao;

import com.epam.esm.dao.impl.CertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DaoFactory {
    private static final AtomicBoolean IS_INSTANCE_CREATED = new AtomicBoolean(false);
    private static final Lock INSTANCE_LOCK = new ReentrantLock();
    private static DaoFactory instance;
    private final CertificateDao certificateDao;
    private final TagDao tagDao;

    private DaoFactory() {
        certificateDao = new CertificateDaoImpl();
        tagDao = new TagDaoImpl();
    }

    public static DaoFactory getInstance() {
       if (!IS_INSTANCE_CREATED.get()) {
           try {
               INSTANCE_LOCK.lock();
               if (instance == null) {
                   instance = new DaoFactory();
                   IS_INSTANCE_CREATED.set(true);
               }
           } finally {
               INSTANCE_LOCK.unlock();
           }
       }

       return instance;
   }

   public CertificateDao getCertificateDao() {
       return certificateDao;
   }

    public TagDao getTagDao() {
        return tagDao;
    }
}
