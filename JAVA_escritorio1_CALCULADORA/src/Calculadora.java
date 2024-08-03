/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jf692
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Calculadora extends javax.swing.JFrame {

    // Definición corregida para la pantalla
    
    //public javax.swing.JTextField pantalla;  
    public float primernumero;
    public float segundonumero;
    public String operador;
    private boolean isProcessing = false;
    private int contadorOperaciones = 0;
    private StringBuilder operacionActual; // Buffer para la operación actual
    private List<String> historialOperaciones = new ArrayList<>();

    public Calculadora() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Inicializa el buffer de la operación actual
        operacionActual = new StringBuilder();
        
        // Agregar el KeyListener para capturar teclas
        pantalla.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                agregarNumero(e);
            }
        });
        pantalla.setFocusable(true);
        pantalla.requestFocusInWindow(); // Solicitar el foco para el componente pantalla
    }
        
    // private boolean isProcessing = false;
    
    private void agregarNumero(KeyEvent e) {
    // Obtener el carácter de la tecla presionada
    char keyChar = e.getKeyChar();
    int keyCode = e.getKeyCode();

    
    // Verificar si el carácter es un número
    if (Character.isDigit(keyChar)) {
        // Agregar el número a la pantalla
        pantalla.setText(pantalla.getText()+keyChar);
        // Emitir un sonido de beep
        Toolkit.getDefaultToolkit().beep();
        //Bitacora agregar el número al buffer de la operacion
        operacionActual.append(keyChar);
        } else if (keyChar == '+' || keyChar == '-' || keyChar == '*' || keyChar == '/') {            
        // Manejar el operador de suma
        primernumero = Float.parseFloat(pantalla.getText());
        operador = String.valueOf(keyChar);
        pantalla.setText("");
        Toolkit.getDefaultToolkit().beep();
        // Agregar el operador al buffer de la operación
        operacionActual.append("\n").append(operador).append("\n");
        } else if (keyChar == '\n') { // Tecla Enter
            if (!pantalla.getText().isEmpty()) {
                float segundoNumero = Float.parseFloat(pantalla.getText());
                float resultado = 0;
            
            switch (operador) {
                case "+":
                    resultado = primernumero + segundoNumero;
                    break;
                case "-":
                    resultado = primernumero - segundoNumero;
                    break;
                case "*":
                    resultado = primernumero * segundoNumero;
                    break;
                case "/":
                    if (segundoNumero != 0) {
                        resultado = primernumero / segundoNumero;
                    } else {
                        pantalla.setText("Error");
                        return; // Salir del método si hay una división por cero
                    }
                    break;
            }
            
                pantalla.setText(String.valueOf(resultado));
                Toolkit.getDefaultToolkit().beep();
                // Agregar el resultado al buffer de la operación
                operacionActual.append("\n=").append(resultado).append("\n");
                // Escribir la operación completa en la bitácora
                escribirEnBitacora(operacionActual.toString());
                // Limpiar el buffer para la próxima operación
                operacionActual.setLength(0);
        }
    }else{ 
    // Verificar cada número y llamar al método correspondiente    switch (keyCode) {
        switch (keyCode) {
        case KeyEvent.VK_0:
        case KeyEvent.VK_NUMPAD0:
            BTN_0ActionPerformed(null);
            break;
        case KeyEvent.VK_1:
        case KeyEvent.VK_NUMPAD1:
            BTN_1ActionPerformed(null);
            break;
        case KeyEvent.VK_2:
        case KeyEvent.VK_NUMPAD2:
            BTN_2ActionPerformed(null);
            break;
        case KeyEvent.VK_3:
        case KeyEvent.VK_NUMPAD3:
            BTN_3ActionPerformed(null);
            break;
        case KeyEvent.VK_4:
        case KeyEvent.VK_NUMPAD4:
            BTN_4ActionPerformed(null);
            break;
        case KeyEvent.VK_5:
        case KeyEvent.VK_NUMPAD5:
            BTN_5ActionPerformed(null);
            break;
        case KeyEvent.VK_6:
        case KeyEvent.VK_NUMPAD6:
            BTN_6ActionPerformed(null);
            break;
        case KeyEvent.VK_7:
        case KeyEvent.VK_NUMPAD7:
            BTN_7ActionPerformed(null);
            break;
        case KeyEvent.VK_8:
        case KeyEvent.VK_NUMPAD8:
            BTN_8ActionPerformed(null);
            break;
        case KeyEvent.VK_9:
        case KeyEvent.VK_NUMPAD9:
            BTN_9ActionPerformed(null);
            break;
        }
    }

    // Restaurar el estado
    isProcessing = false;
}


    private void escribirEnBitacora(String texto) {
            try (BufferedWriter bitacora = new BufferedWriter(new FileWriter("C:\\Users\\jf692\\OneDrive\\Escritorio\\Calculadora_JAVA\\bitacoraCalculadora.txt", true))) {
        bitacora.write(texto + "\n"); // Escribe el texto seguido de una nueva línea
    } catch (IOException ex) {
        ex.printStackTrace(); // Maneja la excepción en caso de error
    }
    }
    
