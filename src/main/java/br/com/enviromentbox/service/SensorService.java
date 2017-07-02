package br.com.enviromentbox.service;

/**
 * Created by Kloss Teles on 16/05/2017.
 */
public interface SensorService {
    String consultarSensoresByDeviceId(Long device_id);
    String consultarSensoresByDeviceIdFiltradas(Long device_id, String data_inicial, String data_final);
}

