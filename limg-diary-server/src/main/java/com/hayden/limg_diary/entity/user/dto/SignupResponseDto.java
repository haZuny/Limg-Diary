package com.hayden.limg_diary.entity.user.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class SignupResponseDto {

        int status;
        boolean success;
        String msg;

        public void setSuccess(){
            this.status = HttpStatus.CREATED.value();
            this.success = true;
            this.msg = "success";
        }

        public void setFail(){
            this.status = HttpStatus.BAD_REQUEST.value();
            this.success = false;
            this.msg = "fail";
        }
}
