package kopo.poly.controller;

import kopo.poly.dto.FoodDTO;
import kopo.poly.service.IFoodService;
import kopo.poly.service.impl.FoodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/food")
public class FoodController {
    @Resource(name = "FoodService")
    private IFoodService foodService;

    @RequestMapping(value = "toDayFood")
    public String toDayFood(ModelMap model) throws Exception{
        log.info(this.getClass().getName() + ".toDayFood 시작!!");

        List<FoodDTO> rList = foodService.toDayFood();

        log.info("rList"+rList);
        model.addAttribute("rList", rList);

        log.info(this.getClass().getName() + ".toDayFood 끝!!");
        return "/food/toDayFood";
    }
}
