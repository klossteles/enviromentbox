package br.com.enviromentbox.service;

import br.com.enviromentbox.domain.Sensor;
import br.com.enviromentbox.repository.SensorRepository;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Kloss Teles on 16/05/2017.
 */
@Service
public class SensorServiceImpl implements SensorService {
    @Autowired
    private SensorRepository sensorRepository;


    @Override
    public String consultarSensoresByDeviceId(Long device_id) {
        ArrayList<Object[]> sensores = sensorRepository.consultarSensoresByDeviceId(device_id);
        JSONArray jsonArray = new JSONArray(sensores);
        String str = jsonArray.toString();
        return str;
    }
}
