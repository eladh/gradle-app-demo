package com.jfrog.demogradle.controllers;


import com.jfrog.demogradle.services.GeoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/geo")
public class GeoController {

    @Autowired
    private GeoService geoService;

    // Define the log object for this class
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/getIpInfo")
    @ResponseBody
    public String getInfo(@RequestParam("ip") String ip) throws Exception {
        return  geoService.getIpDetails(ip).toString();
    }

}