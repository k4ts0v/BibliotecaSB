package com.lvg.bibliotecasb.modelo.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "usuario")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 9)
    @NotNull
    @Column(name = "dni", nullable = false, length = 9)
    @Pattern (regexp = "\\d{8}[A-Z]", message = "El DNI debe tener un formato válido.")
    private String dni;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    @Pattern(regexp = "[A-z0-9]{1,100}", message = "Solo puede contener caracteres alfanuméricos, y como máximo, 100 caracteres.")
    private String nombre;

    @Size(max = 100)
    @NotNull
    @Column(name = "email", nullable = false, length = 100)
    @Pattern(regexp = "([A-Za-z0-9]{1,50}@gmail.com)", message = "El email debe ser de gmail.")
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    @Pattern(regexp = "[A-z0-9]{4,12}", message = "La contraseña solo puede contener caracteres alfanuméricos y debe tener entre 4 y 12 caracteres.")
    private String password;

    @NotNull
    @Lob
    @Column(name = "tipo", nullable = false)
    @Pattern(regexp = "(normal|administrador)", message = "El tipo de usuario solo puede ser normal o administrador.")
    private String tipo;

    @Column(name = "penalizacion_hasta")
    private LocalDate penalizacionHasta;

    public void setDni(@Size(min = 9, max = 9) @NotNull String dni) {
        char[] LETRAS_DNI = { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X',
                'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E' };

        try {
            // Extraer la parte numérica y la letra.
            String parteNumerica = dni.substring(0, dni.length() - 1);
            char letra = dni.charAt(dni.length() - 1);

            // Convertir la parte numérica a entero.
            int numero = Integer.parseInt(parteNumerica);

            // Calcular la letra correcta según el resto.
            char letraCorrecta = LETRAS_DNI[numero % 23];

            // Comparar la letra calculada con la letra proporcionada.
            this.dni = (letraCorrecta == letra) ? dni : null;
        } catch (NumberFormatException e) {
            System.out.println("El dni no es válido");
        }
    }
}