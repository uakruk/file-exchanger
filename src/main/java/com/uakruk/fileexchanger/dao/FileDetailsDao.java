package com.uakruk.fileexchanger.dao;

import com.uakruk.fileexchanger.entity.FileDetails;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * fileexchanger class
 *
 * @author Yaroslav Kruk on 9/24/17.
 * e-mail: yakruck@gmail.com
 * GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.8
 */
@Repository
@Slf4j
public class FileDetailsDao {

    @Autowired
    private SessionFactory sessionFactory;

    public FileDetails getByToken(String fileToken) {
        return sessionFactory.getCurrentSession()
                .createQuery("from FileDetails fd where fd.fileToken = :fileToken", FileDetails.class)
                .setParameter("fileToken", fileToken)
                .uniqueResult();
    }

    public void delete(String fileToken) {
        log.info("Removing file with token = {}", fileToken);

        FileDetails fileDetails = getByToken(fileToken);
        log.info("Fetched file = {}", fileDetails);

        sessionFactory.getCurrentSession()
                .remove(fileDetails);
    }

    public void delete(FileDetails fileDetails) {
        sessionFactory.getCurrentSession().delete(fileDetails);
    }

    public FileDetails update(FileDetails fileDetails) {
        log.info("Updating file = {}", fileDetails);

        sessionFactory.getCurrentSession().update(fileDetails);

        return fileDetails;
    }

    public FileDetails create(FileDetails fileDetails) {
        log.info("Creating new file = {}", fileDetails);

        sessionFactory.getCurrentSession().persist(fileDetails);
        log.info("Successfully saved file = {}", fileDetails);

        return fileDetails;
    }

    public List<FileDetails> getAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from FileDetails", FileDetails.class).getResultList();
    }
}
