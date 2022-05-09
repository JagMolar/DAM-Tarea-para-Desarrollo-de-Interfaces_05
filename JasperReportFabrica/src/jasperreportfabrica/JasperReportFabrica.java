/*
 * TAREA PARA DI05.
 */
package jasperreportfabrica;

import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.*;

/**
 *
 * @author Juan A. García Muelas <juangmuelas@gmail.com>
 * @version 1
 * @since 23/02/22
 */
public class JasperReportFabrica {

    /**
     * @param args the command line arguments
     */
    public static Connection conexion = null;
    String baseDatos ="jdbc:mysql://localhost/fabrica";
    String user = "root";
    String password = "";

    public JasperReportFabrica() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection(baseDatos, user, password);
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Fallo al cargar JDBC");
            System.exit(1);
        }
        catch (SQLException sqle) {
            System.err.println("No se pudo conectar a la BD");
            System.exit(1);
        }catch (java.lang.InstantiationException sqlex) {
            System.err.println("imposible  conectar");
            System.exit(1);
        }catch (Exception ex) {
            System.err.println("imposible  conectar");
            System.exit(1);
        }//Fin bloques try-catch
    }//fin constructor
    
    //El método ejecutar recibe el parametro del informe
    public void ejecutar(int ID_Cliente) 
    {
        //Ruta del informe respecto del proyecto NetBeans
        String archivoJasper= "facturas.jasper";
        
        try {
            //Cargamos los parametros del informe en una tabla Hash
            Map parametros = new HashMap();
            parametros.put("ID_Cliente",ID_Cliente);
            //Generamos el informe en memoria
            JasperPrint print = JasperFillManager.fillReport(archivoJasper, parametros, conexion);
             // Exporta el informe a PDF 
            JasperExportManager.exportReportToPdfFile(print, "informe.pdf");
            //Abre el archivo PDF generado
            File path = new File ("informe.pdf");
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString(),"Error",JOptionPane.WARNING_MESSAGE);
        }
    }//Fin método ejecutar
    
    public static void main(String[] args) {
        // TODO code application logic here
        JasperReportFabrica informe = new JasperReportFabrica();
        int ID_Cliente = 1;
        informe.ejecutar(ID_Cliente);
    }//fin main
    
}//fin clase JasperReportFabrica
