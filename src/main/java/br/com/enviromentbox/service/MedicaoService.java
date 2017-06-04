package br.com.enviromentbox.service;

import br.com.enviromentbox.domain.Medicao;

import java.util.List;

/**
 * Created by Kloss Teles on 16/05/2017.
 */
public interface MedicaoService {
    void salvarStr(String str);

    List<Medicao> consultaMedicoesByDeviceId(Long device_id);

    String consultarMedicoesFiltradas(Long device_id, Long sensor_id, String data_inicial, String data_final);

    String consultaDadosMedicoesDevice(Long device_id, String dataHoraMedicaoInicial, String dataHoraMedicaoFinal);

    String consultarDadosMedicoesBySensor(Long device_id, Long sensor_id);
}

