package com.whoscared.amonic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/schedules_changes")
public class SchedulesChangesController {

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
                //обработка файла
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
