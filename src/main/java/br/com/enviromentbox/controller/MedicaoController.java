package br.com.enviromentbox.controller;

import br.com.enviromentbox.service.MedicaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Kloss Teles on 20/05/2017.
 */
@Controller
public class MedicaoController {

    @Autowired
    MedicaoService medicaoService;

    @RequestMapping(value = "/")
    public void salvarStr() {
        String message = "id_device:1151;id_sensor:1202;valor_medicao:2121";
        medicaoService.salvarStr(message);
    }
}
