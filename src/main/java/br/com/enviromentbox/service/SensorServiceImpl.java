package br.com.enviromentbox.service;

import br.com.enviromentbox.domain.Sensor;
import br.com.enviromentbox.repository.MedicaoRepository;
import br.com.enviromentbox.repository.SensorRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Kloss Teles on 16/05/2017.
 */
@Service
public class SensorServiceImpl implements SensorService {
    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private MedicaoRepository medicaoRepository;

    @Override
    public String consultarSensoresByDeviceId(Long device_id) {
        ArrayList<Object[]> sensores = sensorRepository.consultarSensoresByDeviceId(device_id);
        JSONArray jsonArray = new JSONArray(sensores);
        String str = jsonArray.toString();
        return str;
    }

    @Override
    public String consultarSensoresByDeviceIdFiltradas(Long device_id, String data_inicial, String data_final) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Timestamp dataHoraInicial = null;
        Timestamp dataHoraFinal = null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(simpleDateFormat.parse(data_inicial));
            dataHoraInicial = new Timestamp(cal.getTimeInMillis());
            cal.setTime(simpleDateFormat.parse(data_final));
            dataHoraFinal = new Timestamp(cal.getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        ArrayList<Object[]> sensores = sensorRepository.consultarSensoresByDeviceId(device_id);
        JSONArray jsArray = new JSONArray();
        try {
            for (Object[] sensor : sensores){
                BigInteger idSensor = (BigInteger) sensor[0];
                ArrayList<Object[]> medicoes = medicaoRepository.consultarMedicoesFiltradas(device_id,  dataHoraInicial, dataHoraFinal, idSensor.longValue());
                formataMedicoesFiltradas(medicoes, (BigInteger) sensor[0], (String) sensor[1], jsArray);
            }
            jsonObject.put("SENSORES", jsArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        String str = jsonArray.toString();
        return str;
    }

    private void formataMedicoesFiltradas(ArrayList<Object[]> medicoes, BigInteger idSensor, String tipo, JSONArray jsArray) throws JSONException {
        JSONObject medicao = new JSONObject();
//        Pega o id e o tipo do sensor apenas do primeiro, pois é o mesmo para os demais
        medicao.put("ID", idSensor.longValue());
        medicao.put("TIPO", tipo);
        medicao.put("VALORES", getValorMedicoesFromArray(medicoes));
        jsArray.put(medicao);
    }

    private ArrayList<BigDecimal> getValorMedicoesFromArray(List<Object[]> medicoes){
        ArrayList<BigDecimal> valores = new ArrayList<>();
        for(Object[] obj : medicoes){
            valores.add((BigDecimal) obj[1]);
        }
        return valores;
    }

}
