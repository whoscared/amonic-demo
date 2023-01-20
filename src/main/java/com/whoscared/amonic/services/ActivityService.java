package com.whoscared.amonic.services;

import com.whoscared.amonic.domain.person.Person;
import com.whoscared.amonic.domain.utils.Activity;
import com.whoscared.amonic.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public Time getTimeSpendOnSystemLast30days(Person person) {
        List<Activity> personAllActivity = findByPerson(person);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        personAllActivity = personAllActivity.stream().filter(x -> {
            try {
                Date current = format.parse(x.getDate());
                // данное число = 30 дней
                return current.getTime() + 2592000000L <= new Date().getTime();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        Long allTime = personAllActivity.stream()
                .filter(x -> x.getTimeSpendOnSystem() != null)
                .map(x -> x.getTimeSpendOnSystem().getTime())
                .reduce((y, x) -> y + x - 10800000).orElse(null);
        if (allTime == null) {
            return null;
        }
        return new Time(allTime);
    }

    public long getCountUnsuccessfulLogout(Person person) {
        List<Activity> allActivity = findByPerson(person);
        return allActivity.stream().filter(x -> x.getUnsuccessfulLogoutReason() != null).count();
    }
}
