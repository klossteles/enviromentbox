package br.com.enviromentbox.service;

import br.com.enviromentbox.domain.Device;
import br.com.enviromentbox.domain.Medicao;
import br.com.enviromentbox.domain.Sensor;
import br.com.enviromentbox.repository.MedicaoRepository;
import br.com.enviromentbox.repository.SensorRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
public class MedicaoServiceImpl implements MedicaoService {
    @Autowired
    private MedicaoRepository medicaoRepository;

    @Autowired
    private  SensorRepository sensorRepository;

    @Override
    @Transactional
    public void salvarStr(String str) {
        Integer idDevice = new Integer(0);
        Integer idSensor = new Integer(0);
        BigDecimal valor = new BigDecimal(0);
        String[] strArr = str.split(";");
        int index;
        if (strArr[0].contains("id_device")) {
            index = strArr[0].indexOf(":");
            idDevice = new Integer(strArr[0].substring(index + 1, strArr[0].length()));
        }
        if (strArr[1].contains("id_sensor")) {
            index = strArr[1].indexOf(":");
            idSensor = new Integer(strArr[1].substring(index + 1, strArr[1].length()));
        }
        if (strArr[2].contains("valor_medicao")) {
            index = strArr[2].indexOf(":");
            valor = new BigDecimal(strArr[2].substring(index + 1, strArr[2].length()).toString());
        }

        Device device = new Device();
        device.setId(idDevice.longValue());
        Sensor sensor = new Sensor();
        sensor.setId(idSensor.longValue());
        Medicao medicao = new Medicao();
        medicao.setValor(valor);
        medicao.setDevice(device);
        medicao.setSensor(sensor);
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        medicao.setData_hora_medicao(new Timestamp(date.getTime()));
        medicaoRepository.save(medicao);
    }

    @Override
    public List<Medicao> consultaMedicoesByDeviceId(Long device_id) {
        return medicaoRepository.consultaMedicoesByDeviceId(device_id);
    }

    @Override
    public String consultarMedicoesFiltradas(Long device_id, Long sensor_id, String data_inicial, String data_final) {
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
        try {
            jsonObject.put("MEDICOES", medicaoRepository.consultarMedicoesFiltradas(device_id, sensor_id,dataHoraInicial, dataHoraFinal));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        String str = jsonArray.toString();
        return str;
    }

    @Override
    public String consultaDadosMedicoesDevice(Long device_id, String data_inicial, String data_final) {
        StringBuilder strBldr = new StringBuilder();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Timestamp dataHoraInicial = null;
        Timestamp dataHoraFinal = null;

        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(simpleDateFormat.parse(data_inicial));
            cal.set(Calendar.DATE, cal.get(Calendar.DATE)-1);
            dataHoraInicial = new Timestamp(cal.getTimeInMillis());
            cal.setTime(simpleDateFormat.parse(data_final));
            cal.set(Calendar.DATE, cal.get(Calendar.DATE)+1);
            dataHoraFinal = new Timestamp(cal.getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayList<Sensor> sensores = sensorRepository.consultarSensoresByDeviceId(device_id);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject= new JSONObject();
        for(Sensor sensor : sensores){
            Long idSensor = sensor.getId();
            try {
                jsonObject.put("ID_SENSOR", idSensor);
                jsonObject.put("MEDIA", medicaoRepository.consultarMediaMedicaoDeviceFiltrado(device_id, idSensor, dataHoraInicial, dataHoraFinal));
                jsonObject.put("MIN",medicaoRepository.consultarMinMedicaoDeviceFiltrado(device_id, idSensor, dataHoraInicial, dataHoraFinal));
                jsonObject.put("MAX", medicaoRepository.consultarMaxMedicaoDeviceFiltrado(device_id, idSensor, dataHoraInicial, dataHoraFinal));
                jsonObject.put("DESVIO_PADRAO", medicaoRepository.consultarStddevMedicaoDeviceFiltrado(device_id, idSensor, dataHoraInicial, dataHoraFinal));
                jsonObject.put("VARIANCE", medicaoRepository.consultarVarianceMedicaoDeviceFiltrado(device_id, idSensor, dataHoraInicial, dataHoraFinal));
                jsonObject.put("MEDICOES", medicaoRepository.consultarMedicoesFiltradas(device_id, idSensor, dataHoraInicial, dataHoraFinal));
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String str = jsonArray.toString();
        return str;
    }

    @Override
    public String consultarDadosMedicoesBySensor(Long device_id, Long sensor_id) {
        Timestamp dataHoraInicial = null;
        Timestamp dataHoraFinal = null;

        Calendar cal = Calendar.getInstance();
        dataHoraFinal = new Timestamp(cal.getTimeInMillis());
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR)-1);
        dataHoraInicial = new Timestamp(cal.getTimeInMillis());

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("MEDIA", medicaoRepository.consultarMediaMedicaoDeviceFiltrado(device_id,sensor_id,dataHoraInicial,dataHoraFinal));
            jsonObject.put("MIN",medicaoRepository.consultarMinMedicaoDeviceFiltrado(device_id, sensor_id, dataHoraInicial, dataHoraFinal));
            jsonObject.put("MAX", medicaoRepository.consultarMaxMedicaoDeviceFiltrado(device_id, sensor_id, dataHoraInicial, dataHoraFinal));
            jsonObject.put("DESVIO_PADRAO", medicaoRepository.consultarStddevMedicaoDeviceFiltrado(device_id, sensor_id, dataHoraInicial, dataHoraFinal));
            jsonObject.put("VARIANCE", medicaoRepository.consultarVarianceMedicaoDeviceFiltrado(device_id, sensor_id, dataHoraInicial, dataHoraFinal));
            jsonObject.put("MEDICOES", medicaoRepository.consultarMedicoesFiltradas(device_id, sensor_id, dataHoraInicial, dataHoraFinal));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        String str = jsonArray.toString();
        return  str;
    }
}
