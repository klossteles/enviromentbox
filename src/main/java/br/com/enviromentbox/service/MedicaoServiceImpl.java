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
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Kloss Teles on 16/05/2017.
 */
@Service
public class MedicaoServiceImpl implements MedicaoService {
    @Autowired
    private MedicaoRepository medicaoRepository;

    @Autowired
    private  SensorRepository sensorRepository;

    private static final BigInteger RUIDO = BigInteger.valueOf(1);
    private static final BigInteger TEMPERATURA = BigInteger.valueOf(2);
    private static final BigInteger UMIDADE = BigInteger.valueOf(3);
    private static final BigInteger MONOXIDO = BigInteger.valueOf(4);

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

        verificarAlertas(idDevice.longValue(), idSensor.longValue());
    }

    private void verificarAlertas(long idDevice, long idSensor) {
        Calendar cal = Calendar.getInstance();
        ArrayList<Object[]> objects = sensorRepository.consultarSensores();
        for (Object[] obj : objects){
            BigInteger sensor_id = (BigInteger) obj[0];
            String nome_sensor = (String) obj[1];
            BigInteger device_id = (BigInteger) obj[2];
            String nome_device = (String) obj[3];
            BigInteger id_tipo_sensor = (BigInteger) obj[4];
            if(id_tipo_sensor.compareTo(RUIDO) == 0){
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 8, 0, 85);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 7,0, 86);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 6,0, 87);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 5,0, 88);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 4,30, 89);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 4,0, 91);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 3,30, 92);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 3,0, 93);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 2,30, 94);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 2,0, 95);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 1,45, 96);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 1,15, 98);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 1,0, 100);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 0,45, 102);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 0,35, 104);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 0,30, 105);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 0,25, 106);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 0,20, 108);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 0,15, 110);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 0,10, 112);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 0,8, 114);
                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor, 0,7, 115);

            }else if(id_tipo_sensor.compareTo(TEMPERATURA) == 0){
//                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor);
            }else if(id_tipo_sensor.compareTo(UMIDADE) == 0){
//                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor);
            }else if(id_tipo_sensor.compareTo(MONOXIDO) == 0){
//                verificarAlertasRuido(cal, nome_sensor, device_id, nome_device, id_tipo_sensor);
            }
        }

    }

    private void verificarAlertasRuido(Calendar cal, String nome_sensor, BigInteger device_id, String nome_device, BigInteger id_tipo_sensor, int numHoras, int numMinutos, int numDB) {
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)-numHoras);
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)-numMinutos);
        BigDecimal mediaAlerta = medicaoRepository.consultaMediaAlerta(device_id.longValue(), id_tipo_sensor.longValue(), new Timestamp(cal.getTimeInMillis()));
        if(mediaAlerta != null && mediaAlerta.compareTo(BigDecimal.valueOf(numDB)) >= 0){
            System.out.println("Gerar alerta para o sensor: " + nome_sensor + " do device: " + nome_device);
            System.out.println("DB excedido: " + numDB);
            System.out.println("Tempo excedido: " + numHoras + " horas e " + numMinutos + " minutos");
            System.out.println("Valor encontrado:" + mediaAlerta);
            System.out.println();
        }
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
