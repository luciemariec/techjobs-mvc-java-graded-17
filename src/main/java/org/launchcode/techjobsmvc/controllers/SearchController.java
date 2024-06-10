package org.launchcode.techjobsmvc.controllers;

import org.launchcode.techjobsmvc.models.Job;
import org.launchcode.techjobsmvc.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

import static org.launchcode.techjobsmvc.controllers.ListController.columnChoices;


/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @GetMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", columnChoices);
        return "search";
    }

    // TODO #3 - Create a handler to process a search request and render the updated search view.
    @PostMapping(value = "results")
    public String displaySearchResults(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        ArrayList<Job> jobs = null;
        if (searchType.equals("all")) { //Check if the searchType is "all"
            jobs = JobData.findByValue(searchTerm);
            model.addAttribute("title", "All Jobs");
        }
        else
        { //if searchType is specific (not "all"), find jobs by a specific column and value
            jobs = JobData.findByColumnAndValue(searchType, searchTerm);
            model.addAttribute("title", "Jobs with " +
                    ListController.columnChoices.get(searchType) + ": " + searchTerm);
        }
        model.addAttribute("jobs", jobs); //add the list of jobs to the model
        model.addAttribute("columns", ListController.columnChoices); //add the column choices to the model to populate the search form
        model.addAttribute("searchType", searchType); //add the search type to the model
        return "search";
    }
}

