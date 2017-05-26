package br.com.enviromentbox.service;

import br.com.enviromentbox.domain.Medicao;
import com.sun.beans.decoder.ValueObject;
import org.json.JSONException;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Kloss Teles on 16/05/2017.
 */
public interface MedicaoService {
    void salvarStr(String str);

    List<Medicao> consultaMedicoesByDeviceId(Long device_id);

    List<Medicao> consultaMedicoesFiltradas(Long device_id, Timestamp data_hora_inicial, Timestamp data_hora_final);

    String consultaDadosMedicoesDevice(Long device_id);
}

