package com.example.sk_example;


import com.example.sk_example.dto.CurrentDto;
import com.example.sk_example.dto.SkDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping(value = "/modify")
@Slf4j
public class SkController {

    private final SkService skService;

    public SkController(SkService skService) {
        this.skService = skService;
    }

    @PostMapping
    public ResponseEntity<?> getCurrent(@RequestBody SkDto skDto) {
        log.info("getCurrent skDto: {}", skDto);
        CurrentDto currentDto = skService.returnCurrent(skDto);
        if (currentDto != null) {
            return new ResponseEntity<>((currentDto), HttpStatus.OK);
        } else {
            throw new HttpClientErrorException(HttpStatus.I_AM_A_TEAPOT, "Not found current id");
        }
    }


}




