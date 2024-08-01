package com.senasa.bpm.ng.service.impl;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.senasa.bpm.ng.dao.SesionDao;
import com.senasa.bpm.ng.exception.ApiValidateException;
import com.senasa.bpm.ng.model.bean.DoctorBean;
import com.senasa.bpm.ng.model.bean.UserBean;
import com.senasa.bpm.ng.model.request.ClinicaRequest;
import com.senasa.bpm.ng.model.request.UsuarioRequest;
import com.senasa.bpm.ng.model.response.DoctorResponse;
import com.senasa.bpm.ng.model.response.UserResponse;
import com.senasa.bpm.ng.service.SesionService;
import jdk.nashorn.internal.ir.IfNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class SesionServiceImpl implements SesionService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private final AmazonSimpleEmailService sesClient;

    private SesionDao sesionDao;

    @Override
    public String crearUsuario(UsuarioRequest request) {
        /*if (!sesionDao.validarCodigo(request.getEmail(), request.getCodigo())){
            throw new ApiValidateException("El código de validación expiró.");
        }*/
    return sesionDao.crearUsuario(request);
  }
    @Override
    public List<UserResponse> iniciar(String email, String password) {
        List<UserBean> enfermedadesBean = sesionDao.iniciar(email, password);
        List<UserResponse> response = new ArrayList<>();

        for (UserBean bean : enfermedadesBean) {
            UserResponse aaaa = UserResponse.builder()
                    .email(bean.getEmail())
                    .nombres(bean.getNombres())
                    .ape_Paterno(bean.getApe_Paterno())
                    .ape_Materno(bean.getApe_Materno())
                    .rol(bean.getRol())
                    .build();
            response.add(aaaa);
        }
        return response;
    }

//
//    @Override
//    public String loginGoogle(String email, String accessToken) {
//        return sesionDao.loginGoogle(email, accessToken);
//    }

    @Override
    public String obtenerRefreshToken(String email) {
        String refreshToken = sesionDao.obtenerRefreshToken(email);
        if (refreshToken == null){
            throw new ApiValidateException("El correo "+email+" no se encuentra registrado.");
        }
        return refreshToken;
    }
    @Override
    public String enviarCodigo(String to) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String codigoVerif = generateRandomCode();
        String htmlContent = "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>Verifica tu Correo</title>"
                + "<style>"
                + "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }"
                + ".container { width: 100%; max-width: 600px; background: #ffffff; margin: 0 auto; padding: 20px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }"
                + ".header { background-color: #004a99; color: white; padding: 20px; text-align: center; border-top-left-radius: 5px; border-top-right-radius: 5px; }"
                + ".content { padding: 20px; text-align: center; }"
                + ".content p { font-size: 16px; line-height: 1.5; color: #666; }"
                + ".verification-code { font-size: 24px; margin: 20px 0; color: #333; }"
                + ".footer { font-size: 12px; text-align: center; color: #888; padding: 20px; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class=\"container\">"
                + "<div class=\"header\">"
                + "<h1>Cirugía 24 Horas</h1>"
                + "</div>"
                + "<div class=\"content\">"
                + "<img src=\"https://cirugia24horas-bucket-imagenes.s3.us-east-2.amazonaws.com/imagenbodyverificacionemail.png\" alt=\"Verifica tu correo\" style=\"width: 400px; height: auto; margin-bottom: 20px;\">"
                + "<h2>Verifica tu correo</h2>"
                + "<p>Este es tu código de verificación. Introdúcelo en la pantalla donde comenzaste el registro. También puedes copiar y pegar el código:</p>"
                + "<div class=\"verification-code\">"+codigoVerif+"</div>"
                + "<p>Este código caducará en 5 minutos.</p>"
                + "</div>"
                + "<div class=\"footer\">"
                + "Quiero conocer más información <a href=\"https://terminosya.com/terminos-y-condiciones/1719941803\">Términos y condiciones</a>\n <br>"
                + "© 2024 Cirugía 24 Horas, Todos los derechos reservados"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        helper.setTo(to);
        helper.setSubject("Verifica tu correo de Cirugía 24 Horas App");
        helper.setText(htmlContent, true);
        String guardar = sesionDao.guardarCodigoVerificacion(to, codigoVerif);
        mailSender.send(message);
        return guardar;
    }

    @Override
    public void crearClinica(ClinicaRequest request) {
        int id = sesionDao.crearClinica(request);

        for (String imagen : request.getImagen_visual()) {
            sesionDao.relacionarImagenesConClinica(imagen, id);
        }
    }

    private String generateRandomCode() {
    Random random = new Random();
    int code = 100000 + random.nextInt(900000);
    return String.valueOf(code);
  }
}
