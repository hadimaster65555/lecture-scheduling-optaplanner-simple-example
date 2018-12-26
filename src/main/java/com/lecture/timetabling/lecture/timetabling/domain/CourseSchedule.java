package com.lecture.timetabling.lecture.timetabling.domain;

import java.util.ArrayList;
import java.util.List;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.drools.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PlanningSolution
public class CourseSchedule {
    Logger logger = LoggerFactory.getLogger("CourseSchedule");
    
    private List<Integer> roomList;
    private List<Integer> periodList;
    private List<Lecture> lectureList;

    private HardSoftScore score;

    public CourseSchedule() {
        roomList = new ArrayList<>();
        periodList = new ArrayList<>();
        lectureList = new ArrayList<>();
    }

    @ValueRangeProvider(id = "availableRooms")
    @ProblemFactCollectionProperty
    public List<Integer> getRoomList() {
        return this.roomList;
    }

    public void setRoomList(List<Integer> roomList) {
        this.roomList = roomList;
    }
    
    @ValueRangeProvider(id = "availablePeriods")
    @ProblemFactCollectionProperty
    public List<Integer> getPeriodList() {
        return this.periodList;
    }

    public void setPeriodList(List<Integer> periodList) {
        this.periodList = periodList;
    }

    @PlanningEntityCollectionProperty
    public List<Lecture> getLectureList() {
        return this.lectureList;
    }

    public void setLectureList(List<Lecture> lectureList) {
        this.lectureList = lectureList;
    }
    
    @PlanningScore
    public HardSoftScore getScore() {
        return this.score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }

    public void printCourseSchedule() {
        lectureList.stream()
                .map(c -> "Lecture in Room " + c.getRoomNumber().toString() + " during Period " + c.getPeriod().toString())
                .forEach(k -> logger.info(k));
    }

}