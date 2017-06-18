package br.com.enviromentbox.service;

import br.com.enviromentbox.domain.AlertaDevice;
import br.com.enviromentbox.domain.Device;
import br.com.enviromentbox.domain.Medicao;
import br.com.enviromentbox.domain.Sensor;
import br.com.enviromentbox.repository.AlertaDeviceRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by KlossTeles on 17/06/2017.
 */
@Service
public class AlertaDeviceServiceImpl implements AlertaDeviceService {
    @Autowired
    AlertaDeviceRepository alertaDeviceRepository;

    @Override
    public String consultarAlertasNaoProcessadosByDeviceId(Long device_id) {
        ArrayList<Object[]> arrayList = alertaDeviceRepository.consultarAlertasNaoProcessadosByDeviceId(device_id);
        JSONArray jsonArray = new JSONArray();
        try {
            formataAlertas(arrayList,jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String str = jsonArray.toString();
        return str;
    }

    private void formataAlertas(ArrayList<Object[]> alertas, JSONArray jsArray) throws JSONException {
        for(Object[] objects : alertas){
            JSONObject alerta = new JSONObject();
            alerta.put("ID_ALERTA",objects[0]);
            alerta.put("NOME_DEVICE",objects[1]);
            alerta.put("NOME_SENSOR",objects[2]);
            alerta.put("DATA_HORA",objects[3]);
            jsArray.put(alerta);
        }
    }

    @Override
    public void processarAlerta(Long idAlerta) {
        alertaDeviceRepository.processarAlerta(idAlerta);
    }
}
