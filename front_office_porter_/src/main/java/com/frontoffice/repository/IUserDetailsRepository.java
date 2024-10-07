package com.frontoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frontoffice.entity.UserDetailsEntity;

public interface IUserDetailsRepository extends JpaRepository<UserDetailsEntity,Integer> {

	public UserDetailsEntity findByEmail(String email);
	
	public UserDetailsEntity findByEmailAndPwd(String email,String pws);
}
