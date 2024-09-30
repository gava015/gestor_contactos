package co.edu.uniquindio.gestorcontactos.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class ContactoPrincipal {

    private ArrayList<Contacto> contactos;

    public ContactoPrincipal() {
        contactos = new ArrayList<>();
    }

    public void agregarContacto(String nombre, String apellido, String telefono, String correo, LocalDate cumpleAnios)
            throws Exception {
        if (nombre == null || nombre.trim().isEmpty() ||
                apellido == null || apellido.trim().isEmpty() ||
                telefono == null || telefono.trim().isEmpty() ||
                correo == null || correo.trim().isEmpty()) {

            throw new Exception("Todos los campos son obligatorios");

        } else {
            Contacto contacto = Contacto.builder()
                    .id(String.valueOf(UUID.randomUUID()))
                    .nombre(nombre)
                    .apellido(apellido)
                    .telefono(telefono)
                    .correo(correo)
                    .cumpleAnios(cumpleAnios)
                    .build();

            contactos.add(contacto);
        }
    }

    public void actualizarContacto(String contactoId, String nombre, String apellido, String telefono, String correo,
                               LocalDate cumpleAnios) {
        for (int i = 0; i < contactos.size(); i++) {
            if(contactos.get(i).getId().equals(contactoId) ){
                contactos.set(i, Contacto.builder()
                        .id(String.valueOf(UUID.randomUUID()))
                        .nombre(nombre)
                        .apellido(apellido)
                        .telefono(telefono)
                        .correo(correo)
                        .cumpleAnios(cumpleAnios)
                        .build());
                break;
            }
        }
    }

    public void eliminarContacto(String contactoId) {
        for (int i = 0; i < contactos.size(); i++) {
            Contacto contacto = contactos.get(i);
            if (contacto.getId().equals(contactoId)) {
                contactos.remove(contacto);
                break;
            }
        }
    }

    public ArrayList<Contacto> listarContactos() {
        System.out.println(contactos);
        return contactos;
    }

    public ArrayList<String> listarFiltro() {
        ArrayList<String> filtros = new ArrayList<>();
        filtros.add("Nombre");
        filtros.add("Teléfono");
        return filtros;
    }
}
