package empleados;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

public class EjercicioEmpleados {

	public static void main(String[] args) {
		llenarBase();
		List<Empleado> empleadosSalario = obtenerEmpleadosSalarioSuperiorA1000();
		if (empleadosSalario != null) {
			System.out.println("Empleados con salario superior a 1000:");
			for (Empleado empleado : empleadosSalario) {
				System.out.println("ID: " + empleado.getIdEmp());
				System.out.println("Apellido: " + empleado.getApellido());
				System.out.println("Salario: " + empleado.getSalario());
				System.out.println("Oficio: " + empleado.getOficio());
				System.out.println("Fecha alta: " + empleado.getFechaAlta());
				System.out.println("Comisión: " + empleado.getComision());
				System.out.println("Departamento: " + empleado.getIdEmp());
				System.out.println("--------------------------------------");
			}
		} else {
			System.out.println("No se pudo obtener la lista de empleados.");
		}
		Scanner scanner = new Scanner(System.in);

		System.out.print("Ingrese el año: ");
		int año = scanner.nextInt();

		List<Empleado> empleadosYear = obtenerEmpleadosPorAño(año);

		if (empleadosYear != null) {
			System.out.println("Empleados del año " + año + ":");
			for (Empleado empleado : empleadosYear) {
				System.out.println("ID: " + empleado.getIdEmp());
				System.out.println("Apellido: " + empleado.getApellido());
				System.out.println("Salario: " + empleado.getSalario());
				System.out.println("Oficio: " + empleado.getOficio());
				System.out.println("Fecha alta: " + empleado.getFechaAlta());
				System.out.println("Comisión: " + empleado.getComision());
				System.out.println("Departamento: " + empleado.getIdEmp());
				System.out.println("--------------------------------------");
			}
		} else {
			System.out.println("No se pudo obtener la lista de empleados.");
		}
		System.out.print("Ingrese el ID del departamento: ");
		int idDepartamento = scanner.nextInt();
		quitarEmpleadosDepartamento(idDepartamento);
		System.out.print("Ingrese el ID del departamento: ");
		idDepartamento = scanner.nextInt();
		insertarEmpleado();
		System.out.print("Ingrese el ID del departamento: ");
		idDepartamento = scanner.nextInt();
		modificarDepartamento(idDepartamento);
		System.out.print("Ingrese el ID del empleado: ");
		int idEmpleado = scanner.nextInt();
		imprimirEmpleadoYDepartamento(idEmpleado);
		System.out.print("Ingrese el ID del empleado: ");
		idEmpleado = scanner.nextInt();
		borrarEmpleado(idEmpleado);
		List<Empleado> empleadosDep = empleadosDepartamento(idDepartamento);
		System.out.print("Ingrese el ID del departamento: ");
		idDepartamento = scanner.nextInt();
		borrarDepartamento(idDepartamento);
		TraspasoEmpleados();

		if (empleadosDep != null) {
			System.out.println("Empleados del departamento con ID " + idDepartamento + ":");
			for (Empleado empleado : empleadosDep) {
				System.out.println("ID: " + empleado.getIdEmp());
				System.out.println("Apellido: " + empleado.getApellido());
				System.out.println("Salario: " + empleado.getSalario());
				System.out.println("Oficio: " + empleado.getOficio());
				System.out.println("Fecha alta: " + empleado.getFechaAlta());
				System.out.println("Comisión: " + empleado.getComision());
				System.out.println("Departamento: " + empleado.getIdEmp());
				System.out.println("--------------------------------------");
			}
		} else {
			System.out.println("No se pudo obtener la lista de empleados.");
		}
		List<Departamento> departamentos = departamentosConSalariosAltos();

		if (departamentos != null) {
			System.out.println("Departamentos con salarios altos:");
			for (Departamento departamento : departamentos) {
				System.out.println("ID: " + departamento.getIdDep());
				System.out.println("Nombre: " + departamento.getNombre());
				System.out.println("Localidad: " + departamento.getLocalidad());
				System.out.println("--------------------------------------");
			}
		} else {
			System.out.println("No se pudo obtener la lista de departamentos.");
		}
		
	}

