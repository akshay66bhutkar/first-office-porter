package com.frontoffice.service;

import java.util.List;

import com.frontoffice.binding.DashboardResponse;
import com.frontoffice.binding.EnquerySearchCriteria;
import com.frontoffice.binding.EnquiryForm;
import com.frontoffice.entity.CoursesEntity;
import com.frontoffice.entity.StudentEnqueryEntity;

public interface EnquiryService {

	public DashboardResponse getDashboardData(Integer userIdr);
	
	public List<String> getCourses();
	
	public String saveEnquiry(EnquiryForm enquiryForm);
	
	public List<StudentEnqueryEntity> loadStudentEnqueries();
	
	
	public List<StudentEnqueryEntity> loadFilteredEnqueries(EnquerySearchCriteria criteria,Integer userId);
	
}
