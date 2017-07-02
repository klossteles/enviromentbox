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

    @RequestMapping(value = "/consultarSensoresByDeviceIdFiltradas/{device_id}/{data_hora_medicao_inicial}/{data_hora_medicao_final}", method = RequestMethod.GET)
    public String consultaMedicoesByDeviceIdFiltradas(@PathVariable("device_id") Long device_id, @PathVariable("data_hora_medicao_inicial") String dataMedicaoInicial, @PathVariable("data_hora_medicao_final") String dataMedicaoFinal) {
        return sensorService.consultarSensoresByDeviceIdFiltradas(device_id.longValue(), dataMedicaoInicial, dataMedicaoFinal);
    }
}