private void actualizarHistorial(String operacionActual, String resultado) {
    // Agrega la nueva operación al historial
    historialOperaciones.add(operacionActual);
    contadorOperaciones++;

    // Construye el texto de la bitácora
    StringBuilder bitacoraTexto = new StringBuilder();

    // Agrega todas las operaciones al texto de la bitácora
    for (int i = 0; i < historialOperaciones.size(); i++) {
        String operacionHistorial = historialOperaciones.get(i);
        String encabezado = "Operacion " + (i + 1);
        
        // Agrega el encabezado antes de cada operación
        bitacoraTexto.append(encabezado).append(":\n");
        bitacoraTexto.append(operacionHistorial).append("\n");

        // Agrega el resultado solo para la última operación
        if (i == historialOperaciones.size() - 1) {
            bitacoraTexto.append("=").append(resultado).append("\n");
        }
        
        // Agrega una línea divisoria si no es la última operación
        if (i < historialOperaciones.size() - 1) {
            bitacoraTexto.append("______________________________________\n");
        }
    }

    // Escribe en el archivo de bitácora
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("bitacora.txt", true))) {
        writer.write(bitacoraTexto.toString());
    } catch (IOException e) {
        e.printStackTrace();
    }
}





        public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Calculadora().setVisible(true);
            }
        });
    }

