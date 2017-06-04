package br.com.enviromentbox.controller;

import br.com.enviromentbox.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Kloss Teles on 20/05/2017.
 */
@RestController
public class SensorController {

    @Autowired
    SensorService sensorService;

    @RequestMapping(value = "/consultarSensoresByDeviceId/{device_id}", method = RequestMethod.GET)
    public String consultaMedicoesByDeviceId(@PathVariable("device_id") Long device_id) {
        return sensorService.consultarSensoresByDeviceId(device_id.longValue());
    }
}
