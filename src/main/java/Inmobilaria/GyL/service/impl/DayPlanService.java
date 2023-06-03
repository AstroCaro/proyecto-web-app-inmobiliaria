package Inmobilaria.GyL.service.impl;

import Inmobilaria.GyL.entity.DayPlan;
import Inmobilaria.GyL.entity.Property;
import Inmobilaria.GyL.entity.User;
import Inmobilaria.GyL.repository.DayPlanRepository;
import Inmobilaria.GyL.service.IAppointmentService;
import Inmobilaria.GyL.service.IDayPlanService;
import Inmobilaria.GyL.service.IPropertyService;
import Inmobilaria.GyL.service.ITimePeriodService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class DayPlanService implements IDayPlanService {

    private final DayPlanRepository dayPlanRepository;

    private final IPropertyService propertyService;

    private final IAppointmentService appointmentService;

    public DayPlanService(DayPlanRepository dayPlanRepository, IPropertyService propertyService, ITimePeriodService timePeriodService, IAppointmentService appointmentService) {
        this.dayPlanRepository = dayPlanRepository;
        this.propertyService = propertyService;
        this.appointmentService = appointmentService;
    }

    @Override
    public DayPlan addDayPlan(Long propertyId, LocalDate timetableDay, LocalTime start, LocalTime end) {
        DayPlan dayPlan = new DayPlan();
        Property property = propertyService.findById(propertyId);
        dayPlan.setTimetableDay(timetableDay);
        dayPlan.setStart(start);
        dayPlan.setEnd(end);
        dayPlan.setProperty(property);
        appointmentService.saveAvailableAppointments(dayPlan, propertyId);
        return dayPlanRepository.save(dayPlan);
    }

    @Override
    public List<DayPlan> findAllDayPlanByProperty(Long id) {
        return dayPlanRepository.findAllByProperty(id);
    }

    @Override
    public List<DayPlan> findAllDayPlanByUser(Long id) {
        return dayPlanRepository.findAllByUser(id);
    }

    @Override
    public void deleteDayPlan(Long id, User user) {
        DayPlan dayPlan = dayPlanRepository.findById(id).get();
        Property property = dayPlan.getProperty();
        if (propertyService.findByUser(user.getId()).contains(property) && ) {
            dayPlanRepository.deleteById(id);
        }
    }
}
