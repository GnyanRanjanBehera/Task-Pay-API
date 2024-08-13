package com.task_pay.task_pay.controllers;
import com.task_pay.task_pay.models.dtos.BankDto;
import com.task_pay.task_pay.payloads.ApiMessageResponse;
import com.task_pay.task_pay.services.BankService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/bank")
public class BankController {

    @Autowired
    private BankService bankService;



    @PostMapping("/saveBank/{userId}")
    public ResponseEntity<BankDto> saveBank(@Valid @RequestBody BankDto bankDto,@Valid @PathVariable Integer userId){
        BankDto bankDto1 = bankService.saveBank(userId, bankDto);
        return new ResponseEntity<>(bankDto1, HttpStatus.OK);
    }
    @GetMapping("/fetchBank")
    public  ResponseEntity<BankDto> fetchBank(@RequestParam(value = "userId") Integer userId){
        BankDto bankDto = bankService.fetchBank(userId);
        return new ResponseEntity<>(bankDto,HttpStatus.OK);
    }

    @PutMapping("/updateBank")
    public ResponseEntity<BankDto> updateBank(@Valid @RequestBody BankDto bankDto){
        BankDto bankDto1 = bankService.updateBank(bankDto);
        return new ResponseEntity<>(bankDto1,HttpStatus.OK);
    }
    @DeleteMapping("/deleteBank")
    public ResponseEntity<ApiMessageResponse> deleteBank(@RequestParam(value = "bankId") Integer bankId){
        bankService.deleteBank(bankId);
        ApiMessageResponse deleteSuccess = ApiMessageResponse.builder().message("Delete successfully").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(deleteSuccess,HttpStatus.OK);
    }
}
