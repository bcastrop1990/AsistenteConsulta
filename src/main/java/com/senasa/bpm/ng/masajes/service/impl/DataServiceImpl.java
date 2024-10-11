package com.senasa.bpm.ng.masajes.service.impl;

import com.senasa.bpm.ng.masajes.dao.DataDao;
import com.senasa.bpm.ng.masajes.model.bean.DoctorBean;
import com.senasa.bpm.ng.masajes.model.bean.ServiciosBean;
import com.senasa.bpm.ng.masajes.model.response.DetalleServicioResponse;
import com.senasa.bpm.ng.masajes.model.response.DoctorResponse;
import com.senasa.bpm.ng.masajes.model.response.EspecialidadResponse;
import com.senasa.bpm.ng.masajes.model.response.ServicioResponse;
import com.senasa.bpm.ng.masajes.service.DataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DataServiceImpl implements DataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private DataDao dataDao;

    @Override
    public List<EspecialidadResponse> listarEspecialidades() {
        List<ServiciosBean> enfermedadesBean = dataDao.listarEspecialidades();
        List<EspecialidadResponse> response = new ArrayList<>();

        for (ServiciosBean bean : enfermedadesBean) {
            EspecialidadResponse aaaa = EspecialidadResponse.builder()
                    .id_especialidad(bean.getId_especialidad())
                    .nombre(bean.getNombre())
                    .imagen_url(bean.getImagen_url())
                    .build();
            response.add(aaaa);
        }
        return response;
    }
    @Override
    public List<ServicioResponse> listarServicioPorEsp(Long id_especialidad) {
        List<ServiciosBean> enfermedadesBean = dataDao.listarServicioPorEsp(id_especialidad);
        List<ServicioResponse> response = new ArrayList<>();

        for (ServiciosBean bean : enfermedadesBean) {
            ServicioResponse aaaa = ServicioResponse.builder()
                    .id_servicios(bean.getId_servicio())
                    .id_especialidad(bean.getId_especialidad())
                    .nombre(bean.getNombre())
                    .imagen_url(bean.getImagen_url())
                    .costo(bean.getCosto())
                    .build();
            response.add(aaaa);
        }
        return response;
    }
    @Override
    public List<DetalleServicioResponse> obtenerDetalle(Integer id_especialidad) {
        List<ServiciosBean> enfermedadesBean = dataDao.obtenerDetalle(id_especialidad);
        List<DetalleServicioResponse> response = new ArrayList<>();

        for (ServiciosBean bean : enfermedadesBean) {
            DetalleServicioResponse aaaa = DetalleServicioResponse.builder()
                    .hospitalizacion(bean.getHospitalizacion())
                    .duracion(bean.getDuracion())
                    .anestesia(bean.getAnestesia())
                    .recuperacion_total(bean.getRecuperacion_total())
                    .tipo(bean.getTipo())
                    .codigo_tipo(bean.getCodigo_tipo())
                    .sedes(bean.getSedes())
                    .nombre_cx(bean.getNombre_cx())
                    .build();
            response.add(aaaa);
        }
        return response;
    }
    @Override
    public List<DoctorResponse> listarDoctores() {
        List<DoctorBean> enfermedadesBean = dataDao.listarDoctores();
        List<DoctorResponse> response = new ArrayList<>();

        for (DoctorBean bean : enfermedadesBean) {
            DoctorResponse aaaa = DoctorResponse.builder()
                    .idEspecialidad(bean.getId_especialidad())
                    .nombre(bean.getNombre_doctor())
                    .imagen(bean.getImagen_url())
                    .email(bean.getEmail())
                    .build();
            response.add(aaaa);
        }
        return response;
    }

    @Override
    public List<DoctorResponse> listarDoctoresPorEsp(Long id_especialidad) {
        List<DoctorBean> enfermedadesBean = dataDao.listarDoctoresPorEsp(id_especialidad);
        List<DoctorResponse> response = new ArrayList<>();

        for (DoctorBean bean : enfermedadesBean) {
            DoctorResponse aaaa = DoctorResponse.builder()
                    .idEspecialidad(bean.getId_especialidad())
                    .nombre(bean.getNombre_doctor())
                    .imagen(bean.getImagen_url())
                    .email(bean.getEmail())
                    .build();
            response.add(aaaa);
        }
        return response;
    }
    @Override
    public List<DoctorResponse> listarDoctoresPorSrvc(Integer id_especialidad) {
        List<DoctorBean> enfermedadesBean = dataDao.listarDoctoresPorEsp(id_especialidad);
        List<DoctorResponse> response = new ArrayList<>();

        for (DoctorBean bean : enfermedadesBean) {
            DoctorResponse aaaa = DoctorResponse.builder()
                    .idEspecialidad(bean.getId_especialidad())
                    .nombre(bean.getNombre_doctor())
                    .imagen(bean.getImagen_url())
                    .email(bean.getEmail())
                    .build();
            response.add(aaaa);
        }
        return response;
    }
}
