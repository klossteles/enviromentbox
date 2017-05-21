package br.com.enviromentbox.service;

import br.com.enviromentbox.domain.Medicao;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Kloss Teles on 16/05/2017.
 */
public interface MedicaoService {
    void salvarStr(String str);

    List<Medicao> consultaMedicoesByDeviceId(Long device_id);
    List<Medicao> consultaMedicoesFiltradas(Long device_id, Timestamp data_hora_inicial, Timestamp data_hora_final);
}

