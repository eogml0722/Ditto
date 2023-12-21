package com.ditto.controller;

import com.ditto.dto.ItemSearchDTO;
import com.ditto.dto.MainItemDTO;
import com.ditto.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping(value = "/")
    public String goMain(Model model){
        return "index";
    }





}
