package br.com.enviromentbox.controller;

import br.com.enviromentbox.domain.Medicao;
import br.com.enviromentbox.service.MedicaoService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Kloss Teles on 20/05/2017.
 */
@RestController
public class MedicaoController {

    @Autowired
    MedicaoService medicaoService;

    @RequestMapping(value = "/consultaMedicoesByDeviceId/{device_id}", method = RequestMethod.GET)
    public List<Medicao> consultaMedicoesByDeviceId(@PathVariable("device_id") Long device_id) {
        return medicaoService.consultaMedicoesByDeviceId(device_id.longValue());
    }

    @RequestMapping(value = "/consultarMedicoesFiltradas/{device_id}/{sensor_id}/{data_hora_medicao_inicial}/{data_hora_medicao_final}", method = RequestMethod.GET)
    public String consultarMedicoesFiltradas(@PathVariable("device_id") Long device_id, @PathVariable("sensor_id") Long sensor_id, @PathVariable("data_hora_medicao_inicial") String dataMedicaoInicial, @PathVariable("data_hora_medicao_final") String dataMedicaoFinal) {
        return medicaoService.consultarMedicoesFiltradas(device_id.longValue(), sensor_id.longValue(), dataMedicaoInicial, dataMedicaoFinal);
    }

    @RequestMapping(value = "/consultarDadosMedicoesDevice/{device_id}/{data_inicial}/{data_final}", method = RequestMethod.GET)
    public String consultaDadosMedicoesDevice(@PathVariable("device_id") Long device_id, @PathVariable("data_inicial") String data_hora_inicial, @PathVariable("data_final") String data_hora_final){
        return medicaoService.consultaDadosMedicoesDevice(device_id.longValue(), data_hora_inicial, data_hora_final);
    }

    @RequestMapping(value = "/consultarDadosMedicoesBySensor/{device_id}/{sensor_id}", method = RequestMethod.GET)
    public String consultarDadosMedicoesBySensor(@PathVariable("device_id") Long device_id, @PathVariable("sensor_id") Long sensor_id){
        return  medicaoService.consultarDadosMedicoesBySensor(device_id.longValue(), sensor_id.longValue());
    }
}
