package com.practica.integracion;

import com.practica.integracion.DAO.AuthDAO;
import com.practica.integracion.DAO.GenericDAO;
import com.practica.integracion.DAO.User;
import com.practica.integracion.manager.SystemManager;
import com.practica.integracion.manager.SystemManagerException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestValidUser {

	/**
	 * RELLENAR POR EL ALUMNO
	 */

	@Mock
	private static AuthDAO mockAuthDao;
	@Mock
	private static GenericDAO mockGenericDao;

	@Test
	public void testStartRemoteSystemWithValidUserAndValidSystem() throws Exception {
		User validUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String validId = "12345"; // id valido de sistema
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
		when(mockGenericDao.getSomeData(validUser, "where id=" + validId)).thenReturn(lista);

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		// llamada al api a probar
		Collection<Object> retorno = manager.startRemoteSystem(validUser.getId(), validId);
		assertEquals(retorno.toString(), "[uno, dos]");

		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao,times(1)).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao,times(1)).getSomeData(validUser, "where id=" + validId);
	}
	@Test
	public void testStartRemoteSystemWithValidUserAndInvalidSystem() throws Exception {
		User validUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String notValidId = "99999"; // id Invalido de sistema
		when(mockGenericDao.getSomeData(validUser, "where id=" + notValidId)).thenThrow(new OperationNotSupportedException());

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		// llamada al api a probar

		assertThrows(SystemManagerException.class,
				() -> manager.startRemoteSystem(validUser.getId(), notValidId),"Excepción no lanzada");
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao,times(1)).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao,times(1)).getSomeData(validUser, "where id=" + notValidId);
	}
	@Test
	public void testStopRemoteSystemWithValidUserAndValidSystem() throws Exception {
		User validUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String validId = "12345"; // id valido de sistema
		ArrayList<Object> lista = new ArrayList<>(Arrays.asList("uno", "dos"));
		when(mockGenericDao.getSomeData(validUser, "where id=" + validId)).thenReturn(lista);

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		// llamada al api a probar
		Collection<Object> retorno = manager.stopRemoteSystem(validUser.getId(), validId);
		assertEquals(retorno.toString(), "[uno, dos]");
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao,times(1)).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao,times(1)).getSomeData(validUser, "where id=" + validId);
	}
	@Test
	public void testStopRemoteSystemWithValidUserAndInvalidSystem() throws Exception {
		User validUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String notValidId = "99999"; // id Invalido de sistema
		when(mockGenericDao.getSomeData(validUser, "where id=" + notValidId)).thenThrow(new OperationNotSupportedException());

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		// llamada al api a probar

		assertThrows(SystemManagerException.class,
				() -> manager.stopRemoteSystem(validUser.getId(), notValidId),"Excepción no lanzada");
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao,times(1)).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao,times(1)).getSomeData(validUser, "where id=" + notValidId);
	}
	@Test
	public void testAddRemoteSystemWithValidUserAndValidSystem() throws Exception {
		User validUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String validId = "12345"; // id valido de sistema
		when(mockGenericDao.updateSomeData(validUser, validId)).thenReturn(true);

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		// llamada al api a probar
		manager.addRemoteSystem(validUser.getId(), validId);
		// add es void no devuelve nada debemos comprobar
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao,times(1)).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao,times(1)).updateSomeData(validUser, validId);
	}
	@Test
	public void testAddRemoteSystemWithValidUserAndInvalidSystem() throws Exception {
		User validUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String notValidId = "99999"; // id Invalido de sistema
		when(mockGenericDao.getSomeData(validUser,  notValidId)).thenThrow(new OperationNotSupportedException());

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		// llamada al api a probar

		assertThrows(SystemManagerException.class,
				() -> manager.addRemoteSystem(validUser.getId(), notValidId),"Excepción no lanzada");
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao,times(1)).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao,times(1)).updateSomeData(validUser,  notValidId);
	}
	@Test
	public void testDeleteRemoteSystemWithValidUserAndValidSystem() throws Exception {
		User validUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String validId = "12345"; // id valido de sistema
		when(mockGenericDao.deleteSomeData(validUser, validId)).thenReturn(true);

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		// llamada al api a probar
		 manager.deleteRemoteSystem(validUser.getId(), validId);
		// delete es un void no devuelve nada
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao,times(1)).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao,times(1)).deleteSomeData(validUser, validId);
	}
	@Test
	public void testDeleteRemoteSystemWithValidUserAndInvalidSystem() throws Exception {
		User validUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(validUser.getId())).thenReturn(validUser);

		String notValidId = "99999"; // id Invalido de sistema
		when(mockGenericDao.deleteSomeData(validUser,  notValidId)).thenThrow(new OperationNotSupportedException());

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		// llamada al api a probar

		assertThrows(SystemManagerException.class,
				() -> manager.deleteRemoteSystem(validUser.getId(), notValidId),"Excepción no lanzada");
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao,times(1)).getAuthData(validUser.getId());
		ordered.verify(mockGenericDao,times(1)).deleteSomeData(validUser,  notValidId);
	}
}
