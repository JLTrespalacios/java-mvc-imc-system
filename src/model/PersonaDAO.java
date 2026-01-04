package model;

import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
    private List<Paciente> listaPacientes;

    public PersonaDAO() {
        this.listaPacientes = new ArrayList<>();
    }

    public void registrarPaciente(Paciente paciente) {
        listaPacientes.add(paciente);
    }

    public List<Paciente> obtenerPacientes() {
        return listaPacientes;
    }

    public boolean actualizarPaciente(int indice, String nombre, int edad, double peso, double estatura) {
        if (indice >= 0 && indice < listaPacientes.size()) {
            Paciente p = listaPacientes.get(indice);
            p.setNombre(nombre);
            p.setEdad(edad);
            p.setPeso(peso);
            p.setEstatura(estatura);
            return true;
        }
        return false;
    }

    public boolean eliminarPaciente(int indice) {
        if (indice >= 0 && indice < listaPacientes.size()) {
            listaPacientes.remove(indice);
            return true;
        }
        return false;
    }
}
