package com.example.mybatisdemo.controller;

import com.example.mybatisdemo.service.DataServiceApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/")
public class DataServiceController {
    @Autowired
    private DataServiceApp dataServiceApp;

    @PostMapping("/api/data/list")
    @ResponseBody
    public Map<String, Object> getData(@Validated @RequestBody Map<String, Object> map) {

        return dataServiceApp.getData(map);
    }
}
