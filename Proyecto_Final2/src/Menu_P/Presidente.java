package Menu_P;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Presidente {

    private String nombre_apellidos;


    public Presidente(String nombre_apellidos) {
        this.nombre_apellidos = nombre_apellidos;

    }

    // Getters y Setters
    public String getNombre() {
        return nombre_apellidos;
    }

    public void setNombre(String nombre_apellidos) {
        this.nombre_apellidos = nombre_apellidos;
    }


    // Método para guardar un presidente en un archivo
    public static void guardarPresidente(Presidente presidente) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("nombres_presidentes.txt", true))) {
            writer.write(presidente.getNombre());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar presidentes desde un archivo
    public static String cargarPresidentes() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("nombres_presidentes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}