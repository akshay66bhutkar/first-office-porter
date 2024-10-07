package com.frontoffice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frontoffice.binding.DashboardResponse;
import com.frontoffice.binding.EnquerySearchCriteria;
import com.frontoffice.binding.EnquiryForm;
import com.frontoffice.entity.StudentEnqueryEntity;
import com.frontoffice.service.EnquiryServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
public class EnqueryController {
	@Autowired
	private HttpSession session;
	@Autowired
	private EnquiryServiceImpl enquiryService;
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}
	@GetMapping("/dashboard")
	public String dashboardPage(Map<String,Object> map) {
		Integer userId=(Integer)session.getAttribute("userId");
		DashboardResponse dashboardData=enquiryService.getDashboardData(userId);
		map.put("dashboardData", dashboardData);
		return "dashboard";
	}
	
	
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		List<String> courseStrings=enquiryService.getCourses();
         System.out.println("courses are "+courseStrings.get(0));
	    model.addAttribute("courses",courseStrings);
	    model.addAttribute("enquiryform",new EnquiryForm());	
	
		return "enquiry";
	}
	@PostMapping("/enquiry")
	public String saveEnquiry(@ModelAttribute("enquiryform") EnquiryForm enquiryData,Map<String,String> map ) {
		
		//System.out.println("name is "+enquiryData.getName()+" Phone number is "+enquiryData.getContactNumber()+" course is "+enquiryData.getCourse()+" Class mode is "+enquiryData.getClassMode()+" enquiry status is "+enquiryData.getEnquiryStatu());
		String resultString=enquiryService.saveEnquiry(enquiryData);
		map.put("Result", resultString);
		return "redirect:/enquiry";
		
	}
	@GetMapping("/view-enquiry")
	public String viewEnquiriesPage(Map<String,Object> map){
		List<String> courses= enquiryService.getCourses();
	
		map.put("course", courses);
		List<StudentEnqueryEntity> studentEnqueries =new ArrayList<StudentEnqueryEntity>();
		studentEnqueries =enquiryService.loadStudentEnqueries();
		studentEnqueries.forEach(enquery->{
			System.out.println("this is Students names "+enquery.getName());
		});
		map.put("userEnqueries",studentEnqueries);
		return "view-enquiry";
	}
	
	@GetMapping("/getCourses")

	public String getCoruses(Map<String,Object> map, @RequestParam("cname") String cname,@RequestParam String status,@RequestParam String mode) {
		EnquerySearchCriteria enqueriSearchCriteria=new EnquerySearchCriteria();
		Integer userIdInteger=(Integer)session.getAttribute("userId");
		
        enqueriSearchCriteria.setClassMode(mode);
        enqueriSearchCriteria.setCourseName(cname);
        enqueriSearchCriteria.setEnqStatus(status);
        System.out.println(mode);
        System.out.println(cname);
        System.out.println(status);
        List<StudentEnqueryEntity> enquerie=enquiryService.loadFilteredEnqueries(enqueriSearchCriteria,userIdInteger );
        System.out.println("This is enqueries list size()"+enquerie.size());
        enquerie.forEach(item->{
        	System.out.println("This is course name"+item.getCourse());
        });
        map.put("userEnqueries",enquerie );
		return "filtered-enquery-table";
	}

}
