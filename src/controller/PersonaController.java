package controller;

import model.Persona;
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
        view.mostrarPersonas(dao.obtenerPersonas());
    }

    private class RegistrarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nombre = view.getNombre();
                int edad = Integer.parseInt(view.getEdad());
                double peso = Double.parseDouble(view.getPeso());
                double estatura = Double.parseDouble(view.getEstatura());

                Persona persona = new Persona(nombre, edad, peso, estatura);
                dao.registrarPersona(persona);
                view.mostrarMensaje("Persona registrada con éxito.");
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
                view.mostrarError("Seleccione una persona de la tabla para actualizar.");
                return;
            }

            try {
                String nombre = view.getNombre();
                int edad = Integer.parseInt(view.getEdad());
                double peso = Double.parseDouble(view.getPeso());
                double estatura = Double.parseDouble(view.getEstatura());

                boolean exito = dao.actualizarPersona(fila, nombre, edad, peso, estatura);
                if (exito) {
                    view.mostrarMensaje("Persona actualizada con éxito.");
                    view.limpiarCampos();
                    actualizarTabla();
                } else {
                    view.mostrarError("No se pudo actualizar la persona.");
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
                view.mostrarError("Seleccione una persona de la tabla para eliminar.");
                return;
            }

            // Confirmación opcional
            boolean exito = dao.eliminarPersona(fila);
            if (exito) {
                view.mostrarMensaje("Persona eliminada con éxito.");
                view.limpiarCampos();
                actualizarTabla();
            } else {
                view.mostrarError("No se pudo eliminar la persona.");
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
                    // Nota: En una app real, usaríamos el ID o el objeto Persona directamente.
                    // Aquí usamos los valores de la tabla, asumiendo que coinciden con el orden.
                    // Mejor práctica: obtener el objeto Persona del DAO por índice.
                    
                    Persona p = dao.obtenerPersonas().get(fila);
                    view.setNombre(p.getNombre());
                    view.setEdad(String.valueOf(p.getEdad()));
                    view.setPeso(String.valueOf(p.getPeso()));
                    view.setEstatura(String.valueOf(p.getEstatura()));
                }
            }
        }
    }
}
