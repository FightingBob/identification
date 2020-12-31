package com.bob.identification.service.impl;

import com.bob.identification.service.IdentificationCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IdentificationCodeServiceImplTest {

    @Autowired
    private IdentificationCodeService codeService;

    @Test
    void addCode() {
        codeService.laikouAddCode("0");
        codeService.laikouAddCode("1");
        codeService.laikouAddCode("2");
        codeService.laikouAddCode("3");
        codeService.laikouAddCode("4");
        codeService.fenyiAddCode("0");
        codeService.fenyiAddCode("1");
        codeService.fenyiAddCode("2");
        codeService.fenyiAddCode("3");
        codeService.fenyiAddCode("4");
    }
}