	public static void llenarBase() {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			Random random = new Random();

			// Generar y guardar empleados
			for (int i = 1; i <= 10; i++) {
				Empleado empleado = new Empleado("Apellido" + i, "Oficio" + i, new Date(), 1000L * i, 100L * i,
						random.nextInt(5) + 1);
				session.save(empleado);
			}

			// Generar y guardar departamentos
			for (int i = 1; i <= 5; i++) {
				Departamento departamento = new Departamento("Departamento" + i, "Localidad" + i);
				session.save(departamento);
			}

			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public static void modificarDepartamento(int idDepartamento) {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			Departamento departamento = session.get(Departamento.class, idDepartamento);

			if (departamento == null) {
				System.out.println("El departamento con el ID proporcionado no existe.");
				return;
			}

			Scanner scanner = new Scanner(System.in);

			System.out.println("¿Qué campo desea modificar?");
			System.out.println("1. Nombre");
			System.out.println("2. Localidad");
			System.out.print("Ingrese el número correspondiente: ");
			int opcion = scanner.nextInt();
			scanner.nextLine(); // Consumir el salto de línea pendiente

			String nuevoValor;

			switch (opcion) {
			case 1:
				System.out.print("Ingrese el nuevo nombre: ");
				nuevoValor = scanner.nextLine();
				departamento.setNombre(nuevoValor);
				break;
			case 2:
				System.out.print("Ingrese la nueva localidad: ");
				nuevoValor = scanner.nextLine();
				departamento.setLocalidad(nuevoValor);
				break;
			default:
				System.out.println("Opción inválida. No se realizaron modificaciones.");
				return;
			}

			session.update(departamento);
			tx.commit();

			System.out.println("Departamento modificado correctamente.");
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public static int insertarEmpleado() {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		int nuevoId = -1;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			Scanner scanner = new Scanner(System.in);

			System.out.print("Ingrese el apellido del empleado: ");
			String apellido = scanner.nextLine();

			System.out.print("Ingrese el oficio del empleado: ");
			String oficio = scanner.nextLine();

			System.out.print("Ingrese la fecha de alta del empleado (dd-MM-yyyy): ");
			String fechaAltaStr = scanner.nextLine();
			Date fechaAlta = obtenerFecha(fechaAltaStr);

			System.out.print("Ingrese el salario del empleado: ");
			Long salario = scanner.nextLong();

			System.out.print("Ingrese la comisión del empleado: ");
			Long comision = scanner.nextLong();

			System.out.print("Ingrese el ID del departamento del empleado: ");
			Integer idDepartamento = scanner.nextInt();

			Empleado empleado = new Empleado(apellido, oficio, fechaAlta, salario, comision, idDepartamento);
			session.save(empleado);
			nuevoId = empleado.getIdEmp();

			tx.commit();

			System.out.println("Empleado insertado correctamente. ID del empleado: " + nuevoId);
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return nuevoId;
	}

	private static Date obtenerFecha(String fechaStr) {
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
		Date fecha = null;

		try {
			fecha = formatoFecha.parse(fechaStr);
		} catch (ParseException e) {
			System.out.println("Error al convertir la fecha. Se utilizará la fecha actual.");
			fecha = new Date();
		}

		return fecha;
	}

	public static void imprimirEmpleadoYDepartamento(int idEmpleado) {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {
		    session = sessionFactory.openSession();
		    tx = session.beginTransaction();

		    // Consulta para obtener el empleado y el departamento en una sola consulta
		    String sql = "SELECT e.*, d.* FROM Empleado e INNER JOIN Departamento d ON e.idDep = d.idDep WHERE e.idEmp = :idEmpleado";

		    NativeQuery<Object[]> query = session.createNativeQuery(sql);
		    query.setParameter("idEmpleado", idEmpleado);
		    query.addEntity("e", Empleado.class);
		    query.addEntity("d", Departamento.class);

		    Object[] result = query.uniqueResult();

		    if (result == null) {
		        System.out.println("El empleado con el ID proporcionado no existe.");
		        return;
		    }

		    Empleado empleado = (Empleado) result[0];
		    Departamento departamento = (Departamento) result[1];

		    System.out.println("Detalles del empleado:");
		    System.out.println("ID: " + empleado.getIdEmp());
		    System.out.println("Apellido: " + empleado.getApellido());
		    System.out.println("Oficio: " + empleado.getOficio());
		    System.out.println("Fecha de alta: " + empleado.getFechaAlta());
		    System.out.println("Salario: " + empleado.getSalario());
		    System.out.println("Comisión: " + empleado.getComision());
		    System.out.println("Departamento:");
		    System.out.println("  ID: " + departamento.getIdDep());
		    System.out.println("  Nombre: " + departamento.getNombre());
		    System.out.println("  Localidad: " + departamento.getLocalidad());

		    tx.commit();
		} catch (Exception e) {
		    if (tx != null) {
		        tx.rollback();
		    }
		    e.printStackTrace();
		} finally {
		    if (session != null) {
		        session.close();
		    }
		}
	}

	public static void borrarEmpleado(int idEmpleado) {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			Empleado empleado = session.get(Empleado.class, idEmpleado);

			if (empleado == null) {
				System.out.println("El empleado con el ID proporcionado no existe.");
				return;
			}

			session.delete(empleado);
			tx.commit();

			System.out.println("Empleado eliminado correctamente.");
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public static void borrarDepartamento(int idDep) {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			Departamento departamento = session.get(Departamento.class, idDep);

			if (departamento == null) {
				System.out.println("El departamento con el ID proporcionado no existe.");
				return;
			}

			// Eliminar todos los empleados asociados al departamento
			Query<Empleado> query = session.createQuery("FROM Empleado WHERE idDep = :idDep", Empleado.class);
			query.setParameter("idDep", idDep);
			List<Empleado> empleados = query.list();

			for (Empleado empleado : empleados) {
				session.delete(empleado);
			}

			// Eliminar el departamento
			session.delete(departamento);
			tx.commit();

			System.out.println("Departamento eliminado correctamente junto con sus empleados.");
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public static void TraspasoEmpleados() {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			// Solicitar al usuario los identificadores de los departamentos
			Scanner scanner = new Scanner(System.in);
			System.out.print("Ingrese el ID del departamento origen: ");
			int idDepartamentoOrigen = scanner.nextInt();
			System.out.print("Ingrese el ID del departamento destino: ");
			int idDepartamentoDestino = scanner.nextInt();

			// Obtener los objetos Departamento correspondientes a los identificadores
			// proporcionados
			Departamento departamentoOrigen = session.get(Departamento.class, idDepartamentoOrigen);
			Departamento departamentoDestino = session.get(Departamento.class, idDepartamentoDestino);

			if (departamentoOrigen == null || departamentoDestino == null) {
				System.out.println("Uno o ambos departamentos con los ID proporcionados no existen.");
				return;
			}

			// Obtener la lista de empleados del departamento origen
			Query<Empleado> query = session.createQuery("FROM Empleado WHERE idDep = :idDep", Empleado.class);
			query.setParameter("idDep", idDepartamentoOrigen);
			List<Empleado> empleados = query.list();

			if (empleados.isEmpty()) {
				System.out.println("El departamento origen no tiene empleados.");
				return;
			}

			// Actualizar el departamento en los objetos de tipo Empleado
			for (Empleado empleado : empleados) {
				empleado.setIdDep(idDepartamentoDestino);
				session.update(empleado);
			}
			tx.commit();

			System.out.println("Traspaso de empleados realizado correctamente.");
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public static List<Empleado> obtenerEmpleadosSalarioSuperiorA1000() {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			// Realizar la consulta para obtener los empleados con salario superior a 1000
			String hql = "FROM Empleado WHERE salario > 1000";
			Query<Empleado> query = session.createQuery(hql, Empleado.class);
			List<Empleado> empleados = query.list();

			tx.commit();

			return empleados;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return null;
	}

	public static List<Empleado> obtenerEmpleadosPorAño(int año) {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			// Realizar la consulta para obtener los empleados del año especificado
			String hql = "FROM Empleado WHERE YEAR(fechaAlta) = :year";
			Query<Empleado> query = session.createQuery(hql, Empleado.class);
			query.setParameter("year", año);
			List<Empleado> empleados = query.list();

			tx.commit();

			return empleados;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return null;
	}

	public static List<Empleado> empleadosDepartamento(int idDep) {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			// Realizar la consulta para obtener los empleados del departamento específico
			String hql = "FROM Empleado WHERE departamento.idDep = :idDepartamento";
			Query<Empleado> query = session.createQuery(hql, Empleado.class);
			query.setParameter("idDepartamento", idDep);
			List<Empleado> empleados = query.list();

			tx.commit();

			return empleados;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return null;
	}

	public static List<Departamento> departamentosConSalariosAltos() {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			// Realizar la consulta para obtener los departamentos con salarios altos
			String hql = "SELECT DISTINCT d FROM Departamento d JOIN d.empleados e WHERE e.salario > 2000";
			Query<Departamento> query = session.createQuery(hql, Departamento.class);
			List<Departamento> departamentos = query.list();

			tx.commit();

			return departamentos;
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return null;
	}
	public static void quitarEmpleadosDepartamento(int idDep) {
	    SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
	    Session session = null;
	    Transaction tx = null;

	    try {
	        session = sessionFactory.openSession();
	        tx = session.beginTransaction();

	        // Buscar los empleados del departamento utilizando el ID de departamento en la clase Empleado
	        String hql = "FROM Empleado WHERE idDep = :idDepartamento";
	        Query<Empleado> query = session.createQuery(hql, Empleado.class);
	        query.setParameter("idDepartamento", idDep);
	        List<Empleado> empleados = query.list();

	        if (empleados.isEmpty()) {
	            System.out.println("El departamento no tiene empleados.");
	            return;
	        }

	        // Mostrar los empleados del departamento
	        System.out.println("Empleados del departamento con ID " + idDep + ":");
	        for (Empleado empleado : empleados) {
	        	System.out.println("ID: " + empleado.getIdEmp());
				System.out.println("Apellido: " + empleado.getApellido());
				System.out.println("Salario: " + empleado.getSalario());
				System.out.println("Oficio: " + empleado.getOficio());
				System.out.println("Comisión: " + empleado.getComision());
				System.out.println("Departamento: " + empleado.getIdEmp());
				System.out.println("--------------------------------------");
	        }

	        // Preguntar al usuario qué empleados desea desasociar del departamento
	        Scanner scanner = new Scanner(System.in);
	        System.out.println("Ingrese el ID del empleado que desea desasociar (0 para salir):");
	        int idEmpleado = scanner.nextInt();

	        while (idEmpleado != 0) {
	            // Buscar el empleado por ID
	            Empleado empleado = session.get(Empleado.class, idEmpleado);

	            if (empleado != null) {
	                // Desasociar el empleado del departamento actualizando el campo correspondiente a null
	                empleado.setIdDep(null);
	                session.update(empleado);
	                System.out.println("Empleado con ID " + idEmpleado + " desasociado del departamento.");
	            } else {
	                System.out.println("El empleado con el ID proporcionado no existe.");
	            }

	            System.out.println("Ingrese el ID del empleado que desea desasociar (0 para salir):");
	            idEmpleado = scanner.nextInt();
	        }

	        tx.commit();
	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	        e.printStackTrace();
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }
	}
}
