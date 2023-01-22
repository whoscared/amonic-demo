package com.whoscared.amonic.controllers;

import com.whoscared.amonic.utils.ScheduleChangesHandling;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Controller
@RequestMapping("/schedules_changes")
public class SchedulesChangesController {

    private final ScheduleChangesHandling changesHandling;

    @Autowired
    public SchedulesChangesController(ScheduleChangesHandling changesHandling) {
        this.changesHandling = changesHandling;
    }

    @GetMapping()
    public String schedulesChanges(@ModelAttribute("message") String message,
                                   Model model) {
        model.addAttribute("message", message);
        return "schedule/schedules_changes";
    }

    @PostMapping()
    public String applySchedulesChanges(@RequestParam("file") MultipartFile file,
                                        Model model) {
        try {
            if (!file.isEmpty()) {
                changesHandling.cleanChangesResult();
                InputStream inputStream = file.getInputStream();
                String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                HashMap<String, Integer> changeResult = changesHandling.handling(result);
                model.addAttribute("changesResult", changeResult);
                model.addAttribute("message", "You successfully uploaded file");
            } else {
                model.addAttribute("message", "File was empty!");
            }
        } catch (Exception e) {
            model.addAttribute("message", "Error!!!");
        }
        return "schedule/schedules_changes";
    }
}
