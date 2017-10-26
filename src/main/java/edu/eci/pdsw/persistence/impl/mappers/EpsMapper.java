/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.persistence.impl.mappers;

import edu.eci.pdsw.samples.entities.Eps;
import java.util.List;

/**
 *
 * @author Nicolás
 */
public interface EpsMapper {
        
    public List<Eps> loadAllEPS();
}
