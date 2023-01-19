package com.whoscared.amonic.services;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.domain.utils.Activity;
import com.whoscared.amonic.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Activity findById(Long id) {
        return activityRepository.findById(id).orElse(null);
    }

    public void edit(Long id, Activity activity) {
        activity.setId(id);
        activityRepository.save(activity);
    }

    public List<Activity> findByPerson(Person person) {
        return activityRepository.findByPerson(person);
    }

    public Activity getLastActivityByPerson(Person person) {
        return findByPerson(person).stream().sorted(Comparator.comparing(Activity::getLoginTime)).toList().get(findByPerson(person).size() - 1);
    }

    public void successfulLogout(Long id, Date logoutTime) {

        Activity current = activityRepository.findById(id).orElse(null);
        current.setLogoutTime(logoutTime);

        current.setTimeSpendOnSystem(new Time(current.getLogoutTime().getTime() - current.getLoginTime().getTime() - 10800000));

        edit(id, current);

    }
}
