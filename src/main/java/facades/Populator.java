/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.PersonDTO;
import entities.Person;
import entities.RenameMe;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = PersonFacade.getPersonFacade(emf);
        //fe.create(new PersonDTO("Jol", 3));
        //fe.create(new PersonDTO("Pol", 4));
        //fe.create(new PersonDTO("Dol", 5));
    }
    
    public static void main(String[] args) {
        populate();
    }
}
