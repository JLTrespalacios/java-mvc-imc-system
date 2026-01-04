package model;

public class Persona {
    private String nombre;
    private int edad;
    private double peso;
    private double estatura;

    public Persona(String nombre, int edad, double peso, double estatura) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.estatura = estatura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public double calcularIMC() {
        if (estatura == 0) return 0;
        return peso / (estatura * estatura);
    }

    public String interpretarIMC() {
        double imc = calcularIMC();
        if (imc < 18.5) {
            return "Bajo peso";
        } else if (imc < 25) {
            return "Normal";
        } else if (imc < 30) {
            return "Sobrepeso";
        } else {
            return "Obesidad";
        }
    }

    public void mostrarInformacion() {
        System.out.printf("| %-20s | %-5d | %-6.2f | %-6.2f | %-6.2f | %-15s |%n",
                nombre, edad, peso, estatura, calcularIMC(), interpretarIMC());
    }
}
