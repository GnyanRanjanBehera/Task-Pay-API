package com.task_pay.task_pay.utils;
import com.task_pay.task_pay.models.enums.DateFormatPattern;
import com.task_pay.task_pay.payloads.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public  static<U,V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type){
        List<U> entity = page.getContent();
        List<V> dtoList=entity.stream().map(object -> new ModelMapper().map(object,type)).collect(Collectors.toList());
        PageableResponse<V> response=new PageableResponse<>();
        response.setData(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return  response;
        
    }

    public static Date convertStringToDate(String date) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat(DateFormatPattern.ISO.getPattern());
        return format.parse(date);
    }



}
