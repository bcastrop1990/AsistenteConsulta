package com.senasa.bpm.ng.service.impl;

import com.senasa.bpm.ng.dao.DoctorDao;
import com.senasa.bpm.ng.exception.ApiValidateException;
import com.senasa.bpm.ng.model.Medico;
import com.senasa.bpm.ng.model.request.ClinicaRequest;
import com.senasa.bpm.ng.model.request.DoctorDisponibilidadRequest;
import com.senasa.bpm.ng.model.request.DoctorRequest;
import com.senasa.bpm.ng.model.response.DoctorCubaMedDisponibilidadResponse;
import com.senasa.bpm.ng.model.response.DoctorCubaMedResponse;
import com.senasa.bpm.ng.service.DoctorService;
import com.senasa.bpm.ng.utility.ConstantUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorDao doctorDao;

    @Override
    public Medico validarDoctor(DoctorRequest doctor) throws IOException {
        Medico especialidad;

        if(doctor.getColegio().equals("0")){
            especialidad = doctorDao.validarDoctor(doctor);
        }else{
            especialidad = doctorDao.validarOdontologo(doctor);
        }
        Medico medico = new Medico();
        medico.setNombres(doctor.getNombre());
        medico.setApellidos(doctor.getApellido());
        medico.setCodigo(doctor.getCodigo());
        medico.setImagen(especialidad.getImagen());
        medico.setEspecialidad(especialidad.getEspecialidad());

        return medico;


    }


    public List<DoctorCubaMedResponse> listarDoctor(Long idEspecialidad, String nombre){
        return doctorDao.listarDoctor(idEspecialidad, nombre);
    }

    @Override
    public void configurarDisponibilidadDoctor(DoctorDisponibilidadRequest request) {
        doctorDao.configurarDisponibilidadDoctor(request);
    }

    @Override
    public DoctorCubaMedDisponibilidadResponse obtenerDisponibilidadPorCorreo(String email){
      return doctorDao.obtenerDisponibilidadPorCorreo(email);
    }

}
