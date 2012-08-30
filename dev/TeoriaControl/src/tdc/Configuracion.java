/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tdc;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author fanky
 */
public class Configuracion {
    public static final String PROPERTIES_PACKAGE = "/tdc/properties/";
    private static final String PROPERTIES_VERSION = "version";
    public static double getVersionActual(){
        return 1.02D;
    }
    public static String getVersion(){
        try{
            //context = WebContextProvider.get().getServletContext();
            //logging.Debugger.debug("i got my context!! "+context.getContextPath());
            String propFile = PROPERTIES_PACKAGE + "version.properties";
            System.out.println("looking up configuration file from: "+propFile);
            Properties properties = new Properties();
            properties.load(Configuracion.class.getResourceAsStream(propFile));
            return properties.getProperty(PROPERTIES_VERSION,"0.0");
//            db_type_conn = Integer.parseInt(properties.getProperty(PROPERTIES_DBCONN,String.valueOf(MYSQLDB_CONN)));
        }catch(IOException e){
            System.out.println(e.getMessage());
            return "0.0";
        }
    }
}
