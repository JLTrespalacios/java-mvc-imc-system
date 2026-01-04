package model;

public class Paciente extends Persona {
    private double peso;
    private double estatura;

    public Paciente(String nombre, int edad, double peso, double estatura) {
        super(nombre, edad);
        this.peso = peso;
        this.estatura = estatura;
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
        double estaturaMetros = this.estatura;
        // Normalización de unidades (si el usuario ingresó cm o mm)
        if (estaturaMetros > 3.0) { 
            if (estaturaMetros > 300) { // Probablemente milímetros (ej. 1780)
                estaturaMetros /= 1000;
            } else { // Probablemente centímetros (ej. 178)
                estaturaMetros /= 100;
            }
        }
        
        if (estaturaMetros <= 0) return 0;
        return peso / (estaturaMetros * estaturaMetros);
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

    /**
     * Obtiene el rango de peso "normal" aproximado basado en la edad.
     * @return Un arreglo de double donde [0] es peso mínimo y [1] es peso máximo. Retorna null si no aplica.
     */
    public double[] obtenerRangoPesoPorEdad() {
        if (edad >= 18 && edad <= 24) return new double[]{50, 70};
        if (edad >= 25 && edad <= 34) return new double[]{55, 75};
        if (edad >= 35 && edad <= 44) return new double[]{58, 78};
        if (edad >= 45 && edad <= 54) return new double[]{60, 80};
        if (edad >= 55 && edad <= 64) return new double[]{58, 78};
        if (edad >= 65) return new double[]{55, 75};
        return null; // Menor de 18 años
    }

    /**
     * Evalúa si el peso actual es bajo, normal o alto respecto al rango de edad.
     * @return Mensaje descriptivo.
     */
    public String analizarPesoPorEdad() {
        double[] rango = obtenerRangoPesoPorEdad();
        if (rango == null) {
            return "Edad fuera de rango referencial (menor de 18).";
        }
        
        double pesoMin = rango[0];
        double pesoMax = rango[1];
        
        if (peso < pesoMin) {
            return "Bajo para su edad (Esperado: " + (int)pesoMin + "-" + (int)pesoMax + "kg)";
        } else if (peso > pesoMax) {
            return "Alto para su edad (Esperado: " + (int)pesoMin + "-" + (int)pesoMax + "kg)";
        } else {
            return "Adecuado para su edad (" + (int)pesoMin + "-" + (int)pesoMax + "kg)";
        }
    }

    @Override
    public void mostrarInformacion() {
        System.out.printf("Paciente: %s | Edad: %d | IMC: %.2f%n", nombre, edad, calcularIMC());
    }
}
