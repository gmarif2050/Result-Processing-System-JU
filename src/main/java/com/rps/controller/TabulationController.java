package com.rps.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rps.dto.tabulation.MarkEntity;
import com.rps.entities.storage.Student;
import com.rps.entities.tabulation.TCourse;
import com.rps.entities.tabulation.TExam;
import com.rps.entities.tabulation.TMark;
import com.rps.entities.tabulation.TStudent;
import com.rps.entities.tabulation.Teacher;
import com.rps.service.storage.ProgramService;
import com.rps.service.storage.StudentService;
import com.rps.service.tabulation.TCourseService;
import com.rps.service.tabulation.TExamService;
import com.rps.service.tabulation.TMarkService;
import com.rps.service.tabulation.TStudentService;
import com.rps.service.tabulation.TeacherService;
import com.rps.utility.tabulation.TStudentListContainer;
import com.rps.utility.tabulation.UtilityService;

@Controller
@RequestMapping("/tabulation")
public class TabulationController {
	
	@Autowired
	TeacherService teacherService;
	@Autowired
	TExamService texamService;
	@Autowired
	TCourseService tcourseService;
	@Autowired
	StudentService studentService;
	@Autowired
	TStudentService tstudentService;
	@Autowired
	TMarkService tmarkService;
	
	@Autowired
	ProgramService programService;

	
	@GetMapping(value="")
	public String signuploginPrompt()
	{
		return "tabulation/landing-page";
	}
	
	@GetMapping(value="/signup")
	public String tsignup(Model model)
	{
		Teacher teacher = new Teacher();
		teacher.setName("hahah");
		model.addAttribute("teacher", teacher);
		return "tabulation/a-teacher-signup";
	}
	
	@PostMapping(value="/signup")
	public String tsignupPost(Model model, @ModelAttribute("teacher") Teacher teacher)
	{
		Teacher teacherTmp = teacherService.getTeacherByEmail(teacher.getEmail());
		
		if(teacherTmp==null)
		{
			teacherService.addTeacher(teacher);
			return "tabulation/landing-page";			
		}
		else 
		{
			model.addAttribute("emailExists", true);
			return "tabulation/a-teacher-signup";
		}
		
	}
	
	@GetMapping(value="/login")
	public String tloginGet(Model model)
	{
		Teacher teacher = new Teacher();
		model.addAttribute("teacher", teacher);
		return "tabulation/b-teacher-login";
	}
	
	@PostMapping(value="/login")
	public String tloginPost(Model model, @ModelAttribute("teacher") Teacher teacher)
	{
		teacher.setEmail(teacher.getUsername());
		
		long teacherId = teacherService.authenticateTeacher(teacher);
		
		model.addAttribute("teacherId", teacherId);

		if(teacherId != -1)
		{
			return "tabulation/d-show-exams";
		}
		else 
		{
			model.addAttribute("loginFailed", true);
			return "tabulation/b-teacher-login";
		}
	}
	
	@GetMapping(value="/{teacherId}/addExam")
	public String addExam(Model model, @PathVariable("teacherId") long teacherId)
	{
		TExam texam = new TExam();
		model.addAttribute("texam", texam);
		model.addAttribute("teacherId", teacherId);
		return "tabulation/c-add-new-exam";
	}
	
	@PostMapping(value="/{teacherId}")
	public String addExamPost(Model model, @ModelAttribute("texam") TExam texam, @PathVariable("teacherId") long teacherId)
	{
		Teacher teacher = teacherService.getTeacher(teacherId);
		texam.setTeacher(teacher);
		texamService.addExam(texam);
		model.addAttribute("examAdded", true);
		return "tabulation/tabulator-front-page";
	}
	
	@GetMapping(value="/{teacherId}")
	public String tabulationFrontShowGet(Model model, @ModelAttribute("texam") TExam texam, @PathVariable("teacherId") long teacherId)
	{
		Teacher teacher = teacherService.getTeacher(teacherId);
		return "tabulation/tabulator-front-page";
	}
	