public class FormularioOpciones extends javax.swing.JFrame {
        public FormularioOpciones() {
        // Configura la ventana principal
        setTitle("Formulario Opciones");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centra el formulario en la pantalla

        // Configura el diseño
        setLayout(new FlowLayout());

        // Inicializa y agrega componentes
        JButton miBoton = new JButton("Nuevo");
        JButton miHisto = new JButton("Historial");
        
                // Agrega ActionListener a los botones
        miBoton.addActionListener(new ActionListener() {
            @Override
            
public void actionPerformed(ActionEvent e) {
    // Leer el contenido del archivo de la bitácora
    StringBuilder bitacoraContent = new StringBuilder();
    String filePath = "C:\\Users\\jf692\\OneDrive\\Escritorio\\Calculadora_JAVA\\bitacoraCalculadora.txt";
    
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            bitacoraContent.append(line).append("\n");
        }
    } catch (IOException ex) {
        ex.printStackTrace();
        bitacoraContent.append("Error al leer la bitácora.");
    }

    // Mostrar el cuadro de diálogo con el contenido de la bitácora
    int opcion = JOptionPane.showConfirmDialog(null, 
    "¿Deseas continuar?", 
    "Confirmación", 
    JOptionPane.OK_CANCEL_OPTION, 
    JOptionPane.INFORMATION_MESSAGE);

// Si el usuario presiona el botón OK, borrar la pantalla
if (opcion == JOptionPane.OK_OPTION) {
    pantalla.setText(""); // Limpiar el JLabel
}

    // Si el usuario presiona el botón OK, borrar la pantalla
    if (opcion == JOptionPane.OK_OPTION) {
        pantalla.setText(""); // Limpiar el JLabel
    }    
}

        });
                miHisto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Comportamiento del botón "Historial"
                // Por ejemplo, mostrar el formulario del historial
                mostrarHistorial();
            }
        });
        //add(miEtiqueta);
        add(miBoton);
        add(miHisto);
    }
    // Método para mostrar el historial
    private void mostrarHistorial() {
        // Aquí puedes agregar el código para mostrar el formulario del historial
        JFrame historialFrame = new JFrame("Historial de Operaciones");
        historialFrame.setSize(400, 300);
        historialFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        historialFrame.setLocationRelativeTo(null); // Centra el formulario en la pantalla

        // Crear un JTextArea para mostrar el historial
        JTextArea historialArea = new JTextArea();
        historialArea.setEditable(false);

        // Cargar el historial desde el archivo y mostrarlo en el JTextArea
        historialArea.setText(cargarHistorial());


        JScrollPane scrollPane = new JScrollPane(historialArea);
        historialFrame.add(scrollPane);

        historialFrame.setVisible(true);
    }

    // Método para cargar el historial (puedes adaptarlo según cómo manejes el historial en tu aplicación)
    private String cargarHistorial() {
        // Aquí debes cargar el historial de operaciones desde tu archivo o lista
        // Por ejemplo, leyendo desde el archivo de bitácora
        StringBuilder historial = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\jf692\\OneDrive\\Escritorio\\Calculadora_JAVA\\bitacoraCalculadora.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                historial.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return historial.toString();
    }
    

}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BTN_ELIMINAR = new javax.swing.JButton();
        BTN_DIVISION = new javax.swing.JButton();
        BTN_MULTIPLICAR = new javax.swing.JButton();
        BTN_RESTAR = new javax.swing.JButton();
        BTN_7 = new javax.swing.JButton();
        BTN_8 = new javax.swing.JButton();
        BTN_9 = new javax.swing.JButton();
        BTN_SUMAR = new javax.swing.JButton();
        BTN_IGUAL_RESPUESTA = new javax.swing.JButton();
        BTN_4 = new javax.swing.JButton();
        BTN_5 = new javax.swing.JButton();
        BTN_6 = new javax.swing.JButton();
        BTN_1 = new javax.swing.JButton();
        BTN_2 = new javax.swing.JButton();
        BTN_3 = new javax.swing.JButton();
        BTN_0 = new javax.swing.JButton();
        BTN_PUNTO = new javax.swing.JButton();
        pantalla = new javax.swing.JLabel();
        BTN_AYUDA = new javax.swing.JButton();
        BTN_OPCIONES = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        BTN_ELIMINAR.setText("C");
        BTN_ELIMINAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_ELIMINARActionPerformed(evt);
            }
        });

        BTN_DIVISION.setText("/");
        BTN_DIVISION.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_DIVISIONActionPerformed(evt);
            }
        });

        BTN_MULTIPLICAR.setText("*");
        BTN_MULTIPLICAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_MULTIPLICARActionPerformed(evt);
            }
        });

        BTN_RESTAR.setText("-");
        BTN_RESTAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_RESTARActionPerformed(evt);
            }
        });

        BTN_7.setText("7");
        BTN_7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_7ActionPerformed(evt);
            }
        });

        BTN_8.setText("8");
        BTN_8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_8ActionPerformed(evt);
            }
        });

        BTN_9.setText("9");
        BTN_9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_9ActionPerformed(evt);
            }
        });

        BTN_SUMAR.setText("+");
        BTN_SUMAR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_SUMARActionPerformed(evt);
            }
        });

        BTN_IGUAL_RESPUESTA.setText("=");
        BTN_IGUAL_RESPUESTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_IGUAL_RESPUESTAActionPerformed(evt);
            }
        });

        BTN_4.setText("4");
        BTN_4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_4ActionPerformed(evt);
            }
        });

        BTN_5.setText("5");
        BTN_5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_5ActionPerformed(evt);
            }
        });

        BTN_6.setText("6");
        BTN_6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_6ActionPerformed(evt);
            }
        });

        BTN_1.setText("1");
        BTN_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_1ActionPerformed(evt);
            }
        });

        BTN_2.setText("2");
        BTN_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_2ActionPerformed(evt);
            }
        });

        BTN_3.setText("3");
        BTN_3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_3ActionPerformed(evt);
            }
        });

        BTN_0.setText("0");
        BTN_0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_0ActionPerformed(evt);
            }
        });

        BTN_PUNTO.setText(".");
        BTN_PUNTO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_PUNTOActionPerformed(evt);
            }
        });

        pantalla.setBackground(new java.awt.Color(255, 255, 255));
        pantalla.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        pantalla.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 255)));

        BTN_AYUDA.setText("Ayuda");
        BTN_AYUDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_AYUDAActionPerformed(evt);
            }
        });

        BTN_OPCIONES.setText("Opciones");
        BTN_OPCIONES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_OPCIONESActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BTN_OPCIONES)
                        .addGap(24, 24, 24)
                        .addComponent(BTN_AYUDA))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(pantalla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(BTN_7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(BTN_ELIMINAR, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(BTN_4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                        .addComponent(BTN_1, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(BTN_8, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(BTN_DIVISION, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(BTN_5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(BTN_2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
                                .addComponent(BTN_0, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(BTN_9, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BTN_MULTIPLICAR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BTN_6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                .addComponent(BTN_3, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                .addComponent(BTN_PUNTO, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(BTN_IGUAL_RESPUESTA, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(BTN_SUMAR, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BTN_RESTAR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BTN_AYUDA)
                    .addComponent(BTN_OPCIONES))
                .addGap(2, 2, 2)
                .addComponent(pantalla, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BTN_DIVISION, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BTN_RESTAR, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BTN_ELIMINAR, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BTN_MULTIPLICAR, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BTN_7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTN_8, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTN_9, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BTN_4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTN_5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTN_6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(BTN_SUMAR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BTN_1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTN_2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BTN_3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BTN_PUNTO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BTN_0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(BTN_IGUAL_RESPUESTA, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(69, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BTN_ELIMINARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_ELIMINARActionPerformed
    // TODO add your handling code here:    
    this.pantalla.setText("");
    }//GEN-LAST:event_BTN_ELIMINARActionPerformed

    private void BTN_IGUAL_RESPUESTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_IGUAL_RESPUESTAActionPerformed
        // TODO add your handling code here:
        if (pantalla.getText().isEmpty()) return;

    this.segundonumero = Float.parseFloat(this.pantalla.getText());
    float resultado = 0; // float resultado 

    String operacion = "";
    
    switch (this.operador) {
        case "+": 
            resultado = this.primernumero + this.segundonumero;
            operacion = this.primernumero + " + " + this.segundonumero;
            break;
        case "-": 
            resultado = this.primernumero - this.segundonumero;
            operacion = this.primernumero + " - " + this.segundonumero;
            break;
        case "*": 
            resultado = this.primernumero * this.segundonumero;
            operacion = this.primernumero + " * " + this.segundonumero;
            break;
        case "/":
            if (this.segundonumero == 0) {
                this.pantalla.setText("Error: no se puede dividir entre cero.");
                Toolkit.getDefaultToolkit().beep();
                // Registro del error en la bitácora
                escribirEnBitacora("Operación: " + this.primernumero + " / " + this.segundonumero + " = Error: no se puede dividir entre cero.");
                return; // Salir del método si hay un error de división por cero
            } else {
                resultado = this.primernumero / this.segundonumero;
                operacion = this.primernumero + " / " + this.segundonumero;
            }
            break;
        default:
            this.pantalla.setText("Operador no válido");
            Toolkit.getDefaultToolkit().beep();
            // Registro del error en la bitácora
            escribirEnBitacora("Operador no válido");
            return;
    }

    // Mostrar el resultado en la pantalla
    this.pantalla.setText(String.valueOf(resultado));
    Toolkit.getDefaultToolkit().beep();
    
    // Registrar el resultado en la bitácora
        escribirEnBitacora("Operación: " + this.primernumero + " " + this.operador + " " + this.segundonumero + " = " + resultado);
    }//GEN-LAST:event_BTN_IGUAL_RESPUESTAActionPerformed

    private void BTN_0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_0ActionPerformed
    this.pantalla.setText(this.pantalla.getText() + "0");
    Toolkit.getDefaultToolkit().beep();
    //escribirEnBitacora("0");
    }//GEN-LAST:event_BTN_0ActionPerformed

    private void BTN_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_1ActionPerformed
    this.pantalla.setText(this.pantalla.getText() + "1");
    Toolkit.getDefaultToolkit().beep();
    //escribirEnBitacora("1");
    }//GEN-LAST:event_BTN_1ActionPerformed

    private void BTN_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_2ActionPerformed
    this.pantalla.setText(this.pantalla.getText() + "2");
    Toolkit.getDefaultToolkit().beep();
    //escribirEnBitacora("2");
    }//GEN-LAST:event_BTN_2ActionPerformed

    private void BTN_3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_3ActionPerformed
    this.pantalla.setText(this.pantalla.getText() + "3");
    Toolkit.getDefaultToolkit().beep();
    //escribirEnBitacora("3");
    }//GEN-LAST:event_BTN_3ActionPerformed

    private void BTN_4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_4ActionPerformed
    this.pantalla.setText(this.pantalla.getText() + "4");
    Toolkit.getDefaultToolkit().beep();
    //escribirEnBitacora("4");
    }//GEN-LAST:event_BTN_4ActionPerformed

    private void BTN_5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_5ActionPerformed
    this.pantalla.setText(this.pantalla.getText() + "5");
    Toolkit.getDefaultToolkit().beep();
    //escribirEnBitacora("5");
    }//GEN-LAST:event_BTN_5ActionPerformed

    private void BTN_6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_6ActionPerformed
    this.pantalla.setText(this.pantalla.getText() + "6");
    Toolkit.getDefaultToolkit().beep();
    //escribirEnBitacora("6");
    }//GEN-LAST:event_BTN_6ActionPerformed

    private void BTN_7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_7ActionPerformed
    this.pantalla.setText(this.pantalla.getText() + "7");
    Toolkit.getDefaultToolkit().beep();
    //escribirEnBitacora("7");
    }//GEN-LAST:event_BTN_7ActionPerformed

    private void BTN_8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_8ActionPerformed
    this.pantalla.setText(this.pantalla.getText() + "8");
    Toolkit.getDefaultToolkit().beep();
    //escribirEnBitacora("8");
    }//GEN-LAST:event_BTN_8ActionPerformed

    private void BTN_9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_9ActionPerformed
    this.pantalla.setText(this.pantalla.getText() + "9");
    Toolkit.getDefaultToolkit().beep();
    //escribirEnBitacora("9");
    }//GEN-LAST:event_BTN_9ActionPerformed

    private void BTN_SUMARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_SUMARActionPerformed
        // TODO add your handling code here:
        this.primernumero=Float.parseFloat(this.pantalla.getText());
        this.operador="+";
        this.pantalla.setText("");
        Toolkit.getDefaultToolkit().beep();
        //escribirEnBitacora("+");
    }//GEN-LAST:event_BTN_SUMARActionPerformed

    private void BTN_RESTARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_RESTARActionPerformed
        // TODO add your handling code here:
        this.primernumero=Float.parseFloat(this.pantalla.getText());
        this.operador="-";
        this.pantalla.setText("");
        Toolkit.getDefaultToolkit().beep();
        //escribirEnBitacora("-");
    }//GEN-LAST:event_BTN_RESTARActionPerformed

    private void BTN_MULTIPLICARActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_MULTIPLICARActionPerformed
        // TODO add your handling code here:
        this.primernumero=Float.parseFloat(this.pantalla.getText());
        this.operador="*";
        this.pantalla.setText("");
        Toolkit.getDefaultToolkit().beep();
        //escribirEnBitacora("*");
    }//GEN-LAST:event_BTN_MULTIPLICARActionPerformed

    private void BTN_DIVISIONActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_DIVISIONActionPerformed
        // TODO add your handling code here:
        this.primernumero=Float.parseFloat(this.pantalla.getText());
        this.operador="/";
        this.pantalla.setText("");
        Toolkit.getDefaultToolkit().beep();
        //escribirEnBitacora("/");
    }//GEN-LAST:event_BTN_DIVISIONActionPerformed

    private void BTN_PUNTOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_PUNTOActionPerformed
        // TODO add your handling code here:
        if(!(this.pantalla.getText().contains("."))){
            this.pantalla.setText(this.pantalla.getText()+".");
        }
        Toolkit.getDefaultToolkit().beep();
        //escribirEnBitacora(".");
    }//GEN-LAST:event_BTN_PUNTOActionPerformed

    private void BTN_AYUDAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_AYUDAActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Componentes Principales:\n"
    +"- Pantalla: Muestra el número ingresado y el resultado de las operaciones.\n"
    +"- Botones Numéricos (0-9): Permiten ingresar números.\n"
    +"- Botones de Operación (+, -, *, /): Permiten realizar sumas, restas, multiplicaciones y divisiones.\n"
    +"- Botón Igual (=): Calcula y muestra el resultado de la operación.\n"
    +"- Botón Nuevo: Reinicia la calculadora.\n"
    +"- Botón Historial: Muestra el historial de operaciones realizadas.\n"
    +"- Botón Ayuda: Muestra información de ayuda sobre el uso de la calculadora.\n"
    +"\nPor si hay dificultades en su uso seguir los siguientes puntos para poder ejecutar correctamente el programa:\n"
    +"1.  Ingresar Números: Use los botones numéricos o el teclado numérico para ingresar el primer número deseado.\n"
    +"2.  Seleccionar Operación: Pulse el botón correspondiente a la operación que desea realizar (+, -, *, /) o use los símbolos del teclado numérico.\n"
    +"3.  Ingresar el Segundo Número: Ingrese el segundo número utilizando los botones numéricos o el teclado numérico.\n"
    +"4.  Obtener el Resultado: Pulse el botón = o presione Enter para calcular y mostrar el resultado en la pantalla.\n"
    +"\nReiniciar la Calculadora:\n"
    +"- Reiniciar: Pulse el botón Nuevo para limpiar la pantalla y reiniciar la calculadora.\n"
    +"\nVer el Historial de Operaciones:\n"
    +"- Mostrar Historial: Pulse el botón Historial para abrir una nueva ventana que muestra el historial de todas las operaciones realizadas.\n"
    +"\nObtener Ayuda:\n"
    +"- Mostrar Ayuda: Pulse el botón Ayuda para ver un mensaje emergente con información de ayuda sobre el uso de la calculadora.\n"
    +"\nEjemplo de Uso:\n"
    +"\nSuma\n"
    +"- Ingrese 5 utilizando los botones numéricos o el teclado numérico.\n"  
    +"- Pulse el botón + o presione + en el teclado.\n"
    +"- Ingrese 3 utilizando los botones numéricos o el teclado numérico.\n"  
    +"- Pulse el botón = o presione Enter. La pantalla mostrará 8.\n"
    +"- Al finalizar la operacion presione el boton C para limpiar pantalla.\n"
    +"\nHistorial\n"
    +"- Pulse el botón Historial. Se abrirá una ventana mostrando el historial de operaciones realizadas.\n"
           
);
    }//GEN-LAST:event_BTN_AYUDAActionPerformed

    private void BTN_OPCIONESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_OPCIONESActionPerformed
    // Crea una instancia del nuevo formulario
    // Crea una instancia del nuevo formulario
    FormularioOpciones opciones = new FormularioOpciones();
    
    // Configura el nuevo formulario si es necesario
    opciones.setLocationRelativeTo(null); // Centra el formulario en la pantalla
    
    // Muestra el nuevo formulario
    opciones.setVisible(true);
    }//GEN-LAST:event_BTN_OPCIONESActionPerformed

    /**
     * @param args the command line arguments
     */
    
    public String sincero(float resultado){
        String retorno="";
        
        retorno=Float.toString(resultado);
        
        if(resultado%1==0){
            retorno=retorno.substring(0, retorno.length()-2);
        }
        return retorno;
    }
    
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BTN_0;
    private javax.swing.JButton BTN_1;
    private javax.swing.JButton BTN_2;
    private javax.swing.JButton BTN_3;
    private javax.swing.JButton BTN_4;
    private javax.swing.JButton BTN_5;
    private javax.swing.JButton BTN_6;
    private javax.swing.JButton BTN_7;
    private javax.swing.JButton BTN_8;
    private javax.swing.JButton BTN_9;
    private javax.swing.JButton BTN_AYUDA;
    private javax.swing.JButton BTN_DIVISION;
    private javax.swing.JButton BTN_ELIMINAR;
    private javax.swing.JButton BTN_IGUAL_RESPUESTA;
    private javax.swing.JButton BTN_MULTIPLICAR;
    private javax.swing.JButton BTN_OPCIONES;
    private javax.swing.JButton BTN_PUNTO;
    private javax.swing.JButton BTN_RESTAR;
    private javax.swing.JButton BTN_SUMAR;
    private javax.swing.JLabel pantalla;
    // End of variables declaration//GEN-END:variables

    private void agregarNumero(char c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    };
};