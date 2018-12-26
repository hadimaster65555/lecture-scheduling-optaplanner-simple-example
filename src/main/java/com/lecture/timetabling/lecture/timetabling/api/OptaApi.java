package com.lecture.timetabling.lecture.timetabling.api;

import java.util.Arrays;
import java.util.List;

import com.lecture.timetabling.lecture.timetabling.domain.CourseSchedule;
import com.lecture.timetabling.lecture.timetabling.domain.Lecture;

import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OptaApi {

    Logger logger = LoggerFactory.getLogger("OptaApi");
    
    static CourseSchedule unsolvedCourseSchedule;

    public void setUp() {
        unsolvedCourseSchedule = new CourseSchedule();
        for (int i = 0; i < 20; i++) {
            unsolvedCourseSchedule.getLectureList().add(new Lecture());
        }
        System.out.println("=== Daftar Kuliah ===");
        logger.info(unsolvedCourseSchedule.getLectureList().toString());
        unsolvedCourseSchedule.getPeriodList().addAll(Arrays.asList(new Integer[] {1,2,3}));
        unsolvedCourseSchedule.getRoomList().addAll(Arrays.asList(new Integer[] {1,2,3,4,5,6,7,8,9,10}));
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Lecture> testSolver() {
        setUp();
        SolverFactory<CourseSchedule> solverFactory = SolverFactory.createFromXmlResource("CourseScheduleConfig.xml");
        Solver<CourseSchedule> solver = solverFactory.buildSolver();
        logger.info(solver.toString());
        CourseSchedule solvedCourseSchedule = solver.solve(unsolvedCourseSchedule);  
        // unsolvedCourseSchedule.printCourseSchedule();
        System.out.println("==== Skor ====");
        System.out.println(solvedCourseSchedule.getScore());
        System.out.println("==== Nilai HardScore ====");
        System.out.println(solvedCourseSchedule.getScore().getHardScore());
        for(Lecture lecture : solvedCourseSchedule.getLectureList()) {
            System.out.println("Kuliah di ruangan " + lecture.getRoomNumber().toString() + " di periode " + lecture.getPeriod().toString());
        }
        return solvedCourseSchedule.getLectureList();
    }

    // public void printCourseSchedule() {
    //     lectureList.stream()
    //             .map(c -> "Lecture in Room " + c.getRoomNumber().toString() + " during Period " + c.getPeriod().toString())
    //             .forEach(k -> logger.info(k));
    // }

}