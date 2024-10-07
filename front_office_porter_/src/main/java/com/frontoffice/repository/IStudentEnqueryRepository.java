package com.frontoffice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frontoffice.entity.StudentEnqueryEntity;

public interface IStudentEnqueryRepository extends JpaRepository<StudentEnqueryEntity,Integer>{

	//public List<StudentEnqueryEntity> findById(Integer userId);
}
