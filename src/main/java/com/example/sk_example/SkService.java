package com.example.sk_example;

import com.example.sk_example.dto.CurrentDto;
import com.example.sk_example.dto.SkDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class SkService {

    private final SkDao skDao;

    public SkService(SkDao skDao) {
        this.skDao = skDao;
    }

    public CurrentDto returnCurrent(@RequestBody SkDto skDto) {
        CurrentDto currentDto = new CurrentDto(skDao.increaseCurrentById(skDto));
        if (currentDto.getCurrent() != null) return currentDto;
        else return null;
    }
}