	@GetMapping(value="/showExams")
	public String showExams(HttpServletRequest request, Model model)
	{
		String email = request.getUserPrincipal().getName();
		Teacher teacher = teacherService.getTeacherByEmail(email);
		Long teacherId = teacher.getTeacherId();
		
		TExam texam = new TExam();
		model.addAttribute("texam", texam);
		model.addAttribute("teacherId", teacherId);
		Set<TExam> texamSet = teacherService.findExams(teacherId);
		
		List<TExam> texamList = new ArrayList(texamSet);
		Comparator<TExam> texamIdComparator = (a,b) -> Long.compare(a.getTexamId(), b.getTexamId());
		Collections.sort(texamList, texamIdComparator.reversed());
		
		model.addAttribute("texamList", texamList);
		return "tabulation/d-show-exams";
	}
	
	@GetMapping(value="/{teacherId}/exam/{texamId}/delete")
	public String removeExamGet(Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId)
	{
		TExam texam = texamService.getExam(texamId);
		texamService.removeTExam(texam);
		
		model.addAttribute("texam", texam);
		model.addAttribute("teacherId", teacherId);
		Set<TExam> texamSet = teacherService.findExams(teacherId);
		
		List<TExam> texamList = new ArrayList(texamSet);
		Comparator<TExam> texamIdComparator = (a,b) -> Long.compare(a.getTexamId(), b.getTexamId());
		Collections.sort(texamList, texamIdComparator.reversed());
		
		model.addAttribute("texamList", texamList);
		return "tabulation/d-show-exams";
	}
	
	@GetMapping(value="/{teacherId}/exam/{texamId}/course")
	public String addAndShowCoursesGet(Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId)
	{
		model.addAttribute("teacherId", teacherId);

		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		Set<TCourse> tcourseSetDb = texamService.findCourses(texamId);
		
		//need to refactor set to list everywhere
		List<TCourse> tcourseSet = new ArrayList<>(tcourseSetDb);
		
		Collections.sort(tcourseSet, (o1, o2) -> Long.compare(o1.getTcourseCode(), o2.getTcourseCode()));
		
		model.addAttribute("tcourseSet", tcourseSet);
		
		TCourse tcourse = new TCourse();
		model.addAttribute("tcourse", tcourse);
		
		return "tabulation/e-add-and-show-courses";
	}
	
	@GetMapping(value="/{teacherId}/exam/{texamId}/prepareTabulationSheet")
	public String prepareTabulationSheetGet(Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId)
	{
		TExam texam = texamService.getExam(texamId);
		Set<TCourse> tcourseSet = texam.getTcourses();
		
		Set<TStudent> tstudentSet = new HashSet<>();
		Set<Long> examRollSetUnordered = new HashSet<>();

		for(TCourse tcourse: tcourseSet)
		{
			tstudentSet = tcourse.getTstudents();
			
			for(TStudent tstudent: tstudentSet)
			{
				examRollSetUnordered.add(tstudent.getExamRoll());
			}
		}
		
		Set<Long> examRollSet = new TreeSet<>(examRollSetUnordered);
		model.addAttribute("examRollSet", examRollSet);
		model.addAttribute("teacherId", teacherId);
		model.addAttribute("texam", texam);
		return "tabulation/i-show-student-tabulation-mark-sheet";
	}
	
