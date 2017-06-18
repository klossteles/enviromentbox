package br.com.enviromentbox.service;

import br.com.enviromentbox.domain.*;
import br.com.enviromentbox.repository.AlertaDeviceRepository;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    private SensorRepository sensorRepository;

    @Autowired
    private AlertaDeviceRepository alertaDeviceRepository;

    @Override
    @Transactional
    public void salvarStr(String str) {
        Integer idDevice = new Integer(0);
        Integer idSensor = new Integer(0);
        BigDecimal valor = new BigDecimal(0);
        String[] strArr = str.split(":");
//        int index;
        idDevice = new Integer(strArr[0]);
        idSensor = new Integer(strArr[1]);
        valor = new BigDecimal(strArr[2]);
//        if (strArr[0].contains("id_device")) {
//            index = strArr[0].indexOf(":");
//            idDevice = new Integer(strArr[0].substring(index + 1, strArr[0].length()));
//        }
//        if (strArr[1].contains("id_sensor")) {
//            index = strArr[1].indexOf(":");
//            idSensor = new Integer(strArr[1].substring(index + 1, strArr[1].length()));
//        }
//        if (strArr[2].contains("valor_medicao")) {
//            index = strArr[2].indexOf(":");
//            valor = new BigDecimal(strArr[2].substring(index + 1, strArr[2].length()).toString());
//        }

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
            if(id_tipo_sensor.compareTo(TipoSensor.RUIDO) == 0){
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 8, 0, 85);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 7,0, 86);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 6,0, 87);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 5,0, 88);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 4,30, 89);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 4,0, 91);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 3,30, 92);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 3,0, 93);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 2,30, 94);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 2,0, 95);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 1,45, 96);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 1,15, 98);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 1,0, 100);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 0,45, 102);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 0,35, 104);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 0,30, 105);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 0,25, 106);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 0,20, 108);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 0,15, 110);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 0,10, 112);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 0,8, 114);
                verificarAlertasRuido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor, 0,7, 115);
            }else if(id_tipo_sensor.compareTo(TipoSensor.TEMPERATURA) == 0){
                verificarAlertasTempertatura(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor,1,0,20,23);
            }else if(id_tipo_sensor.compareTo(TipoSensor.UMIDADE) == 0){
                verificarAlertasUmidade(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor,1,0,40);
            }else if(id_tipo_sensor.compareTo(TipoSensor.MONOXIDO) == 0){
                verificarAlertasMonoxido(cal, nome_sensor, sensor_id, device_id, nome_device, id_tipo_sensor,1,0,20);
            }
        }

    }

    private void verificarAlertasMonoxido(Calendar cal, String nome_sensor, BigInteger idSensor, BigInteger device_id, String nome_device, BigInteger id_tipo_sensor, int numHoras, int numMinutos, int numPart) {
        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) - numHoras);
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - numMinutos);
        BigDecimal mediaAlerta = medicaoRepository.consultaMediaAlerta(device_id.longValue(), id_tipo_sensor.longValue(), new Timestamp(cal.getTimeInMillis()));
        Boolean possuiAlertaNaoProcessado = verificaSePossuiAlertaNaoProcessado(device_id.longValue(), idSensor.longValue());
        if(mediaAlerta != null && mediaAlerta.compareTo(BigDecimal.valueOf(numPart)) > 0 && !possuiAlertaNaoProcessado){
            StringBuilder strBuilder = new StringBuilder();
            String deviceToken = "bd72255ab5f15f7e2cc9e4";
            System.out.println("Gerar alerta para o sensor: " + nome_sensor + " do device: " + nome_device);
            System.out.println("Número de partículas acima de " + numPart);
            System.out.println("Valor medido: " + mediaAlerta.setScale(2,BigDecimal.ROUND_UP));
            strBuilder.append("Alerta gerado pelo device " + nome_device);
            strBuilder.append("Número de partículas acima de " + numPart);
            strBuilder.append("Valor medido: " + mediaAlerta.setScale(2,BigDecimal.ROUND_UP));
//            Gera o alerta no ios e salva o alerta no bd
            gerarAlerta(idSensor, device_id, strBuilder, deviceToken);
        }
    }

    private void verificarAlertasUmidade(Calendar cal, String nome_sensor, BigInteger idSensor, BigInteger device_id, String nome_device, BigInteger id_tipo_sensor, int numHoras, int numMinutos, int umidadeMin) {
        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) - numHoras);
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - numMinutos);
        BigDecimal mediaAlerta = medicaoRepository.consultaMediaAlerta(device_id.longValue(), id_tipo_sensor.longValue(), new Timestamp(cal.getTimeInMillis()));
        Boolean possuiAlertaNaoProcessado = verificaSePossuiAlertaNaoProcessado(device_id.longValue(), idSensor.longValue());
        if(mediaAlerta != null && mediaAlerta.compareTo(BigDecimal.valueOf(umidadeMin)) < 0 && !possuiAlertaNaoProcessado){
            StringBuilder strBuilder = new StringBuilder();
            String deviceToken = "bd72255ab5f15f7e2cc9e4";
            System.out.println("Gerar alerta para o sensor: " + nome_sensor + " do device: " + nome_device);
            System.out.println("Umidade abaixo de " + umidadeMin);
            System.out.println("Valor encontrado: " + mediaAlerta.setScale(2,BigDecimal.ROUND_UP));
            strBuilder.append("Alerta gerado pelo device " + nome_device);
            strBuilder.append("Umidade abaixo de " + umidadeMin);
            strBuilder.append("Valor medido: " + mediaAlerta.setScale(2,BigDecimal.ROUND_UP));

//            Gera o alerta no ios e salva o alerta no bd
            gerarAlerta(idSensor, device_id, strBuilder, deviceToken);
        }
    }

    private void verificarAlertasTempertatura(Calendar cal, String nome_sensor, BigInteger idSensor, BigInteger device_id, String nome_device, BigInteger id_tipo_sensor, int numHoras, int numMinutos, int temperaturaMin , int temperaturaMax) {
        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) - numHoras);
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - numMinutos);
        BigDecimal mediaAlerta = medicaoRepository.consultaMediaAlerta(device_id.longValue(), id_tipo_sensor.longValue(), new Timestamp(cal.getTimeInMillis()));
        Boolean possuiAlertaNaoProcessado = verificaSePossuiAlertaNaoProcessado(device_id.longValue(), idSensor.longValue());
        if(mediaAlerta != null && (mediaAlerta.compareTo(BigDecimal.valueOf(temperaturaMin)) < 0 || mediaAlerta.compareTo(BigDecimal.valueOf(temperaturaMax)) > 0) && !possuiAlertaNaoProcessado){
            StringBuilder strBuilder = new StringBuilder();
            String deviceToken = "bd72255ab5f15f7e2cc9e4";
            System.out.println("Gerar alerta para o sensor: " + nome_sensor + " do device: " + nome_device);
            strBuilder.append("Alerta gerado pelo device " + nome_device);
            if(mediaAlerta.compareTo(BigDecimal.valueOf(temperaturaMin)) < 0){
                System.out.println("Temperatura abaixo: " + temperaturaMin);
                strBuilder.append("Temperatura abaixo: " + temperaturaMin);
            }else{
                System.out.println("Temperatura excedida: " + temperaturaMax);
                strBuilder.append("Temperatura excedida: " + temperaturaMin);
            }
            System.out.println("Valor encontrado: " + mediaAlerta.setScale(2, BigDecimal.ROUND_UP));
            strBuilder.append("Valor medido: " + mediaAlerta.setScale(2,BigDecimal.ROUND_UP));

//            Gera o alerta no ios e salva o alerta no bd
            gerarAlerta(idSensor, device_id, strBuilder, deviceToken);
        }
    }

    private void verificarAlertasRuido(Calendar cal, String nome_sensor, BigInteger idSensor, BigInteger device_id, String nome_device, BigInteger id_tipo_sensor, int numHoras, int numMinutos, int numDB) {
        cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) - numHoras);
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) - numMinutos);
        BigDecimal mediaAlerta = medicaoRepository.consultaMediaAlerta(device_id.longValue(), id_tipo_sensor.longValue(), new Timestamp(cal.getTimeInMillis()));
        Boolean possuiAlertaNaoProcessado = verificaSePossuiAlertaNaoProcessado(device_id.longValue(), idSensor.longValue());
        if(mediaAlerta != null && mediaAlerta.compareTo(BigDecimal.valueOf(numDB)) >= 0 && !possuiAlertaNaoProcessado){
            String deviceToken = "bd72255ab5f15f7e2cc9e4";
            System.out.println("Gerar alerta para o sensor: " + nome_sensor + " do device: " + nome_device);
            System.out.println("DB excedido: " + numDB);
            System.out.println("Tempo excedido: " + numHoras + " horas e " + numMinutos + " minutos");
            System.out.println("Valor encontrado:" + mediaAlerta.setScale(2,BigDecimal.ROUND_UP));
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("O device " + nome_device + " gerou um alerta!");
            strBuilder.append("Db excedido: " + numDB);
            strBuilder.append("Tempo excedido: " + numHoras + " horas e " + numMinutos + " minutos");
            strBuilder.append("Valor medido:" + mediaAlerta.setScale(2,BigDecimal.ROUND_UP));

//            Gera o alerta no ios e salva o alerta no bd
            gerarAlerta(idSensor, device_id, strBuilder, deviceToken);
        }
    }

    public Boolean verificaSePossuiAlertaNaoProcessado(Long id_device, Long id_sensor){
        ArrayList<Object> arrayList = alertaDeviceRepository.consultarAlertaDeviceNaoProcessado(id_device, id_sensor);
        if(arrayList != null && arrayList.size() > 0){
            return true;
        }else{
            return false;
        }
    }

    private void gerarAlerta(BigInteger idSensor, BigInteger device_id, StringBuilder strBuilder, String deviceToken) {
        PushyAPI pushyAPI = new PushyAPI();
        pushyAPI.sendAlertaPush(strBuilder.toString(), deviceToken);

        Device device = new Device();
        device.setId(device_id.longValue());
        Sensor sensor = new Sensor();
        sensor.setId(idSensor.longValue());
        Date date = new Date();
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        LocalDate localDate = zonedDateTime.toLocalDate();
        AlertaDevice alertaDevice = new AlertaDevice();
        alertaDevice.setDevice(device);
        alertaDevice.setSensor(sensor);
        alertaDevice.setData_hora(localDate);
        alertaDevice.processado(0);
        alertaDeviceRepository.save(alertaDevice);
    }

    @Override
    public String consultaMedicoesByDeviceId(Long device_id) {
        List<Object[]> medicoes = medicaoRepository.consultaMedicoesByDeviceId(device_id);
        JSONArray jsonArray = new JSONArray(medicoes);
        String str = jsonArray.toString();
        return str;
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
            ArrayList<Object[]> medicoes = medicaoRepository.consultarMedicoesFiltradas(device_id,  dataHoraInicial, dataHoraFinal, sensor_id.longValue());
            JSONArray jsArray = new JSONArray();
            formataMedicoes(medicoes, jsArray);
            jsonObject.put("MEDICOES", jsArray.toString());
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

        ArrayList<Object[]> sensores = sensorRepository.consultarSensoresByDeviceId(device_id);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject= new JSONObject();
        for(Object[] sensor : sensores){
            BigInteger idSensor = (BigInteger) sensor[0];
            try {
                jsonObject.put("ID_SENSOR", idSensor);
                jsonObject.put("MEDIA", medicaoRepository.consultarMediaMedicaoDeviceFiltrado(device_id, idSensor.longValue(), dataHoraInicial, dataHoraFinal));
                jsonObject.put("MIN",medicaoRepository.consultarMinMedicaoDeviceFiltrado(device_id, idSensor.longValue(), dataHoraInicial, dataHoraFinal));
                jsonObject.put("MAX", medicaoRepository.consultarMaxMedicaoDeviceFiltrado(device_id, idSensor.longValue(), dataHoraInicial, dataHoraFinal));
                jsonObject.put("DESVIO_PADRAO", medicaoRepository.consultarStddevMedicaoDeviceFiltrado(device_id, idSensor.longValue(), dataHoraInicial, dataHoraFinal));
                jsonObject.put("VARIANCE", medicaoRepository.consultarVarianceMedicaoDeviceFiltrado(device_id, idSensor.longValue(), dataHoraInicial, dataHoraFinal));
                ArrayList<Object[]> medicoes = medicaoRepository.consultarMedicoesFiltradas(device_id,  dataHoraInicial, dataHoraFinal, idSensor.longValue());
                JSONArray jsArray = new JSONArray();
                formataMedicoes(medicoes, jsArray);
                jsonObject.put("MEDICOES", jsArray.toString());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String str = jsonArray.toString();
        return str;
    }

    private void formataMedicoes(ArrayList<Object[]> medicoes, JSONArray jsArray) throws JSONException {
        for(Object[] objects : medicoes){
            JSONObject medicao = new JSONObject();
            medicao.put("ID_MEDICAO",objects[0]);
            medicao.put("VALOR",objects[1]);
            medicao.put("DATA_HORA_MEDICAO",objects[2]);
            medicao.put("DEVICE_ID",objects[3]);
            medicao.put("SENSOR_ID",objects[4]);
            jsArray.put(medicao);
        }
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
            ArrayList<Object[]> medicoes = medicaoRepository.consultarMedicoesFiltradas(device_id,  dataHoraInicial, dataHoraFinal, sensor_id);
            JSONArray jsonArray = new JSONArray();
            formataMedicoes(medicoes, jsonArray);
            jsonObject.put("MEDICOES", jsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        String str = jsonArray.toString();
        return  str;
    }
}
