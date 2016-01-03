package ru.pavel2107.neostoreRest.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.pavel2107.neostoreRest.utils.LoggerWrapper;


/**
 * Created by lenovo on 12.12.2015.
 */
@Controller
public class RootController {
    private static final LoggerWrapper LOG = LoggerWrapper.get(RootController.class);

    @RequestMapping( value = "/", method = RequestMethod.GET)
    public String root()
    {
        return "index";
    }


}