	@GetMapping(value="/{teacherId}/exam/{texamId}/viewMarkSheet/{examRoll}")
	public String viewSingleMarkSheetGet(Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("examRoll") Long examRoll)
	{
		
		List<TStudent> tstudentList = tstudentService.getTStudentByExamRoll(examRoll);
		
		List<MarkEntity> markList = new ArrayList<>();
		
		TExam texam = texamService.getExam(texamId);
		Set<TCourse> tcourseSet = texam.getTcourses();
		
		Double totalCreditHr = 0.0;
		Double totalWeightedGradePoint = 0.0; 
		
		for(TStudent tstudent : tstudentList)
		{
			if(tstudent.getTcourse().getTexam().getTexamId() != texam.getTexamId()) continue;
			
			MarkEntity mark = new MarkEntity();
			
			TCourse tcourse = tstudent.getTcourse();
			TMark tmark = tstudent.getTmark();

			totalCreditHr += tcourse.getTcourseCredit();
			totalWeightedGradePoint += (tmark.getGradePoint() * tcourse.getTcourseCredit());
			
			mark.setCourseName(tcourse.getTcourseName());
			mark.setCourseNumber(tcourse.getTcourseCode());
			mark.setCourseCredit(tcourse.getTcourseCredit());
			
			mark.setTutorialMark(tmark.getTutorialMark());
			mark.setInternalMark(tmark.getInternalMark());
			mark.setExternalMark(tmark.getExternalMark());
			mark.setThirdExaminerMark(tmark.getThirdExaminerMark());
			mark.setFinalMark(tmark.getFinalMark());
			mark.setTotalMark(tmark.getTotalMark());
			mark.setGradePoint(tmark.getGradePoint());
			mark.setLetterGrade(tmark.getLetterGrade());
			mark.setRemarks(tmark.getRemarks());
			
			markList.add(mark);
		}	
		
		Collections.sort(markList);
		
		Double gpa = 0.0;
		
		if(markList.size() == tcourseSet.size())
		{
			gpa = totalWeightedGradePoint/totalCreditHr;
		}
		
		model.addAttribute("markList", markList);
		model.addAttribute("examRoll", examRoll);
		model.addAttribute("teacherId", teacherId);
		model.addAttribute("texam", texam);
		
		gpa = UtilityService.round(gpa, 2);
		model.addAttribute("gpa", gpa);
		
		
		Student student = studentService.getStudentByExamRoll(examRoll);
		model.addAttribute("student", student);
		model.addAttribute("texam", texam);
		
		return "tabulation/ia-show-single-student-mark-sheet";
	}
	
	@PostMapping(value="/{teacherId}/exam/{texamId}/course")
	public String addAndShowCoursesPost(Model model, @ModelAttribute("tcourse") TCourse tcourse, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId)
	{	
		model.addAttribute("teacherId", teacherId);

		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		tcourse.setTexam(texam);
		
		tcourseService.addCourse(tcourse);
		
		Set<TCourse> tcourseSetDb = texamService.findCourses(texamId);
		
		//need to refactor set to list everywhere
		List<TCourse> tcourseSet = new ArrayList<>(tcourseSetDb);
		
		Collections.sort(tcourseSet, (o1, o2) -> Long.compare(o1.getTcourseCode(), o2.getTcourseCode()));
		
		model.addAttribute("tcourseSet", tcourseSet);
		
		TCourse newTcourse = new TCourse();
		
		model.addAttribute("tcourse", newTcourse);
		
		return "tabulation/e-add-and-show-courses";
	}
	
	@GetMapping(value="/{teacherId}/exam/{texamId}/course/{tcourseId}/delete")
	public String courseDeleteGet(Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId)
	{
		model.addAttribute("teacherId", teacherId);

		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		
		tcourseService.removeCourse(tcourse);
		
		Set<TCourse> tcourseSetDb = texamService.findCourses(texamId);
		
		//need to refactor set to list everywhere
		List<TCourse> tcourseSet = new ArrayList<>(tcourseSetDb);
		
		Collections.sort(tcourseSet, (o1, o2) -> Long.compare(o1.getTcourseCode(), o2.getTcourseCode()));
		
		model.addAttribute("tcourseSet", tcourseSet);
		
		TCourse newTcourse = new TCourse();
		
		model.addAttribute("tcourse", newTcourse);
		
		return "tabulation/e-add-and-show-courses";
	}
	
