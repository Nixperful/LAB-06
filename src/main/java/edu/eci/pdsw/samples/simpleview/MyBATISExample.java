/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.simpleview;

import edu.eci.pdsw.persistence.impl.mappers.PacienteMapper;
import edu.eci.pdsw.samples.entities.Consulta;
import edu.eci.pdsw.samples.entities.Eps;
import edu.eci.pdsw.samples.entities.Paciente;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author hcadavid
 */
public class MyBATISExample {

/**
     * Método que construye una fábrica de sesiones de MyBatis a partir del
     * archivo de configuración ubicado en src/main/resources
     *
     * @return instancia de SQLSessionFactory
     */
    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getLocalizedMessage(),e);
            }
        }
        return sqlSessionFactory;
    }

    /**
     * Programa principal de ejempo de uso de MyBATIS
     * @param args
     * @throws SQLException 
     */
    public static void main(String args[]) throws SQLException {
        //consultarPacientes();
        //consultarPaciente(5555,"CC"); 
        
        
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        PacienteMapper pmap = sqlss.getMapper(PacienteMapper.class);
        
        Eps eps = new Eps("SaludTotal", "8456986");
        Paciente p = new Paciente(888, "CC", "IT", new Date(2000,01,01), eps);
        
        registrarNuevoPaciente(pmap,p);
        sqlss.commit();
        actualizarPaciente(pmap,p);
        sqlss.commit();
        
    }

    /**
     * Registra un nuevo paciente y sus respectivas consultas (si existiesen).
     * @param pmap mapper a traves del cual se hará la operacion
     * @param p paciente a ser registrado
     */
    public static void registrarNuevoPaciente(PacienteMapper pmap, Paciente p){
        

        pmap.insertarPaciente(p);
        System.out.println("PACIENTE:" +p.getNombre()+"  insertado..."); 
        
        
        Consulta c=new Consulta(new Date(2017,12,01),"Dolor Muelas",20000);
        pmap.insertConsulta(c,p.getId(), p.getTipoId(),(int)c.getCosto());
        System.out.println("CONSULTA insertada...");
         	
    }
    
    public static void consultarPacientes(){
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        PacienteMapper pmapper=sqlss.getMapper(PacienteMapper.class);
        
        List<Paciente> pacientes=pmapper.loadPacientes();
        for( int i =0; i<pacientes.size();i++){
            System.out.println(pacientes.get(i).getNombre());
        }
    }
    
    public static void consultarPaciente(int id, String tipoId){
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        SqlSession sqlss = sessionfact.openSession();
        PacienteMapper pmapper=sqlss.getMapper(PacienteMapper.class);

        Paciente paciente=pmapper.loadPacienteByID(id,tipoId);
        System.out.println(paciente.getNombre());
        
    }
    
    /**
    * @obj Actualizar los datos básicos del paciente, con sus * respectivas consultas.
    * @pre El paciente p ya existe
    * @param pmap mapper a traves del cual se hará la operacion
    * @param p paciente a ser registrado
    */
    public static void actualizarPaciente(PacienteMapper pmap, Paciente p){
        pmap.actualizarPaciente(p);
        System.out.println("PACIENTE:" +p.getNombre()+"  Actualizado..."); 
        
        
        Consulta c=new Consulta(new Date(2017,12,02),"Dolor Muelas",20000);
        pmap.actualizarConsulta(c,p.getId(), p.getTipoId(),(int)c.getCosto());
        System.out.println("CONSULTA ACTUALIZADA PARA EL USUARIO...");
    }
    
}
