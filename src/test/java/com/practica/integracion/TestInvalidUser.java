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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TestInvalidUser {
	/**
	 * RELLENAR POR EL ALUMNO
	 */
	@Mock
	private static AuthDAO mockAuthDao;
	@Mock
	private static GenericDAO mockGenericDao;

	@Test
	public void testStartRemoteSystemWithInValidUserAndValidSystem() throws Exception {
		User invalidUser = new User("666","Isabel","Díaz","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);

		String validId = "12345"; // id valido de sistema
		when(mockGenericDao.getSomeData(invalidUser, "where id=" + validId)).thenThrow(
				new OperationNotSupportedException()
		);

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		// llamada al api a probar

		assertThrows(SystemManagerException.class,()-> manager.startRemoteSystem(invalidUser.getId(),validId));

		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao,times(1)).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao,times(1)).getSomeData(invalidUser, "where id=" + validId);
	}

	@Test
	public void testStopRemoteSystemWithInValidUserAndValidSystem() throws Exception {
		User invalidUser = new User("666","Isabel","Díaz","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);

		String validId = "12345"; // id valido de sistema
		when(mockGenericDao.getSomeData(invalidUser, "where id=" + validId)).thenThrow(
				new OperationNotSupportedException()
		);

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		// llamada al api a probar

		assertThrows(SystemManagerException.class,()-> manager.stopRemoteSystem(invalidUser.getId(),validId));

		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao,times(1)).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao,times(1)).getSomeData(invalidUser, "where id=" + validId);
	}
	@Test
	public void testAddRemoteSystemWithInValidUserAndValidSystem() throws Exception {
		User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);

		String validId = "12345"; // id valido de sistema
		when(mockGenericDao.updateSomeData(invalidUser, validId)).thenThrow(
				new OperationNotSupportedException()
		);

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		// llamada al api a probar
		assertThrows(SystemManagerException.class,()->manager.addRemoteSystem(invalidUser.getId(), validId));
		// add es void no devuelve nada debemos comprobar
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao,times(1)).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao,times(1)).updateSomeData(invalidUser, validId);
	}
	@Test
	public void testDeleteRemoteSystemWithInValidUserAndValidSystem() throws Exception {
		User invalidUser = new User("1","Ana","Lopez","Madrid", new ArrayList<Object>(Arrays.asList(1, 2)));
		when(mockAuthDao.getAuthData(invalidUser.getId())).thenReturn(null);

		String validId = "12345"; // id valido de sistema
		when(mockGenericDao.deleteSomeData(invalidUser, validId)).thenThrow(
				new OperationNotSupportedException()
		);

		// primero debe ejecutarse la llamada al dao de autenticación
		// despues el de  acceso a datos del sistema (la validaciones del orden en cada prueba)
		InOrder ordered = inOrder(mockAuthDao, mockGenericDao);

		// instanciamos el manager con los mock creados
		SystemManager manager = new SystemManager(mockAuthDao, mockGenericDao);

		// llamada al api a probar
		assertThrows(SystemManagerException.class, () -> manager.deleteRemoteSystem(invalidUser.getId(), validId));

		// delete es un void no devuelve nada
		// vemos si se ejecutan las llamadas a los dao, y en el orden correcto
		ordered.verify(mockAuthDao,times(1)).getAuthData(invalidUser.getId());
		ordered.verify(mockGenericDao,times(1)).deleteSomeData(invalidUser, validId);
	}


}
