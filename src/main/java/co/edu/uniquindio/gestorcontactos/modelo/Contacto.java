package co.edu.uniquindio.gestorcontactos.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contacto {
    private String id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private LocalDate cumpleAnios;
    private String filtroTabla;
    private String foto;
}
