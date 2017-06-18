package br.com.enviromentbox.service;

/**
 * Created by KlossTeles on 17/06/2017.
 */
public interface AlertaDeviceService {
    String consultarAlertasNaoProcessadosByDeviceId(Long device_id);
    void processarAlerta(Long idAlerta);
}
