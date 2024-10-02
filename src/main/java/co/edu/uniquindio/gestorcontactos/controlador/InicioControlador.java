package co.edu.uniquindio.gestorcontactos.controlador;

import co.edu.uniquindio.gestorcontactos.modelo.Contacto;
import co.edu.uniquindio.gestorcontactos.modelo.ContactoPrincipal;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InicioControlador implements Initializable {
    @FXML
    private TableView<Contacto> tablaContactos;

    @FXML
    private TableColumn<Contacto, String> colNombre;

    @FXML
    private TableColumn<Contacto, String> colApellido;

    @FXML
    private TableColumn<Contacto, String> colTelefono;

    @FXML
    private TableColumn<Contacto, String> colCumpleanios;

    @FXML
    private TableColumn<Contacto, String> colCorreo;

    @FXML
    private TableColumn<Contacto, String> colFoto;

    @FXML
    private ComboBox<String> cbFiltro;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtFoto;

    @FXML
    private DatePicker dpFechaCumpleanios;

    @FXML
    private TextField txtFiltro;

    private ObservableList<Contacto> observableList;

    private final ContactoPrincipal contactoPrincipal;

    public InicioControlador() {
        contactoPrincipal = new ContactoPrincipal();
    }

    public void crearContacto(ActionEvent e) {
        try {
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String telefono = txtTelefono.getText();
            String correo = txtCorreo.getText();
            String foto = txtFoto.getText();
            LocalDate fechaCumpleanio = dpFechaCumpleanios.getValue();

            contactoPrincipal.agregarContacto(nombre, apellido, telefono, correo, foto, fechaCumpleanio);
            mostrarAlerta("Contacto creado correctamente", Alert.AlertType.INFORMATION);

            limpiarFormulario();
            actualizarTabla();

        } catch (Exception ex) {
            mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
            tablaContactos.setItems(FXCollections.observableArrayList(contactoPrincipal.listarContactos()));
        }
    }

    public void actualizarContacto(ActionEvent e) {
        Contacto contactoSeleccionado = tablaContactos.getSelectionModel().getSelectedItem();

        try {
            if (contactoSeleccionado != null) {
                String contactoId = contactoSeleccionado.getId();
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String telefono = txtTelefono.getText();
                String correo = txtCorreo.getText();
                String foto = txtFoto.getText();
                LocalDate fechaCumpleanio = dpFechaCumpleanios.getValue();

                contactoPrincipal.actualizarContacto(contactoId, nombre, apellido, telefono, correo, foto, fechaCumpleanio);
                mostrarAlerta("Contacto actualizado correctamente", Alert.AlertType.INFORMATION);

                limpiarFormulario();
                actualizarTabla();
            } else {
                mostrarAlerta("No se ha seleccionado ningún contacto para actualizar", Alert.AlertType.WARNING);
            }

        } catch (Exception ex) {
            mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void eliminarContacto(ActionEvent e) {
        try {
            Contacto contacto = tablaContactos.getSelectionModel().getSelectedItem();

            if (contacto != null) {
                contactoPrincipal.eliminarContacto(contacto.getId());
                mostrarAlerta("Contacto eliminado correctamente", Alert.AlertType.INFORMATION);
                actualizarTabla();

            } else {
                mostrarAlerta("No se ha seleccionado ningún contacto para eliminar", Alert.AlertType.WARNING);
            }

        } catch (Exception ex) {
            mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
            tablaContactos.setItems(FXCollections.observableArrayList(contactoPrincipal.listarContactos()));
        }
    }

    public void filtrarListaContactos(ActionEvent e) {
        try {
            String tipoFiltro = cbFiltro.getValue();
            String valorFiltro = txtFiltro.getText();

            if (tipoFiltro == "Todo") {
                txtFiltro.clear();
            }

            ArrayList<Contacto> listaContactos = contactoPrincipal.filtrarListaContactos(tipoFiltro, valorFiltro);
            observableList.setAll(listaContactos);

        } catch (Exception ex) {
            mostrarAlerta(ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void cargarContactoSeleccionado() {
        Contacto contacto = tablaContactos.getSelectionModel().getSelectedItem();

        if (contacto != null) {
            txtNombre.setText(contacto.getNombre());
            txtApellido.setText(contacto.getApellido());
            txtTelefono.setText(contacto.getTelefono());
            dpFechaCumpleanios.setValue(contacto.getCumpleAnios());
            txtCorreo.setText(contacto.getCorreo());
            txtFoto.setText(contacto.getFoto());
        } else {
            limpiarFormulario();
        }
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    public void actualizarTabla() {
        observableList.setAll(contactoPrincipal.listarContactos());
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtCorreo.clear();
        txtNombre.clear();
        txtFoto.clear();
        dpFechaCumpleanios.setValue(null);
        cbFiltro.setValue("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colApellido.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        colCorreo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));
        colCumpleanios.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCumpleAnios().toString()));
        cbFiltro.setItems(FXCollections.observableArrayList(contactoPrincipal.listarFiltro()));

        observableList = FXCollections.observableArrayList();
        observableList.setAll(contactoPrincipal.listarContactos());
        tablaContactos.setItems(observableList);

        tablaContactos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cargarContactoSeleccionado();
        });

        colFoto.setCellValueFactory(new PropertyValueFactory<>("foto"));
        colFoto.setCellFactory(cell -> new TableCell<>() {

            final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String s, boolean b) {
                super.updateItem(s, b);
                if (s != null) {
                    Image image = new Image(s, 50, 50, true, true);
                    imageView.setImage(image);
                    setGraphic(imageView);
                } else {
                    setGraphic(null);
                }
            }
        });
    }
}
