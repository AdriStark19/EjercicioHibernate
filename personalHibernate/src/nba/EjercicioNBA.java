package nba;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class EjercicioNBA {

	public static void main(String[] args) {

		List<Jugadores> jugadoresEspanoles = obtenerJugadoresEspanoles();
		List<Jugadores> jugadoresPesoPosicion = obtenerJugadoresPesoPosicion();
		List<Jugadores> jugadoresLakersConY = obtenerJugadoresLakersConY();
		List<Jugadores> jugadores9293 = obtenerJugadores9293();
		List<Equipos> equiposEsteCentralyOeste = obtenerEquiposEsteCentralyOeste();
		List<Partidos> partidos40 = obtenerPartidos40Puntos();
		// Imprimir el número de jugadores españoles
		int numJugadoresEspanoles = jugadoresEspanoles.size();
		System.out.println("Número de jugadores españoles: " + numJugadoresEspanoles);

		// Imprimir los nombres de los jugadores españoles
		System.out.println("Nombres de los jugadores españoles:");

		try {
			for (Jugadores jugador : jugadoresEspanoles) {
				System.out.println(jugador.getNombre());
			}
			System.out.println();
		} catch (Exception ex) {
			System.out.println("No hay jugadores españoles\n");
		}
		System.out.println("Jugadores que pesan más de 220 libras y juegan de base o escolta ('G'):");
		try {

			for (Jugadores jugador : jugadoresPesoPosicion) {
				System.out.println(jugador.getNombre());
			}
			System.out.println();
		} catch (Exception ex) {
			System.out.println("No hay jugadores de más de 220 libras en esa posición\n");
		}

		// Imprimir la lista de jugadores
		System.out.println("Jugadores de los Lakers con una 'y' en su nombre:");
		try {
			for (Jugadores jugador : jugadoresLakersConY) {
				System.out.println(jugador.getNombre());
			}
			System.out.println();
		} catch (Exception ex) {
			System.out.println("No hay jugadores de los Lakers con una 'y' en su nombre\n");
		}
		System.out.println("La diferencia de puntos entre Kobe y Pau en 07/08 fue: "
				+ Double.toString(obtenerDiferenciaPuntosPorPartido()));
		System.out.println("Jugadores de la temporada 92-93:");
		try {
			for (Jugadores jugador : jugadores9293) {
				System.out.println(jugador.getNombre());
			}
			System.out.println();
		} catch (Exception ex) {
			System.out.println("No hay jugadores o registros de la temporada\n");
		}
		System.out.println("Equipos Este central y equipos Oeste:");
		try {
			for (Equipos equipo : equiposEsteCentralyOeste) {
				System.out.println(equipo.getNombre() + " " + equipo.getConferencia() + " " + equipo.getDivision());
			}
			System.out.println();
		} catch (Exception ex) {
			System.out.println("No hay equipos\n");
		}
		System.out.println("Partidos donde gana el local por más de 40 puntos:");
		try {
			for (Partidos partido : partidos40) {
				System.out.println(partido.getEquiposByEquipoLocal() + " " + partido.getPuntosLocal() + " "
						+ partido.getEquiposByEquipoVisitante() + " " + partido.getPuntosVisitante());
			}
			System.out.println("Número de partidos: " + partidos40.size());
		} catch (Exception ex) {
			System.out.println("No hay partidos\n");
		}
		Float[] picos = obtenerMaxyMin();
		System.out.println("El máximo es: " + picos[0] + " y el mínimo es: " + picos[1] + "\n");
		int[] datosBucks = obtenerDatosBucks();
		System.out.println("La media local de Bucks es: " + datosBucks[0] + " y la visitante: " + datosBucks[1]
				+ " la diferencia es (local-visitante): " + (datosBucks[0] - datosBucks[1]) + "\n");
		try {
			// Llamar al método obtenerDatosTapones
			LinkedHashMap<String, float[]> datosTapones = obtenerDatosTapones();

			// Imprimir el mapa de datosTapones
			for (Map.Entry<String, float[]> entry : datosTapones.entrySet()) {
				String temporada = entry.getKey();
				float[] valores = entry.getValue();
				System.out.println("Temporada '" + temporada + "' datos tapones: ");
				System.out.println("Máximo: " + valores[0]);
				System.out.println("Mínimo: " + valores[1]);
				System.out.println("Media: " + valores[2]);
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			List<Partidos> partidosPerdidos = obtenerPartidosPerdidosLakers();
			int numPartidos = partidosPerdidos.size();

			System.out.println("Resultados de los partidos en los que los Lakers han perdido:");
			System.out.println("Resultados de los partidos en los que los Lakers han perdido:");
			for (Partidos partido : partidosPerdidos) {
			    String equipoLocal = partido.getEquiposByEquipoLocal().getNombre();
			    String equipoVisitante = partido.getEquiposByEquipoVisitante().getNombre();

			    // Determinar el equipo perdedor
			    String equipoPerdedor = (partido.getPuntosLocal() > partido.getPuntosVisitante())
			            ? equipoVisitante
			            : equipoLocal;

			    // Determinar el equipo ganador
			    String equipoGanador = (partido.getPuntosLocal() > partido.getPuntosVisitante())
			            ? equipoLocal
			            : equipoVisitante;

			    System.out.println("Equipo ganador: " + equipoGanador);
			    System.out.println("Equipo perdedor: " + equipoPerdedor);
			    System.out.println("Resultado: " + partido.getPuntosLocal() + " - " + partido.getPuntosVisitante());
			    System.out.println();
			}

			System.out.println("Número de partidos perdidos por los Lakers: " + numPartidos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
	        List<Object[]> datosJugadores = obtenerDatosJugadoresTemporada();

	        System.out.println("Datos de los jugadores en la temporada 00/01:");
	        for (Object[] datos : datosJugadores) {
	            String nombre = (String) datos[0];
	            float puntosPorPartido = (float) datos[1];
	            float rebotesPorPartido = (float) datos[2];

	            System.out.println("Nombre: " + nombre);
	            System.out.println("Puntos por partido: " + puntosPorPartido);
	            System.out.println("Rebotes por partido: " + rebotesPorPartido);
	            System.out.println();
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	
		/* try {
		 	crearJugadorEstadisticas();
		}catch(Exception ex) {
			System.out.println("Error al ejecutar");
		}
		 */
		
	}

	public static List<Jugadores> obtenerJugadoresEspanoles() {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		List<Jugadores> jugadoresEspanoles = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			String hql = "FROM Jugadores WHERE procedencia = 'Spain'";
			Query<Jugadores> query = session.createQuery(hql, Jugadores.class);
			jugadoresEspanoles = query.list();

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

		return jugadoresEspanoles;
	}

	public static List<Jugadores> obtenerJugadoresPesoPosicion() {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		List<Jugadores> jugadores = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			String hql = "FROM Jugadores WHERE peso > 220 AND (posicion = 'G' OR posicion = 'SG')";
			Query<Jugadores> query = session.createQuery(hql, Jugadores.class);
			jugadores = query.list();

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

		return jugadores;
	}

	public static List<Jugadores> obtenerJugadoresLakersConY() {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		List<Jugadores> jugadores = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			String hql = "FROM Jugadores WHERE nombre_equipo = 'Lakers' AND nombre LIKE '%y%'";
			Query<Jugadores> query = session.createQuery(hql, Jugadores.class);
			jugadores = query.list();

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

		return jugadores;
	}

	public static double obtenerDiferenciaPuntosPorPartido() {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		float diferencia = 0.0f;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			String sql = "SELECT (puntos_por_partido) " + "FROM estadisticas " + "WHERE temporada = '07/08' "
					+ "AND jugador IN (SELECT codigo FROM jugadores WHERE nombre = 'Kobe Bryant' OR nombre = 'Pau Gasol')";

			Query<Float> query = session.createSQLQuery(sql);
			List<Float> resultados = query.list();

			if (resultados.size() == 2) {
				diferencia = resultados.get(0) - resultados.get(1);
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

		return diferencia;
	}

	public static List<Jugadores> obtenerJugadores9293() {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		List<Jugadores> jugadores = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			String sql = "SELECT * FROM jugadores INNER JOIN estadisticas ON estadisticas.jugador = jugadores.codigo WHERE estadisticas.temporada = '92/93'";
			Query<Jugadores> query = session.createSQLQuery(sql).addEntity(Jugadores.class);
			jugadores = query.list();
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

		return jugadores;
	}

	private static List<Equipos> obtenerEquiposEsteCentralyOeste() {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		List<Equipos> equipos = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			String sql = "SELECT * FROM equipos WHERE conferencia = 'West' OR (conferencia = 'East' AND division = 'central')";
			Query<Equipos> query = session.createSQLQuery(sql).addEntity(Equipos.class);
			equipos = query.list();
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
		return equipos;
	}

	private static List<Partidos> obtenerPartidos40Puntos() {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		List<Partidos> partidos = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			String sql = "SELECT * FROM partidos WHERE (puntos_local - puntos_visitante) > 40";
			Query<Partidos> query = session.createSQLQuery(sql).addEntity(sql, Partidos.class);
			partidos = query.getResultList();
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
		return partidos;
	}

	public static Float[] obtenerMaxyMin() {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		Float[] maxMin = new Float[2];

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			String sql = "SELECT MAX(puntos_por_partido), MIN(puntos_por_partido) FROM estadisticas";
			Query<Object[]> query = session.createSQLQuery(sql);
			Object[] result = query.uniqueResult();
			maxMin[0] = (Float) result[0];
			maxMin[1] = (Float) result[1];
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

		return maxMin;
	}

	public static int[] obtenerDatosBucks() {
	    SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
	    Session session = null;
	    Transaction tx = null;
	    int[] datosBucks = new int[2];

	    try {
	        session = sessionFactory.openSession();
	        tx = session.beginTransaction();
	        // Consulta para obtener la media de puntos como local
	        String sqlLocal = "SELECT AVG(puntos_local) FROM partidos WHERE equipo_local = 'Bucks'";
	        Query<Integer> queryLocal = session.createSQLQuery(sqlLocal);
	        Integer mediaLocalLong = queryLocal.uniqueResult();
	        int mediaLocal = mediaLocalLong != null ? mediaLocalLong.intValue() : 0;

	        // Consulta para obtener la media de puntos como visitante
	        String sqlVisitante = "SELECT AVG(puntos_visitante) FROM partidos WHERE equipo_visitante = 'Bucks'";
	        Query<Integer> queryVisitante = session.createSQLQuery(sqlVisitante);
	        Integer mediaVisitanteLong = queryVisitante.uniqueResult();
	        int mediaVisitante = mediaVisitanteLong != null ? mediaVisitanteLong.intValue() : 0;

	        datosBucks[0] = mediaLocal;
	        datosBucks[1] = mediaVisitante;
	        tx.commit();

	        // Hacer lo que necesites con los datos obtenidos

	    } catch (Exception e) {
	        if (tx != null) {
	            tx.rollback();
	        }
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }
	    return datosBucks;
	}


	public static LinkedHashMap<String, float[]> obtenerDatosTapones() {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		Transaction tx = null;
		LinkedHashMap<String, float[]> datosTapones = new LinkedHashMap<>();

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			// Consulta para obtener el máximo, mínimo y media de tapones por partido por
			// cada temporada
			String sql = "SELECT temporada, MAX(tapones_por_partido) AS max_tapones, MIN(tapones_por_partido) AS min_tapones, AVG(tapones_por_partido) AS media_tapones "
					+ "FROM estadisticas " + "GROUP BY temporada " + "ORDER BY media_tapones DESC";
			Query<?> query = session.createSQLQuery(sql);
			List<Object[]> resultados = (List<Object[]>) query.getResultList();

			// Obtener los valores de máximo, mínimo y media de tapones por cada temporada y
			// agregarlos al mapa
			for (Object[] fila : resultados) {
				String temporada = (String) fila[0];
				float maxTapones = ((Number) fila[1]).floatValue();
				float minTapones = ((Number) fila[2]).floatValue();
				float mediaTapones = ((Number) fila[3]).floatValue();

				float[] valores = { maxTapones, minTapones, mediaTapones };
				datosTapones.put(temporada, valores);
			}

			tx.commit();

			// Hacer lo que necesites con los datos obtenidos

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			datosTapones = new LinkedHashMap<>(); // Devolver un mapa vacío en caso de error
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return datosTapones;
	}

	public static List<Partidos> obtenerPartidosPerdidosLakers() {
		SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
		Session session = null;
		List<Partidos> partidosPerdidos = null;

		try {
			session = sessionFactory.openSession();
			String sql = "SELECT * FROM partidos WHERE (equipo_local = 'Lakers' AND puntos_local < puntos_visitante) OR "
					+ "(equipo_visitante = 'Lakers' AND puntos_visitante < puntos_local)";
			Query<Partidos> query = session.createSQLQuery(sql).addEntity(Partidos.class);
			partidosPerdidos = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return partidosPerdidos;
	}
	public static List<Object[]> obtenerDatosJugadoresTemporada() {
	    SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
	    Session session = null;
	    List<Object[]> datosJugadores = null;

	    try {
	        session = sessionFactory.openSession();
	        String sql = "SELECT j.nombre, e.puntos_por_partido, e.rebotes_por_partido " +
	                     "FROM jugadores j " +
	                     "INNER JOIN estadisticas e ON e.jugador = j.codigo " +
	                     "WHERE e.temporada = '00/01'";
	        Query<Object[]> query = session.createSQLQuery(sql);
	        datosJugadores = query.getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (session != null) {
	            session.close();
	        }
	    }

	    return datosJugadores;
	}
	public static void crearJugadorEstadisticas() {
	    try {
	        SessionFactory sessionFactory = UtilesHibernate.getSessionFactory();
	        Session session = sessionFactory.openSession();
	        Transaction tx = session.beginTransaction();
	        
	        // Crear jugador
	        Jugadores jugador = new Jugadores();
	        jugador.setCodigo(1925);
	        jugador.setNombre("Adrian");
	        jugador.setProcedencia("Spain");
	        jugador.setAltura("1'81");
	        jugador.setPeso(85);
	        jugador.setPosicion("SG");

	        // Crear estadísticas de temporada 00/01
	        Estadisticas estadisticas1 = new Estadisticas();
	        estadisticas1.setId(new EstadisticasId("22/23", jugador.getCodigo()));
	        estadisticas1.setJugadores(jugador);
	        estadisticas1.setPuntosPorPartido(65.5f);
	        estadisticas1.setAsistenciasPorPartido(5.2f);
	        estadisticas1.setTaponesPorPartido(1.8f);
	        estadisticas1.setRebotesPorPartido(7.3f);

	        // Crear estadísticas de temporada 01/02
	        Estadisticas estadisticas2 = new Estadisticas();
	        estadisticas2.setId(new EstadisticasId("21/22", jugador.getCodigo()));
	        estadisticas2.setJugadores(jugador);
	        estadisticas2.setPuntosPorPartido(48.7f);
	        estadisticas2.setAsistenciasPorPartido(4.9f);
	        estadisticas2.setTaponesPorPartido(1.2f);
	        estadisticas2.setRebotesPorPartido(6.8f);

	        // Asignar estadísticas al jugador
	        Set<Estadisticas> estadisticasSet = new HashSet<>();
	        estadisticasSet.add(estadisticas1);
	        estadisticasSet.add(estadisticas2);
	        jugador.setEstadisticases(estadisticasSet);

	        // Guardar jugador y estadísticas
	        session.save(jugador);
	        session.save(estadisticas1);
	        session.save(estadisticas2);
	        session.getTransaction().commit();
	        
	        session.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
