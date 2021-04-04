package com.jz.aqjianzhi.ws_service.controller;


import com.jz.aqjianzhi.utils.R;
import com.jz.aqjianzhi.ws_service.entity.AqUserContact;
import com.jz.aqjianzhi.ws_service.service.AqUserContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xyk
 * @since 2021-04-03
 */
@RestController
@RequestMapping("/ws_service")
public class AqUserContactController {

    @Autowired
    private AqUserContactService userContactService;

    @PutMapping("/addContact")
    public R addContact(@RequestBody AqUserContact userContact) {

        userContactService.save(userContact);

        return R.ok();
    }
}

