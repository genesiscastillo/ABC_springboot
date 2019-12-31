package cesar.castillo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cesar.castillo.entity.Phone;
import cesar.castillo.entity.Usuario;
import cesar.castillo.exception.BussinesException;
import cesar.castillo.vo.EstadoUsuario;

@Service
@EnableTransactionManagement
public class UsuarioDaoImpl extends AbstractDao<Usuario> {

	private static final Logger LOGGER = Logger.getLogger(UsuarioDaoImpl.class.getName());
	public UsuarioDaoImpl() {
		super(Usuario.class);
	}
	
	public Phone createPhone(Phone phone) {
    	getEntityManager().getTransaction().begin();
		getEntityManager().persist( phone );
    	getEntityManager().getTransaction().commit();
		return phone;
	}

	public Optional<Usuario> findByEmail(String email) {
		LOGGER.info("findByEmail(String email) " + email);
		Usuario usuario = null;
		try {
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
	        CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
	        Root<Usuario> root = criteriaQuery.from(Usuario.class);
	        List<Predicate> predicates = new ArrayList<>();
	        predicates.add(criteriaBuilder.equal(   root.get("email") , email));
	        
	        criteriaQuery.select(root);
	        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
	        usuario = getEntityManager().createQuery(criteriaQuery).getSingleResult();
	        
		} catch (NoResultException noResultException) {
			LOGGER.log(Level.WARNING , "noResultException email -> "+email);
		}
		return Optional.ofNullable(usuario);
	}

	public List<Phone> findAllPhoneById(String id) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Phone> criteriaQuery = criteriaBuilder.createQuery(Phone.class);
        Root<Phone> root = criteriaQuery.from(Phone.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(   root.get("id") , id));
        
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        List<Phone> phones  = getEntityManager().createQuery(criteriaQuery).getResultList();

		return phones;
	}

	public void activarUsuario(String email) throws BussinesException {
		Optional<Usuario> optional = findByEmail(email);
		Usuario usuario = optional.orElseThrow(() -> new BussinesException("Correo no encontrado"));
		usuario.setEstadoUsuario(EstadoUsuario.ACTIVO);
		update(usuario);
	}
}
