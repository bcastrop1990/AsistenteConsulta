package com.senasa.bpm.ng.masajes.service.impl;

import com.senasa.bpm.ng.masajes.dao.DoctorDao;
import com.senasa.bpm.ng.masajes.model.Medico;
import com.senasa.bpm.ng.masajes.model.request.DoctorDisponibilidadRequest;
import com.senasa.bpm.ng.masajes.model.request.DoctorRequest;
import com.senasa.bpm.ng.masajes.model.response.DoctorCubaMedDisponibilidadResponse;
import com.senasa.bpm.ng.masajes.model.response.DoctorCubaMedResponse;
import com.senasa.bpm.ng.masajes.model.response.DoctorResponse;
import com.senasa.bpm.ng.masajes.service.DoctorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorDao doctorDao;

    @Override
    public Medico validarDoctor(DoctorRequest doctor) throws IOException {
        Medico especialidad;

        /*
        if(doctor.getColegio().equals("0")){
            especialidad = doctorDao.validarDoctor(doctor);
        }else{
            especialidad = doctorDao.validarOdontologo(doctor);
        }
        */

        Medico medico = new Medico();
        medico.setNombres(doctor.getNombre());
        medico.setApellidos(doctor.getApellido());
       // medico.setCodigo(doctor.getCodigo());
        //medico.setImagen(especialidad.getImagen());
        //medico.setEspecialidad(especialidad.getEspecialidad());

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


    @Override
    public DoctorResponse guardarDoctor(DoctorRequest doctorRequest) {
        return doctorDao.guardarDoctor(doctorRequest);
    }

    @Override
    public DoctorResponse editarDoctor(Long id, DoctorRequest doctorRequest) {
        return doctorDao.editarDoctor(id, doctorRequest);
    }

    @Override
    public List<DoctorResponse> listarTodosLosDoctores() {
        return doctorDao.listarTodosLosDoctores();
    }

    @Override
    public DoctorResponse alternarEstadoDoctor(int id) {
        return doctorDao.alternarEstadoDoctor(id);
    }

}
