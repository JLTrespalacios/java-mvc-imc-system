package model;

import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
    private List<Persona> listaPersonas;

    public PersonaDAO() {
        this.listaPersonas = new ArrayList<>();
    }

    public void registrarPersona(Persona persona) {
        listaPersonas.add(persona);
    }

    public List<Persona> obtenerPersonas() {
        return listaPersonas;
    }

    public boolean actualizarPersona(int indice, String nombre, int edad, double peso, double estatura) {
        if (indice >= 0 && indice < listaPersonas.size()) {
            Persona p = listaPersonas.get(indice);
            p.setNombre(nombre);
            p.setEdad(edad);
            p.setPeso(peso);
            p.setEstatura(estatura);
            return true;
        }
        return false;
    }

    public boolean eliminarPersona(int indice) {
        if (indice >= 0 && indice < listaPersonas.size()) {
            listaPersonas.remove(indice);
            return true;
        }
        return false;
    }
}
