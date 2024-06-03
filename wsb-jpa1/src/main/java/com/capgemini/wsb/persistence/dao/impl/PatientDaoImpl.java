package com.capgemini.wsb.persistence.dao.impl;

import com.capgemini.wsb.persistence.dao.PatientDao;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.entity.VisitEntity;
import com.capgemini.wsb.persistence.enums.TreatmentType;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {


    @Override
    public List<PatientEntity> findByDoctor(String firstName, String lastName) { // TODO - napisac query

        String jpql = "SELECT p FROM PatientEntity p JOIN p.visits v JOIN v.doctor d " +
                "WHERE d.firstName = :firstName AND d.lastName = :lastName";
        TypedQuery<PatientEntity> query = entityManager.createQuery(jpql, PatientEntity.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsHavingTreatmentType(TreatmentType treatmentType) { // TODO - napisac query

        String jpql = "SELECT DISTINCT p FROM PatientEntity p JOIN p.visits v JOIN v.medicalTreatments mt " +
                "WHERE mt.type = :treatmentType";
        TypedQuery<PatientEntity> query = entityManager.createQuery(jpql, PatientEntity.class);
        query.setParameter("treatmentType", treatmentType);
        return query.getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsSharingSameLocationWithDoc(String firstName, String lastName) { // TODO - napisac query

        String jpql = "SELECT DISTINCT p FROM PatientEntity p JOIN p.addresses pa, DoctorEntity d JOIN d.addresses da " +
                "WHERE d.firstName = :firstName AND d.lastName = :lastName AND pa = da";
        TypedQuery<PatientEntity> query = entityManager.createQuery(jpql, PatientEntity.class);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsWithoutLocation() { // TODO - napisac query

        String jpql = "SELECT p FROM PatientEntity p WHERE p.addresses IS EMPTY";
        TypedQuery<PatientEntity> query = entityManager.createQuery(jpql, PatientEntity.class);
        return query.getResultList();
    }
    public void delete(Long id) {
        PatientEntity patient = findOne(id);
        if (patient != null) {
            entityManager.remove(patient);
        }
    }
}
