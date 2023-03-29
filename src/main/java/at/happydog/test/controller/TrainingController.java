package at.happydog.test.controller;

import at.happydog.test.enity.AppUserRoles;
import at.happydog.test.enity.Training;
import at.happydog.test.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.stereotype.Controller
@RequestMapping(path = "/training")
public class TrainingController {

    private final TrainingService trainingService;

    @Autowired
    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }


    @GetMapping( "view")
    public ModelAndView userProfileView(@RequestParam Long training_id){

        ModelAndView mav = new ModelAndView("training");

        Training training = trainingService.getTrainingById(training_id).get();


        mav.addObject("training", training);

        return mav;
    }

}
