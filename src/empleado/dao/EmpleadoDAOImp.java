package empleado.dao;

import java.sql.Connection;
import conexion.ConexionBD;
import static conexion.ConexionBD.conectar;
import empleado.dominio.Empleado;
import java.sql.SQLException;
import java.util.List;
import empleado.dao.EmpleadoDAO;
import empleado.errores.codigoError;
import empleado.errores.userIncorrectException;
import java.sql.Statement;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;

public class EmpleadoDAOImp implements EmpleadoDAO {

    private List<Empleado> empleados;

    public EmpleadoDAOImp() {
        List<Empleado> empleadoList = new ArrayList<>();
        try {
            var conection = ConexionBD.conectar();

            Statement sentencia = conection.createStatement();

            ResultSet resultado = sentencia.executeQuery("Select * from empleados;");

            try {

                while (resultado.next()) {
                    int codigo = resultado.getInt("e_codigo");
                    String nombre = resultado.getString("e_nombre");
                    String apellidos = resultado.getString("e_apellidos");
                    String password = resultado.getString("e_password");

                    empleadoList.add(new Empleado(codigo, nombre, apellidos, password));
                }

            } catch (Exception ex) {
                System.out.println("Error de lectura en: ");

            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAOImp.class.getName()).log(Level.SEVERE, null, ex);

        }

        setEmpleados(empleadoList);

    }

    public List<Empleado> getEmpleados() {
        return this.empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    @Override
    public List<Empleado> leerEmpleados() {
        List<Empleado> empleadoList = new ArrayList<>();
        try {
            var conection = ConexionBD.conectar();

            Statement sentencia = conection.createStatement();

            ResultSet resultado = sentencia.executeQuery("Select * from empleados;");

            try {

                while (resultado.next()) {

                    while (resultado.next()) {
                        int codigo = resultado.getInt("e_codigo");
                        String nombre = resultado.getString("e_nombre");
                        String apellidos = resultado.getString("e_apellidos");
                        String password = resultado.getString("e_password");

                        empleadoList.add(new Empleado(codigo, nombre, apellidos, password));
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(EmpleadoDAOImp.class.getName()).log(Level.SEVERE, null, ex);

            }
        } catch (Exception e) {

        }
        return empleadoList;
    }

    @Override

    public Empleado getEmpleadoPorCodigo(int codigo) {

        Empleado empleado = null;

        try {
            

                var conection = ConexionBD.conectar();

                Statement sentencia = conection.createStatement();

                ResultSet resultado = sentencia.executeQuery("Select * from empleados where e_codigo=" + codigo + ";");

                while (resultado.next()) {
                    int newCodigo = resultado.getInt("e_codigo");
                    String nombre = resultado.getString("e_nombre");
                    String apellidos = resultado.getString("e_apellidos");
                    String password = resultado.getString("e_password");

                    empleado = new Empleado(newCodigo, nombre, apellidos, password);
                }
            
        } catch (SQLException ex) {
            System.err.println("Error de lectura en base de datos");
        }

        return empleado;
    }

    public boolean esCodigoValido(int codigo) {
        boolean valido = false;
        for (Empleado empleado : empleados) {
            if (codigo == empleado.getCodigo()) {
                valido = true;
            } else {
                valido = false;
            }
        }
        return valido;
    }

    public void actualizarPassword(Empleado employee, String employeePassword) {
        List<Empleado> empleList = new ArrayList<Empleado>();
        empleList.add(employee);

        for (int i = 0; i < empleList.size(); i++) {
            if (empleList.get(i).getCodigo() == employee.getCodigo()) {
                empleList.get(i).setPassword(employeePassword);
            }

        }
        this.escribirEnDB(employee, employeePassword);
        //this.escribirEnArchivo();
    }

    public void escribirEnDB(Empleado empleado, String emplePassword) {

        String query;
        Statement statement = null;
        ResultSet result = null;

        try {
            query = "UPDATE empleados SET e_password = \""
                    + empleado.getPassword() + "\" WHERE e_codigo = "
                    + empleado.getCodigo() + ";";
            Connection connect = ConexionBD.conectar();
            statement = (Statement) connect.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            System.err.println("problemas con la base de datos.");
        }
//        finally {
//            this.empleados = (new EmpleDAOImp()).getEmpleados();
//        }

    }

    @Override
    public boolean actualizarEmpleado(Empleado empleado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        

}
