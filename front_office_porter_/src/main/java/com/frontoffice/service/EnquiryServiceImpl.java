package com.frontoffice.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frontoffice.binding.DashboardResponse;
import com.frontoffice.binding.EnquerySearchCriteria;
import com.frontoffice.binding.EnquiryForm;
import com.frontoffice.entity.CoursesEntity;
import com.frontoffice.entity.StudentEnqueryEntity;
import com.frontoffice.entity.UserDetailsEntity;
import com.frontoffice.repository.ICoursesEntityRepository;
import com.frontoffice.repository.IStudentEnqueryRepository;
import com.frontoffice.repository.IUserDetailsRepository;

import jakarta.mail.Session;
import jakarta.servlet.http.HttpSession;
@Service("enquiryservice")
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired 
	IUserDetailsRepository userDetailsRepository;
	
	@Autowired
	HttpSession session;
	@Autowired
	IStudentEnqueryRepository studentEnquery;
	@Autowired
	ICoursesEntityRepository coursesEntityRepository ;
	@Override
	public DashboardResponse getDashboardData(Integer userIdr) {
		DashboardResponse response=new DashboardResponse();

		UserDetailsEntity findById=userDetailsRepository.getById(userIdr);
		if(findById !=null) {
			java.util.List<StudentEnqueryEntity> enquiriesList=findById.getEnquiries();
			Integer totalCnt=enquiriesList.size();
			Integer enrolledCount=0;
			Integer lostCount=0;
			for(StudentEnqueryEntity enquery :enquiriesList) {
				if(enquery.getEnquiryStatu().equals("Inrolled") ){
					enrolledCount++;
				}
				
			}
			for(StudentEnqueryEntity enquery :enquiriesList) {
				if(enquery.getEnquiryStatu()=="LOST") {
					lostCount++;
				}
				
			}
			response.setEnrolledCnt(enrolledCount);
			response.setLostCnt(lostCount);
			response.setTotalEnquriesCnt(totalCnt);
			
		}
		return response;
	}
	@Override
	public List<String> getCourses() {
		
		List<CoursesEntity> courses =coursesEntityRepository.findAll();
	
		if(courses == null) {
			return null;
		}else {
			List<String> list=new ArrayList<String>();
			for(CoursesEntity item:courses) {
				list.add(item.getCourses());
			}
			return list;
		}
		
	}
	@Override
	public String saveEnquiry(EnquiryForm enquiryForm) {
		StudentEnqueryEntity enquiryEntity=new StudentEnqueryEntity();
		BeanUtils.copyProperties(enquiryForm, enquiryEntity);	
		enquiryEntity.setDateCreate(LocalDate.now());

		Integer useridInteger=(Integer) session.getAttribute("userId");
		Optional< UserDetailsEntity> userentity =userDetailsRepository.findById(useridInteger);
		UserDetailsEntity userEntity=userentity.get();
	    enquiryEntity.setUser(userEntity);
	    studentEnquery.save(enquiryEntity);
		
		return "Enquery Added Successfully";
	}
	@Override
	public List<StudentEnqueryEntity> loadStudentEnqueries() {
		// get user id 
		Integer userid =(Integer) session.getAttribute("userId");
		
		//fetch all records from database
		Optional<UserDetailsEntity> userDetails =userDetailsRepository.findById(userid);
		if(userDetails.isPresent()) {
			UserDetailsEntity userDetailsEntity=userDetails.get();
			List<StudentEnqueryEntity> studentEnqueries=userDetailsEntity.getEnquiries();
			return studentEnqueries;
		}else {
			return null;
		}
	
	}
	@Override
	public List<StudentEnqueryEntity> loadFilteredEnqueries(EnquerySearchCriteria criteria,Integer userId) {

		Optional<UserDetailsEntity> userDetails = userDetailsRepository.findById(userId);
		List<StudentEnqueryEntity> studentEnqueries=null;
		List<StudentEnqueryEntity> filteredStudentEnquery=new ArrayList<StudentEnqueryEntity>();
		if(userDetails.isPresent()) {
			System.out.println("Comming in loadfilteredEnqueries");
			UserDetailsEntity userDetailsEntity=userDetails.get();
			studentEnqueries = userDetailsEntity.getEnquiries();
	
			//filter logic
			//for courseName
			if(!criteria.getCourseName().equals("select")) {
				
				 System.out.println("This is select "+criteria.getCourseName());
		        studentEnqueries.forEach(enquery->{
		   		 System.out.println("this is student enquery entity "+enquery.getCourse());
					if(enquery.getCourse().equals(criteria.getCourseName())) {
						filteredStudentEnquery.add(enquery);
					}
				});
		
			}
			//forClassMode
			if(!criteria.getClassMode().equals("select")) {
				   System.out.println("This is select "+criteria.getClassMode());
				studentEnqueries.forEach(enquery->{
					if(enquery.getClassMode().equals(criteria.getClassMode())) {
						filteredStudentEnquery.add(enquery);
					}
				});
			}
			//forEnqueryStatus
			if(!criteria.getEnqStatus().equals("select")) {
			   System.out.println("This is select "+criteria.getEnqStatus());
				studentEnqueries.forEach(enquery->{
					if(enquery.getEnquiryStatu().equals(criteria.getEnqStatus())) {
						filteredStudentEnquery.add(enquery);
					}
				});
				}
			System.out.println("this is size of student enqueries in filtered method"+studentEnqueries.size());
			
		}
		
	
		return filteredStudentEnquery;
	}

	


}
