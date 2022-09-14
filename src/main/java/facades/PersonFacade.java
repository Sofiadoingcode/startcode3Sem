package facades;

import dtos.PersonDTO;
import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;

//import errorhandling.PersonNotFoundException;
import utils.EMF_Creator;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private PersonFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public PersonDTO create(PersonDTO rm){
        Person rme = new Person(rm.getId(), rm.getName(), rm.getAge());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(rme);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(rme);
    }

    public PersonDTO update(PersonDTO rm){
        EntityManager em = getEntityManager();
        Person fromDB = em.find(Person.class, rm.getId());
        if(fromDB == null) {
            throw new EntityNotFoundException("No person with this ID exists");
        } else {
            Person rme = new Person(rm.getId(), rm.getName(), rm.getAge());
            try {
                em.getTransaction().begin();
                em.merge(rme);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return new PersonDTO(rme);

        }

    }

    public PersonDTO getById(long id) { //throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        Person rm = em.find(Person.class, id);
//        if (rm == null)
//            throw new PersonNotFoundException("The Person entity with ID: "+id+" Was not found");
        return new PersonDTO(rm);
    }
    
    //TODO Remove/Change this before use
    public long getPersonCount(){
        EntityManager em = getEntityManager();
        try{
            long PersonCount = (long)em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
            return PersonCount;
        }finally{  
            em.close();
        }
    }
    
    public List<PersonDTO> getAll(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> rms = query.getResultList();
        return PersonDTO.getDtos(rms);
    }
    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade fe = getPersonFacade(emf);
       // fe.getAll().forEach(dto->System.out.println(dto));
        fe.update(new PersonDTO(2, "Hentry", 8));
    }

}
