package co.edu.uniquindio.gestorcontactos.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class ContactoPrincipal {

    private ArrayList<Contacto> contactos;

    public ContactoPrincipal() {
        contactos = new ArrayList<>();
    }

    public void agregarContacto(String nombre, String apellido, String telefono, String correo, String foto, LocalDate cumpleAnios)
            throws Exception {

        if (nombre == null || nombre.trim().isEmpty() ||
                apellido == null || apellido.trim().isEmpty() ||
                telefono == null || telefono.trim().isEmpty() ||
                foto == null || foto.trim().isEmpty() ||
                correo == null || correo.trim().isEmpty()) {

            throw new Exception("Todos los campos son obligatorios");

        } else {

            boolean existeContacto = buscarContactoPorCorreo(correo);
            if (existeContacto) {
                throw new Exception("Ya existe el contacto registrado.");
            }

            Contacto contacto = Contacto.builder()
                    .id(String.valueOf(UUID.randomUUID()))
                    .nombre(nombre)
                    .apellido(apellido)
                    .telefono(telefono)
                    .correo(correo)
                    .foto(foto)
                    .cumpleAnios(cumpleAnios)
                    .build();

            contactos.add(contacto);
        }
    }

    private boolean buscarContactoPorCorreo(String correo) {
        for(int i=0; i < contactos.size(); i++){
            String contactoBuscado = contactos.get(i).getCorreo();
            if(contactoBuscado.equalsIgnoreCase(correo)) {
               return true;
            }
        }
        return false;
    }

    public void actualizarContacto(String contactoId, String nombre, String apellido, String telefono, String correo,
                                   String foto, LocalDate cumpleAnios) throws Exception{

        if (nombre == null || nombre.trim().isEmpty() ||
                apellido == null || apellido.trim().isEmpty() ||
                telefono == null || telefono.trim().isEmpty() ||
                foto == null || foto.trim().isEmpty() ||
                correo == null || correo.trim().isEmpty()) {

            throw new Exception("Todos los campos son obligatorios");

        } else {

            for (int i = 0; i < contactos.size(); i++) {
                if(contactos.get(i).getId().equals(contactoId) ){
                    contactos.set(i, Contacto.builder()
                            .id(String.valueOf(UUID.randomUUID()))
                            .nombre(nombre)
                            .apellido(apellido)
                            .telefono(telefono)
                            .correo(correo)
                            .foto(foto)
                            .cumpleAnios(cumpleAnios)
                            .build());
                    break;
                }
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

    public ArrayList<Contacto> filtrarListaContactos(String tipoFiltro, String valorFiltro) throws Exception{
       if(tipoFiltro != "Todo" && valorFiltro.trim().equalsIgnoreCase("")) {
           throw new Exception("Ingresa un valor para filtrar la lista");
       }

        ArrayList<Contacto> contactoTemporal = new ArrayList<>();
        for (int i = 0; i < contactos.size(); i++) {
            Contacto contacto = contactos.get(i);
            switch (tipoFiltro) {
                case "Nombre":
                    if (contacto.getNombre().equalsIgnoreCase(valorFiltro.trim())) {
                        contactoTemporal.add(contacto);
                    }
                    break;

                case "Telefono":
                    if (contacto.getTelefono().equalsIgnoreCase(valorFiltro.trim())) {
                        contactoTemporal.add(contacto);
                    }
                    break;

                default:
                    return listarContactos();
            }
        }
        return contactoTemporal;
    }

    public ArrayList<Contacto> listarContactos() {
        System.out.println(contactos);
        return contactos;
    }

    public ArrayList<String> listarFiltro() {
        ArrayList<String> filtros = new ArrayList<>();
        filtros.add("Todo");
        filtros.add("Nombre");
        filtros.add("Telefono");
        return filtros;
    }
}
