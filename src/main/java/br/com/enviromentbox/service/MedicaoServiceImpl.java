package br.com.enviromentbox.service;

import br.com.enviromentbox.domain.Device;
import br.com.enviromentbox.domain.Medicao;
import br.com.enviromentbox.domain.Sensor;
import br.com.enviromentbox.repository.MedicaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
        medicao.setDataHoraMedicao(new Timestamp(date.getTime()));
        medicaoRepository.save(medicao);
    }

    @Override
    public List<Medicao> consultaMedicoesByDeviceId(Long device_id) {
        return medicaoRepository.consultaMedicoesByDeviceId(device_id);
    }

    @Override
    public List<Medicao> consultaMedicoesFiltradas(Long device_id, Timestamp data_hora_inicial, Timestamp data_hora_final) {
        return medicaoRepository.consultaMedicoesFiltradas(device_id, data_hora_inicial, data_hora_final);
    }

    @Override
    public String consultaDadosMedicoesDevice(Long device_id) {
        StringBuilder strBldr = new StringBuilder();
        strBldr.append("MEDIA:").append(medicaoRepository.consultarMediaMedicaoDevice(device_id)).append(";");
        strBldr.append("MIN:").append(medicaoRepository.consultarMinMedicaoDevice(device_id)).append(";");
        strBldr.append("MAX:").append(medicaoRepository.consultarMaxMedicaoDevice(device_id)).append(";");
        strBldr.append("DESVIO_PADRAO:").append(medicaoRepository.consultarStddevMedicaoDevice(device_id)).append(";");
        strBldr.append("VARIANCE:").append(medicaoRepository.consultarVarianceMedicaoDevice(device_id)).append(";");
        return strBldr.toString();
    }
}
