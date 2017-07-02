package br.com.enviromentbox.controller;

import br.com.enviromentbox.domain.AlertaDevice;
import br.com.enviromentbox.service.AlertaDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by KlossTeles on 17/06/2017.
 */
@RestController
public class AlertaDeviceController {
    @Autowired
    AlertaDeviceService alertaDeviceService;

    @RequestMapping(value = "/consultarAlertasNaoProcessadosByDeviceId/{device_id}", method = RequestMethod.GET)
    public String consultarAlertasNaoProcessadosByDeviceId(@PathVariable("device_id") Long device_id) {
        return alertaDeviceService.consultarAlertasNaoProcessadosByDeviceId(device_id.longValue());
    }

    @RequestMapping(value = "/processarAlerta/{id_alerta}", method = RequestMethod.GET)
    public String processarAlerta(@PathVariable("id_alerta") Long id_alerta){
        return alertaDeviceService.processarAlerta(id_alerta.longValue());
    }

}