	@GetMapping(value="/{teacherId}/exam/{texamId}/course/{tcourseId}/courseConfig")
	public String showCourseConfigPageGet(Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId)
	{
		model.addAttribute("teacherId", teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		return "tabulation/ea-course-config-page";
	}
	
	
	@GetMapping(value="/{teacherId}/exam/{texamId}/course/{tcourseId}/registerdStudents")
	public String showRegisteredStudentsGet(Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId)
	{
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		Set<TStudent> tstudentSetDb = tcourse.getTstudents();
		Set<TStudent> tstudentSet = new TreeSet(tstudentSetDb);
		model.addAttribute("tstudentSet", tstudentSet);
		
		return "tabulation/f-show-registered-students";
	}
	
	
	@GetMapping(value="/{teacherId}/exam/{texamId}/course/{tcourseId}/registrationConfig")
	public String configRegistrationGet(Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId)
	{
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		Set<TStudent> tstudentSetDb = tcourse.getTstudents();
		Set<TStudent> tstudentSet = new TreeSet(tstudentSetDb);
		model.addAttribute("tstudentSet", tstudentSet);
		
		return "tabulation/g-student-registration-configuration-page";
	}
	
	
	@GetMapping(value="/{teacherId}/exam/{texamId}/course/{tcourseId}/defaultRegistration")
	public String showDefaultRegisteredStudentsGet(Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId)
	{
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		String dept = teacher.getDept();
		String session = texam.getSession();

		List<Student> studentList = studentService.findStudents(dept, session);
		
		//for(Student student : studentList) System.out.println(student.getExamRoll());
		
		tstudentService.registerDefaultStudents(studentList, tcourse);
		
		Set<TStudent> tstudentSetDb = tcourse.getTstudents();
		Set<TStudent> tstudentSet = new TreeSet(tstudentSetDb);
		model.addAttribute("tstudentSet", tstudentSet);
				
		return "tabulation/g-student-registration-configuration-page";
	}
	
	
	@GetMapping(value="/{teacherId}/exam/{texamId}/course/{tcourseId}/student/{tstudentId}")
	public String removeRegistrationGet(Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId, @PathVariable("tstudentId") Long tstudentId)
	{
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		tstudentService.deleteStudentReg(tstudentId);
		
		Set<TStudent> tstudentSetDb = tcourse.getTstudents();
		Set<TStudent> tstudentSet = new TreeSet(tstudentSetDb);
		model.addAttribute("tstudentSet", tstudentSet);
		
		return "tabulation/g-student-registration-configuration-page";
	}
	
	
	@PostMapping(value="/{teacherId}/exam/{texamId}/course/{tcourseId}/bulkStudentRegister")
	public String bulkStudentRegisterGet(Model model, @RequestParam("toBeRegistered") String toBeRegistered, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId)
	{		
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		List<String> examRollList = Pattern.compile(",")
	            .splitAsStream(toBeRegistered)
	            .map(String::trim)
	            .collect(Collectors.toList());
		
		tstudentService.bulkStudentRegister(examRollList, tcourse);
		
		Set<TStudent> tstudentSetDb = tcourse.getTstudents();
		Set<TStudent> tstudentSet = new TreeSet(tstudentSetDb);
		model.addAttribute("tstudentSet", tstudentSet);
		
		return "tabulation/g-student-registration-configuration-page";
	}
	
	
	@PostMapping(value="/{teacherId}/exam/{texamId}/course/{tcourseId}/bulkStudentDeRegister")
	public String bulkStudentDeRegisterGet(Model model, @RequestParam("toBeDeRegistered") String toBeDeRegistered, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId)
	{		
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		List<String> examRollList = Pattern.compile(",")
	            .splitAsStream(toBeDeRegistered)
	            .map(String::trim)
	            .collect(Collectors.toList());
		
		tstudentService.bulkStudentDeRegister(examRollList, tcourse);
		
		Set<TStudent> tstudentSetDb = tcourse.getTstudents();
		Set<TStudent> tstudentSet = new TreeSet(tstudentSetDb);
		model.addAttribute("tstudentSet", tstudentSet);
		
		return "tabulation/g-student-registration-configuration-page";
	}
	
	////////////////////////
	
	
	@GetMapping(value="/{teacherId}/exam/{texamId}/course/{tcourseId}/tutoMarkEntry")
	public String tutoMarkEntryGet(Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId)
	{
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		Set<TStudent> tstudentSet = tcourse.getTstudents();
		
		List<TStudent> tstudentList = new ArrayList<>(tstudentSet);
		Collections.sort(tstudentList);
		
		TStudentListContainer tstudentListContainer = new TStudentListContainer();
		tstudentListContainer.setTstudentList(tstudentList);
		
		model.addAttribute("tstudentListContainer", tstudentListContainer);
		
//		for(TStudent tstudent: tstudentListContainer.getTstudentList())
//		{
//			System.out.println(tstudent);
//		}
		
		return "tabulation/h-tuto-mark-entry";
	}
	
	
	@PostMapping(value="/{teacherId}/exam/{texamId}/course/{tcourseId}/tutoMarkEntry")
	public String tutoMarkEntryPost(Model model, @ModelAttribute("tstudentListContainer") TStudentListContainer tstudentSetContainer,  @PathVariable("teacherId") Long teacherId, @PathVariable("texamId") Long texamId, @PathVariable("tcourseId") Long tcourseId)
	{
		List<TStudent> tstudentList = tstudentSetContainer.getTstudentList();
		
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		for(TStudent tstudent: tstudentList)
		{
			TStudent tstudentDb = tstudentService.getTStudent(tstudent.getTstudentId());
			TMark tmarkDb = tmarkService.getTMarkByTStudent(tstudentDb);
			
			
			Double tutoMark = tstudent.getTmark().getTutorialMark();
		//	System.out.println(tutoMark);
			
			if(tmarkDb==null)
			{
				TMark tmark = new TMark();
				tmark.setTstudent(tstudentDb);
				tmark.setTutorialMark(tutoMark);
				tmarkService.addMarks(tmark);
				
				tmarkService.updateAllMarks(tmark);
			}
			else
			{
				tmarkDb.setTutorialMark(tutoMark);
				tmarkService.addMarks(tmarkDb);
				
				tmarkService.updateAllMarks(tmarkDb);
			}
			
		}
		
		return "tabulation/ea-course-config-page";
	}
	
	@GetMapping(value="/{teacherId}/exam/{texamId}/course/{tcourseId}/internalMarkEntry")
	public String internalMarkEntryGet(Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId)
	{
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		Set<TStudent> tstudentSet =  tcourse.getTstudents();
		List<TStudent> tstudentList = new ArrayList<>(tstudentSet);
		Collections.sort(tstudentList);
		
		TStudentListContainer tstudentListContainer = new TStudentListContainer();
		tstudentListContainer.setTstudentList(tstudentList);
		
		model.addAttribute("tstudentListContainer", tstudentListContainer);
		
		return "tabulation/ha-internal-mark-entry";
	}
	
	
	@PostMapping(value="/{teacherId}/exam/{texamId}/course/{tcourseId}/internalMarkEntry")
	public String internalMarkEntryPost(Model model, @ModelAttribute("tstudentListContainer") TStudentListContainer tstudentSetContainer,  @PathVariable("teacherId") Long teacherId, @PathVariable("texamId") Long texamId, @PathVariable("tcourseId") Long tcourseId)
	{
		List<TStudent> tstudentList = tstudentSetContainer.getTstudentList();
		
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		for(TStudent tstudent: tstudentList)
		{
			TStudent tstudentDb = tstudentService.getTStudent(tstudent.getTstudentId());
			TMark tmarkDb = tmarkService.getTMarkByTStudent(tstudent);
			
			Double internalMark = tstudent.getTmark().getInternalMark();
		//	System.out.println(tutoMark);
			
			if(tmarkDb==null)
			{
				TMark tmark = new TMark();
				tmark.setTstudent(tstudentDb);
				tmark.setInternalMark(internalMark);
				tmarkService.addMarks(tmark);
				
				tmarkService.updateAllMarks(tmark);
			}
			else
			{
				tmarkDb.setInternalMark(internalMark);
				tmarkService.addMarks(tmarkDb);
				
				tmarkService.updateAllMarks(tmarkDb);
			}
		}
		
		return "tabulation/ea-course-config-page";
	}
	
	@GetMapping(value="/{teacherId}/exam/{texamId}/course/{tcourseId}/externalMarkEntry")
	public String externalMarkEntryGet(Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId)
	{
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		Set<TStudent> tstudentSet =  tcourse.getTstudents();
		List<TStudent> tstudentList = new ArrayList<>(tstudentSet);
		Collections.sort(tstudentList);
		
		TStudentListContainer tstudentListContainer = new TStudentListContainer();
		tstudentListContainer.setTstudentList(tstudentList);
		
		model.addAttribute("tstudentListContainer", tstudentListContainer);
		
		return "tabulation/hb-external-mark-entry";
	}
	
	
	@PostMapping(value="/{teacherId}/exam/{texamId}/course/{tcourseId}/externalMarkEntry")
	public String externalMarkEntryPost(Model model, @ModelAttribute("tstudentListContainer") TStudentListContainer tstudentSetContainer,  @PathVariable("teacherId") Long teacherId, @PathVariable("texamId") Long texamId, @PathVariable("tcourseId") Long tcourseId)
	{
		List<TStudent> tstudentList = tstudentSetContainer.getTstudentList();
		
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		for(TStudent tstudent: tstudentList)
		{
			TMark tmarkDb = tmarkService.getTMarkByTStudent(tstudent);
			TStudent tstudentDb = tstudentService.getTStudent(tstudent.getTstudentId());
			
			Double externalMark = tstudent.getTmark().getExternalMark();
		//	System.out.println(tutoMark);
			
			if(tmarkDb==null)
			{
				TMark tmark = new TMark();
				tmark.setTstudent(tstudentDb);
				tmark.setExternalMark(externalMark);
				tmarkService.addMarks(tmark);
				
				tmarkService.updateAllMarks(tmark);
			}
			else
			{
				tmarkDb.setExternalMark(externalMark);
				tmarkService.addMarks(tmarkDb);
				
				tmarkService.updateAllMarks(tmarkDb);
			}
		}
		
		return "tabulation/ea-course-config-page";
	}
	
	@GetMapping(value="/{teacherId}/exam/{texamId}/course/{tcourseId}/thirdExaminerMarkEntry")
	public String thirdExaminerMarkEntryGet(Model model, @PathVariable("teacherId") long teacherId, @PathVariable("texamId") long texamId, @PathVariable("tcourseId") long tcourseId)
	{
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		Set<TStudent> tstudentSet =  tcourse.getTstudents();
		
		tstudentSet.removeIf(tstudent -> 
		Math.abs(tstudent.getTmark().getExternalMark() - tstudent.getTmark().getInternalMark()) <= 10.0);
		
//		Iterator tstudentSetIter = tstudentSet.iterator();
//		
//		while(tstudentSetIter.hasNext())
//		{
//			TStudent tstudent = tstudentSetIter.next();
//			double markDifference = ;
//			System.out.println(markDifference);
//			if(markDifference <= 10.0)
//			{
//				tstudentSet.remove(tstudent);
//			}
//		}
		
		List<TStudent> tstudentList = new ArrayList<>(tstudentSet);
		Collections.sort(tstudentList);
		
		TStudentListContainer tstudentListContainer = new TStudentListContainer();
		tstudentListContainer.setTstudentList(tstudentList);
		
		model.addAttribute("tstudentListContainer", tstudentListContainer);
		
		return "tabulation/hc-third-examiner-mark-entry";
	}
	
	@PostMapping(value="/{teacherId}/exam/{texamId}/course/{tcourseId}/thirdExaminerMarkEntry")
	public String thirdExaminerMarkEntryPost(Model model, @ModelAttribute("tstudentListContainer") TStudentListContainer tstudentSetContainer,  @PathVariable("teacherId") Long teacherId, @PathVariable("texamId") Long texamId, @PathVariable("tcourseId") Long tcourseId)
	{
		List<TStudent> tstudentList = tstudentSetContainer.getTstudentList();
		
		model.addAttribute("teacherId", teacherId);
		Teacher teacher = teacherService.getTeacher(teacherId);
		
		TExam texam = texamService.getExam(texamId);
		model.addAttribute("texam", texam);
		
		TCourse tcourse = tcourseService.getTCourse(tcourseId);
		model.addAttribute("tcourse", tcourse);
		
		for(TStudent tstudent: tstudentList)
		{
			TMark tmarkDb = tmarkService.getTMarkByTStudent(tstudent);
			TStudent tstudentDb = tstudentService.getTStudent(tstudent.getTstudentId());
			
			Double thirdExaminerMark = tstudent.getTmark().getThirdExaminerMark();
			
			if(tmarkDb==null)
			{
				TMark tmark = new TMark();
				tmark.setTstudent(tstudentDb);
				tmark.setThirdExaminerMark(thirdExaminerMark);
				tmarkService.addMarks(tmark);
				
				tmarkService.updateAllMarks(tmark);
			}
			else
			{
				tmarkDb.setThirdExaminerMark(thirdExaminerMark);
				tmarkService.addMarks(tmarkDb);
				
				tmarkService.updateAllMarks(tmarkDb);
			}
		}
		
		return "tabulation/ea-course-config-page";
	}
}
