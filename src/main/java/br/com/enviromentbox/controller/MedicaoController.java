package br.com.enviromentbox.controller;

import br.com.enviromentbox.domain.Medicao;
import br.com.enviromentbox.service.MedicaoService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Kloss Teles on 20/05/2017.
 */
@RestController
public class MedicaoController {

    @Autowired
    MedicaoService medicaoService;

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public void salvarStr(String message) {
//        String message = "id_device:1151;id_sensor:1202;valor_medicao:2121";
//        medicaoService.salvarStr(message);
//    }

    @RequestMapping(value = "/teste", method = RequestMethod.GET)
    public String hello() {
        return "Giovanni Goy";
    }

    @RequestMapping(value = "/consultaMediaByDeviceId/{device_id}", method = RequestMethod.GET)
    public List<Medicao> consultaMedicoesByDeviceId(@PathVariable("device_id") Long device_id) {
        return medicaoService.consultaMedicoesByDeviceId(device_id.longValue());
    }

    @RequestMapping(value = "/consultaM edicoesFiltradas/{device_id}/{data_hora_medicao_inicial}/{data_hora_medicao_final}", method = RequestMethod.GET)
    public List<Medicao> consultaMedicoesFiltradas(@PathVariable("device_id") Long device_id, @PathVariable("data_hora_medicao_inicial") Timestamp dataHoraMedicaoInicial, @PathVariable("data_hora_medicao_final") Timestamp dataHoraMedicaoFinal) {
        return medicaoService.consultaMedicoesFiltradas(device_id.longValue(), dataHoraMedicaoInicial, dataHoraMedicaoFinal);
    }

    @RequestMapping(value = "/consultarDadosMedicoesDevice/{device_id}", method = RequestMethod.GET)
    public String consultaDadosMedicoesDevice(@PathVariable("device_id") Long device_id) throws JSONException {
        return medicaoService.consultaDadosMedicoesDevice(device_id.longValue());
    }
}
