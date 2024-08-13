package com.task_pay.task_pay.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/bank")
public class BankController {
    @PostMapping("/saveBank")
    public ResponseEntity<String> saveBank(){
        return null;
    }
    @GetMapping("/fetchBank")
    public  ResponseEntity<String> fetchBank(){
        return null;
    }

    @PutMapping("/updateBank")
    public ResponseEntity<String> updateBank(){
        return null;
    }
    @DeleteMapping("/deleteBank")
    public ResponseEntity<String> deleteBank(){
        return null;
    }
}
