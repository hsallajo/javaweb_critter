package com.shu.jdnd.course3.critter.schedule;

import com.shu.jdnd.course3.critter.exception.CritterAPIRequestException;
import com.shu.jdnd.course3.critter.model.Employee;
import com.shu.jdnd.course3.critter.model.Pet;
import com.shu.jdnd.course3.critter.model.Schedule;
import com.shu.jdnd.course3.critter.service.ScheduleService;
import com.shu.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        if(scheduleDTO.getEmployeeIds().size() == 0)
            throw new CritterAPIRequestException("Invalid API param: employee id");
        if(scheduleDTO.getPetIds().size() == 0)
            throw new CritterAPIRequestException("Invalid API param: pet id");
        if(scheduleDTO.getActivities().size() == 0)
            throw new CritterAPIRequestException("Invalid API param: activity");

        return scheduleToScheduleDTO(scheduleService.saveSchedule(scheduleDTOToSchedule(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> res = scheduleService.findAll();
        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        for (Schedule s: res
             ) {
            scheduleDTOs.add(scheduleToScheduleDTO(s));
        }

        return scheduleDTOs;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        if(petId == 0)
            throw new CritterAPIRequestException("Invalid API param: pet id");
        return schedulesToScheduleDTOS(scheduleService.findSchedulesForPet(petId));
    }


    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        if(employeeId == 0)
            throw new CritterAPIRequestException("Invalid API param: employee id");

        return schedulesToScheduleDTOS(scheduleService.findSchedulesForEmployee(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        if(customerId == 0)
            throw new CritterAPIRequestException("Invalid API param: customer id");
        return schedulesToScheduleDTOS(scheduleService.findSchedulesForCustomer(customerId));
    }

    private ScheduleDTO scheduleToScheduleDTO(Schedule schedule){
        ScheduleDTO s = new ScheduleDTO();

        BeanUtils.copyProperties(schedule, s);
        ArrayList<Long> employeeIdList = new ArrayList<>();
        for (Employee e: schedule.getEmployeeList()
        ) {
            employeeIdList.add(e.getId());
        }
        ArrayList<Long> petIdList = new ArrayList<>();
        for (Pet p: schedule.getPetList()
             ) {
            petIdList.add(p.getId());
        }
        Set<EmployeeSkill> skills = new HashSet<>(schedule.getActivities());

        s.setEmployeeIds(employeeIdList);
        s.setPetIds(petIdList);
        s.setActivities(skills);

        return s;
    }

    private Schedule scheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule s = new Schedule();
        BeanUtils.copyProperties(scheduleDTO,s);

        ArrayList<Employee> employeeList = new ArrayList<>();
        for (Long e: scheduleDTO.getEmployeeIds()
        ) {
            Employee employee = new Employee();
            employee.setId(e);
            employeeList.add(employee);
        }
        ArrayList<Pet> pets = new ArrayList<>();
        for (Long p: scheduleDTO.getPetIds()
        ) {
            Pet pet = new Pet();
            pet.setId(p);
            pets.add(pet);
        }
        Set<EmployeeSkill> activities = new HashSet<>(scheduleDTO.getActivities());

        s.setEmployeeList(employeeList);
        s.setPetList(pets);
        s.setActivities(activities);
        return s;
    }

    private List<ScheduleDTO> schedulesToScheduleDTOS(List<Schedule> res) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule s : res
        ) {
            scheduleDTOS.add(scheduleToScheduleDTO(s));
        }

        return scheduleDTOS;
    }
}
