package controller;

import model.Paciente;
import model.PersonaDAO;
import view.PersonaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PersonaController {
    private PersonaDAO dao;
    private PersonaView view;

    public PersonaController() {
        this.dao = new PersonaDAO();
        this.view = new PersonaView();
        
        // Inicializar listeners
        this.view.addRegistrarListener(new RegistrarListener());
        this.view.addActualizarListener(new ActualizarListener());
        this.view.addEliminarListener(new EliminarListener());
        this.view.addLimpiarListener(new LimpiarListener());
        this.view.addTablaSelectionListener(new TablaListener());
    }

    public void iniciar() {
        view.setVisible(true);
        actualizarTabla();
    }

    private void actualizarTabla() {
        view.mostrarPacientes(dao.obtenerPacientes());
    }

    private class RegistrarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nombre = view.getNombre();
                int edad = Integer.parseInt(view.getEdad());
                double peso = Double.parseDouble(view.getPeso());
                double estatura = Double.parseDouble(view.getEstatura());

                Paciente paciente = new Paciente(nombre, edad, peso, estatura);
                dao.registrarPaciente(paciente);
                view.mostrarMensaje("Paciente registrado con éxito.");
                view.limpiarCampos();
                actualizarTabla();
            } catch (NumberFormatException ex) {
                view.mostrarError("Por favor ingrese datos numéricos válidos para edad, peso y estatura.");
            } catch (Exception ex) {
                view.mostrarError("Error al registrar: " + ex.getMessage());
            }
        }
    }

    private class ActualizarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int fila = view.getFilaSeleccionada();
            if (fila == -1) {
                view.mostrarError("Seleccione un paciente de la tabla para actualizar.");
                return;
            }

            try {
                String nombre = view.getNombre();
                int edad = Integer.parseInt(view.getEdad());
                double peso = Double.parseDouble(view.getPeso());
                double estatura = Double.parseDouble(view.getEstatura());

                boolean exito = dao.actualizarPaciente(fila, nombre, edad, peso, estatura);
                if (exito) {
                    view.mostrarMensaje("Paciente actualizado con éxito.");
                    view.limpiarCampos();
                    actualizarTabla();
                } else {
                    view.mostrarError("No se pudo actualizar el paciente.");
                }
            } catch (NumberFormatException ex) {
                view.mostrarError("Por favor ingrese datos numéricos válidos.");
            }
        }
    }

    private class EliminarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int fila = view.getFilaSeleccionada();
            if (fila == -1) {
                view.mostrarError("Seleccione un paciente de la tabla para eliminar.");
                return;
            }

            // Confirmación opcional
            boolean exito = dao.eliminarPaciente(fila);
            if (exito) {
                view.mostrarMensaje("Paciente eliminado con éxito.");
                view.limpiarCampos();
                actualizarTabla();
            } else {
                view.mostrarError("No se pudo eliminar el paciente.");
            }
        }
    }

    private class LimpiarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.limpiarCampos();
        }
    }

    private class TablaListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int fila = view.getFilaSeleccionada();
                if (fila != -1) {
                    // Obtener datos del modelo y ponerlos en los campos
                    Paciente p = dao.obtenerPacientes().get(fila);
                    view.setNombre(p.getNombre());
                    view.setEdad(String.valueOf(p.getEdad()));
                    view.setPeso(String.valueOf(p.getPeso()));
                    view.setEstatura(String.valueOf(p.getEstatura()));
                }
            }
        }
    }
}
